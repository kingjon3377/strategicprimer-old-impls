/* Client.cpp
 * Purpose: Implements the game's graphical client [q.v. Client.h]
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop and John Van Enk.
 * Input and output: Only internal (?)
 * How to use: See each function (Need a more general here)
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: (need something here)
 * Key variables: See Client.h; the global Graphics.
 */

#include <sstream>
#include <iostream>
#include <SDL/SDL.h>
#include <SDL/SDL_ttf.h>
#include "Client.h"
#include "Graphics.h"
#include "Console.h"
#include "shared/game_defs.h"
#include "shared/Game.h"
using namespace std;

Graphics *graphics;

void	mainloop();

void	usage()
{
	cout	<<	"Usage:"
			"\t--help     display this help" << endl
		<<	"\t-f         fullscreen" << endl
		<<	"\t-s<server> (required) connect to <server>" << endl
		<<	"\t-n<name>   set player name to <name>" << endl
		<<	"\t-F<fps>    attempt to achieve <fps> frames per second" << endl
		<<	"\t-i         do not grab input" << endl;
	exit(1);
}

int main(int argc, char **argv)
{
	int fullscreen = 0;
	char *server = NULL;
	char *name = "Player";
	unsigned int delay = CLIENT_DELAY;
	int grab = 1;
	for (int i = 0; i < argc; i++)
	{
		if (!strcmp(argv[i], "--help"))
			usage();
		else if (!strcmp(argv[i], "-f"))
			fullscreen = 1;
		else if (!strcmp(argv[i], "-i"))
			grab = 0;
		else if (!strncmp(argv[i], "-s", 2))
		{
			if (strlen(argv[i]) > 2)
				server = argv[i] + 2;
			else
			{
				if (i >= argc - 1)
					usage();
				server = argv[++i];
			}
		}
		else if (!strncmp(argv[i], "-n", 2))
		{
			if (strlen(argv[i]) > 2)
				name = argv[i] + 2;
			else
			{
				if (i >= argc - 1)
					usage();
				name = argv[++i];
			}
		}
		else if (!strncmp(argv[i], "-F", 2))
		{
			if (strlen(argv[i]) > 2)
				delay = atoi(argv[i] + 2);
			else
			{
				if (i >= argc - 1)
					usage();
				delay = atoi(argv[++i]);
			}
			if (delay < 1)
				usage();
			delay = 1000 / delay;
		}
	}
	if (!server)
		usage();

	graphics = new Graphics(GAME_TITLE, fullscreen);

	Client client(server, name);		/* logs into server */
	if (grab)
		client.grabInput();
	client.setDelay(delay);
	client.mainloop();
}

Client::Client(char *server, char *name)
{
	myFontColor.r = myFontColor.g = myFontColor.b = 255;
	myMilliseconds = myLastMilliseconds = 0;
	for (int i = 0; i < RES_MAX; i++)
		myResourceLevelSurfaces[i] = NULL;
	myConsolePos = 0;
	loadDrawables();
	myWorld.createnoalpha(WIDTH, HEIGHT);
	myWorldx = myWorldy = 0;
	myWorldxIncrement = myWorldyIncrement = 0;
	if (myServer = server)
		myGame = new Game(server);	/* client Game object */
	else
		myGame = new Game();		/* master Game object */
	/* Do these after we have a Game object */
	myGame->registerMessageCallback(Client_Message_Callback);
	myGame->login(string(name));
	myWorldNeedsUpdating = 1;
	myMapNeedsUpdating = 1;
	myResourcesNeedsUpdating = 1;
	myRedrawEvent.type = SDL_USEREVENT;
	myRedrawEvent.user.code = 0;
	waitForLoginAck();
	myMap.create(myGame->world);
	myIAmDrawing = 0;
	myItemListVisible = 0;
	myItemListPosition = 0;
	myChoosingUnitType = -1;
	myMousex = myMousey = 0;
	myDraggingMinimap = 0;
}

Client::~Client()
{
	delete myGame;
	delete[] myTerrainDrawables;
	delete[] myResourceDrawables;
	delete[] myUnitDrawables;
	TTF_Quit();
}

