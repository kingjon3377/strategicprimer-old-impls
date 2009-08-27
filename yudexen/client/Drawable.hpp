
#ifndef _DRAWABLE_H_
#define _DRAWABLE_H_ _DRAWABLE_H_

#include <SDL/SDL.h>
#include <SDL/SDL_image.h>
using namespace std;

class Drawable
{
protected:
	SDL_Surface	*mySurface;
	SDL_Rect	myRect;

	void free();

public:
	Drawable();
	Drawable(const char *filename);
	Drawable(SDL_Surface *surf);
	~Drawable();

	int	create(int w, int h, int alpha = 0xFF);
	int	createnoalpha(int w, int h);
	int	load(const char *filename);
	void	blit(Drawable & second, int x, int y);

	inline	SDL_Rect *rect()
	{
		return &myRect;
	}

	inline	SDL_Surface *surf()
	{
		return mySurface;
	}
	
	inline	int w()
	{
		return myRect.w;
	}

	inline	int h()
	{
		return myRect.h;
	}
};

#endif
