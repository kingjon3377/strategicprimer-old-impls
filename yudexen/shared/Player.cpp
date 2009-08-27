/* Player.cpp
 * Purpose: Contains the implementation of the Player object.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish, and
 * John van Enck.
 * Input and output: Only internal.
 * How to use: See Player.h
 */
#include "shared/Player.h"
using namespace std;

/* Player::Player()
 * Purpose: Construct a Player object with the given player name.
 * Precondition: None
 * Postcondition: The Player object is set up and has the given name.
 * Any restrictions on the value of the player name (such as
 * uniqueness, length, special characters, etc.) should be checked by
 * the caller; this ought to at least sanitize the string, but it
 * doesn't.
 */
Player::Player(string name)
{
	myName = name;
}
