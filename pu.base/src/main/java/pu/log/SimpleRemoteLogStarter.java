package pu.log;

import pu.log.log2.Log;
import pu.log.log2.RemoteLogger;
import pu.log.log2.console.rlog.RLogOutputStream;
/**
 * Tijdelijke class, omdat het even de vraag is waar dit heen moet.
 * Misschien een soort loggertje in nl.mediacenter.application.Log,
 * hoewel dat een beetje wringt. Kortom, effe denke nog.
 */
// 26-11-2006 Hierheen gekopieerd omdat ik thuis geen nt9 heb
public class SimpleRemoteLogStarter
{
private static final String LINE = System.getProperty( "line.separator" );

private static final String RLOG_SERVER    = Config.RLOG_SERVER;
private static final int    RLOG_PORT      = Config.RLOG_SERVER_PORT;
private static final int    RLOG_RECONNECT = Config.RLOG_RECONNECT_DELAY;

private static RLogOutputStream rlogOutputStream = null;

/**
 * RemoteConsoleStarter constructor comment.
 */
private SimpleRemoteLogStarter()
{
	super();
}
public static RLogOutputStream getRLogOutputStream()
{
	if ( rlogOutputStream == null )
	{
		rlogOutputStream = new RLogOutputStream( RLOG_SERVER, RLOG_PORT, RLOG_RECONNECT );
		rlogOutputStream.connect();
		RemoteLogger logger = new RemoteLogger( rlogOutputStream );
		Log.addLoggerToAll( logger );
		logProperties();
	}
	return rlogOutputStream;
}

/**
 * Starts remote logging
 */
public static void start()
{
	getRLogOutputStream();
}
/**
 * Values for <code>java.class.version</code>
 * <table>
 * <tr><td>JDK 1.1</td><td>45.3</td></tr>
 * <tr><td>JDK 1.2</td><td>46.0</td></tr>
 * <tr><td>JDK 1.3</td><td>47.0</td></tr>
 * <tr><td>JDK 1.4</td><td>48.0</td></tr>
 * </table>
 */
private static void logProperties()
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
	logProperty( sb, "java.fullversion     " );
	logProperty( sb, "sun.boot.class.path  " );
	logProperty( sb, "sun.boot.library.path" );
	logProperty( sb, "user.name            " );
	logProperty( sb, "user.region          " );
	logProperty( sb, "user.timezone        " );

	Log.info( SimpleRemoteLogStarter.class, sb.toString() );

}
private static void logProperty( StringBuffer sb, String aProperty )
{
	sb.append( aProperty ).append( " " ).append( System.getProperty( aProperty.trim() ) ).append( LINE );
}
}
