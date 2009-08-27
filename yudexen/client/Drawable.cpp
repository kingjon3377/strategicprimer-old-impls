
#include <iostream>
#include <SDL/SDL.h>
#include "Drawable.hpp"
#include "Graphics.hpp"
using namespace std;

extern Graphics *graphics;

Drawable::Drawable()
{
	mySurface = NULL;
	myRect.x = myRect.y = myRect.w = myRect.h = 0;
}

Drawable::Drawable(const char *filename)
{
	mySurface = NULL;
	myRect.x = myRect.y = myRect.w = myRect.h = 0;
	load(filename);
}

Drawable::Drawable(SDL_Surface *surf)
{
	mySurface = SDL_DisplayFormatAlpha(surf);
	if (!mySurface)
		mySurface = surf;
	else
		SDL_FreeSurface(surf);
	myRect.x = myRect.y = 0;
	myRect.w = mySurface->w;
	myRect.h = mySurface->h;
}

Drawable::~Drawable()
{
	free();
}

void	Drawable::free()
{
	if (mySurface)
		SDL_FreeSurface(mySurface);
	myRect.x = myRect.y = myRect.w = myRect.h = 0;
}

int	Drawable::load(const char *filename)
{
	free();
	SDL_Surface *temp = IMG_Load(filename);
	if (!temp)
	{
		cerr << "Failed to load " << filename << endl;
		return -1;
	}
	mySurface = SDL_DisplayFormatAlpha(temp);
	if (!mySurface)
		mySurface = temp;
	else
		SDL_FreeSurface(temp);
	myRect.w = mySurface->w;
	myRect.h = mySurface->h;
	return 0;
}

int	Drawable::create(int w, int h, int alpha)
{
	free();
	SDL_PixelFormat *format = graphics->getScreenSurface()->format;
	SDL_Surface *temp = SDL_CreateRGBSurface(SDL_HWSURFACE | SDL_SRCALPHA,
		w, h, DEPTH,
		format->Rmask, format->Gmask, format->Bmask, format->Amask);
	if (!temp)
		cerr << "Drawable::create() error: " << SDL_GetError() << endl;
	else
	{
		mySurface = SDL_DisplayFormatAlpha(temp);
		if (!mySurface)
			mySurface = temp;
		else
			SDL_FreeSurface(temp);
		myRect.w = w;
		myRect.h = h;
	}
	if (mySurface && alpha != 0xFF)
	{
		PTYPE *p = (PTYPE *) mySurface->pixels;
		int max = myRect.w * myRect.h;
		unsigned int pVal = alpha << mySurface->format->Ashift;
		for (int i = 0; i < max; i++)
			*p++ = pVal;
	}
	return mySurface ? 0 : -1;
}

int	Drawable::createnoalpha(int w, int h)
{
	free();
	SDL_PixelFormat *format = graphics->getScreenSurface()->format;
	SDL_Surface *temp = SDL_CreateRGBSurface(SDL_HWSURFACE,
		w, h, DEPTH,
		format->Rmask, format->Gmask, format->Bmask, 0);
	if (!temp)
		cerr << "Drawable::create() error: " << SDL_GetError() << endl;
	else
	{
		mySurface = SDL_DisplayFormat(temp);
		if (!mySurface)
			mySurface = temp;
		else
			SDL_FreeSurface(temp);
		myRect.w = w;
		myRect.h = h;
	}
	return mySurface ? 0 : -1;
}

void	Drawable::blit(Drawable & second, int x, int y)
{
	SDL_Rect dest;
	dest.x = x;
	dest.y = y;
	dest.w = second.rect()->w;
	dest.h = second.rect()->h;
	SDL_BlitSurface(second.surf(), second.rect(), mySurface, &dest);
}

