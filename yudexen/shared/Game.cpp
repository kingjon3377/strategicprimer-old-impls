/* Game.cpp
 * Purpose: Contains the implementation of the Game object.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brum, and John van Enck.
 * Input and output: Only internal.
 * How to use: See Game.h
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: Client-server implementation;
 * Unit class; Player class; Connection class; World class.
 * Key variables: See Game.h.
 */

#include <vector>
#include <queue>
#include "Game.h"
#include "shared/Unit.h"
#include "shared/Message.h"
using namespace std;

/* Game::Game()
 * Purpose: Set up the game as a server.
 * Preconditions: 1. The game was not already set up.
 * 2. Unit definitions reside in data/units.ydu .
 * Postconditions: The game is set up.
 */
Game::Game() // Master game constructor
{
	myMaster = 1;
	myConnection = new Connection();
	world = new World();
	myUnitDefs.load("data/units.ydu");
}

/* Game::Game()
 * Purpose: Set up the game as a client.
 * Preconditions: 1. The game was not already set up.
 * 2. Unit definitions reside in data/units.ydu .
 * 3. The server is already running (?)
 * Postconditions: The game is set up.
 */
Game::Game(char *server) // Client game constructor
{
	myMaster = 0;
	myConnection = new Connection(server);
	world = new World();
	myUnitDefs.load("data/units.ydu");
}

/* Game::~Game()
 * Purpose: Clean up after the Game object.
 * Preconditions: The Connection object has already been deleted.
 * Postconditions: The object contains no active pointers.
 */
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

/* void Game::action()
 * Purpose: Perform game actions.
 * Preconditions: The game is functioning properly.
 * Postconditions: None yet.
 */
void	Game::action()
{
	/* perform game actions, move units, etc... */	
}

/* int Game::login()
 * Purpose: Log a player in with the given name.
 * Preconditions: The game is functioning properly.
 * Postconditions: The player is logged in; if the given name was not
 * valid, a valid one was substituted. The player ID is returned. 
 */
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

/* void Game::addPlayer()
 * Purpose: Add a player to the game.
 * Preconditions: The ID and name are unused. (Use Game::login().).
 * The game is functioning properly.
 * Postconditions: The player has been added.
 */
void	Game::addPlayer(int id, string name)
{
	while (myPlayers.size() <= id)
		myPlayers.push_back(NULL);
	if (!myPlayers[id])
		myPlayers[id] = new Player(name);
}

/* void Game::logout()
 * Purpose: Log a player out of the game.
 * Preconditions: The game is functioning properly.
 * Postconditions: The player has been removed from the game.
 */
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

/* int Game::validName()
 * Purpose: Check whether a [player] name is valid; at the moment, a
 * valid name is one that is not already used.
 * Preconditions: The game is functioning properly.
 * Postconditions: If the name is valid, 1 is returned; otherwise, 0.
 */
/* Returns zero if name is invalid */
int	Game::validName(const string & name)
{
	for (int i = 0; i < myPlayers.size(); i++)
		if (myPlayers[i] && myPlayers[i]->name() == name)
			return 0;
	return 1;
}

/* int Game::createUnit()
 * Purpose: Create a unit owned by the specified player, of the
 * specified type, at the specified position.
 * Preconditions: Parameter type is within range of known units. 
 * Postconditions: If there were sufficient resources to create the
 * unit and the given position was within bounds, the unit now exists. */
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

