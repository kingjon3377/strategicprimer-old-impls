/*
Contributors:

John Van Enk
*/

#ifndef _GAME_H_
#define _GAME_H_ _GAME_H_

class Game;

#include <vector>
#include <queue>
#include "shared/Unit.h"
#include "shared/Player.h"
#include "shared/Connection.h"
#include "shared/World.h"
#include "shared/UnitDefs.h"
using namespace std;

class Game
{
protected:
	vector<Unit *>	myUnits;
	queue<unitid_t>	myMovingUnits;
	Connection	*myConnection;
	int		myMaster;
	vector<Player *> myPlayers;
	UnitDefs	myUnitDefs;

public:
	World		*world;

	/* Master Game constructor */
	Game();
	/* Client Game constructor */
	Game(char *server);
	~Game();

	void	action();
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
