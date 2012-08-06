
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <iostream>	// NULL, of all things
#include "World.hpp"
using namespace std;

World::World()
{
	myMap = NULL;
//	myFixedUnits = NULL;
	myWidth = myHeight = 0;
}

World::~World()
{
	if (myMap)
		delete[] myMap;
//	if (myFixedUnits)
//		delete[] myFixedUnits;
}

int	World::loadMap(char *map)
{
	string mapfilename = "data/maps/";
	mapfilename += map;
	if (myMap)
		delete[] myMap;
//	if (myFixedUnits)
//		delete[] myFixedUnits;
	myWidth = myHeight = 0;
	myMap = NULL;
	struct stat s;
	if (stat(mapfilename.c_str(), &s))
		return -1;			/* Couldn't stat file */
	FILE *fil;
	if ((fil = fopen(mapfilename.c_str(), "rb")) == NULL)
		return -2;			/* Couldn't open file */

	char *buf = new char[s.st_size];
	fread(buf, 1, s.st_size, fil);
	fclose(fil);

	unsigned int map_data = 0;
	unsigned int tile_data = 0;
	unsigned int spawn_data = 0;
	map_head_ent_t *he = (map_head_ent_t *) buf;
	/* Read through the map headers */
	while (he->type != MAP_HEAD_END)
	{
		switch(he->type)
		{
		case MAP_HEAD_WIDTH:	myWidth = he->value;	break;
		case MAP_HEAD_HEIGHT:	myHeight = he->value;	break;
		case MAP_HEAD_MAP:	map_data = he->value;	break;
		case MAP_HEAD_TILE:	tile_data = he->value;	break;
		case MAP_HEAD_SPAWN:	spawn_data = he->value;	break;
		}
		he++;
	}
	/* Check for bad data */
	if (myWidth == 0 || myHeight == 0 || map_data == 0 ||
		(map_data + myWidth * myHeight) > s.st_size ||
		tile_data >= s.st_size ||
		spawn_data >= s.st_size)
	{
		delete[] buf;
		myWidth = myHeight = 0;
		return -3;
	}

	/* Process Map Data */
	myMap = new unsigned int[myWidth * myHeight];
	memcpy(myMap, buf + map_data, myWidth * myHeight * 4);
//	myFixedUnits = new unsigned int[myWidth * myHeight];
//	memset(myFixedUnits, 0, myWidth * myHeight * sizeof(unsigned int));

	/* TODO: Process Tile Data */

	/* TODO: Process Team Data */

	/* Finished, deallocate buffer */
	delete[] buf;
	return 0;
}
