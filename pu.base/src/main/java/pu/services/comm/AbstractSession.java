package pu.services.comm;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import pu.log.Log;
import pu.services.FormatHelper;

/**
 * AbstractSession
 */

public abstract class AbstractSession implements Session
{
/**
 * The session ID of this client
 */
private final Integer sessionID;

/**
 * The socket that we are listening to
 */
private final TcpSocket socket;

/**
 * The time in milliseconds that we started
 */
private final long timeStarted;

/**
 * The thread that we're running on
 */
private volatile Thread thread = null;

/**
 * The time in milliseconds when the last client activity occurred
 */
private volatile long timeLastActivity = Long.MIN_VALUE;

/**
 * The list of listeners to SessionEvents
 */
private final List<SessionListener> sessionListeners = new CopyOnWriteArrayList<>();

/**
 * Whether a stop is in progress. When you close a ServerSocket, the acceptor receives a
 * SocketException with the text: socket closed. That is not a very clear message because there
 * may be other reasons (we don't know!) that the socket is closed. In other words, we are not sure
 * whether an error has occurred or whether we are purposely closing the socket ourselves. So to be
 * on the safe side, before we do close the socket we set this boolean to true.
 */
private volatile boolean stopping = false;

/**
 * Whether this session has stopped.
 */
private volatile boolean stopped = false;

/**
 * The reason that we stopped.
 * <code>null</code> means the session hasn't stopped or has stopped
 * normally; any other value is usually the message of the exception
 * that caused the session to stop.
 * <p>
 * Is een beetje een probleemveldje. Is nodig omdat de stopReason bepaald wordt op een moment waarop we
 * niet meteen een event kunnen sturen met deze reason erin. Dus we moeten hem even bewaren totdat we
 * het event kunnen afsturen. Ik zou niet weten hoe je dat anders moet doen.
 */
// In theorie is het is niet nodig om dit volatile te maken: assignment van een ref is atomic.
// Maar in principe kan het eeuwig duren voor een wijziging door thread x zichtbaar is voor
// thread y, dus het is wel verstandig
private volatile String stopReason = null;


/**
 * Creates a new AbstractLogClient, which maintains the conversation with a single client
 * @param aSocket The socket that we listen to
 * @exception IllegalArgumentException If the socket is <code>null</code>
 */
public AbstractSession( TcpSocket aSocket, Integer aSessionID )
{
	super();
	if ( aSocket == null )
	{
		throw new IllegalArgumentException( "Socket may not be null" );
	}
	if ( aSessionID == null )
	{
		throw new IllegalArgumentException( "Session id may not be null" );
	}
	sessionID = aSessionID;
	socket = aSocket;
	timeStarted = System.currentTimeMillis();
	timeLastActivity = timeStarted;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getters en setters

/**
 * @return The ID of this session
 */
@Override
public final Integer getSessionId()
{
	return sessionID;
}

/**
 * @return The ip address of the socket
 */
@Override
public final String getIpAddress()
{
	return getSocket().getIpAddress();
}

/**
 * @return The socket that we use
 */
protected TcpSocket getSocket()
{
	return socket;
}

/**
 * Returns the reason that we stopped.
 * <code>null</code> means the session hasn't stopped or has stopped
 * normally; any other value is usually the message of the exception
 * that caused the session to stop.
 * <p>
 * Is een beetje een probleemveldje. Is nodig omdat de stopReason bepaald wordt op een moment waarop we
 * niet meteen een event kunnen sturen met deze reason erin. Dus we moeten hem even bewaren totdat we
 * het event kunnen afsturen. Ik zou niet weten hoe je dat anders moet doen.
 * @return the reason that we stopped.
 */
@Override
public String getStopReason()
{
	return stopReason;
}

/**
 * @return the thread
 */
@Override
public Thread getThread()
{
	return thread;
}

/**
 * @return The last time when activity occurred.
 */
@Override
public long getTimeLastActivity()
{
	return timeLastActivity; // timeLastActivity is volatile
}

/**
 * @return The time the session was started
 */
@Override
public final long getTimeStarted()
{
	return timeStarted;
}

/**
 * @return whether this session is stopped, i.e. no longer accepting client requests
 */
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
 * Sets the reason that we stopped.
 * <code>null</code> means the session hasn't stopped or has stopped
 * normally; any other value is usually the message of the exception
 * that caused the session to stop.
 * <p>
 * Is een beetje een probleemveldje. Is nodig omdat de stopReason bepaald wordt op een moment waarop we
 * niet meteen een event kunnen sturen met deze reason erin. Dus we moeten hem even bewaren totdat we
 * het event kunnen afsturen. Ik zou niet weten hoe je dat anders moet doen.
 * @param newStopReason The reason that we stopped.
 */
private void setStopReason( String newStopReason )
{
	stopReason = newStopReason;
}

/**
 * Marks that this session is stopped, i.e. no longer accepting client requests
 */
protected void setStopped()
{
	stopped = true;
}

/**
 * Marks that this session is stopping, so that the <code>SocketException</code> that
 * we cause is not mistaken for a real error
 */
protected void setStopping()
{
	stopping = true;
}

/**
 * @param aThread the thread to set
 */
@Override
public void setThread( Thread aThread )
{
	thread = aThread;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// SesionListener management

private List<SessionListener> getSessionListenerList()
{
	return sessionListeners;
}

@Override
public void addSessionListener( SessionListener l )
{
	getSessionListenerList().add( l );
}

@Override
public void removeSessionListener( SessionListener l )
{
	getSessionListenerList().remove( l );
}

private void fire( SessionEvent e )
{
	for ( SessionListener sessionListener : getSessionListenerList() )
	{
		sessionListener.sessionEvent( e );
	}
}

protected void fireSessionStarted()
{
	fire( new SessionEvent( SessionEvent.SESSION_STARTED, this ) );
}

protected void fireSessionStopped()
{
	fire( new SessionEvent( SessionEvent.SESSION_STOPPED, this ) );
}

protected void fireSessionChanged()
{
	fire( new SessionEvent( SessionEvent.SESSION_CHANGED, this ) );
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Running

/**
 * Updates the last-activity time with System.currentTimeMillis()
 */
protected void updateTimeLastActivity()
{
	timeLastActivity = System.currentTimeMillis(); // timeLastActivity is volatile
}

/**
 * Starts the conversation with the client
 */
@Override
public final void run()
{
	if ( isStopped() )
	{
		throw new IllegalStateException( "A stopped session cannot be run again. Create new sessions instead" );
	}
	try
	{
		doRun();
	}
	finally
	{
		try
		{
			verwerk_cleanup();
		}
		catch ( Exception e )
		{
			Log.error( this, "Error during cleanup", e );
		}
		setStopped(); // stopped is volatile
		try
		{
			stop();
		}
		catch ( Exception e )
		{
			Log.error( this, "Error during stop", e );
		}
		try
		{
			fireSessionStopped();
		}
		catch ( Exception e )
		{
			Log.error( this, "Error during fireSessioStopped", e );
		}
	}
}

/**
 * Does the actual work of holding a conversation with the client. The following methods are called
 * in this exact order:
 * <ul>
 * <li> verwerk_init() - Do initialisation here
 * <li> fireSessionStarted - Signals to interested parties that a new session has been born
 * <li> verwerkImpl() - Run a loop that reads client input, processes it, etc
 * <li> verwerk_cleanup() - Do any cleanup after the session is stopped
 * <li> fireSessionStopped - Signals to interested parties that a session has died
 * <ul>
 */
private void doRun()
{
	// Recovery remarks
	// If anything goes wrong, this thread simply stops. The clients are
	// designed to autmatically reconnect. Could be improved, but this is
	// simple and good enough for now.

	// HIGH Volgens mij moet je stop() aanroepen als er iets mis gaat. Zo kunnen afgeleiden hun
	//       troep opruimen en files closen etc
	try
	{
		verwerk_init();
		fireSessionStarted();
		verwerkImpl();
	}
	catch ( EOFException e)
	{
		// Dit is normaal als de client de connectie sluit
		// LOW EOFException kan normaal zijn maar hoe zie je dat? Tevens melding (dan) verbeteren
		// HIGH Hoe kom ik hier bij "closing connection"???????????
		Log.error( this, "Caught java.io.EOFException closing connection.", e );
	}
	catch ( SocketException e )
	{
		// If stopping is true, this is a normal exit.
		// Note: stopping is a volatile field.
		// @@NOG Print message if we got the wrong exception?
		if ( ! isStopping() )
		{
			setStopReason( e.getMessage() );

			// Meestal "Connection reset by peer" of in latere java-versies "Connection reset"
			if ( ! e.getMessage().startsWith( "Connection reset" ) )
			{
				Log.error( this, "Client #" + getSessionId() + " quitting because of " + e.getMessage(), e );
			}
		}
	}
	catch ( IOException e )
	{
		Log.error( this, "Unexpected IOException", e );
		setStopReason( e.getMessage() );
	}
	catch ( Exception e )
	{
		Log.error( this, "Unexpected exception. Closing connection. This needs to be investigated!", e);
		setStopReason( e.getMessage() );
	}
}

// LOW Kijk of Exception niet wat specifieker kan worden
protected abstract void verwerk_init() throws Exception;
protected abstract void verwerk_cleanup();
protected abstract void verwerkImpl() throws Exception;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Stopping

/**
 * Closes the socket that we are listening on. As a result, this <code>Runnable</code>ends.
 * If we are the <code>Runnable</code> of a thread (most likely), the thread will terminate also.
 * <p>
 * This method is designed to be called from a different thread than the one that
 * the listener is listening on.
 */
@Override
public void stop()
{
	// Multithread remarks
	// Usually, the verwerkImpl() is busy processing client input, and runs on its own thread.
	// This method might be called from the outside (by a different thread) or from the inside,
	// when verwerkImpl() stops because the conversation is over or because some error occurs,
	// the most common being a client disconnecting.
	// We cannot synchronize on the socket because verwerkImpl() could be holding the lock arbitrarily
	// long. So we do not synchronize at all here.
	// The basic idea is that when called from the outside, we close the socket, forcing an exception
	// in verwerkImpl(). And when verwerkImpl() ends, stop() is also called, to close the socket.
	// So we have two scenarios:
	// 1. Inside job
	//    verwerkImpl() ends, for whatever reason. stop() is called, the socket is closed, stopped
	//    is set to true, and we're done.
	// 2. Outside job
	//    - stop() is called from a different thread. Now two threads run and might interfere with
	//      each other: the thread calling stop() and the thread that used to have a conversation with
	//      the client, but is now in the process of stopping (and it will call stop() as well).
	//    - If we may assume verwerkImpl() is still busy conversing with the client, then it won't call
	//      stop() until we close the socket. But after that, we do nothing here so nothing can be
	//      messed up.
	//    - In the rare event that verwerkImpl() is already ending at about the same time that we
	//      close the socket, we have a sort of race condition. But the only things that could happen
	//      is that
	//      - the socket is closed twice. Although not documented, this is ok with sockets.
	//      - if something is really bad with the socket, such that close() throws an IOException,
	//        we may log this exception twice. I can live with that.
	// Finally we have the 'stopping' boolean. It's only use is to tell verwerkImpl() that this is a
	// normal exit, in spite of the SocketException that we cause. Now stopping is volatile so there
	// never is a question of a thread reading the wrong value of it. and the race condition mentioned
	// above has no impact because 'stopping' is tested BEFORE stop() is called
	// TODO Dit zou misschien allemaal vereenvoudigd kunnen worden. Maar dit algoritme werkt al eeuwen.
	setStopping();
	try
	{
		// Socket kan niet null zijn. En we kunnen niet testen of socket al gesloten is, geen isClosed() o.i.d..
		getSocket().close();
	}
	catch ( IOException e)
	{
		// MARK LogLog
		Log.error( e, "Error closing socket" );
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Equals, hashCode en toString

@Override
public boolean equals( Object aObject )
{
	if ( aObject == this )
	{
		return true;
	}
	if ( ! ( aObject instanceof AbstractSession )  )
	{
		return false;
	}
	return getSessionId() == ( (AbstractSession) aObject ).getSessionId();
}

@Override
public int hashCode()
{
	return getSessionId();
}

/**
 */
@Override
public String toString()
{
	StringBuffer sb = new StringBuffer();
	sb.append( '#' ).append( getSessionId() ).append( ' ' );
	sb.append( getIpAddress() ).append( ' ' );
	// @@NOG Maybe cache this Date
	sb.append( FormatHelper.getDateKortTimeFormatter().format( new Date( getTimeStarted() ) ) );
	return sb.toString();
}

}
