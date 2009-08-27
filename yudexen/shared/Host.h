
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
