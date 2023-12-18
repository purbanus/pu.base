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
public class RemoteLogStarter
{
	public static final String USE_AS400_USER = "nl.mediacenter.use-as400-user";
	public static final String DO_CONSOLE_REDIRECTION = "nl.mediacenter.do-console-logging";
	private static final String LINE = System.getProperty( "line.separator" );
	private static boolean logStarted = false;
	
	public static class EventErrorHandler
	{
		public EventErrorHandler()
		{
			super();
		}
		public void handle( Throwable e )
		{
			//e.printStackTrace();
			Log.error( this, "---Fout in Event Dispatcher---", e );
		}
	}
/**
 * RemoteConsoleStarter constructor comment.
 */
private RemoteLogStarter()
{
	super();
}

public static void start()
{
	if ( Cfg.getDbsCfg().isRlogActive() )
	{
		if ( ! logStarted )
		{
			RLogOutputStream rLogOutputStream = new RLogOutputStream(
				Cfg.getDbsCfg().getRlogServer(),
				Cfg.getDbsCfg().getRlogServerPort(),
				Cfg.getDbsCfg().getRlogReconnectDelay()
			);
			rLogOutputStream.connect();

			boolean doConsoleLogging = "true".equals( System.getProperty( DO_CONSOLE_REDIRECTION ) );
			if ( doConsoleLogging || Cfg.getDbsCfg().isRlogRedirectConsole() )
			{
				ConsoleRedirector.getInstance().addStream( rLogOutputStream );
			}
			else
			{
				RemoteLogger logger = new RemoteLogger( rLogOutputStream );
				Log.addLoggerToAll( logger );
			}
			logStarted = true;
			logProperties();
		}
	}
}
/**
 * Values for <code>java.class.path</code>
 * <table>
 * <tr><td>JDK 1.1</td><td>45.3</td></tr>
 * <tr><td>JDK 1.2</td><td>46.0</td></tr>
 * <tr><td>JDK 1.3</td><td>47.0</td></tr>
 * <tr><td>JDK 1.4</td><td>48.0</td></tr>
 * </table>
 */
public static void logProperties()
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
	logUser( sb );
	logPropertyAndValue( sb, "pu.base.version      ", Version.getVersion( "pu" ) );

	Log.info( RemoteLogStarter.class, sb.toString() );

}
private static void logUser( StringBuffer sb )
{
	String user = "user.name            ";
	// Zolang effe niet
//	if ( "true".equals( System.getProperty( USE_AS400_USER ) ) )
//	{
//		if ( Cfg.isCfgStrategySet() )
//		{
//			Login login = Cfg.getLogin();
//			if ( login != null )
//			{
//				String userName = login.getUser();
//				if ( userName != null )
//				{
//					logPropertyAndValue( sb, user, userName );
//					return;
//				}
//			}
//		}
//	}
	logProperty( sb, user );
}
private static void logProperty( StringBuffer sb, String aProperty )
{
	logPropertyAndValue( sb, aProperty, System.getProperty( aProperty.trim() ) );
}
private static void logPropertyAndValue( StringBuffer sb, String aProperty, String aValue )
{
	sb.append( aProperty ).append( " " ).append( aValue ).append( LINE );
}
}
