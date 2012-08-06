
#ifndef _SERVER_H_
#define _SERVER_H_ _SERVER_H_

#include "shared/Game.hpp"
using namespace std;

typedef struct
{
	Host *host;
	int playerid;
} server_host_player_t;

class Server
{
protected:
	Game *myGame;
	char *myMap;
	vector<server_host_player_t> myClients;

public:
	Server();
	~Server();

	int	loadMap(char *map);
	void	run();
	int	getHostIndex(const Host & host);
	int	getHostIndex(int playerid);
	void	hostDepart(const Host & host);
	void	broadcastMessage(Message *msg);
	void	updateClientResources(int client);
};

#endif
