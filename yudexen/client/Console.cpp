
#include <SDL/SDL.h>
#include <SDL/SDL_ttf.h>
#include <iostream>
#include "Console.h"
#include "Graphics.h"
#include "Drawable.h"
#include "shared/game_defs.h"
using namespace std;

extern Graphics *graphics;
extern TTF_Font *gameFont;

Console::Console()
{
	myFontHeight = TTF_FontHeight(gameFont);

	/* Create the main Console Drawable */
	myWidth = WIDTH;
	myHeight = HEIGHT / 3;
	myHeight -= (myHeight - CONSOLE_BORDER - 1) % myFontHeight;
	myDrawable.create(myWidth, myHeight);

	/* And fill the pixels for it */
	PTYPE *pixels = (PTYPE *) myDrawable.surf()->pixels;
	int npixels = myDrawable.w() * myDrawable.h();
	PTYPE aVal = 0x66 << myDrawable.surf()->format->Ashift;
	PTYPE bVal = (0xFF << myDrawable.surf()->format->Ashift) |
			(0xFFFFFFFF & myDrawable.surf()->format->Gmask);
	for (int i = 0; i < npixels - myWidth*CONSOLE_BORDER; i++)
		*pixels++ = aVal;
	for (int i = 0; i < myWidth*CONSOLE_BORDER; i++)
		*pixels++ = bVal;

	/* Console messages */
	myNumLines = (myHeight - CONSOLE_BORDER) / myFontHeight - 1;
	myLines = new Drawable*[myNumLines];
	for (int i = 0; i < myNumLines; i++)
		myLines[i] = NULL;
	myCurrentLine = 0;
	myBuffer[0] = '>';
	myBuffer[1] = 0;
	myInputPosition = 1;
	myFontColor.r = myFontColor.g = myFontColor.b = 255;
}

Console::~Console()
{
	for (int l = 0; l < myNumLines; l++)
	{
		if (myLines[l])
			delete myLines[l];
	}
}

void	Console::draw()
{
	graphics->draw(myDrawable, 0, 0);
	int n = myCurrentLine;
	for (int l = 0; l < myNumLines; l++)
	{
		if (myLines[n])
			graphics->draw(*myLines[n], 2, myHeight -
				CONSOLE_BORDER - 1 - myFontHeight * (l + 2));
		n = (n + 1) % myNumLines;
	}
	Drawable d(TTF_RenderText_Solid(gameFont, myBuffer, myFontColor));
	graphics->draw(d, 2, myHeight - CONSOLE_BORDER - myFontHeight);
}

void	Console::write(const char *msg)
{
	if (!*msg)
		return;
	myCurrentLine = (myCurrentLine + myNumLines - 1) % myNumLines;
	if (myLines[myCurrentLine])
		delete myLines[myCurrentLine];
	SDL_Surface *surf = TTF_RenderText_Solid(gameFont, msg, myFontColor);
	myLines[myCurrentLine] = new Drawable(surf);
}

void	Console::type(int chr)
{
	if (chr == SDLK_BACKSPACE)
	{
		if (myInputPosition > 1)
			myBuffer[--myInputPosition] = 0;
	}
	else if (chr == '\n' || chr == '\r')
	{
		write(myBuffer + 1);
		parse(myBuffer + 1);
		myInputPosition = 1;
		myBuffer[myInputPosition] = 0;
	}
	else if (chr < 32 || chr > 126)
		return;
	else if (myInputPosition < CONSOLE_BUFFER - 2)
	{
		myBuffer[myInputPosition++] = chr;
		myBuffer[myInputPosition] = 0;
	}
}

void	Console::parse(const char *msg)
{
	if (strncmp(msg, "new ", 4)) {
		// Create a new unit, parsing parameters
	}
	return;
}
