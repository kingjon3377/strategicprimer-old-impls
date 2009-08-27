/* Client-unit.cpp
 * Purpose: Contains unit-related functions of the Client object [Client.h]
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop and John Van Enk.
 * Input and output: Only internal (?)
 * How to use: See each function (Need a more general here)
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: (need something here)
 * Key variables: See Client.h; the global Graphics.
 */

#include <vector>
#include "shared/Unit.h"
#include "Client.h"
using namespace std;

extern Graphics *graphics;

void Client::drawUnits()
{
	vector<Unit *> units = myGame->getUnits();
	int max = units.size();
	for (int i = 0; i < max; i++)
	{
		if (!units[i])
			continue;
		int x = units[i]->position().x;
		int y = units[i]->position().y;
		int w = units[i]->getWidth();
		int h = units[i]->getHeight();
//		cout << "x: " << x << " y: " << y << " w: " << w << " h: " << h << endl;
		if (x > myWorldx - w && x < myWorldx + WIDTH &&
			y > myWorldy - h && y < myWorldy + HEIGHT)
			graphics->draw(*myUnitDrawables[units[i]->getType()], x - myWorldx, y - myWorldy);
	}
}
