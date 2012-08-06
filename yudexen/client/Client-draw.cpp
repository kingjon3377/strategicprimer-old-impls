/* Client-draw.cpp
 * Purpose: Contains drawing functions of the Client object [Client.h]
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, John Van Enk, Job Vranish, 
 * Input and output: Only internal (?)
 * How to use: See each function (Need a more general here)
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: (need something here)
 * Key variables: See Client.h; the global Graphics and global game
 * font.
 */

#include <sstream>
#include <SDL/SDL.h>
#include <SDL/SDL_ttf.h>
#include "Client.hpp"
#include "Graphics.hpp"
#include "shared/ResourceArray.hpp"
using namespace std;

extern Graphics *graphics;
extern TTF_Font *gameFont;

/* void Client::loadDrawables()
 * Purpose: Load all Drawable objects for the Client object.
 * Preconditions: 
 * 1. myTerrainDrawables, myResourceDrawables, and myUnitDrawables do
 * not contain addresses of memory in use.
 * 2. All images used by this function are stored in the data/images/
 * directory in PNG format, of set dimensions (determined earlier by
 * consensus). These dimensions ought to be named constants, but
 * aren't.
 * 3. TILES_MAX contains the number of tile images plus one.
 * 4. RES_MAX contains the number of resource images plus one.
 * 5. UNITS_MAX contains the number of unit images, plus one.
 * 6. Tile images are named by their tiletype number.
 * 7. Resource images are named by their resource number, prefixed
 * with "r".
 * 8. Unit images are named by their unit type number, prefixed with "u".
 * Postconditions:
 * 1. myTerrainDrawables, myResourceDrawables, and myUnitDrawables
 * contain arrays of Drawable objects from the images.
 */
void	Client::loadDrawables()
{
	/* Load terrain drawables */
	myTerrainDrawables = new Drawable*[TILES_MAX];
	for (int t = 1; t < TILES_MAX; t++)
	{ // Produce a filename from a number
		stringstream ss;
		ss << "data/images/";
		ss << (t < 10 ? "0" : "") << t;
		ss << ".png";
		myTerrainDrawables[t] = new Drawable(ss.str().c_str());
	}

	/* Load resource drawables */
	myResourceDrawables = new Drawable*[RES_MAX];
	for (int i = 0; i < RES_MAX; i++)
	{
		stringstream ss;
		ss << "data/images/r" << i << ".png";
		myResourceDrawables[i] = new Drawable(ss.str().c_str());
	}

	/* Load unit drawables */
	myUnitDrawables = new Drawable*[UNITS_MAX];
	for (int i = 0; i < UNITS_MAX; i++)
	{
		stringstream ss;
		ss << "data/images/u" << i << ".png";
		myUnitDrawables[i] = new Drawable(ss.str().c_str());
	}

	/* initialize the ItemList */
	int width = 0;
	int height = 0;
	for (int i = 0; i < UNITS_MAX; i++)
	{
		width = max(width, myUnitDrawables[i]->w());
		height += min(myUnitDrawables[i]->h(), 75) + GRID_SIZE;
	}
	width += 150;
	myItemList.create(width, height);
	// make transparent background
	PTYPE *p = (PTYPE *) myItemList.surf()->pixels;
//	PTYPE pVal = 0x33 << myItemList.surf()->format->Ashift;
//	PTYPE aVal = 0xFF << myItemList.surf()->format->Ashift;
	PTYPE pVal = (0xFF << myItemList.surf()->format->Ashift) | 0x77777777;
	for (int i = 0; i < width * height; i++)
		*p++ = pVal;
	SDL_Rect dest;
	dest.x = dest.y = 10;
	for (int i = 0; i < UNITS_MAX; i++)
	{
		dest.w = myUnitDrawables[i]->w();
		dest.h = myUnitDrawables[i]->h();
//		SDL_FillRect(myItemList.surf(), &dest, aVal);
		SDL_BlitSurface(myUnitDrawables[i]->surf(),
				myUnitDrawables[i]->rect(),
				myItemList.surf(),
				&dest);
		dest.y += min((int) dest.h, 75) + GRID_SIZE;
	}
}

/* void Client::updateWorld()
 * Purpose: Load all Drawable objects for the Client object.
 * Preconditions: 
 * 1. myTerrainDrawables, myResourceDrawables, and myUnitDrawables do
 * not contain addresses of memory in use.
 * 2. All images used by this function are stored in the data/images/
 * directory in PNG format, of set dimensions (determined earlier by
 * consensus). These dimensions ought to be named constants, but
 * aren't.
 * 3. TILES_MAX contains the number of tile images plus one.
 * 4. RES_MAX contains the number of resource images plus one.
 * 5. UNITS_MAX contains the number of unit images, plus one.
 * 6. Tile images are named by their tiletype number.
 * 7. Resource images are named by their resource number, prefixed
 * with "r".
 * 8. Unit images are named by their unit type number, prefixed with "u".
 */
