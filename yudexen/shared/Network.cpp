
#include "unistd.h"		// close
#include "stdio.h"		// perror
#include "stdlib.h"
#include "string.h"		// memcpy
#include <sys/types.h>
#include <sys/socket.h>		// socket, bind
#include <netinet/ip.h>		// sockaddr_in
#include <netdb.h>
#include "shared/game_defs.h"	// PORT
#include "Network.h"		// Network
using namespace std;

Network::Network(int server, char *hostname)
{
	mySockfd = socket(PF_INET, SOCK_DGRAM, 0);
	myServer_addr.sin_family = AF_INET;
	myServer_addr.sin_port = htons(PORT);
	if (myServer = server)
	{
		myServer_addr.sin_addr.s_addr = INADDR_ANY;
		if (bind(mySockfd, (struct sockaddr *) &myServer_addr,
			sizeof(struct sockaddr_in)) == -1)
		{
			perror("bind");
			exit(-1);
		}
	}
	else
	{
		// Resolve the IP address
		struct hostent *hname = gethostbyname2(hostname, AF_INET);
		memcpy(&myServer_addr.sin_addr.s_addr, hname->h_addr, 4);
	}
	myClient_addr_len = sizeof(struct sockaddr);
}

Network::~Network()
{
	close(mySockfd);
}

unsigned int Network::receive(char *buffer, unsigned int maxlen,
	ip_t *ip, port_t *port)
{
	myClient_addr_len = sizeof(struct sockaddr);
	unsigned int rlen = recvfrom(mySockfd, buffer, maxlen, 0,
		(struct sockaddr *) &myClient_addr, &myClient_addr_len);
	*ip = myClient_addr.sin_addr.s_addr;
	*port = ntohs(myClient_addr.sin_port);
	return rlen;
}

void Network::send(char* buffer, unsigned int len)
{
	sendto(mySockfd, buffer, len, 0,
		(struct sockaddr *) &myServer_addr, sizeof(struct sockaddr));
}

void Network::send(char* buffer, unsigned int len, ip_t ip, port_t port)
{
	myClient_addr.sin_port = htons(port);
	myClient_addr.sin_addr.s_addr = ip;
	sendto(mySockfd, buffer, len, 0,
		(struct sockaddr *) &myClient_addr, sizeof(struct sockaddr));
}
