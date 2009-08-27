
#ifndef _PLAYER_H_
#define _PLAYER_H_ _PLAYER_H_

#include <string>
#include "shared/ResourceArray.h"
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
