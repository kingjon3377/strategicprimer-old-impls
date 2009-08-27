
#include <string.h>		// memset
#include "ResourceArray.h"
using namespace std;

ResourceArray::ResourceArray()
{
	memset(myValues, 0, sizeof(myValues));
}

ostream & ResourceArray::print(ostream & out)
{
	for (int i = 0; i < RES_MAX; i++)
		out << myValues[i] << ((i < RES_MAX-1) ? "," : "");
	return out;
}

ResourceArray & ResourceArray::operator+=(const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		myValues[i] += b.myValues[i];
	return *this;
}

ResourceArray & ResourceArray::operator-=(const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		myValues[i] -= b.myValues[i];
	return *this;
}

bool operator>(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] <= b.myValues[i])
			return false;
	return true;
}

bool operator>=(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] < b.myValues[i])
			return false;
	return true;
}

bool operator<(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] >= b.myValues[i])
			return false;
	return true;
}

bool operator<=(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] > b.myValues[i])
			return false;
	return true;
}

ResourceArray operator+(const ResourceArray & a, const ResourceArray & b)
{
	ResourceArray ra;
	for (int i = 0; i < RES_MAX; i++)
		ra.myValues[i] = a.myValues[i] + b.myValues[i];
	return ra;
}

ResourceArray operator-(const ResourceArray & a, const ResourceArray & b)
{
	ResourceArray ra;
	for (int i = 0; i < RES_MAX; i++)
		ra.myValues[i] = a.myValues[i] - b.myValues[i];
	return ra;
}

