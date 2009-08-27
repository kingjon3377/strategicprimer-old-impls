
#ifndef _CLIENT_H_
#define _CLIENT_H_ _CLIENT_H_

#include <vector>
#include <SDL/SDL.h>
#include "Console.h"
#include "Drawable.h"
#include "MiniMap.h"
#include "Graphics.h"
#include "shared/Game.h"
using namespace std;

#define WORLD_SCROLL_SPEED	1
#define	SCROLL_BORDER		10

/* Default tileset */
#define	TILES_MAX		18

class Client
{
protected:
	Console		myConsole;
	int		myConsolePos;
	Game 		*myGame;
	char		*myServer;
	int		myPlayerID;
	string		myPlayerName;
	int		myCurrentFPS;
	/* These two store the world coordinates of the upper-left corner
	 * of the world that the screen is currently viewing, KEEP POSITIVE */
	int		myWorldx;
	int		myWorldy;
	int		myWorldxIncrement;
	int		myWorldyIncrement;
	Drawable	myWorld;	/* Holds the current world, terrain & fixed */
	int		myWorldNeedsUpdating;
	MiniMap		myMap;		/* Holds a mini 'snapshot' of the world */
	int		myMapNeedsUpdating;
	int		myResourcesNeedsUpdating;
	SDL_TimerID	myRedrawTimerID;
	SDL_Event	myRedrawEvent;
	Drawable	**myTerrainDrawables;
	Drawable	**myUnitDrawables;
	Drawable	**myResourceDrawables;
	Drawable	*myResourceLevelSurfaces[RES_MAX];
	Drawable	myItemList;
//	ResourceArray	myResources;
//	ResourceArray	myResourcesDiscovered;
	SDL_Color	myFontColor;
	unsigned int	myMilliseconds;
	unsigned int	myLastMilliseconds;
	int		myIAmDrawing;
	int		myItemListVisible;
	int		myItemListPosition;
	int		myChoosingUnitType;
	Drawable	myChoosingUnitDrawable;
	int		myMousex, myMousey;
	int		myDraggingMinimap;

	void	waitForLoginAck();

public:
	Client(char *server, char *name);
	~Client();
	void	mainloop();
	void	redraw();
	void	grabInput();
	void	setDelay(unsigned int delay);
	void	requestUnit(int type, Position pos);
	void	warpToPosition(int mousex, int mousey);

	/* Client-draw.cpp */
	void	loadDrawables();
	void	updateWorld();
	void	drawResources();

	/* Client-unit.cpp */
	void	drawUnits();

	/* inlines */
	inline int getWorldx(int x) { return x + myWorldx; }
	inline int getWorldy(int y) { return y + myWorldy; }
	inline int getWorldGridx(int x) { return (x + myWorldx) / GRID_SIZE; }
	inline int getWorldGridy(int y) { return (y + myWorldy) / GRID_SIZE; }
	inline void logout() { myGame->logout(myPlayerID); }

	/* friends */
	friend Uint32 Client_Timer_Callback(Uint32 interval, Client *c);
};

void	Client_Message_Callback(Message *msg);

#endif
