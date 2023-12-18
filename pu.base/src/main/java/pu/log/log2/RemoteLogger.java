package pu.log.log2;

import java.io.OutputStream;
import java.io.PrintWriter;
/**
 * Logs to a remote server.
 * Nou ja, hij kan naar iedere outputStream loggen, dus ook naar een RLogOutputStream. Dat was praktischer,
 * zie RemoteLogStarter. Je moet een servernaam, port en reconnectdelay hebben en RemoteLogStarter is daar
 * toch al mee bezig.
 */
public class RemoteLogger extends AbstractLogger
{
	private final PrintWriter writer;
public RemoteLogger( OutputStream aOutputStream )
{
	super();
	writer = new PrintWriter( aOutputStream, true );
}
@Override
public void logImpl( String s )
{
	writer.println( s );
}
}
