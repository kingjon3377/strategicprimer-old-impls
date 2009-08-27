
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

