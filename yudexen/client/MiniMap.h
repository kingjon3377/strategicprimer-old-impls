#ifndef _MINIMAP_H_
#define _MINIMAP_H_

using namespace std;

#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
#include "Drawable.h"
#include "Graphics.h"
#include "shared/World.h"
#include <iostream>
#include <math.h>
#include <string.h>

#define	WIDTH_MAX	160
#define HEIGHT_MAX	160
#define COLORS_MAX	18

class MiniMap : public Drawable
{
public:
	~MiniMap();
	void create(World* world);
	void updateView(int viewX, int viewY);
	void updateTerrain();
	inline int getHeight() { return myRect.h; };
	inline int getWidth() { return myRect.w; };
	
private:
	World *myWorld;
	PTYPE *myTerrain;
	SDL_Rect myViewRect;
	int myWorldWidth, myWorldHeight;
	unsigned int myColors[COLORS_MAX];
	unsigned int *myMapData;
	int myRatioX, myRatioY;

	void drawRectangle(SDL_Surface *surf, SDL_Rect &rect);
	int pointColor(int mapPos);
};

#endif
