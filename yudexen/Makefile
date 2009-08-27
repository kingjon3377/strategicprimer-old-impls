
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

shared/Connection.o: /usr/include/signal.h /usr/include/features.h
shared/Connection.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
shared/Connection.o: /usr/include/bits/sigset.h /usr/include/bits/types.h
shared/Connection.o: /usr/include/bits/wordsize.h
shared/Connection.o: /usr/include/bits/typesizes.h /usr/include/bits/signum.h
shared/Connection.o: /usr/include/pthread.h /usr/include/sched.h
shared/Connection.o: /usr/include/time.h /usr/include/bits/sched.h
shared/Connection.o: /usr/include/bits/pthreadtypes.h
shared/Connection.o: /usr/include/bits/setjmp.h shared/Connection.h
shared/Connection.o: shared/Network.h /usr/include/sys/types.h
shared/Connection.o: /usr/include/sys/socket.h /usr/include/sys/uio.h
shared/Connection.o: /usr/include/bits/uio.h /usr/include/bits/socket.h
shared/Connection.o: /usr/include/limits.h /usr/include/bits/sockaddr.h
shared/Connection.o: /usr/include/asm/socket.h /usr/include/asm/sockios.h
shared/Connection.o: /usr/include/netinet/ip.h /usr/include/netinet/in.h
shared/Connection.o: /usr/include/stdint.h /usr/include/bits/wchar.h
shared/Connection.o: /usr/include/bits/in.h /usr/include/endian.h
shared/Connection.o: /usr/include/bits/endian.h /usr/include/bits/byteswap.h
shared/Connection.o: /usr/include/netdb.h /usr/include/bits/netdb.h
shared/Connection.o: shared/game_defs.h shared/Message.h shared/Host.h
shared/Connection.o: shared/ResourceArray.h
shared/Game.o: shared/Game.h shared/Unit.h shared/ResourceArray.h
shared/Game.o: shared/Position.h shared/game_defs.h shared/Player.h
shared/Game.o: shared/Connection.h shared/Network.h /usr/include/sys/types.h
shared/Game.o: /usr/include/features.h /usr/include/sys/cdefs.h
shared/Game.o: /usr/include/gnu/stubs.h /usr/include/bits/types.h
shared/Game.o: /usr/include/bits/wordsize.h /usr/include/bits/typesizes.h
shared/Game.o: /usr/include/time.h /usr/include/sys/socket.h
shared/Game.o: /usr/include/sys/uio.h /usr/include/bits/uio.h
shared/Game.o: /usr/include/bits/socket.h /usr/include/limits.h
shared/Game.o: /usr/include/bits/sockaddr.h /usr/include/asm/socket.h
shared/Game.o: /usr/include/asm/sockios.h /usr/include/netinet/ip.h
shared/Game.o: /usr/include/netinet/in.h /usr/include/stdint.h
shared/Game.o: /usr/include/bits/wchar.h /usr/include/bits/in.h
shared/Game.o: /usr/include/endian.h /usr/include/bits/endian.h
shared/Game.o: /usr/include/bits/byteswap.h /usr/include/netdb.h
shared/Game.o: /usr/include/bits/netdb.h shared/Message.h shared/Host.h
shared/Game.o: shared/World.h shared/UnitDefs.h
shared/Host.o: shared/Host.h
shared/Message.o: shared/Message.h shared/Host.h shared/game_defs.h
shared/Message.o: shared/ResourceArray.h
shared/Network.o: /usr/include/unistd.h /usr/include/features.h
shared/Network.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
shared/Network.o: /usr/include/bits/posix_opt.h /usr/include/bits/types.h
shared/Network.o: /usr/include/bits/wordsize.h /usr/include/bits/typesizes.h
shared/Network.o: /usr/include/bits/confname.h /usr/include/stdio.h
shared/Network.o: /usr/include/libio.h /usr/include/_G_config.h
shared/Network.o: /usr/include/wchar.h /usr/include/bits/wchar.h
shared/Network.o: /usr/include/gconv.h /usr/include/bits/stdio_lim.h
shared/Network.o: /usr/include/bits/sys_errlist.h /usr/include/stdlib.h
shared/Network.o: /usr/include/string.h /usr/include/sys/types.h
shared/Network.o: /usr/include/time.h /usr/include/sys/socket.h
shared/Network.o: /usr/include/sys/uio.h /usr/include/bits/uio.h
shared/Network.o: /usr/include/bits/socket.h /usr/include/limits.h
shared/Network.o: /usr/include/bits/sockaddr.h /usr/include/asm/socket.h
shared/Network.o: /usr/include/asm/sockios.h /usr/include/netinet/ip.h
shared/Network.o: /usr/include/netinet/in.h /usr/include/stdint.h
shared/Network.o: /usr/include/bits/in.h /usr/include/endian.h
shared/Network.o: /usr/include/bits/endian.h /usr/include/bits/byteswap.h
shared/Network.o: /usr/include/netdb.h /usr/include/bits/netdb.h
shared/Network.o: shared/game_defs.h shared/Network.h
shared/Player.o: shared/Player.h shared/ResourceArray.h
shared/Position.o: shared/Position.h
shared/ResourceArray.o: /usr/include/string.h /usr/include/features.h
shared/ResourceArray.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
shared/ResourceArray.o: shared/ResourceArray.h
shared/Unit.o: /usr/include/math.h /usr/include/features.h
shared/Unit.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
shared/Unit.o: /usr/include/bits/huge_val.h /usr/include/bits/mathdef.h
shared/Unit.o: /usr/include/bits/mathcalls.h shared/Unit.h
shared/Unit.o: shared/ResourceArray.h shared/Position.h shared/game_defs.h
shared/World.o: /usr/include/sys/types.h /usr/include/features.h
shared/World.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
shared/World.o: /usr/include/bits/types.h /usr/include/bits/wordsize.h
shared/World.o: /usr/include/bits/typesizes.h /usr/include/time.h
shared/World.o: /usr/include/sys/stat.h /usr/include/bits/stat.h
shared/World.o: /usr/include/unistd.h /usr/include/bits/posix_opt.h
shared/World.o: /usr/include/bits/confname.h shared/World.h
shared/UnitDefs.o: shared/UnitDefs.h shared/Unit.h shared/ResourceArray.h
shared/UnitDefs.o: shared/Position.h shared/game_defs.h
client/Client-draw.o: /usr/include/SDL/SDL.h /usr/include/SDL/SDL_main.h
client/Client-draw.o: /usr/include/SDL/SDL_types.h
client/Client-draw.o: /usr/include/SDL/SDL_getenv.h
client/Client-draw.o: /usr/include/SDL/SDL_error.h
client/Client-draw.o: /usr/include/SDL/begin_code.h
client/Client-draw.o: /usr/include/SDL/close_code.h
client/Client-draw.o: /usr/include/SDL/SDL_rwops.h /usr/include/stdio.h
client/Client-draw.o: /usr/include/features.h /usr/include/sys/cdefs.h
client/Client-draw.o: /usr/include/gnu/stubs.h /usr/include/bits/types.h
client/Client-draw.o: /usr/include/bits/wordsize.h
client/Client-draw.o: /usr/include/bits/typesizes.h /usr/include/libio.h
client/Client-draw.o: /usr/include/_G_config.h /usr/include/wchar.h
client/Client-draw.o: /usr/include/bits/wchar.h /usr/include/gconv.h
client/Client-draw.o: /usr/include/bits/stdio_lim.h
client/Client-draw.o: /usr/include/bits/sys_errlist.h
client/Client-draw.o: /usr/include/SDL/SDL_timer.h
client/Client-draw.o: /usr/include/SDL/SDL_audio.h
client/Client-draw.o: /usr/include/SDL/SDL_byteorder.h
client/Client-draw.o: /usr/include/SDL/SDL_cdrom.h
client/Client-draw.o: /usr/include/SDL/SDL_joystick.h
client/Client-draw.o: /usr/include/SDL/SDL_events.h
client/Client-draw.o: /usr/include/SDL/SDL_active.h
client/Client-draw.o: /usr/include/SDL/SDL_keyboard.h
client/Client-draw.o: /usr/include/SDL/SDL_keysym.h
client/Client-draw.o: /usr/include/SDL/SDL_mouse.h
client/Client-draw.o: /usr/include/SDL/SDL_video.h
client/Client-draw.o: /usr/include/SDL/SDL_mutex.h
client/Client-draw.o: /usr/include/SDL/SDL_quit.h
client/Client-draw.o: /usr/include/SDL/SDL_version.h
client/Client-draw.o: /usr/include/SDL/SDL_ttf.h /usr/include/SDL/SDL.h
client/Client-draw.o: client/Client.h client/Console.h client/Drawable.h
client/Client-draw.o: /usr/include/SDL/SDL_image.h client/MiniMap.h
client/Client-draw.o: client/Graphics.h shared/game_defs.h shared/World.h
client/Client-draw.o: /usr/include/math.h /usr/include/bits/huge_val.h
client/Client-draw.o: /usr/include/bits/mathdef.h
client/Client-draw.o: /usr/include/bits/mathcalls.h /usr/include/string.h
client/Client-draw.o: shared/Game.h shared/Unit.h shared/ResourceArray.h
client/Client-draw.o: shared/Position.h shared/Player.h shared/Connection.h
client/Client-draw.o: shared/Network.h /usr/include/sys/types.h
client/Client-draw.o: /usr/include/time.h /usr/include/sys/socket.h
client/Client-draw.o: /usr/include/sys/uio.h /usr/include/bits/uio.h
client/Client-draw.o: /usr/include/bits/socket.h /usr/include/limits.h
client/Client-draw.o: /usr/include/bits/sockaddr.h /usr/include/asm/socket.h
client/Client-draw.o: /usr/include/asm/sockios.h /usr/include/netinet/ip.h
client/Client-draw.o: /usr/include/netinet/in.h /usr/include/stdint.h
client/Client-draw.o: /usr/include/bits/in.h /usr/include/endian.h
client/Client-draw.o: /usr/include/bits/endian.h /usr/include/bits/byteswap.h
client/Client-draw.o: /usr/include/netdb.h /usr/include/bits/netdb.h
client/Client-draw.o: shared/Message.h shared/Host.h shared/UnitDefs.h
client/Client.o: /usr/include/SDL/SDL.h /usr/include/SDL/SDL_main.h
client/Client.o: /usr/include/SDL/SDL_types.h /usr/include/SDL/SDL_getenv.h
client/Client.o: /usr/include/SDL/SDL_error.h /usr/include/SDL/begin_code.h
client/Client.o: /usr/include/SDL/close_code.h /usr/include/SDL/SDL_rwops.h
client/Client.o: /usr/include/stdio.h /usr/include/features.h
client/Client.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
client/Client.o: /usr/include/bits/types.h /usr/include/bits/wordsize.h
client/Client.o: /usr/include/bits/typesizes.h /usr/include/libio.h
client/Client.o: /usr/include/_G_config.h /usr/include/wchar.h
client/Client.o: /usr/include/bits/wchar.h /usr/include/gconv.h
client/Client.o: /usr/include/bits/stdio_lim.h
client/Client.o: /usr/include/bits/sys_errlist.h /usr/include/SDL/SDL_timer.h
client/Client.o: /usr/include/SDL/SDL_audio.h
client/Client.o: /usr/include/SDL/SDL_byteorder.h
client/Client.o: /usr/include/SDL/SDL_cdrom.h /usr/include/SDL/SDL_joystick.h
client/Client.o: /usr/include/SDL/SDL_events.h /usr/include/SDL/SDL_active.h
client/Client.o: /usr/include/SDL/SDL_keyboard.h
client/Client.o: /usr/include/SDL/SDL_keysym.h /usr/include/SDL/SDL_mouse.h
client/Client.o: /usr/include/SDL/SDL_video.h /usr/include/SDL/SDL_mutex.h
client/Client.o: /usr/include/SDL/SDL_quit.h /usr/include/SDL/SDL_version.h
client/Client.o: /usr/include/SDL/SDL_ttf.h /usr/include/SDL/SDL.h
client/Client.o: client/Client.h client/Console.h client/Drawable.h
client/Client.o: /usr/include/SDL/SDL_image.h client/MiniMap.h
client/Client.o: client/Graphics.h shared/game_defs.h shared/World.h
client/Client.o: /usr/include/math.h /usr/include/bits/huge_val.h
client/Client.o: /usr/include/bits/mathdef.h /usr/include/bits/mathcalls.h
client/Client.o: /usr/include/string.h shared/Game.h shared/Unit.h
client/Client.o: shared/ResourceArray.h shared/Position.h shared/Player.h
client/Client.o: shared/Connection.h shared/Network.h
client/Client.o: /usr/include/sys/types.h /usr/include/time.h
client/Client.o: /usr/include/sys/socket.h /usr/include/sys/uio.h
client/Client.o: /usr/include/bits/uio.h /usr/include/bits/socket.h
client/Client.o: /usr/include/limits.h /usr/include/bits/sockaddr.h
client/Client.o: /usr/include/asm/socket.h /usr/include/asm/sockios.h
client/Client.o: /usr/include/netinet/ip.h /usr/include/netinet/in.h
client/Client.o: /usr/include/stdint.h /usr/include/bits/in.h
client/Client.o: /usr/include/endian.h /usr/include/bits/endian.h
client/Client.o: /usr/include/bits/byteswap.h /usr/include/netdb.h
client/Client.o: /usr/include/bits/netdb.h shared/Message.h shared/Host.h
client/Client.o: shared/UnitDefs.h
client/Console.o: /usr/include/SDL/SDL.h /usr/include/SDL/SDL_main.h
client/Console.o: /usr/include/SDL/SDL_types.h /usr/include/SDL/SDL_getenv.h
client/Console.o: /usr/include/SDL/SDL_error.h /usr/include/SDL/begin_code.h
client/Console.o: /usr/include/SDL/close_code.h /usr/include/SDL/SDL_rwops.h
client/Console.o: /usr/include/stdio.h /usr/include/features.h
client/Console.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
client/Console.o: /usr/include/bits/types.h /usr/include/bits/wordsize.h
client/Console.o: /usr/include/bits/typesizes.h /usr/include/libio.h
client/Console.o: /usr/include/_G_config.h /usr/include/wchar.h
client/Console.o: /usr/include/bits/wchar.h /usr/include/gconv.h
client/Console.o: /usr/include/bits/stdio_lim.h
client/Console.o: /usr/include/bits/sys_errlist.h
client/Console.o: /usr/include/SDL/SDL_timer.h /usr/include/SDL/SDL_audio.h
client/Console.o: /usr/include/SDL/SDL_byteorder.h
client/Console.o: /usr/include/SDL/SDL_cdrom.h
client/Console.o: /usr/include/SDL/SDL_joystick.h
client/Console.o: /usr/include/SDL/SDL_events.h /usr/include/SDL/SDL_active.h
client/Console.o: /usr/include/SDL/SDL_keyboard.h
client/Console.o: /usr/include/SDL/SDL_keysym.h /usr/include/SDL/SDL_mouse.h
client/Console.o: /usr/include/SDL/SDL_video.h /usr/include/SDL/SDL_mutex.h
client/Console.o: /usr/include/SDL/SDL_quit.h /usr/include/SDL/SDL_version.h
client/Console.o: /usr/include/SDL/SDL_ttf.h /usr/include/SDL/SDL.h
client/Console.o: client/Console.h client/Drawable.h
client/Console.o: /usr/include/SDL/SDL_image.h client/Graphics.h
client/Console.o: shared/game_defs.h
client/Drawable.o: /usr/include/SDL/SDL.h /usr/include/SDL/SDL_main.h
client/Drawable.o: /usr/include/SDL/SDL_types.h /usr/include/SDL/SDL_getenv.h
client/Drawable.o: /usr/include/SDL/SDL_error.h /usr/include/SDL/begin_code.h
client/Drawable.o: /usr/include/SDL/close_code.h /usr/include/SDL/SDL_rwops.h
client/Drawable.o: /usr/include/stdio.h /usr/include/features.h
client/Drawable.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
client/Drawable.o: /usr/include/bits/types.h /usr/include/bits/wordsize.h
client/Drawable.o: /usr/include/bits/typesizes.h /usr/include/libio.h
client/Drawable.o: /usr/include/_G_config.h /usr/include/wchar.h
client/Drawable.o: /usr/include/bits/wchar.h /usr/include/gconv.h
client/Drawable.o: /usr/include/bits/stdio_lim.h
client/Drawable.o: /usr/include/bits/sys_errlist.h
client/Drawable.o: /usr/include/SDL/SDL_timer.h /usr/include/SDL/SDL_audio.h
client/Drawable.o: /usr/include/SDL/SDL_byteorder.h
client/Drawable.o: /usr/include/SDL/SDL_cdrom.h
client/Drawable.o: /usr/include/SDL/SDL_joystick.h
client/Drawable.o: /usr/include/SDL/SDL_events.h
client/Drawable.o: /usr/include/SDL/SDL_active.h
client/Drawable.o: /usr/include/SDL/SDL_keyboard.h
client/Drawable.o: /usr/include/SDL/SDL_keysym.h /usr/include/SDL/SDL_mouse.h
client/Drawable.o: /usr/include/SDL/SDL_video.h /usr/include/SDL/SDL_mutex.h
client/Drawable.o: /usr/include/SDL/SDL_quit.h /usr/include/SDL/SDL_version.h
client/Drawable.o: client/Drawable.h /usr/include/SDL/SDL_image.h
client/Drawable.o: /usr/include/SDL/SDL.h client/Graphics.h
client/Drawable.o: shared/game_defs.h
client/Graphics.o: /usr/include/SDL/SDL.h /usr/include/SDL/SDL_main.h
client/Graphics.o: /usr/include/SDL/SDL_types.h /usr/include/SDL/SDL_getenv.h
client/Graphics.o: /usr/include/SDL/SDL_error.h /usr/include/SDL/begin_code.h
client/Graphics.o: /usr/include/SDL/close_code.h /usr/include/SDL/SDL_rwops.h
client/Graphics.o: /usr/include/stdio.h /usr/include/features.h
client/Graphics.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
client/Graphics.o: /usr/include/bits/types.h /usr/include/bits/wordsize.h
client/Graphics.o: /usr/include/bits/typesizes.h /usr/include/libio.h
client/Graphics.o: /usr/include/_G_config.h /usr/include/wchar.h
client/Graphics.o: /usr/include/bits/wchar.h /usr/include/gconv.h
client/Graphics.o: /usr/include/bits/stdio_lim.h
client/Graphics.o: /usr/include/bits/sys_errlist.h
client/Graphics.o: /usr/include/SDL/SDL_timer.h /usr/include/SDL/SDL_audio.h
client/Graphics.o: /usr/include/SDL/SDL_byteorder.h
client/Graphics.o: /usr/include/SDL/SDL_cdrom.h
client/Graphics.o: /usr/include/SDL/SDL_joystick.h
client/Graphics.o: /usr/include/SDL/SDL_events.h
client/Graphics.o: /usr/include/SDL/SDL_active.h
client/Graphics.o: /usr/include/SDL/SDL_keyboard.h
client/Graphics.o: /usr/include/SDL/SDL_keysym.h /usr/include/SDL/SDL_mouse.h
client/Graphics.o: /usr/include/SDL/SDL_video.h /usr/include/SDL/SDL_mutex.h
client/Graphics.o: /usr/include/SDL/SDL_quit.h /usr/include/SDL/SDL_version.h
client/Graphics.o: /usr/include/SDL/SDL_ttf.h /usr/include/SDL/SDL.h
client/Graphics.o: client/Graphics.h client/Drawable.h
client/Graphics.o: /usr/include/SDL/SDL_image.h shared/game_defs.h
client/MiniMap.o: client/MiniMap.h /usr/include/SDL/SDL.h
client/MiniMap.o: /usr/include/SDL/SDL_main.h /usr/include/SDL/SDL_types.h
client/MiniMap.o: /usr/include/SDL/SDL_getenv.h /usr/include/SDL/SDL_error.h
client/MiniMap.o: /usr/include/SDL/begin_code.h /usr/include/SDL/close_code.h
client/MiniMap.o: /usr/include/SDL/SDL_rwops.h /usr/include/stdio.h
client/MiniMap.o: /usr/include/features.h /usr/include/sys/cdefs.h
client/MiniMap.o: /usr/include/gnu/stubs.h /usr/include/bits/types.h
client/MiniMap.o: /usr/include/bits/wordsize.h /usr/include/bits/typesizes.h
client/MiniMap.o: /usr/include/libio.h /usr/include/_G_config.h
client/MiniMap.o: /usr/include/wchar.h /usr/include/bits/wchar.h
client/MiniMap.o: /usr/include/gconv.h /usr/include/bits/stdio_lim.h
client/MiniMap.o: /usr/include/bits/sys_errlist.h
client/MiniMap.o: /usr/include/SDL/SDL_timer.h /usr/include/SDL/SDL_audio.h
client/MiniMap.o: /usr/include/SDL/SDL_byteorder.h
client/MiniMap.o: /usr/include/SDL/SDL_cdrom.h
client/MiniMap.o: /usr/include/SDL/SDL_joystick.h
client/MiniMap.o: /usr/include/SDL/SDL_events.h /usr/include/SDL/SDL_active.h
client/MiniMap.o: /usr/include/SDL/SDL_keyboard.h
client/MiniMap.o: /usr/include/SDL/SDL_keysym.h /usr/include/SDL/SDL_mouse.h
client/MiniMap.o: /usr/include/SDL/SDL_video.h /usr/include/SDL/SDL_mutex.h
client/MiniMap.o: /usr/include/SDL/SDL_quit.h /usr/include/SDL/SDL_version.h
client/MiniMap.o: /usr/include/SDL/SDL_image.h /usr/include/SDL/SDL.h
client/MiniMap.o: client/Drawable.h client/Graphics.h shared/game_defs.h
client/MiniMap.o: shared/World.h /usr/include/math.h
client/MiniMap.o: /usr/include/bits/huge_val.h /usr/include/bits/mathdef.h
client/MiniMap.o: /usr/include/bits/mathcalls.h /usr/include/string.h
client/Client-unit.o: shared/Unit.h shared/ResourceArray.h shared/Position.h
client/Client-unit.o: shared/game_defs.h client/Client.h
client/Client-unit.o: /usr/include/SDL/SDL.h /usr/include/SDL/SDL_main.h
client/Client-unit.o: /usr/include/SDL/SDL_types.h
client/Client-unit.o: /usr/include/SDL/SDL_getenv.h
client/Client-unit.o: /usr/include/SDL/SDL_error.h
client/Client-unit.o: /usr/include/SDL/begin_code.h
client/Client-unit.o: /usr/include/SDL/close_code.h
client/Client-unit.o: /usr/include/SDL/SDL_rwops.h /usr/include/stdio.h
client/Client-unit.o: /usr/include/features.h /usr/include/sys/cdefs.h
client/Client-unit.o: /usr/include/gnu/stubs.h /usr/include/bits/types.h
client/Client-unit.o: /usr/include/bits/wordsize.h
client/Client-unit.o: /usr/include/bits/typesizes.h /usr/include/libio.h
client/Client-unit.o: /usr/include/_G_config.h /usr/include/wchar.h
client/Client-unit.o: /usr/include/bits/wchar.h /usr/include/gconv.h
client/Client-unit.o: /usr/include/bits/stdio_lim.h
client/Client-unit.o: /usr/include/bits/sys_errlist.h
client/Client-unit.o: /usr/include/SDL/SDL_timer.h
client/Client-unit.o: /usr/include/SDL/SDL_audio.h
client/Client-unit.o: /usr/include/SDL/SDL_byteorder.h
client/Client-unit.o: /usr/include/SDL/SDL_cdrom.h
client/Client-unit.o: /usr/include/SDL/SDL_joystick.h
client/Client-unit.o: /usr/include/SDL/SDL_events.h
client/Client-unit.o: /usr/include/SDL/SDL_active.h
client/Client-unit.o: /usr/include/SDL/SDL_keyboard.h
client/Client-unit.o: /usr/include/SDL/SDL_keysym.h
client/Client-unit.o: /usr/include/SDL/SDL_mouse.h
client/Client-unit.o: /usr/include/SDL/SDL_video.h
client/Client-unit.o: /usr/include/SDL/SDL_mutex.h
client/Client-unit.o: /usr/include/SDL/SDL_quit.h
client/Client-unit.o: /usr/include/SDL/SDL_version.h client/Console.h
client/Client-unit.o: /usr/include/SDL/SDL_ttf.h /usr/include/SDL/SDL.h
client/Client-unit.o: client/Drawable.h /usr/include/SDL/SDL_image.h
client/Client-unit.o: client/MiniMap.h client/Graphics.h shared/World.h
client/Client-unit.o: /usr/include/math.h /usr/include/bits/huge_val.h
client/Client-unit.o: /usr/include/bits/mathdef.h
client/Client-unit.o: /usr/include/bits/mathcalls.h /usr/include/string.h
client/Client-unit.o: shared/Game.h shared/Player.h shared/Connection.h
client/Client-unit.o: shared/Network.h /usr/include/sys/types.h
client/Client-unit.o: /usr/include/time.h /usr/include/sys/socket.h
client/Client-unit.o: /usr/include/sys/uio.h /usr/include/bits/uio.h
client/Client-unit.o: /usr/include/bits/socket.h /usr/include/limits.h
client/Client-unit.o: /usr/include/bits/sockaddr.h /usr/include/asm/socket.h
client/Client-unit.o: /usr/include/asm/sockios.h /usr/include/netinet/ip.h
client/Client-unit.o: /usr/include/netinet/in.h /usr/include/stdint.h
client/Client-unit.o: /usr/include/bits/in.h /usr/include/endian.h
client/Client-unit.o: /usr/include/bits/endian.h /usr/include/bits/byteswap.h
client/Client-unit.o: /usr/include/netdb.h /usr/include/bits/netdb.h
client/Client-unit.o: shared/Message.h shared/Host.h shared/UnitDefs.h
server/Server.o: /usr/include/sys/time.h /usr/include/features.h
server/Server.o: /usr/include/sys/cdefs.h /usr/include/gnu/stubs.h
server/Server.o: /usr/include/bits/types.h /usr/include/bits/wordsize.h
server/Server.o: /usr/include/bits/typesizes.h /usr/include/time.h
server/Server.o: /usr/include/bits/time.h /usr/include/sys/select.h
server/Server.o: /usr/include/bits/select.h /usr/include/bits/sigset.h
server/Server.o: /usr/include/signal.h /usr/include/bits/signum.h
server/Server.o: shared/Game.h shared/Unit.h shared/ResourceArray.h
server/Server.o: shared/Position.h shared/game_defs.h shared/Player.h
server/Server.o: shared/Connection.h shared/Network.h
server/Server.o: /usr/include/sys/types.h /usr/include/sys/socket.h
server/Server.o: /usr/include/sys/uio.h /usr/include/bits/uio.h
server/Server.o: /usr/include/bits/socket.h /usr/include/limits.h
server/Server.o: /usr/include/bits/sockaddr.h /usr/include/asm/socket.h
server/Server.o: /usr/include/asm/sockios.h /usr/include/netinet/ip.h
server/Server.o: /usr/include/netinet/in.h /usr/include/stdint.h
server/Server.o: /usr/include/bits/wchar.h /usr/include/bits/in.h
server/Server.o: /usr/include/endian.h /usr/include/bits/endian.h
server/Server.o: /usr/include/bits/byteswap.h /usr/include/netdb.h
server/Server.o: /usr/include/bits/netdb.h shared/Message.h shared/Host.h
server/Server.o: shared/World.h shared/UnitDefs.h server/Server.h
