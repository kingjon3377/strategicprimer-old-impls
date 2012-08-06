/* World.h
 * Purpose: Represents the map, with its terrain data and fixed
 * elements (not yet implemented). Since for speed purposes the map is
 * just an int *, this object keeps track of its dimensions.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brum, and John Van Enck.
 * Input and output: Loads the map from disk in loadMap(). 
 * How to use: Only one of these should exist; one is created by the
 * Game object, and any other users should refer to it by pointer if
 * not going through the Game object's interface to it.
 * Assumptions: (need something here)
 * Exceptions: (need something here)
 * Major algorithms and data structures: map_head_ent_t,
 * map_head_tile_t, the map-loading code.
 * Key variables: myMap .
 * Analysis of this class will be spotty, since this wasn't my code,
 * it is entirely lacking in documentation, and it has been more than
 * a year since it was written.
 * Note symbolic constants -- they're used in the loading code, but
 * beyond that -- huh?
 */

#ifndef _WORLD_H_
#define _WORLD_H_ _WORLD_H_

using namespace std;

#define	MAP_HEAD_END	0
#define	MAP_HEAD_WIDTH	1
#define	MAP_HEAD_HEIGHT	2
#define	MAP_HEAD_MAP	3
#define	MAP_HEAD_TILE	4
#define	MAP_HEAD_SPAWN	5

typedef struct
{
	unsigned int type;
	unsigned int value;
} map_head_ent_t;

typedef struct
{
	unsigned int type;
	char	filename[];
} map_tile_ent_t;

class World
{
protected:
	int	myWidth;
	int	myHeight;
	unsigned int	*myMap;
//	unsigned int	*myFixedUnits;

public:
	World();
	~World();

	int	loadMap(char *map);

	inline unsigned int & at(unsigned int x, unsigned int y)
	{
		return myMap[y * myWidth + x];
	}

//	inline unsigned int & unitat(unsigned int x, unsigned int y)
//	{
//		return myFixedUnits[y * myWidth + x];
//	}

	inline unsigned int *map() { return myMap; }
//	inline unsigned int *fixedUnits() { return myFixedUnits; }

	inline int width() { return myWidth; }
	inline int height() { return myHeight; }
};

#endif
