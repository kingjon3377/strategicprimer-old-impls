/* Host.cpp
 * Purpose: Contains the implementation of the Host object.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brum, and John van Enck.
 * Input and output: Only internal.
 * How to use: See Host.h
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: Operator overloading
 * Key variables: See Host.h.
 */

#include <iostream>
#include "shared/Host.h"
using namespace std;

/* Host::Host()
 * Purpose: Set up the object
 * Preconditions: The IP and port # describe an existent host. This is
 * not checked.
 * Postconditions: The Host object is set up.
 */
Host::Host(unsigned int ip, unsigned short port)
{
	myIP = ip;
	myPort = port;
}

/* bool operator==()
 * Purpose: Test whether two Host objects are the same.
 * Preconditions: None.
 * Postconditions: Returns true if both IP addresses and port #s
 * match, otherwise false.
 */
bool operator==(const Host & a, const Host & b)
{
	return a.myIP == b.myIP && a.myPort == b.myPort;
}

/* bool operator!=()
 * Purpose: Test whether two Host objects are not the same.
 * Preconditions: None.
 * Postconditions: Returns true if either the IP addresses or the port
 * #s do not match; otherwise false.
 */
bool operator!=(const Host & a, const Host & b)
{
	return a.myIP != b.myIP || a.myPort != b.myPort;
}

/* ostream & operator<<()
 * Purpose: Turn the Host object into text.
 * Preconditions: None.
 * Postconditions: The IP address and port # have been output in
 * human-readable format on the supplied output stream.
 */
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
