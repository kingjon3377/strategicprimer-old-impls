/* Player.h
 * Purpose: Represents a player in the game.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish and
 * John van Enck.
 * Input and output: Only internal.
 * How to Use: The Game object contains a vector of these.
 * Major algorithms and data structures: ResourceArray
 */
#ifndef _PLAYER_H_
#define _PLAYER_H_ _PLAYER_H_

#include <string>
#include "shared/ResourceArray.hpp"
using namespace std;

class Player
{
protected:
	string		myName;
	ResourceArray	myResources;
	ResourceArray	myResourcesDiscovered;

public:
	Player(string name);

	inline string name() { return myName; }
	inline ResourceArray & resources() { return myResources; }
	inline ResourceArray & resourcesDiscovered() { return myResourcesDiscovered; }
};

#endif
