/* Game.h
 * Purpose: The primary object in the backend of the game.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brum, and John van Enck.
 * Input and output: Only internal.
 * How to use: Each instance of the program has one instance of this
 * object, with the myMaster flag showing whether this is a client or
 * server. (The flag is set by the constructor, depending on which
 * version of it is called.) This object is the access point to the
 * network (via the Connection object), all the units, the movement
 * queue, all the unit definitions, all the players, and the
 * game-world.
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: Client-server implementation;
 * data hiding; Unit class; Player class; Connection class; World
 * class
 * Key variables: *world,  myMovingUnits, *myConnection
 */

#ifndef _GAME_H_
#define _GAME_H_ _GAME_H_

class Game;

#include <vector>
#include <queue>
#include "shared/Unit.hpp"
#include "shared/Player.hpp"
#include "shared/Connection.hpp"
#include "shared/World.hpp"
#include "shared/UnitDefs.hpp"
using namespace std;

class Game
{
protected:
	vector<Unit *>	myUnits;
	queue<unitid_t>	myMovingUnits;
	Connection	*myConnection;
	int		myMaster; // Is this the server?
	vector<Player *> myPlayers;
	UnitDefs	myUnitDefs;

public:
	World		*world;

	/* Master Game constructor */
	Game();
	/* Client Game constructor */
	Game(char *server);
	~Game();

	void	action(); /* perform game actions, move units, etc... 
			   * (currently empty) */	
	int	login(string name);	/* create new Player */
	int	validName(const string & name);
	int	logout(int id);
	void	addPlayer(int id, string name);
	int	createUnit(int owner, int type, int x, int y, int id, int moveable = 0);

	inline vector<Unit *> & getUnits()
	{
		return myUnits;
	}

	inline int loadMap(char *map)
	{
		return world->loadMap(map);
	}

	inline void registerMessageCallback(message_callback_t mc)
	{
		myConnection->registerMessageCallback(mc);
	}

	inline Connection *getConnection() { return myConnection; }
	inline Message *getMessage() { return myConnection->getMessage(); }
	inline	int players() { return myPlayers.size(); }
	inline Player *player(int id)
	{
		return (id < 0 || id >= myPlayers.size())
			? NULL
			: myPlayers[id];
	}
};

#endif
