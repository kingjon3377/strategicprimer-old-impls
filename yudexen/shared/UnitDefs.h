
#ifndef _UNITDEFS_H_
#define _UNITDEFS_H_ _UNITDEFS_H_

#include <string>
#include <vector>
#include "shared/Unit.h"
#include "shared/ResourceArray.h"
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
