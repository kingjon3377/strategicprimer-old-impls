
#include <iostream>
#include <signal.h>
#include <pthread.h>
#include <queue>
#include "Connection.h"
using namespace std;

void	Connection_SigHandler(int signal);


Connection::Connection()
{
	myMessageCallback = NULL;
	myNetwork = new Network(1, NULL);
	create_thread();
}

Connection::Connection(char *server)
{
	myMessageCallback = NULL;
	myNetwork = new Network(0, server);
	create_thread();
}

Connection::~Connection()
{
	destroy_thread();
	delete myNetwork;
}

void	Connection::registerMessageCallback(message_callback_t mc)
{
	myMessageCallback = mc;
}

/* This method must be defined outside of the class to be a pthread
 * initialization thread, but is a friend of the Connection class so that
 * it can directly access Connection member variables
 */
void	*Connection_Thread(void *ptr)
{
	/* Set up our pointer to the Connection object */
	Connection *c = (Connection *) ptr;
	
	/* Set up SIGQUIT signals to exit this thread */
	signal(SIGQUIT, Connection_SigHandler);

	/* Loop to wait for incoming network packets */
	char *buffer;
	unsigned int length;
	ip_t ip;
	port_t port;
	for (;;)
	{
		buffer = new char[BUFFER_LENGTH];
		length = c->myNetwork->receive(buffer, BUFFER_LENGTH, &ip, &port);
		/* We have received a packet, process it */
		if (c->myMessageCallback)
			c->myMessageCallback(new Message(buffer, length, ip, port));
		else
		{
			pthread_mutex_lock(&c->myMessageLock);
			c->myMessages.push(new Message(buffer, length, ip, port));
			pthread_mutex_unlock(&c->myMessageLock);
		}
	}
}

/* Called when the thread gets a SIGQUIT signal, so it needs to exit */
void	Connection_SigHandler(int signal)
{
	/* Stop listening for packets */
	pthread_exit(0);
}

void	Connection::create_thread()
{
	pthread_mutex_init(&myMessageLock, NULL);
	if (pthread_create(&myThread, NULL, Connection_Thread, this))
		cerr << "ERROR: pthread_create() failed!" << endl;
}

void	Connection::destroy_thread()
{
	pthread_kill(myThread, SIGQUIT);
}

void	Connection::sendMessage(Message *message)
{
	myNetwork->send(message->getBuffer(), message->getLength());
}

void	Connection::sendMessage(Message *message, ip_t ip, port_t port)
{
	myNetwork->send(message->getBuffer(), message->getLength(), ip, port);
}

Message *Connection::getMessage()
{
	pthread_mutex_lock(&myMessageLock);
	if (myMessages.size() == 0)
	{
		pthread_mutex_unlock(&myMessageLock);
		return NULL;
	}
	Message *message = myMessages.front();
	myMessages.pop();
	pthread_mutex_unlock(&myMessageLock);
	return message;
}
