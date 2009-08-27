/* Network.h
 * Purpose: Represents the game's network connection.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish and
 * John van Enck.
 * Input and output: Only internal.
 * How to use: The Connection object includes one of these (by way of
 * pointer); it is given bytestreams to send via send() and polled via
 * receive() to get received bytestreams. The Connection object hides
 * the bytestreams from the user, instead using Message objects.
 * Assumptions: 
 * Exceptions: (need something here)
 * Major algorithms and data structures: Standard networking
 * algorithms.
 * Key variables: myServer.
 */
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
	int			mySockfd; // Socket file descriptor
	int			myServer; // Is this a server? (Boolean)
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