void	Client::updateWorld()
{
	SDL_FillRect(myWorld.surf(), NULL, 0);
	int gx = getWorldGridx(0);
	int gy = getWorldGridy(0);
	int fx = -(myWorldx % GRID_SIZE);
	int fy = -(myWorldy % GRID_SIZE);
	World *wd = myGame->world;
	int gwidth = wd->width();
	int gheight = wd->height();
	unsigned int *mapbase = wd->map() + gy * gwidth + gx;
	SDL_Rect dest;
	dest.w = dest.h = GRID_SIZE;
	int lgx = gx + (fx ? (WIDTH / GRID_SIZE) + 1 : (WIDTH / GRID_SIZE));
	int lgy = gy + (fy ? (HEIGHT / GRID_SIZE) + 1 : (HEIGHT / GRID_SIZE));
	if (lgx > gwidth)
		lgx = gwidth;
	if (lgy > gheight)
		lgy = gheight;
	unsigned int *map;
	int dy = fy;
	int dx;
	for (int y = gy; y < lgy; y++)
	{
		map = mapbase;
		dx = fx;
		for (int x = gx; x < lgx; x++)
		{
			dest.x = dx;
			dest.y = dy;
			int tileid = *map & 0xFF;
			if (myTerrainDrawables[tileid])
				SDL_BlitSurface(myTerrainDrawables[tileid]->surf(),
					NULL,
					myWorld.surf(),
					&dest);
			map++;
			dx += GRID_SIZE;
		}
		mapbase += gwidth;
		dy += GRID_SIZE;
	}
}

void	Client::drawResources()
{
	if (myResourcesNeedsUpdating)
	{
		for (int i = 0; i < RES_MAX; i++)
		{
			if (myResourceLevelSurfaces[i])
				delete myResourceLevelSurfaces[i];
			myResourceLevelSurfaces[i] = NULL;
			stringstream ss;
			ss << myGame->player(myPlayerID)->resources().resource(i);
			if (myGame->player(myPlayerID)->resourcesDiscovered().resource(i))
				myResourceLevelSurfaces[i] = new Drawable(TTF_RenderText_Solid(gameFont, ss.str().c_str(), myFontColor));
		}
		myResourcesNeedsUpdating = 0;
	}
	for (int i = 0; i < RES_MAX; i++)
	{
		if (myGame->player(myPlayerID)->resourcesDiscovered().resource(i))
		{
			graphics->draw(*myResourceDrawables[i], 5, HEIGHT - myMap.getHeight() - 31 - 27*i);
			graphics->draw(*myResourceLevelSurfaces[i], 35, HEIGHT - myMap.getHeight() - 31 - 27*i);
		}
	}
}

void	Client::redraw()
{
	myIAmDrawing = 1;
	myLastMilliseconds = myMilliseconds;
	myMilliseconds = SDL_GetTicks();
	myCurrentFPS = 1000 / (myMilliseconds - myLastMilliseconds);
	/* move the world if needed */
	int wwidth = myGame->world->width() * GRID_SIZE;
	int wheight = myGame->world->height() * GRID_SIZE;
	if (myWorldxIncrement)
	{
		int lastWorldx = myWorldx;
		myWorldx += myWorldxIncrement * (myMilliseconds - myLastMilliseconds);
		if (myWorldx < 0)
		{
			myWorldx = 0;
			myWorldxIncrement = 0;
		}
		else if (myWorldx > wwidth - WIDTH)
		{
			myWorldx = wwidth - WIDTH;
			if (myWorldx < 0)
				myWorldx = 0;
			myWorldxIncrement = 0;
		}
		if (myWorldx != lastWorldx)
			myWorldNeedsUpdating = myMapNeedsUpdating = 1;
	}
	if (myWorldyIncrement)
	{
		int lastWorldy = myWorldy;
		myWorldy += myWorldyIncrement * (myMilliseconds - myLastMilliseconds);
		if (myWorldy < 0)
		{
			myWorldy = 0;
			myWorldyIncrement = 0;
		}
		else if (myWorldy > wheight - HEIGHT)
		{
			myWorldy = wheight - HEIGHT;
			if (myWorldy < 0)
				myWorldy = 0;
			myWorldyIncrement = 0;
		}
		if (myWorldy != lastWorldy)
			myWorldNeedsUpdating = myMapNeedsUpdating = 1;
	}

	/* update the world if needed */
	if (myWorldNeedsUpdating)
	{
		updateWorld();
		myWorldNeedsUpdating = 0;
	}

	/* draw the world */
	graphics->draw(myWorld, 0, 0);

	/* draw the units */
	drawUnits();

	/* update the minimap if needed */
	myMap.updateTerrain();
	if (myMapNeedsUpdating)
	{
		myMap.updateView(myWorldx, myWorldy);
		myMapNeedsUpdating = 0;
	}

	/* draw the unit placement grid */
	if (myChoosingUnitType >= 0)
	{
		int x = getWorldGridx(myMousex - 12) * GRID_SIZE - myWorldx;
		int y = getWorldGridy(myMousey - 12) * GRID_SIZE - myWorldy;
		graphics->draw(myChoosingUnitDrawable, x, y);
	}

	/* draw the minimap */
	graphics->draw(myMap, 0, HEIGHT - myMap.getHeight());

	/* draw the resources */
	drawResources();

	/* draw the item list */
	if (myItemListVisible)
		graphics->draw(myItemList, 0, myItemListPosition);

	/* draw the console */
	if (myConsolePos)
		myConsole.draw();

	/* send finished buffer to screen */
	graphics->flip();
	myIAmDrawing = 0;
}

