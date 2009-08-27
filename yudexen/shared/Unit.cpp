/* Unit.cpp
 * Purpose: Implements the Unit class of Unit.h
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish and
 * John van Enck.
 * Input and output: Only internal.
 * How to use: See Unit.h
 * Assumptions: None.
 * Exceptions: None.
 * Major algorithms and data structures: See Unit.h
 * Key variables: See Unit.h
*/
#include <iostream>
#include <vector>
#include <queue>
#include <math.h>
#include "shared/Unit.h"
#include "shared/ResourceArray.h"
#include "shared/Position.h"
#include "shared/game_defs.h"
using namespace std;

/* Unit::Unit()
 * Purpose: Construct the Unit object with sane default values.
 * Precondition: None
 * Postconditions: The unit has a type of 0, a width and height of one
 * grid unit, and is not moving.
 */
Unit::Unit()
{
	myType = 0;
	myWidth = myHeight = GRID_SIZE;
	myMoving = false;
}
