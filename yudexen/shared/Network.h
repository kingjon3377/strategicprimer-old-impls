
#ifndef _NETWORK_H_
#define _NETWORK_H_ _NETWORK_H_

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/ip.h>
#include <netdb.h>
#include "shared/game_defs.h"
using namespace std;

typedef unsigned int ip_t;
typedef unsigned short port_t;

class Network
{
private:
	int			mySockfd;
	int			myServer;
	struct sockaddr_in	myServer_addr;
	struct sockaddr_in	myClient_addr;
	socklen_t		myClient_addr_len;

public:
	Network(int server, char * hostname);
	~Network();

	unsigned int receive(char *buffer, unsigned int maxlen,
		ip_t *ip, port_t *port);
	void	send(char *buffer, unsigned int len);
	void	send(char* buffer, unsigned int len, ip_t ip, port_t port);
};

#endif
