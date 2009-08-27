

#include "shared/Message.h"
using namespace std;

Message::Message()
{
	myBuffer = new char[myLength = sizeof(message_t)];
}

Message::Message(void *buffer, unsigned int length,
		unsigned int ip, unsigned short port)
{
	myBuffer = (char *) buffer;
	myLength = length;
	myIP = ip;
	myPort = port;
}

Message::~Message()
{
	delete[] myBuffer;
}

