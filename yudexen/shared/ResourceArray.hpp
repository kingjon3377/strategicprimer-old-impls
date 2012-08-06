/* ResourceArray.h
 * Purpose: Represents a (possibly zero) quantity of each resource in
 * the game.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish and
 * John van Enck.
 * Input and output: Only internal.
 * How to use: Supports basic arithmetic (like vectors). Should
 * probably be replaced with vectors of a "Resource" struct
 * (containing the resource ID and quantity).
 * Major algorithms and data structures: Operator overloading.
 * Note symbolic constants defining the resources.
 */
#ifndef _RESOURCEARRAY_H_
#define _RESOURCEARRAY_H_ _RESOURCEARRAY_H_

#define RES_MAX		5
#define RES_YUDEXEN	0
#define RES_DUCT_TAPE	1
#define RES_ANTIMATTER	2
#define RES_CHOCOLATE	3
#define RES_CAFFEINE	4

#include <iostream>
using namespace std;

class ResourceArray
{
protected:
	int	myValues[RES_MAX];

public:
	ResourceArray();
	ostream & print(ostream & out);
	inline int & resource(unsigned int rsrc) { return myValues[rsrc]; }

	ResourceArray & operator+=(const ResourceArray & b);
	ResourceArray & operator-=(const ResourceArray & b);
	friend bool operator>(const ResourceArray & a, const ResourceArray & b);
	friend bool operator>=(const ResourceArray & a, const ResourceArray & b);
	friend bool operator<(const ResourceArray & a, const ResourceArray & b);
	friend bool operator<=(const ResourceArray & a, const ResourceArray & b);
	friend ResourceArray operator+(const ResourceArray & a, const ResourceArray & b);
	friend ResourceArray operator-(const ResourceArray & a, const ResourceArray & b);
};

inline ostream & operator<<(ostream & out, ResourceArray & r)
{
	return r.print(out);
}

#endif
