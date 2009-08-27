
#include <vector>
#include <queue>
#include "Game.h"
#include "shared/Unit.h"
#include "shared/Message.h"
using namespace std;

Game::Game()
{
	myMaster = 1;
	myConnection = new Connection();
	world = new World();
	myUnitDefs.load("data/units.ydu");
}

Game::Game(char *server)
{
	myMaster = 0;
	myConnection = new Connection(server);
	world = new World();
	myUnitDefs.load("data/units.ydu");
}

Game::~Game()
{
	while (myUnits.size())
	{
		delete myUnits[myUnits.size()-1];
		myUnits.pop_back();
	}
	while (myPlayers.size())
	{
		if (myPlayers[myPlayers.size()-1])
			delete myPlayers[myPlayers.size()-1];
		myPlayers.pop_back();
	}
	delete world;
}

void	Game::action()
{
	/* perform game actions, move units, etc... */	
}

/* Returns player ID to server */
int	Game::login(string name)
{
	if (name.length() > 20)
		name = name.substr(0, 20);
	if (myMaster)
	{
		if (!validName(name))
		{
			char buf[20];
			for (int i = 0; i < 10000; i++)
			{
				sprintf(buf, "%d", i);
				name = "Player";
				name += buf;
				if (validName(name))
					break;
			}
			
		}
		myPlayers.push_back(new Player(name));
		return myPlayers.size() - 1;
	}
	else
	{
		Message msg;
		message_t *m = msg.getBufferMessage();
		m->type = MSG_LOGIN_REQ;
		strcpy(m->login_name, name.c_str());
		myConnection->sendMessage(&msg);
		return -1;
	}
}

void	Game::addPlayer(int id, string name)
{
	while (myPlayers.size() <= id)
		myPlayers.push_back(NULL);
	if (!myPlayers[id])
		myPlayers[id] = new Player(name);
}

int	Game::logout(int playerid)
{
	if (myMaster)
	{
		if (playerid < myPlayers.size() && myPlayers[playerid])
		{
			delete myPlayers[playerid];
			myPlayers[playerid] = NULL;
		}
	}
	else
	{
		Message msg;
		message_t *m = msg.getBufferMessage();
		m->type = MSG_DEPART;
		m->login_playerid = playerid;
		myConnection->sendMessage(&msg);
		return -1;
	}
}

/* Returns zero if name is invalid */
int	Game::validName(const string & name)
{
	for (int i = 0; i < myPlayers.size(); i++)
		if (myPlayers[i] && myPlayers[i]->name() == name)
			return 0;
	return 1;
}

int	Game::createUnit(int owner, int type, int x, int y, int id, int moveable)
{
	if (owner >= myPlayers.size() || !myPlayers[owner])
		return -1;
	Player *p = player(owner);
	ResourceArray *cost = &myUnitDefs.getUnitDef(type)->cost;
	for (int i = 0; i < RES_MAX; i++)
		if (cost->resource(i) > p->resources().resource(i))
			return -2;	/* not enough resources */
	if (!moveable)
	{
		int gx = x / GRID_SIZE;
		int gy = y / GRID_SIZE;
		int sx = myUnitDefs.getUnitDef(type)->unit.getWidth() / GRID_SIZE;
		int sy = myUnitDefs.getUnitDef(type)->unit.getHeight() / GRID_SIZE;
		int width = world->width();
		int height = world->height();
		unsigned int buildmask = myUnitDefs.getUnitDef(type)->buildMask << 8;
		unsigned int *map = world->map() + width * gy + gx;
		if (gx < 0 || gx >= width - sx)
			return -3;
		if (gy < 0 || gy >= height - sy)
			return -3;
		for (int y = 0; y < sy; y++)
		{
			for (int x = 0; x < sx; x++)
			{
				if (!(*map & buildmask))
					return -3;
				map++;
			}
			map += width - sx;
		}
	}
	/* at this point we are committed to making the unit */
	for (int i = 0; i < RES_MAX; i++)
		p->resources().resource(i) -= cost->resource(i);
	if (id >= 0)
	{
		while (myUnits.size() <= id)
			myUnits.push_back(NULL);
	}
	else
	{
		myUnits.push_back(NULL);
		id = myUnits.size() - 1;
	}
	myUnits[id] = new Unit(myUnitDefs.getUnitDef(type)->unit);
	myUnits[id]->setType(type);
	myUnits[id]->setOwner(owner);
	myUnits[id]->position().x = x;
	myUnits[id]->position().y = y;
	return id;
}

