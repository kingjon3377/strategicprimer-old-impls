// Unit.cpp
#include <iostream>
#include <vector>
#include <queue>
#include <math.h>
#include "shared/Unit.h"
#include "shared/ResourceArray.h"
#include "shared/Position.h"
#include "shared/game_defs.h"
using namespace std;

Unit::Unit()
{
	myType = 0;
	myWidth = myHeight = GRID_SIZE;
	myMoving = false;
}
