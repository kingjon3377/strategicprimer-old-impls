/* UnitDefs.h
 * Purpose: An object to hold information on the initial stats and
 * construction costs of every unit in the game.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brum, and John van Enck.
 * Input and output: Only internal.
 * How to use: The Game object has one of these, which it loads from
 * disk.
 * Assumptions: (Need something here)
 * Exceptions: (Need something here)
 * Major algorithms and data structures: struct unit_def_t, the
 * string-splitting algorithm.
 * Key variables: myUnitDefs
 */
#ifndef _UNITDEFS_H_
#define _UNITDEFS_H_ _UNITDEFS_H_

#include <string>
#include <vector>
#include "shared/Unit.hpp"
#include "shared/ResourceArray.hpp"
using namespace std;

#define UNITS_MAX 2

typedef struct
{
	int		enabled;
	string		name;
	ResourceArray	cost;
	Unit		unit;
	unsigned int	buildMask;
	int		moveable;
} unit_def_t;

class UnitDefs
{
protected:
	unit_def_t	myUnitDefs[UNITS_MAX];

public:
	UnitDefs();
	void	load(char *filename);

	inline unit_def_t *getUnitDef(int id)
	{
		return (id >= 0 && id < UNITS_MAX) ? myUnitDefs + id : NULL;
	}
};

vector<string> split_string(const string & s, char delim);

#endif
