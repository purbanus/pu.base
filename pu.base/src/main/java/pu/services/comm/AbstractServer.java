package pu.services.comm;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract server for a class of servers that occurs often:
 * <ul>
 * <li> The server starts a set of CoonectionListeners (typically 1) thet listen on certain ports
 * <li> For every connection attempt a new clienthandler is started on a new thread: a Session. This session
 *      handles the conversation with the client.
 * </ul>
 * This <code>AbstractServer</code> can be a useful base class for a large number of servers:
 * <ul>
 * <li> A webserver
 * <li> Many kinds of RMI servers
 * <li> A chat server
 * <li> Log servers
 * </ul>
 * And many other kinds of server
 */
// HIGH Test maken voor het 'contract' van een AbstractServer. Paar dingen:
//       - garanties over (onverwachte) fouten: dat running de juiste waarde heeft,
//         dat de boel integer is, etc.
//       - Onafhankelijkheid van slordige fouten in subclasses
//       - Wij moeten gegarandeerd in de lucht blijven tenzij stop() wordt aangeroepen
//         Dat is niet zo moeilijk want we doen niets! Al het "in de lucht blijven"
//         zit in ConnectionListener.
public abstract class AbstractServer implements Server
{
/**
 * The connectionlisteners that actually listen to the server ports
 * and starts client conversations
 */
private List<ConnectionListener> connectionListeners = new ArrayList<>();

/**
 * Creates a new AbstractServer
 */
public AbstractServer()
{
	super();
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Getters en setters

/**
 * @return The List of ConnectionListeners
 */
public List<ConnectionListener> getConnectionListeners()
{
	return connectionListeners;
}

/**
 * Sets the list of connectionListeners. I currently have no need for add/removeCollectionListener
 * @param The List of ConnectionListeners
 */
public void setConnectionListeners( List<ConnectionListener> aConnectionListeners )
{
	stopConnectionListeners();
	connectionListeners = aConnectionListeners;
}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Start / stop

/**
 * Starts the server by starting threads that listen to connections
 */
@Override
public final void run()
{
	for ( ConnectionListener connectionListener: getConnectionListeners() )
	{
		Thread thr = new Thread( connectionListener, connectionListener.getLogName() );

		// LOW It doesn't seem useful to give these threads a high priority, since they handle
		//      connection events, and a little delay doen't matter then.
		//      CHECK REASONING
		//		thr.setPriority( Thread.MAX_PRIORITY );

		thr.start();
	}
}

/**
 * Stops the ConnectionListeners by calling their <code>stop()</code> methods.
 * This is <em>not</em> the Thread.stop() method since a ConnectionListener is not a Thread.
 */
public void stop()
{
	try
	{
		stopConnectionListeners();
		// @@NOG Als je altijd een List met clients hebt, kun je die nu meteen killen allemaal
		//       Die List is er nog niet, zit in de afgeleide classes. Ideetje.
	}
	finally
	{
	}
}

private void stopConnectionListeners()
{
	for ( ConnectionListener connectionListener : getConnectionListeners() )
	{
		connectionListener.stop();
	}
}

}
