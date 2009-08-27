
#ifndef _CONNECTION_H_
#define _CONNECTION_H_ _CONNECTION_H_

#include <queue>
#include "shared/Network.h"
#include "shared/Message.h"
using namespace std;

#define BUFFER_LENGTH 1010

typedef void (*message_callback_t)(Message *);

class Connection
{
protected:
	Network			*myNetwork;
	pthread_t		myThread;
	pthread_mutex_t		myMessageLock;
	queue<Message *>	myMessages;
	message_callback_t	myMessageCallback;

	friend void *Connection_Thread(void *ptr);
	void	create_thread();
	void	destroy_thread();

public:
	Connection();
	Connection(char *server);
	~Connection();

	void	registerMessageCallback(message_callback_t mc);
	void	sendMessage(Message *message);
	void	sendMessage(Message *message, ip_t ip, port_t port);
	/* Callee is responsible for deleting the Message object
	 * referenced by the returned pointer after using it	*/
	Message *getMessage();
};

#endif
