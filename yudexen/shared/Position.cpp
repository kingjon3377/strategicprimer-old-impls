/* Position.cpp
 * Purpose: Implements the Position class.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish, and
 * John van Enck.
 * See Position.h
 */
//#include <iostream>
#include "Position.h"
using namespace std;

/* Position::Position()
 * Purpose: Construct the Position object with the given coordinates.
 * Precondition: None.
 * Postcondition: The Position object's coordinates match the given
 * values.
 */
Position::Position(int tx, int ty){
	x = tx;
	y = ty;
}

/* Position::~Position()
 * Purpose: Clean up after the Position object.
 * Preconditions: None
 * Postconditions: None
 * Why does this object have a declared/defined destructor?
 */
Position::~Position(){
}

/* Position::Position(const Position&)
 * Purpose: Create a Position object equal to the given Position
 * object.
 * Preconditions: None
 * Postconditions: The Position object's coordinates are equal to
 * those of the given object, so operator==() will return true.
 * Why not use the built-in (bit-by-bit) copy constructor?
 */
Position::Position(const Position &cpypos){
	x = cpypos.x;
	y = cpypos.y;
}

/* Position& Position::operator=()
 * Purpose: Make this Position object equal to the given one.
 * Preconditions: None
 * Postconditions: The Position object's coordinates are equal to
 * those of the given object, so operator==() will return true.
 * I seem to recall that there is a bit-by-bit assignment operator
 * defined; if so, why not use it?
 */
Position& Position::operator=(const Position& p){
	x = p.x;
	y = p.y;
}

/* Position operator+(Position&, Position&)
 * Purpose: Add two Position objects together.
 * Preconditions: None.
 * Postconditions: None.
 * Returns: A Position object, the sum (as in vector addition) of the
 * addends.
 */
Position operator+(const Position& a, const Position& b){
	return Position(a.x + b.x, a.y + b.y);
}

/* Position operator-(Position&, Position&)
 * Purpose: Subtract one Position object from another.
 * Preconditions: None
 * Postconditions: None
 * Returns: A Position object, the difference (as in vector
 * subtraction) of the arguments.
 */
Position operator-(const Position& a, const Position& b){
	return Position(a.x - b.x, a.y - b.y);
}

/* bool operator==(Position&, Position&)
 * Purpose: Determine whether two Position objects are equal.
 * Preconditions: None
 * Postconditions: None
 * Returns: True if each coordinate of each Position equals the same
 * coordinate of the other, false otherwise.
 */
bool operator==(const Position& a, const Position& b){
	return (a.x == b.x && a.y == b.y);
}

/* bool operator!=(Position&, Position&
 * Purpose: Determine whether two Position objects are inequal.
 * Preconditions: None.
 * Postconditions: None.
 * Returns: False if each coordinate of each Position equals the same
 * coordinate of the other, true otherwise.
 */
bool operator!=(const Position& a, const Position& b){
	return (a.x != b.x || a.y != b.y);
}
