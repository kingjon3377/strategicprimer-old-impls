
# To build cleanly from scratch:
# make clean && make depend && make

PREFIX = bin
CFLAGS = -I. `sdl-config --cflags`
CXX=g++
CXXFLAGS=$(CFLAGS) -fno-rtti -fno-exceptions
LIBS = -lpthread
CLIENT_LIBS = $(LIBS) `sdl-config --libs` -lSDL_image -lSDL_ttf

SRC=shared/Connection.cpp shared/Game.cpp shared/Host.cpp shared/Message.cpp \
	shared/Network.cpp shared/Player.cpp shared/Position.cpp \
	shared/ResourceArray.cpp shared/Unit.cpp shared/World.cpp \
	shared/UnitDefs.cpp \
	client/Client-draw.cpp client/Client.cpp client/Console.cpp \
	client/Drawable.cpp client/Graphics.cpp client/MiniMap.cpp \
	client/Client-unit.cpp \
	server/Server.cpp

SHARED_OBJS=shared/Connection.o shared/Game.o shared/Host.o shared/Message.o \
	shared/Network.o shared/Player.o shared/Position.o \
	shared/ResourceArray.o shared/Unit.o shared/World.o shared/UnitDefs.o
CLIENT_OBJS=client/Client-draw.o client/Client.o client/Console.o \
	client/Drawable.o client/Graphics.o client/MiniMap.o \
	client/Client-unit.o
SERVER_OBJS=server/Server.o

.PHONY:	all client server clean depend

all:	client server

client: $(CLIENT_OBJS) $(SHARED_OBJS)
	g++ -o $(PREFIX)/client $(CLIENT_LIBS) $(CLIENT_OBJS) $(SHARED_OBJS)

server: $(SERVER_OBJS) $(SHARED_OBJS)
	g++ -o $(PREFIX)/server $(LIBS) $(SERVER_OBJS) $(SHARED_OBJS)

clean:
	-rm -f $(PREFIX)/client $(PREFIX)/server
	-find . -name '*~' | xargs rm -f
	-find . -name '*.o' | xargs rm -f

depend:
	makedepend -- $(CFLAGS) $(CXXFLAGS) -- $(SRC)

# The following is used by 'make' to automatically
#  generate dependency information
# DO NOT DELETE
