package pu.log.log2.console;

import pu.log.log2.Log;
import pu.log.log2.RemoteLogger;
import pu.log.log2.console.rlog.RLogOutputStream;
import pu.services.Version;
/**
 * Tijdelijke class, omdat het even de vraag is waar dit heen moet.
 * Misschien een soort loggertje in nl.mediacenter.application.Log,
 * hoewel dat een beetje wringt. Kortom, effe denke nog.
 */
public class SimpleRemoteLogStarter
{
	private static final String LINE = System.getProperty( "line.separator" );
	private static final int    DEFAULT_RLOG_PORT      = 44444;
	private static final int    DEFAULT_RLOG_RECONNECT_TIME = 60000;
	
	private static boolean logStarted = false;
	
	private final String server;
	private final int port;
	private final int rlogReconnectTime; 
	
/**
 * SimpleRemoteConsoleStarter constructor comment.
 */
public SimpleRemoteLogStarter( String aServer, int aPort, int aRlogReconnectTime )
{
	super();
	server = aServer;
	port = aPort;
	rlogReconnectTime = aRlogReconnectTime;
}
public SimpleRemoteLogStarter( String aServer )
{
	this( aServer, DEFAULT_RLOG_PORT, DEFAULT_RLOG_RECONNECT_TIME );
}

public String getServer()
{
	return server;
}
public int getPort()
{
	return port;
}
public int getRlogReconnectTime()
{
	return rlogReconnectTime;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Bizniz

public void start()
{
	if ( ! logStarted )
	{
		logStarted = true;
		RLogOutputStream rLogOutputStream = createRLogOutputStream();
		createRemoteLogger(rLogOutputStream);
		logProperties();
	}
}

public void startWithConsoleRedirection()
{
	if ( ! logStarted )
	{
		logStarted = true;
		Log.clearAllCategories();
		RLogOutputStream rLogOutputStream = createRLogOutputStream();
		createRemoteLogger(rLogOutputStream);
		ConsoleRedirector.getInstance().addStream( rLogOutputStream );
		logProperties();
	}
}
private RLogOutputStream createRLogOutputStream()
{
	RLogOutputStream rLogOutputStream = new RLogOutputStream( getServer(), getPort(), getRlogReconnectTime() );
	rLogOutputStream.connect();
	return rLogOutputStream;
}
private void createRemoteLogger( RLogOutputStream rLogOutputStream )
{
	RemoteLogger logger = new RemoteLogger( rLogOutputStream );
	Log.addLoggerToAll( logger );
}
@SuppressWarnings( "unused" )
private void clearLogCategories()
{
	Log.clearAllCategories();
}

/**
 * Values for <code>java.class.path</code>
 * <table>
 * <tr><td>JDK 1.1</td><td>45.3</td></tr>
 * <tr><td>JDK 1.2</td><td>46.0</td></tr>
 * <tr><td>JDK 1.3</td><td>47.0</td></tr>
 * <tr><td>JDK 1.4</td><td>48.0</td></tr>
 * </table>
</tr>
</table>

 *
 */
private void logProperties()
{
	StringBuffer sb = new StringBuffer();
	sb.append( LINE );
	logProperty( sb, "os.name              " );
	logProperty( sb, "os.version           " );
	logProperty( sb, "java.class.path      " );
	logProperty( sb, "java.ext.dirs        " );
	logProperty( sb, "java.home            " );
	logProperty( sb, "java.library.path    " );
	logProperty( sb, "java.runtime.name    " );
	logProperty( sb, "java.runtime.version " );
	logProperty( sb, "java.version         " );
	logProperty( sb, "java.vm.version      " );
	logProperty( sb, "java.class.version   " );
	logProperty( sb, "sun.boot.class.path  " );
	logProperty( sb, "sun.boot.library.path" );
	logProperty( sb, "user.name            " );
	logPropertyAndValue( sb, "mc.base.version      ", Version.getVersion( "nl.mediacenter" ) );

	Log.info( SimpleRemoteLogStarter.class, sb.toString() );

}
private void logProperty( StringBuffer sb, String aProperty )
{
	logPropertyAndValue( sb, aProperty, System.getProperty( aProperty.trim() ) );
}
private void logPropertyAndValue( StringBuffer sb, String aProperty, String aValue )
{
	sb.append( aProperty ).append( " " ).append( aValue ).append( LINE );
}
}
