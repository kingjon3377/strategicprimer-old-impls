/* Host.h
 * Purpose: Represents a host on a network.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brum, and John van Enck.
 * Input and output: Direct via << to output stream.
 * How to use: Set IP and port information via constructor, then query
 * via the accessor functions or in an output stream.
 * Assumptions: The IP and port information are valid and describe an
 * actual host.
 * Exceptions: None.
 * Major algorithms and data structures: Output stream.
 * Key variables: myIP, myPort . */

#ifndef _HOST_H_
#define _HOST_H_

#include <iostream>
using namespace std;

class Host
{
protected:
	unsigned int myIP;
	unsigned short myPort;

public:
	Host(unsigned int ip, unsigned short port);
	inline unsigned int getIP() { return myIP; }
	inline unsigned short getPort() { return myPort; }

	friend bool operator==(const Host & a, const Host & b);
	friend bool operator!=(const Host & a, const Host & b);
	friend ostream & operator<<(ostream & out, const Host & h);
};

#endif
