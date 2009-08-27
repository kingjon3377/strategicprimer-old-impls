
#ifndef _GRAPHICS_H_
#define _GRAPHICS_H_ _GRAPHICS_H_

#include <SDL/SDL.h>
#include "Drawable.hpp"
#include "shared/game_defs.hpp"
using namespace std;

#define DEPTH 32
#define PTYPE Uint32

class Graphics
{
private:
	SDL_Surface	*myScreen;

public:
	Graphics(const char *wname, int fullscreen);
	~Graphics();
	void	draw(Drawable & d, int x, int y);

	inline void	flip()
	{
		SDL_Flip(myScreen);
	}

	inline void	clear()
	{
		SDL_FillRect(myScreen, NULL, 0);
	}

	// Returns bool for whether an event is available or not
	inline int	wait_event(SDL_Event *event)
	{
		return SDL_WaitEvent(event);
	}
	
	inline SDL_Surface *getScreenSurface()
	{
		return myScreen;
	}

};
/*
void	CopySurface(SDL_Surface *src, SDL_Rect *srect, SDL_Surface *dest, SDL_Rect *drect, int alphabase);
*/
#endif
