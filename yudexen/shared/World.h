
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