void	Client::waitForLoginAck()
{
	SDL_Event event;
	Message		*msg;
	message_t	*m;
	int done = 0;
	while(!done)
	{
		if (graphics->wait_event(&event))
		{
			if (event.type == SDL_USEREVENT && event.user.code == 1)
			{
				msg = (Message *) event.user.data1;
				m = msg->getBufferMessage();
				if (m->type == MSG_LOGIN_ACK)
				{
					myPlayerID = m->login_playerid;
					myPlayerName = string(m->login_name);
					cout << "Logged in successfully with user name '" << myPlayerName << "'" << endl;
					myGame->addPlayer(myPlayerID, myPlayerName);
					myGame->loadMap(m->map_name);
					done = 1;
				}
				delete msg;
			}
		}
	}
}

void	Client::grabInput()
{
	SDL_WM_GrabInput(SDL_GRAB_ON);
}

void	Client::setDelay(unsigned int delay)
{
	myRedrawTimerID = SDL_AddTimer(delay,
		(SDL_NewTimerCallback) Client_Timer_Callback, this);
}

Uint32	Client_Timer_Callback(Uint32 interval, Client *c)
{
	if (!c->myIAmDrawing)
		SDL_PushEvent(&c->myRedrawEvent);
	return interval;
}

void	Client_Message_Callback(Message *msg)
{
	SDL_Event event;
	event.type = SDL_USEREVENT;
	event.user.code = 1;			// Message available
	event.user.data1 = msg;
	SDL_PushEvent(&event);
}

