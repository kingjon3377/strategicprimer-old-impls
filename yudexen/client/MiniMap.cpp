#include "MiniMap.h"

extern Graphics *graphics;

MiniMap::~MiniMap()
{
	delete [] myTerrain;
}

void MiniMap::create(World* world)
{
	myWorld = world;
	myWorldWidth = world->width();
	myWorldHeight = world->height();
	myMapData = world->map();

	int width, height;
	
	if (myWorldWidth < WIDTH_MAX || myWorldHeight < HEIGHT_MAX) {
		width = myWorldWidth;
		height = myWorldHeight;
		myRatioX = myRatioY = 1;
	} else {
		myRatioX = (int)ceil((double)myWorldWidth / WIDTH_MAX);
		myRatioY = (int)ceil((double)myWorldHeight / HEIGHT_MAX);
		myRatioX = min(myRatioX, myRatioY);
		myRatioY = myRatioX;
		width = (int)ceil((double)myWorldWidth / myRatioX);
		height = (int)ceil((double)myWorldHeight / myRatioY);
	}
	width += 2;
	height += 2;
	Drawable::create(width, height);

	myTerrain = new PTYPE[width * height];

	myViewRect.w = (Uint16)round((double)WIDTH / (GRID_SIZE * myWorldWidth) * width);
	myViewRect.h = (Uint16)round((double)HEIGHT / (GRID_SIZE * myWorldHeight) * height);

//	unsigned int blank = SDL_MapRGBA(mySurface->format, 0, 0, 0, 200);
	unsigned int land = SDL_MapRGBA(mySurface->format, 157, 111, 38, 200);
	unsigned int water = SDL_MapRGBA(mySurface->format, 0, 0, 200, 200);
	for (int i = 0; i < COLORS_MAX; i++)
		myColors[i] = SDL_MapRGBA(mySurface->format, 78, 66, 110, 200);

	myColors[13] = land; // Land
	myColors[14] = water; // Water
	myColors[17] = SDL_MapRGBA(mySurface->format, 0, 150, 0, 255); // Trees
}

void MiniMap::updateView(int viewX, int viewY)
{
	memcpy(mySurface->pixels, myTerrain, myRect.w * myRect.h * 4);

	myViewRect.x = (Uint16)round((double)viewX / (GRID_SIZE * myWorldWidth) * (myRect.w-2))+1;
	myViewRect.y = (Uint16)round((double)viewY / (GRID_SIZE * myWorldHeight) * (myRect.h-2))+1;
	drawRectangle(mySurface, myViewRect);
}

void MiniMap::updateTerrain()
{
	PTYPE *pixels = (PTYPE*)myTerrain;
//	unsigned int *map = myWorld->map();
	int mapPos = 0;

	unsigned int border = SDL_MapRGBA(mySurface->format, 255, 255, 255, 255);

	for (int i = 0; i < myRect.h; i++) {
		for (int j = 0; j < myRect.w; j++) {
			if (i == 0 || i == myRect.h-1 || j == 0 || j == myRect.w-1)
				*pixels++ = border;
			else {
//				if (map[mapPos] >= 0 && map[mapPos] <= COLORS_MAX)
//					*pixels++ = myColors[map[mapPos]];
				*pixels++ = pointColor(mapPos);
				mapPos += myRatioX;
			}
		}
		if (i > 0 && i < myRect.h - 1)
			mapPos = i * myRatioY * myWorldWidth;
	}
}

void MiniMap::drawRectangle(SDL_Surface *surf, SDL_Rect &rect)
{
	PTYPE border = 0xFFFFFFFF;
	PTYPE *pixels;
	pixels = (PTYPE*)surf->pixels + myRect.w * rect.y + rect.x;
	for (int i = 0; i < rect.w; i++)
		*pixels++ = border;

	pixels = (PTYPE*)surf->pixels + myRect.w * (rect.y + rect.h - 1) + rect.x;
	for (int i = 0; i < rect.w; i++)
		*pixels++ = border;

	pixels = (PTYPE*)surf->pixels + myRect.w * rect.y + rect.x;
	for (int i = 1; i <= rect.h * 2; i++)
	{
		*pixels = border;
		if (i % 2)
			pixels += rect.w - 1;
		else
			pixels += (myRect.w - rect.w + 1);
	}
}

int MiniMap::pointColor(int mapPos)
{
	int x = myRatioX / 2;
	int y = myRatioY / 2;
	unsigned int c = myMapData[mapPos + x + y * myWorldWidth] & 0xFF;
	if (c < COLORS_MAX)
		return myColors[myMapData[mapPos + x + y * myWorldWidth] & 0xFF];
	else
		return 0;
}
