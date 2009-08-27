
#ifndef _MESSAGE_H_
#define _MESSAGE_H_ _MESSAGE_H_

#define MSG_LOGIN_REQ		0
#define MSG_LOGIN_ACK		1
#define MSG_NEW_PLAYER_BC	2
#define MSG_DEPART		3
#define MSG_RESOURCE_LEVELS	4
#define MSG_NEW_UNIT_REQ	5
#define MSG_NEW_UNIT_BC		6
#define MSG_DEPART_BC		7

#include "shared/Host.h"
#include "shared/game_defs.h"
#include "shared/ResourceArray.h"
using namespace std;

typedef struct
{
	unsigned int	type;
	union
	{
		struct
		{
			char		login_name[21];	// login request, login ack, new player
			unsigned int	login_playerid;	// login ack, depart, new player
			char		map_name[50];	// login ack
		}; /* MSG_LOGIN_REQ, MSG_LOGIN_ACK, MSG_DEPART, MSG_NEW_PLAYER_BC */
		struct
		{
			unsigned int	resourceLevels[RES_MAX];
			unsigned int	resourcesDiscovered[RES_MAX];
		}; /* MSG_RESOURCE_LEVELS */
		struct
		{
			unsigned int	unit_owner;	// new unit req, bc
			unsigned int	unit_type;	// new unit req, bc
			int		unit_x;		// new unit req, bc
			int		unit_y;		// new unit req, bc
			unsigned int	unit_id;	// new unit bc
		}; /* MSG_NEW_UNIT_* */
	};
} message_t;

class Message
{
protected:
	char		*myBuffer;
	unsigned int	myLength;
	unsigned int	myIP;
	unsigned int	myPort;

public:
	Message();
	Message(void *buffer, unsigned int length,
		unsigned int ip, unsigned short port);
	~Message();

	inline char *getBuffer() { return myBuffer; }
	inline message_t *getBufferMessage() { return (message_t *) myBuffer; }
	inline unsigned int getLength() { return myLength; }
	inline unsigned int getIP() { return myIP; }
	inline unsigned int getPort() { return myPort; }
	inline Host getHost() { return Host(myIP, myPort); }
};

#endif
