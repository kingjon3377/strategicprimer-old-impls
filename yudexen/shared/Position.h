/* Position.h
 * Purpose: An object-oriented, data-hiding 2D coordinate class.
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish and
 * John van Enck.
 * Input and output: Only internal.
 * How to use: Use anywhere X and Y coordinates would ordinarily be
 * used.
 * Major algorithms and data structures: Basic OO concepts.
 * Key variables: x, y
 */
#ifndef T_POSITION
#define T_POSITION T_POSITION

using namespace std;

class Position{
public:
	int x;
	int y;

	Position(int tx = 0, int ty = 0);
	Position(const Position &cpypos);
	~Position();
	
	Position& operator=(const Position&);

	friend bool operator==(const Position& a, const Position& b);
	friend bool operator!=(const Position& a, const Position& b);
	friend Position operator+(const Position& a, const Position& b);
	friend Position operator-(const Position& a, const Position& b);
};

#endif

