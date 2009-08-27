/* Unit.h
 * Purpose: Represents a unit in the game.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish and
 * John van Enck.
 * Input and output: Only internal.
 * How to use: The Game object includes a vector of all the units in
 * the game; it can be instructed to create a new unit according to
 * an instance of the UnitDef class. [Need more here]
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: (need something here)
 * Key variables: myPosition, myWaypoints
 */
#ifndef T_UNIT
#define T_UNIT T_UNIT

#include <iostream>
#include <queue>
#include "shared/ResourceArray.h"
#include "shared/Position.h"
#include "shared/game_defs.h"
using namespace std;

class Unit
{
protected:
	int		myType;
	int		myOwner;
	Position	myPosition;
	queue<Position>	myWaypoints;
	bool		myMoving;
	int		myWidth;
	int		myHeight;

public:
	Unit();

	inline void addWaypoint(const Position & pos) { myWaypoints.push(pos); }

	/* access to member variables */
	inline int getType() { return myType; }
	inline void setType(int type) { myType = type; }
	inline int getOwner() { return myOwner; }
	inline void setOwner(int owner) { myOwner = owner; }
	inline int getWidth() { return myWidth; }
	inline void setWidth(int w) { myWidth = w; }
	inline int getHeight() { return myHeight; }
	inline void setHeight(int h) { myHeight = h; }
	inline Position & position() { return myPosition; }

	inline bool isMoving() { return myMoving; }
};

#endif
