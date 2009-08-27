
#include <iostream>
#include "shared/Host.h"
using namespace std;

Host::Host(unsigned int ip, unsigned short port)
{
	myIP = ip;
	myPort = port;
}

bool operator==(const Host & a, const Host & b)
{
	return a.myIP == b.myIP && a.myPort == b.myPort;
}

bool operator!=(const Host & a, const Host & b)
{
	return a.myIP != b.myIP || a.myPort != b.myPort;
}

ostream & operator<<(ostream & out, const Host & h)
{
	out << std::dec << (h.myIP & 0xFF);
	out << '.';
	out << ((h.myIP >> 8) & 0xFF);
	out << '.';
	out << ((h.myIP >> 16) & 0xFF);
	out << '.';
	out << ((h.myIP >> 24) & 0xFF);
	out << ':';
	out << h.myPort;
	return out;
}
