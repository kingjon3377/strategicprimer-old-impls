
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
