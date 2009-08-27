
#ifndef _CONSOLE_H_
#define _CONSOLE_H_ _CONSOLE_H_

#include <SDL/SDL_ttf.h>
#include "Drawable.h"
using namespace std;

#define CONSOLE_BORDER 2
#define CONSOLE_BUFFER 256

class Console
{
protected:
	Drawable myDrawable;
	int	myWidth;
	int	myHeight;
	TTF_Font *myFont;
	int	myFontHeight;
	SDL_Color myFontColor;
	Drawable **myLines;
	int	myNumLines;
	int	myCurrentLine;
	char	myBuffer[CONSOLE_BUFFER];
	int	myInputPosition;

public:
	Console();
	~Console();
	void	draw();
	void	write(const char *msg);
	void	type(int chr);
};

#endif
