/* Connection.cpp
 * Purpose: Contains the implementation of the game's connection to
 * the network, contained in Connection.h
 * Authors and Date: Created by the CSX Gamedev SIG, Spring 2006.
 * Major contributors include Josh Holtrop, Tim Brom, Job Vranish, and
 * John van Enck.
 * Input and output: Only internal.
 * How to use: See Connection.h
 * Assumptions: If the object is threaded, it is functioning.
 * Exceptions: (need something here)
 * Major algorithms and data structures: POSIX threading; Network class.
 * Key variables: See Connection.h.
 */
#include <iostream>
#include <signal.h>
#include <pthread.h>
#include <queue>
#include "Connection.hpp"
using namespace std;

void	Connection_SigHandler(int signal);


/* Connection::Connection()
 * Purpose: Construct the Connection object for a server Game object.
 * Precondition: This is being called as a constructor rather than a
 * normal function.
 * Postconditions: Connection object's Network object is set up, and
 * it is threaded.
 */
Connection::Connection()
{
	myMessageCallback = NULL;
	myNetwork = new Network(1, NULL);
	create_thread();
}

/* Connection::Connection(char *server)
 * Purpose: Construct the Connection object for a client Game object
 * connecting to the given server.
 * Precondition: This is being called as a constructor.
 * Postconditions: Connection object's Network object is set up, and
 * it is threaded.
 */
Connection::Connection(char *server)
{
	myMessageCallback = NULL;
	myNetwork = new Network(0, server);
	create_thread();
}

/* Connection::~Connection()
 * Purpose: Clean up after the Connection object.
 * Preconditions: None.
 * Postconditions: The object contains no active pointers and is no
 * longer threaded.
 */
Connection::~Connection()
{
	destroy_thread();
	delete myNetwork;
}

/* void Connection::registerMessageCallback()
 * Purpose: Register the object's message callback function.
 * Preconditions: None.
 * Postconditions: The passed message callback has been registered.
 */
void	Connection::registerMessageCallback(message_callback_t mc)
{
	myMessageCallback = mc;
}

/* This method must be defined outside of the class to be a pthread
 * initialization thread, but is a friend of the Connection class so that
 * it can directly access Connection member variables
 */
/* friend void *Connection_Thread()
 * Purpose: Do the work of the Connection class: when passed a packet,
 * process it.
 * Preconditions: Its argument is a pointer to a valid Connection
 * object.
 * Postconditions: None; this function loops until given SIGQUIT.
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
/* void Connection_SigHandler()
 * Purpose: Signal handler to exit the thread when called.
 * Preconditions: The thread has just been given a SIGQUIT signal.
 * Postconditions: The object is no longer threaded.
 */
void	Connection_SigHandler(int signal)
{
	/* Stop listening for packets */
	pthread_exit(0);
}

/* void Connection::create_thread()
 * Purpose: Set the object up in its own thread.
 * Preconditions: The object is not already threaded.
 * Postconditions: None.
 */
void	Connection::create_thread()
{
	pthread_mutex_init(&myMessageLock, NULL);
	if (pthread_create(&myThread, NULL, Connection_Thread, this))
		cerr << "ERROR: pthread_create() failed!" << endl;
}

/* void Connection::destroy_thread()
 * Purpose: Destroy the object's thread.
 * Preconditions: The object is is threaded and has its signal handler
 * running properly.
 * Postconditions: The object is no longer threaded.
 */
void	Connection::destroy_thread()
{
	pthread_kill(myThread, SIGQUIT);
}

/* void Connection::sendMessage()
 * Purpose: Send the given message.
 * Preconditions: The argument is a valid pointer and the object is
 * properly functioning.
 * Postconditions: The message has been sent.
 * This is basically a wrapper around the Network object's send() function.
 */
void	Connection::sendMessage(Message *message)
{
	myNetwork->send(message->getBuffer(), message->getLength());
}

/* void Connection::sendMessage()
 * Purpose: Send the given message to the given address.
 * Preconditions: *message is a valid pointer; the object is properly
 * functioning.
 * Postconditions: The message has been sent.
 * This is basically a wrapper around the Network object's send() function.
 */
void	Connection::sendMessage(Message *message, ip_t ip, port_t port)
{
	myNetwork->send(message->getBuffer(), message->getLength(), ip, port);
}

/* Message *Connection::getMessage()
 * Purpose: Get a message from the stack.
 * Preconditions: The object is properly functioning, including
 * threading.
 * Postconditions: If there were any messages on the stack, the first
 * one is returned and is no longer on the stack; if not, NULL is
 * returned.
 */
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