void	Client::mainloop()
{
	SDL_Event	event;
	Message		*msg;
	message_t	*m;
	int		i;
	stringstream	ss;
	for (;;)
	{
		if (graphics->wait_event(&event))
		{
			SDLKey key = event.key.keysym.sym;
			switch (event.type)
			{
			case SDL_QUIT:
				logout();
				exit(0);
			case SDL_KEYDOWN:
				if (key == SDLK_F2)
				{
					ss.str(string(""));
					ss << "FPS: ";
					ss << myCurrentFPS;
					myConsole.write(ss.str().c_str());
					break;
				}
				else if (key == SDLK_F1)
				{
					SDL_WM_GrabInput(SDL_WM_GrabInput(SDL_GRAB_QUERY) == SDL_GRAB_ON ? SDL_GRAB_OFF : SDL_GRAB_ON);
					break;
				}
				if (myConsolePos)	/* Console is active */
				{
					if (key == SDLK_BACKQUOTE || key == SDLK_ESCAPE)
					{
						myConsolePos = 0;
						break;
					}
					myConsole.type(event.key.keysym.unicode);
					break;
				}
				switch (key)
				{
				case SDLK_q:
					logout();
					exit(0);
				case SDLK_BACKQUOTE:
					myConsolePos = !myConsolePos;
					break;
				case SDLK_TAB:
					myItemListVisible = !myItemListVisible;
					break;
				}
				break;
			case SDL_MOUSEBUTTONDOWN:
				cout << "(" << event.button.x << ", " << event.button.y << ")" << endl;
//				button = event.button.button;
//				cout << "Button: " << button << endl;
//				cout << "  World grid position: " <<
//					getWorldGridx(event.button.x) <<
//					", " <<
//					getWorldGridy(event.button.y) << endl;
				switch(event.button.button)
				{
					case SDL_BUTTON_LEFT:
						if (myItemListPosition)
						{
						}
						else if (event.button.x < myMap.getWidth() && event.button.y > (HEIGHT - myMap.getHeight()))
						{
							/* user clicked minimap, change world position */
							warpToPosition(event.button.x, event.button.y);
							myDraggingMinimap = 1;
							break;
						}
						else if (myChoosingUnitType >= 0)	/* client requests a unit */
						{
							requestUnit(myChoosingUnitType, Position(getWorldGridx(event.button.x - 12) * GRID_SIZE, getWorldGridy(event.button.y - 12) * GRID_SIZE));
							myChoosingUnitType = -1;
						}
						break;
					case SDL_BUTTON_RIGHT:
						myChoosingUnitType = 1;
						myChoosingUnitDrawable.create(myUnitDrawables[myChoosingUnitType]->w(), myUnitDrawables[myChoosingUnitType]->h(), 0x44);
						myChoosingUnitDrawable.blit(*myUnitDrawables[myChoosingUnitType], 0, 0);
						break;
				}
				break;
			case SDL_MOUSEBUTTONUP:
				myDraggingMinimap = 0;
				break;
			case SDL_MOUSEMOTION:
				myMousex = event.motion.x;
				myMousey = event.motion.y;
				if (myDraggingMinimap)
				{
					warpToPosition(myMousex, myMousey);
					break;
				}
				if (event.motion.x < SCROLL_BORDER)
					myWorldxIncrement = -WORLD_SCROLL_SPEED;
				else if (event.motion.x > (WIDTH - SCROLL_BORDER))
					myWorldxIncrement = WORLD_SCROLL_SPEED;
				else
					myWorldxIncrement = 0;
				if (event.motion.y < SCROLL_BORDER)
					myWorldyIncrement = -WORLD_SCROLL_SPEED;
				else if (event.motion.y > (HEIGHT - SCROLL_BORDER))
					myWorldyIncrement = WORLD_SCROLL_SPEED;
				else
					myWorldyIncrement = 0;
				break;
			case SDL_USEREVENT:
				if (event.user.code == 0)	// redraw event
				{
					myGame->action();
					redraw();
				}
				else if (event.user.code == 1)	// message
				{
					msg = (Message *) event.user.data1;
					m = msg->getBufferMessage();
					switch (m->type)
					{
					case MSG_RESOURCE_LEVELS:
//						cout << "Client got resource levels message" << endl;
						for (i = 0; i < RES_MAX; i++)
						{
							myGame->player(myPlayerID)->resources().resource(i) = m->resourceLevels[i];
							myGame->player(myPlayerID)->resourcesDiscovered().resource(i) = m->resourcesDiscovered[i];
						}
						myResourcesNeedsUpdating = 1;
						break;
					case MSG_NEW_PLAYER_BC:
						myGame->addPlayer(m->login_playerid, string(m->login_name));
						break;
					case MSG_DEPART_BC:
//						myGame->delPlayer(m->login_playerid);
						break;
					case MSG_NEW_UNIT_BC:	/* new unit */
						myGame->createUnit(m->unit_owner, m->unit_type, m->unit_x, m->unit_y, m->unit_id);
						break;
					}
					delete msg;
				}
				break;
			}
		}
	}
}

void	Client::warpToPosition(int mousex, int mousey)
{
	myWorldx = mousex * GRID_SIZE * myGame->world->width() / myMap.getWidth() - (WIDTH / 2);
	myWorldy = (mousey - HEIGHT + myMap.getHeight()) * GRID_SIZE * myGame->world->height() / myMap.getHeight() - (HEIGHT / 2);
	if (myWorldx < 0)
		myWorldx = 0;
	if (myWorldx > ((myGame->world->width() * GRID_SIZE) - WIDTH))
		myWorldx = (myGame->world->width() * GRID_SIZE) - WIDTH;
	if (myWorldy < 0)
		myWorldy = 0;
	if (myWorldy > ((myGame->world->height() * GRID_SIZE) - HEIGHT))
		myWorldy = (myGame->world->height() * GRID_SIZE) - HEIGHT;
	myWorldNeedsUpdating = myMapNeedsUpdating = 1;
	myWorldxIncrement = myWorldyIncrement = 0;
}

void	Client::requestUnit(int type, Position pos)
{
	Message msg;
	message_t *m = msg.getBufferMessage();
	m->type = MSG_NEW_UNIT_REQ;
	m->unit_owner = myPlayerID;
	m->unit_type = type;
	m->unit_x = pos.x;
	m->unit_y = pos.y;
	myGame->getConnection()->sendMessage(&msg);
}

