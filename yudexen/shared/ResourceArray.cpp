/* ResourceArray.cpp
 * Purpose: Implements the ResourceArray class, which represents a
 * quantity of each resource in the game.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish and
 * John van Enck.
 * See ResourceArray.h
 */
#include <string.h>		// memset
#include "ResourceArray.h"
using namespace std;

/* ResourceArray::ResourceArray()
 * Purpose: Initialize the ResourceArray to zero of each resource.
 * Precondition: None.
 * Postcondition: Each element in the array of resource quantities is
 * zero.
 */
ResourceArray::ResourceArray() // Initialize them all to 0
{
	memset(myValues, 0, sizeof(myValues));
}

/* ostream& ResourceArray::print(ostream&)
 * Purpose: Print the resource quantities to the output stream in a
 * comma-separated list.
 * Precondition: None.
 * Postcondition: The resource quantities and appropriate commas and
 * spaces have been pushed into the ostream.
 * Returns: The ostream.
 * Shouldn't this function be const?
 */
ostream & ResourceArray::print(ostream & out)
{
	for (int i = 0; i < RES_MAX; i++)
		out << myValues[i] << ((i < RES_MAX-1) ? "," : "");
	return out;
}

/* ResourceArray& ResourceArray::operator+=(ResourceArray&)
 * Purpose: Add the given ResourceArray to this one.
 * Precondition: None.
 * Postcondition: Every element in this ResourceArray has been
 * incremented by the corresponding element in the given.
 * Returns: This ResourceArray.
 */
ResourceArray & ResourceArray::operator+=(const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		myValues[i] += b.myValues[i];
	return *this;
}

/* ResourceArray& ResourceArray::operator-=(ResourceArray&)
 * Purpose: Subtract the given ResourceArray from this one.
 * Precondition: None.
 * Postcondition: Every element in this ResourceArray has been
 * decremented by the corresponding element in the given.
 * Returns: This ResourceArray.
 */
ResourceArray & ResourceArray::operator-=(const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		myValues[i] -= b.myValues[i];
	return *this;
}

/* bool operator>(ResourceArray&,ResourceArray&)
 * Purpose: Determine whether one ResourceArray is greater than
 * another.
 * Precondition: None.
 * Postcondition: None.
 * Returns: True if every element in the first ResourceArray is
 * greater than the corresponding element in the second, false
 * otherwise.
 */
bool operator>(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] <= b.myValues[i])
			return false;
	return true;
}

/* bool operator>=(ResourceArray&,ResourceArray&)
 * Purpose: Determine whether one ResourceArray is greater than
 * or equal to another.
 * Precondition: None.
 * Postcondition: None.
 * Returns: True if every element in the first ResourceArray is
 * greater than or equal to the corresponding element in the second,
 * false otherwise.
 */
bool operator>=(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] < b.myValues[i])
			return false;
	return true;
}

/* bool operator>(ResourceArray&,ResourceArray&)
 * Purpose: Determine whether one ResourceArray is less than another.
 * Precondition: None.
 * Postcondition: None.
 * Returns: True if every element in the first ResourceArray is
 * less than the corresponding element in the second, false otherwise.
 */
bool operator<(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] >= b.myValues[i])
			return false;
	return true;
}

/* bool operator>=(ResourceArray&,ResourceArray&)
 * Purpose: Determine whether one ResourceArray is less than or equal
 * to another.
 * Precondition: None.
 * Postcondition: None.
 * Returns: True if every element in the first ResourceArray is less
 * than or equal to the corresponding element in the second, false
 * otherwise.
 */
bool operator<=(const ResourceArray & a, const ResourceArray & b)
{
	for (int i = 0; i < RES_MAX; i++)
		if (a.myValues[i] > b.myValues[i])
			return false;
	return true;
}

/* ResourceArray operator+(ResourceArray&,ResourceArray&)
 * Purpose: Add two ResourceArrays.
 * Precondition: None
 * Postcondition: None
 * Returns: The sum, as in vector addition, of the two addends.
 */
ResourceArray operator+(const ResourceArray & a, const ResourceArray & b)
{
	ResourceArray ra;
	for (int i = 0; i < RES_MAX; i++)
		ra.myValues[i] = a.myValues[i] + b.myValues[i];
	return ra;
}

/* ResourceArray operator-(ResourceArray&,ResourceArray&)
 * Purpose: Subtract two ResourceArrays.
 * Precondition: None
 * Postcondition: None
 * Returns: The difference, as in vector subtraction, of the two operands.
 */
ResourceArray operator-(const ResourceArray & a, const ResourceArray & b)
{
	ResourceArray ra;
	for (int i = 0; i < RES_MAX; i++)
		ra.myValues[i] = a.myValues[i] - b.myValues[i];
	return ra;
}

