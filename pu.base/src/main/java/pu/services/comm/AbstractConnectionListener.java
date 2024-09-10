package pu.services.comm;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import pu.log.Log;

/**
 * ConnectionListener listens to a ServerSocket and accepts incoming requests.
 * For each request, it starts a new thread to handle traffic on the socket, and continues to
 * listen for new requests.
 * <p>
 * MultiThread Policy
 * <p>
 * This class is designed so that each instance runs on its own thread. However, no other object
 * on whatever thread may communicate with this class, except for one thing: any thread is allowed
 * to call <code>stop()</code> on this thread, after which it stops listening to its socket and
 * this thread terminates.
 */
// TODO Test error recovery

public abstract class AbstractConnectionListener implements ConnectionListener
{
// HIGH Naar een Config
/** The default client thread priority is high */
public static final int DEFAULT_CLIENT_THREAD_PRIORITY = Thread.MAX_PRIORITY;

private static final Logger LOG = LogManager.getLogger( AbstractConnectionListener.class );

/**
 * The port on which we accept new incoming requests.
 */
private final int serverPort;

// HIGH Als we dit project weer oppikken moet dit settable worden
private final TcpServerSocketFactory tcpServerSocketFactory;
/**
 * The socket we are listening on for incoming requests
 */
// Kan niet final zijn want bij de recovery proberen we een nieuwe serverSocket te maken
// HIGH Moet dit niet volatile zijn?
private TcpServerSocket serverSocket;

/**
 * Whether a stop is in progress. When you close a ServerSocket, the acceptor receives a
 * SocketException with the text: socket closed. That is not a very clear message because there
 * may be other reasons (we don't know!) that the socket is closed. In other words, we are not sure
 * whether an error has occurred or whether we are purposely closing the socket ourselves. So to be
 * safe side, before we do close the socket we set this boolean to true.
 */
private volatile boolean stopping = false;

/**
 * Whether the listener has stopped.
 */
private volatile boolean stopped = false;

/**
 * Creates a new ConnectionListener, listening on the specified port.
 * @param aServerSocket The socket that we listen to for incoming requests
 * @throws IOException
 * @exception IllegalArgumentException If the socket is <code>null</code>
 */
public AbstractConnectionListener( int aPort ) throws IOException
{
	super();
	if ( aPort <= 0 )
	{
		throw new IllegalArgumentException( "Port must be a positive number" );
	}
	serverPort = aPort;
	tcpServerSocketFactory = new DefaultTcpServerSocketFactory( aPort );
	serverSocket = tcpServerSocketFactory.createTcpServerSocket();
}

/**
 * Creates a new ConnectionListener, listening on the specified server socket.
 * @param aServerSocket The socket that we listen to for incoming requests
 * @exception IllegalArgumentException If the socket is <code>null</code>
 */
public AbstractConnectionListener( TcpServerSocket aServerSocket )
{
	super();
	if ( aServerSocket == null )
	{
		throw new IllegalArgumentException( "ServerSocket may not nu null" );
	}
	// HIGH Port kan ge-elinmineerd worden, zit in socket
	serverPort = aServerSocket.getLocalPort();
	tcpServerSocketFactory = new DefaultTcpServerSocketFactory( serverPort );
	serverSocket = aServerSocket;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Static helper methods

/**
 * Utility method for binding to a server socket and handling the exceptions involved.
 * @param aPort The port to bind to
 * @return The newly created ServerSocket
 */
// Gaat nu via de TcpServerSocketFactory
/****************************
protected static TcpServerSocket createServerSocket( int aPort )
{
	ServerSocket socket = null;
	try
	{
		socket = new ServerSocket( aPort );
	}
	catch ( IOException e )
	{
		LOG.fatal( "Could not listen on port: " + aPort, e );
		throw new RuntimeException( e );
	}
	return tcpS new TcpServerSocketImpl( socket );
}
 ***********************/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getters en setters

/**
 * @return the port that we are listening on
 */
protected int getServerPort()
{
	return serverPort;
}

/**
 * @return the server socket that we are listening on
 */
protected TcpServerSocket getServerSocket()
{
	return serverSocket;
}

/**
 * @return The thread priority of client threads that we start
 */
protected int getClientThreadPriority()
{
	return DEFAULT_CLIENT_THREAD_PRIORITY;
}

/**
 * @return Whether this ConnectionListener has been stopped
 */
@Override
public boolean isStopped()
{
	return stopped;
}

/**
 * @return Whether this session is trying to stop
 */
public boolean isStopping()
{
	return stopping;
}

/**
 * Marks that this connectionlistener is stopped, i.e. no longer accepting client requests
 */
protected void setStopped()
{
	stopped = true;
}

/**
 * Marks that this connectionListener is stopping, so that the <code>SocketException</code> that
 * we cause is not mistaken for a real error
 */
protected void setStopping()
{
	stopping = true;
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Running / recovering / stopping

/**
 * Starts this connection listener
 */
@Override
public final void run()
{
	if ( isStopped() )
	{
		throw new IllegalStateException( "A stopped connectionlistener cannot be run again. Create a new connectionlistener instead" );
	}
	LOG.info( getLogName() + " started on port " + getServerSocket().getLocalPort() );
	for ( ;; )
	{
		try
		{
			acceptClient( getServerSocket().accept() );
		}
		catch ( IOException e )
		{
			// If stopping is true, this is a normal exit.
			// Note: stopping is a volatile field.
			// @@NOG Print message if we got the wrong exception?
			if ( isStopping() )
			{
				break;
			}
			recover( e );
			if ( isStopping() )
			{
				break;
			}
		}
		catch ( TestingConnectionListenerException e )
		{
			// Dit is alleen voor testen
			break;
		}
		catch ( Throwable e )
		{
			// Er is ergens een NullpointerException gegooid, of een OutOfMemoryException
			// of weet ik veel. In de meeste gevallen moeten we gewoon door zien te gaan
			// met het accepteren van connecties. Dus even een stacktrace.
			// HIGH Er zijn veel plaatsen waar je een critical error kan krijgen en je toch wilt doorgaan. HandleCriticalError of zo?
			// HIGH En nagaan wat er op dat moment safe is: kan ik Log gebruiken? Etc.
			LOG.error( e );
		}
	}
	try
	{
		getServerSocket().close();
	}
	catch ( IOException e )
	{
		// Ignore
	}
	setStopped(); // stopped is volatile
	LOG.info( getLogName() + " stopped!" );
}

/**
 * Called when a client connection is accepted. Starts a new thread to handle
 * client traffic.
 * @param aSocket The socket that is connected to the client.
 */
protected void acceptClient( TcpSocket aSocket )
{
	Session session = createSession( aSocket );
	Thread t = new Thread( session, getSessionThreadNamePrefix() + "-" + session.getSessionId() );
	session.setThread( t ); // Beetje kip/ei
	t.setPriority( getClientThreadPriority() );
	t.setDaemon( true );
	t.start();
}

/**
 * Attempts to recover when the ServerSocket receives an error
 * @param e java.io.IOException
 */
private void recover( IOException aException )
{
	// @@NOG Recovery
	//       If this thread dies, RLogServer is dead too, because no new
	//       connections will be accepted. So we must recover. If there really is a
	//       problem (network gone), it may take seconds or hours before the problem goes
	//       away. Maybe use an exponentional backoff time.
	//       Best chance is socket.close() and make a new one.
	//       If the port is bound to another process, report that and die?

	// Report the error and what we are going to do
	LOG.error( "Starting error recovery on port " + getServerPort(), aException );

	// First close the server socket
	try
	{
		if ( serverSocket != null )
		{
			serverSocket.close();
		}
	}
	catch ( IOException e)
	{
		LOG.error( "Error recovery stopped: unable to close server socket on " + getServerPort(), e );
		setStopping();
		return;
	}

	final int SECOND = 1000;
	final int START_DELAY = 2;
	final int MAX_DELAY = 30;

	// Start main recovery loop
	int delay = START_DELAY;
	int counter = 0;
	int subCounter = 0;
	for ( ;; )
	{
		if ( isStopping() )
		{
			Log.info( this, "Error recovery stopped since a stop is in progress" );
			return;
		}

		LOG.info( "Recovery attempt #" + ++counter );

		try
		{
			serverSocket = tcpServerSocketFactory.createTcpServerSocket();
			break;
		}
		catch ( IOException e)
		{
			LOG.error( " failed: " + e.getMessage() );
		}
		try
		{
			if ( serverSocket != null )
			{
				serverSocket.close();
			}
		}
		catch ( IOException e)
		{
		}
		if ( ++subCounter == 5 )
		{
			subCounter = 0;
			delay *= 2;
			if ( delay > MAX_DELAY )
			{
				delay = MAX_DELAY;
			}
		}
		LOG.info( ". Next attempt in " + delay + "s." );
		try
		{
			Thread.sleep( delay * SECOND );
		}
		catch ( InterruptedException e )
		{
			//????
			LOG.error( " Recovery Interrupted. Going to stop" );
			setStopping();
			return;
		}
	}
	LOG.info( " Recovery successfull" );
}

/**
 * Closes the socket that we are listening on. As a result, this <code>Runnable</code>ends.
 * If we are the <code>Runnable</code> of a thread (most likely), the thread will terminate also.
 * <p>
 * This method is designed to be called from a different thread than the one that
 * the ConnectionListener is listening on.
 */
@Override
public void stop()
{
	// See the comment in AbstractSession: we use the same method here, with one difference: when we see
	// an error on the socket, we try to recover by creating a new socket
	// HIGH Eh... Dat klopt niet, ik zie niets van een nieuwe socket maken!
	setStopping();
	try
	{
		if ( serverSocket != null )
		{
			serverSocket.close();
		}
	}
	catch ( IOException e)
	{
		LOG.error( "Error closing server socket", e );
	}
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Abstract methods

/**
 * Called when a new Session needs to be created
 * @param aSocket The socket that the new Session should use
 */
protected abstract Session createSession( TcpSocket aSocket );

/**
 * Returns the prefix that is to be for the thread name for session threads that we create.
 * We simply append the session id so the thread name would be something like "Log4jSession-12345".
 *
 * @return the prefix name for session threads that we create
 */
protected abstract String getSessionThreadNamePrefix();


}
