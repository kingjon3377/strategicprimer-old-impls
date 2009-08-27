
//#include <iostream>
#include "Position.h"
using namespace std;

Position::Position(int tx, int ty){
	x = tx;
	y = ty;
}

Position::~Position(){
}

Position::Position(const Position &cpypos){
	x = cpypos.x;
	y = cpypos.y;
}

Position& Position::operator=(const Position& p){
	x = p.x;
	y = p.y;
}

Position operator+(const Position& a, const Position& b){
	return Position(a.x + b.x, a.y + b.y);
}

Position operator-(const Position& a, const Position& b){
	return Position(a.x - b.x, a.y - b.y);
}

bool operator==(const Position& a, const Position& b){
	return (a.x == b.x && a.y == b.y);
}

bool operator!=(const Position& a, const Position& b){
	return (a.x != b.x || a.y != b.y);
}
