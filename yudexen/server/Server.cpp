
#include <iostream>
#include <sys/time.h>		// setitimer
#include <signal.h>		// signal
#include "shared/Game.h"
#include "shared/game_defs.h"
#include "server/Server.h"
using namespace std;

void	usage()
{
	cout	<<	"Usage:"
			"\n-m<map>   use map <map>" << endl;
	exit(1);
}

int	main(int argc, char **argv)
{
	char *map = NULL;
	for (int i = 0; i < argc; i++)
	{
		if (!strncmp(argv[i], "-m", 2))
		{
			if (strlen(argv[i]) > 2)
				map = argv[i] + 2;
			else
			{
				if (i >= argc - 1)
					usage();
				map = argv[++i];
			}
		}

	}
	if (!map)
		usage();
	Server myServer;
	myServer.loadMap(map);
	myServer.run();
}

Server::Server()
{
	myGame = new Game();
	myMap = "";
}

Server::~Server()
{
	delete myGame;
}

void	dum(int d)
{
}

int	Server::loadMap(char *map)
{
	myMap = map;
	return myGame->loadMap(map);
}

void	Server::run()
{
	struct itimerval it;
	it.it_interval.tv_sec = it.it_value.tv_sec = 0;
	it.it_interval.tv_usec = it.it_value.tv_usec = SERVER_DELAY;
	signal(SIGALRM, dum);
	setitimer(ITIMER_REAL, &it, NULL);

	Message *msg;
	for (;;)
	{
		if (msg = myGame->getMessage())
		{
			message_t *m = msg->getBufferMessage();
			Message reply;
			int id, idx;
			Player *player;
			switch (m->type)
			{
			case MSG_LOGIN_REQ:
				hostDepart(msg->getHost());
				id = myGame->login(m->login_name);
				cout << "Login request from " << msg->getHost() << ", requested name '" << m->login_name << "', got '" << myGame->player(id)->name().c_str() << "'" << endl;

				m = reply.getBufferMessage();
				m->type = MSG_LOGIN_ACK;
				strcpy(m->login_name, myGame->player(id)->name().c_str());
				m->login_playerid = id;
				strcpy(m->map_name, myMap);
				myGame->getConnection()->sendMessage(&reply, msg->getIP(), msg->getPort());

				m->type = MSG_NEW_PLAYER_BC;
				broadcastMessage(&reply);

				myGame->player(id)->resources().resource(0) = 2000;
				myGame->player(id)->resources().resource(1) = 200;
				myGame->player(id)->resourcesDiscovered().resource(0) = 1;
				myGame->player(id)->resourcesDiscovered().resource(1) = 1;

				server_host_player_t hp;
				hp.host = new Host(msg->getHost());
				hp.playerid = id;
				myClients.push_back(hp);

				updateClientResources(id);
				break;
			case MSG_DEPART:
				if ((idx = getHostIndex(msg->getHost())) < 0)
					break;
				if (m->login_playerid != myClients[idx].playerid)
					break;
				hostDepart(msg->getHost());
				m->type = MSG_DEPART_BC;
				broadcastMessage(msg);
				break;
			case MSG_NEW_UNIT_REQ:
				if ((idx = getHostIndex(msg->getHost())) < 0)
					break;
				if (m->unit_owner != myClients[idx].playerid)
					break;
				id = myGame->createUnit(m->unit_owner, m->unit_type, m->unit_x, m->unit_y, -1);
//				cout << "Server got back unit id #" << id << endl;
				if (id < 0)
					break;
				m->type = MSG_NEW_UNIT_BC;
				m->unit_id = id;
				broadcastMessage(msg);
				updateClientResources(m->unit_owner);
				break;
			}
			delete msg;
		}
		myGame->action();
		pause();
	}
}

void	Server::hostDepart(const Host & host)
{
	int i;
	if ((i = getHostIndex(host)) < 0)
		return;
	myGame->logout(myClients[i].playerid);
	myClients.erase(myClients.begin() + i, myClients.begin() + i + 1);
}

int	Server::getHostIndex(const Host & host)
{
	int max = myClients.size();
	for (int i = 0; i < max; i++)
	{
		if (*(myClients[i].host) == host)
			return i;
	}
	return -1;
}

int	Server::getHostIndex(int playerid)
{
	int max = myClients.size();
	for (int i = 0; i < max; i++)
	{
		if (myClients[i].playerid == playerid)
			return i;
	}
	return -1;
}

void	Server::broadcastMessage(Message *msg)
{
	int max = myClients.size();
	for (int i = 0; i < max; i++)
		myGame->getConnection()->sendMessage(msg, myClients[i].host->getIP(), myClients[i].host->getPort());
}

void	Server::updateClientResources(int client)
{
	Message msg;
	int index = getHostIndex(client);
	if (index < 0)
		return;
	message_t *m = msg.getBufferMessage();
	m->type = MSG_RESOURCE_LEVELS;
	for (int i = 0; i < RES_MAX; i++)
	{
		m->resourceLevels[i] = myGame->player(client)->resources().resource(i);
		m->resourcesDiscovered[i] = myGame->player(client)->resourcesDiscovered().resource(i);
	}
	myGame->getConnection()->sendMessage(&msg, myClients[index].host->getIP(), myClients[index].host->getPort());
}

