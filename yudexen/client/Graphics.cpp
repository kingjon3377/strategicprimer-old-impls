
#include <SDL/SDL.h>
#include <SDL/SDL_ttf.h>
#include <iostream>
#include "Graphics.hpp"
#include "Drawable.hpp"
using namespace std;

TTF_Font *gameFont;

Graphics::Graphics(const char *wname, int fullscreen)
{
	if (SDL_Init(SDL_INIT_VIDEO | SDL_INIT_TIMER))
	{
		cerr << "Failed to initialize SDL!" << endl;
		exit(1);
	}

	if (!(myScreen = SDL_SetVideoMode(WIDTH, HEIGHT, DEPTH,
		SDL_DOUBLEBUF | SDL_HWSURFACE |
			(fullscreen ? SDL_FULLSCREEN : 0))))
	{
		cerr << "Failed to set video mode!" << endl;
		exit(2);
	}
	atexit(SDL_Quit);
	SDL_WM_SetCaption(wname, wname);
	SDL_EnableUNICODE(1);	/* receive key translations from SDL */
	SDL_EnableKeyRepeat(500, 50);

	/* initialize fonts */
	if (TTF_Init() == -1)
	{
		printf("TTF_Init: %s\n", TTF_GetError());
		exit(-1);
	}

	/* Set up the fonts */
	gameFont = TTF_OpenFont(GAME_FONT, 12);
	if (!gameFont)
	{
		printf("Failed to open font '" GAME_FONT "'!\n");
		exit(-1);
	}

}

Graphics::~Graphics()
{
	SDL_Quit();
}

void	Graphics::draw(Drawable & d, int x, int y)
{
	SDL_Rect rect;
	rect.x = x;
	rect.y = y;
	rect.w = d.rect()->w;
	rect.h = d.rect()->h;
	SDL_BlitSurface(d.surf(), d.rect(), myScreen, &rect);
}
/*
void	CopySurface(SDL_Surface *src, SDL_Rect *srect, SDL_Surface *dest, SDL_Rect *drect, int alphabase)
{
	PTYPE *pcpy = (PTYPE *) src->pixels;
	PTYPE *pdest = ((PTYPE *) dest->pixels) + drect->y * dest->w + drect->x;
	unsigned int amask = dest->format->Amask;
	unsigned int notamask = amask ^ 0xFFFFFFFF;
	unsigned int ashift = dest->format->Ashift;
	unsigned int arange = 255 - alphabase;
	for (int i = 0; i < srect->h; i++)
	{
		PTYPE *dcpy = pdest;
		for (int j = 0; j < srect->w; j++)
		{
			unsigned int aval = (*pcpy & amask) >> ashift;
			unsigned int newaval = (unsigned int) ( ((double)aval) * (double) arange / 255.0 + (double) alphabase );
			*dcpy++ = (*pcpy++ & notamask) | (newaval << ashift);
		}
		pdest += dest->w;
	}
}
*/
