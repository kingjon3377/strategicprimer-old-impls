
#include <iostream>
#include <fstream>
#include "UnitDefs.h"
using namespace std;

UnitDefs::UnitDefs()
{
	for (int i = 0; i < UNITS_MAX; i++)
	{
		myUnitDefs[i].enabled = 0;
		
	}
}

void	UnitDefs::load(char *filename)
{
	ifstream in(filename);
	if (!in.is_open())
	{
		cerr << "Could not open " << filename << endl;
		return;
	}
	int unitid = -1;
	char readbuf[4000];
	string sin;
	while (getline(in, sin))
	{
		int len = sin.length();
		if (len < 1)
			continue;
		if (sin[0] == '#')
			continue;
		if (len > 2 && sin[0] == '[' && sin[len-1] ==']')
		{
			string sid = sin.substr(1,len-2);
			unitid = atoi(sid.c_str());
			if (unitid >= UNITS_MAX)
				unitid = -1;
			continue;
		}
		if (unitid < 0)
			continue;
		int pos = sin.find("=", 0);
		if (pos != string::npos)
		{
			string attr = sin.substr(0, pos);
			string val = sin.substr(pos+1, len-pos-1);
			if (attr == "Name")
				myUnitDefs[unitid].name = val;
			else if (attr == "Width")
				myUnitDefs[unitid].unit.setWidth(atoi(val.c_str()));
			else if (attr == "Height")
				myUnitDefs[unitid].unit.setHeight(atoi(val.c_str()));
			else if (attr == "Cost")
			{
				vector<string> rsrcs = split_string(val, ',');
				for (int i = 0; i < max(RES_MAX, (int)rsrcs.size()); i++)
					myUnitDefs[unitid].cost.resource(i) = atoi(rsrcs[i].c_str());
			}
			else if (attr == "BuildOn")
				myUnitDefs[unitid].buildMask = atoi(val.c_str());
			else if (attr == "Moveable")
				myUnitDefs[unitid].moveable = atoi(val.c_str());
		}
	}
	in.close();
}

vector<string> split_string(const string & s, char delim)
{
	vector<string> res;
	res.push_back(string(""));
	int on = 0;
	int pos;
	for (pos = 0; pos < s.length(); pos++)
	{
		if (s[pos] != delim)
		{
			/* append to current substring */
			res[on] += s[pos];
		}
		else
		{
			on++;
			res.push_back(string(""));
		}
	}
	return res;
}

