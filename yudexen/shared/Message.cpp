/* Message.cpp
 * Purpose: Contains the implementation of the Message object.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brum, and John van Enck.
 * Input and output: Only internal.
 * How to use: See Message.h
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: (need something here)
 * Key variables: See Message.h */

#include "shared/Message.hpp"
using namespace std;

/* Message::Message()
 * Purpose: Set up the object's buffer.
 * Preconditions: We are within resource limitations.
 * Postconditions: The Message object's buffer is ready.
 */
Message::Message()
{
	myBuffer = new char[myLength = sizeof(message_t)];
}

/* Message::Message()
 * Purpose: Set up the object.
 * Preconditions: buffer is a valid pointer; length is its length (?);
 * ip and port describe a valid host.
 * Postconditions: The Message object is initialized with the given
 * values. */
Message::Message(void *buffer, unsigned int length,
		unsigned int ip, unsigned short port)
{
	myBuffer = (char *) buffer;
	myLength = length;
	myIP = ip;
	myPort = port;
}

/* Message::~Message()
 * Purpose: Clean up after the object.
 * Preconditions: None.
 * Postconditions: The object contains no valid pointers. */
Message::~Message()
{
	delete[] myBuffer;
}

