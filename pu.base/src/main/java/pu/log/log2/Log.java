package pu.log.log2;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import pu.services.Globals;
import pu.services.StringHelper;
import pu.services.Util;

public class Log
{

	// Vaste categorieen
	public static final LogCategorie DEBUG = new LogDebug();
	public static final LogCategorie INFO  = new LogInfo();
	public static final LogCategorie ERROR = new LogError();
	public static final LogCategorie WARNING = new LogWarning();
	
	// Teksten voor nadrukkelijk loggen van OutOfMemoryErrors
	public static String[] outOfMemTekst;
	private static final int AANTAL_OOME_TEKSTREGELS = 20;
	
	/**
	 * Zet dit op true als je TOCH wilt loggen in applets, bijvoorbeeld in IrisRunner. 
	 */
	public static boolean overrideEnableConsoleLogging = false;

	/**
	 * Zet dit op true als je alleen de errors wilt loggen, niet de info
	 * (bijvoorbeeld vanuit signaleringslijst IB vs Planning) 
	 */
	public static boolean onlyLogErrorMessages = false;

	private static final Object lock = new Object();

	public static boolean LOG_THREAD = true;
	
	private static Map<Class<? extends LogCategorie>, List<Logger>> categorieMap = null;
	static
	{
		categorieMap = new HashMap<>();
		categorieMap.put( LogDebug  .class, new ArrayList<>() );
		categorieMap.put( LogError  .class, new ArrayList<>() );
		categorieMap.put( LogInfo   .class, new ArrayList<>() );
		categorieMap.put( LogWarning.class, new ArrayList<>() );

		// Standaard loggers
		addLoggerToAll( new ConsoleLogger() );
		
		// Extra tekst bij een OOME	moet in de static{ } worden geladen
		// omdat je de fout alleen kan loggen zonder nieuw geheugen te alloceren	
		outOfMemTekst = new String[ AANTAL_OOME_TEKSTREGELS ];
		for( int x = 0; x < AANTAL_OOME_TEKSTREGELS; x++ )
		{
		 	outOfMemTekst[ x ] = " Oh Jee! OutOfMemoryError " + StringHelper.repString( "@", x );			 	
		}
}
private Log()
{
	super();
}
private static Map<Class<? extends LogCategorie>, List<Logger>> getCategorieMap()
{
	return categorieMap;
}

private static void addLogger( LogCategorie aCategorie, Logger aLogger )
{
	// Multithread opmerkingen
	// We vervangen synchronized de List met loggers door een nieuwe.
	// Bij het loggen verderop halen we steeds synchronized de list op, en die List
	// kan niet veranderen omdat we hier en in removeLogger steeds een nieuwe maken.
	synchronized( lock )
	{
		List<Logger> loggers = getCategorieMap().get( aCategorie.getClass() );
		if ( loggers != null )
		{
			List<Logger> newLoggers = new ArrayList<>( loggers );
			newLoggers.add( aLogger );
			getCategorieMap().put( aCategorie.getClass(), newLoggers );
		}
	}
}
private static void removeLogger( LogCategorie aCategorie, Logger aLogger )
{
	synchronized( lock )
	{
		List<Logger> loggers = getCategorieMap().get( aCategorie.getClass() );
		if ( loggers != null )
		{
			List<Logger> newLoggers = new ArrayList<>();
			for ( Logger logger : loggers )
            {
	            if ( ! logger.getClass().isInstance( aLogger ) )
	            {
	            	newLoggers.add( logger );
	            }
            }
			getCategorieMap().put( aCategorie.getClass(), newLoggers );
		}
	}
}
public static void addLoggerToAll( Logger aLogger )
{
	addLogger( DEBUG  , aLogger );
	addLogger( ERROR  , aLogger );
	addLogger( INFO   , aLogger );
	addLogger( WARNING, aLogger );
}
public static void removeLoggerFromAll( Logger aLogger )
{
	removeLogger( DEBUG  , aLogger );
	removeLogger( ERROR  , aLogger );
	removeLogger( INFO   , aLogger );
	removeLogger( WARNING, aLogger );
}
/**
 * Removes all Loggers from all categories
 * @param cat
 */
public static void clearAllCategories()
{
	clearCategory( DEBUG   );
	clearCategory( ERROR   );
	clearCategory( INFO    );
	clearCategory( WARNING );
}
/**
 * Removes all Loggers from a category
 * @param aCategorie
 */
private static void clearCategory( LogCategorie aCategorie )
{
	// Multithread opmerkingen
	// We vervangen synchronized de List met loggers door een nieuwe.
	// Bij het loggen verderop halen we steeds synchronized de list op, en die List
	// kan niet veranderen omdat we hier en in add/removeLogger steeds een nieuwe maken.
	synchronized( lock )
	{
		List<Logger> loggers = getCategorieMap().get( aCategorie.getClass() );
		if ( loggers != null )
		{
			getCategorieMap().put( aCategorie.getClass(), new ArrayList<>() );
		}
	}
}

public static void enableConsoleLogger( boolean b )
{
	if ( ! overrideEnableConsoleLogging )
	{
		// Er kan gelogd worden terwijl we hier bezig zijn. Dat betekent dat het ene statement nog net wel,
		// en het andere statement net niet meer gelogd wordt. Pech gehad. Als we het wilden oplossen moet
		// de hele log( cat, s) synchronized worden en dat kan domweg niet.
		synchronized( lock )
		{
			for ( List<Logger> loggers : getCategorieMap().values() )
			{
				for ( Logger logger : loggers )
				{
					if ( logger instanceof ConsoleLogger )
					{
						logger.setEnabled( b );
					}
				}
			
			}
		}
	}
}
private static List<Logger> getLoggerListCopy( LogCategorie cat ) throws RuntimeException
{
	// Synchroniseer zo kort mogelijk om de loggers op te halen. Als later een
	// andere thread addLogger doet, heeft dat geen effect op de kopie die wij hebben.
	synchronized( lock )
	{
		return new ArrayList<>( getCategorieMap().get( cat.getClass() ) );
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Het loggen zelf

private static void log( Object aCaller, LogCategorie cat, Object msg, Throwable aThrowable )
{
	// Alleen fouten?  
	if ( onlyLogErrorMessages && ( ! cat.equals( ERROR ) ) )
	{
		return;
	}
	
	String finalMessage = null;

	List<Logger> loggerList = getLoggerListCopy( cat );
	if ( loggerList == null )
	{
		throw new RuntimeException( "Geen loggers voor " + cat.getClass().getName() );
	}
	for ( Logger logger : loggerList )
	{
		if ( logger.isZelfLogger() )
		{
			logger.log( aCaller, String.valueOf( msg ), aThrowable, cat );
		}
		else
		{
			if ( finalMessage == null )
			{
				finalMessage = maakMessage( cat, msg );
			}
			logger.log( aCaller, finalMessage, aThrowable, cat );
			if ( aThrowable != null )
			{
				logThrowable( aCaller, cat, aThrowable, logger );
			}
		}
	}	
}
/**
 * De OOME is een nare klant, omdat de stacktrace vaak niet beschikbaar is of
* kan worden gemaakt (vanwege diezelfde OutOfMemory!)
* Toch willen we iets meer loggen dan alleen 1 regeltje, want het is nogal ernstig.
*/
private static void logThrowable( Object aCaller, LogCategorie aCategorie, Throwable aThrowable, Logger aLogger )
{
//
//	// Het volgende statement mag niet mislukken
//	try
//	{
//		System.out.println( Globals.getTimeFormatter().format( new Date() ) ); 
//	}
//	catch( Throwable jammerDan )
//	{
//	}
//
	if ( aThrowable instanceof OutOfMemoryError )
	{
		// misschien dat dit opvalt.
		for( int x = 0; x < AANTAL_OOME_TEKSTREGELS; x++ )
		{
			// Oppassen! Hier geen nieuw geheugen alloceren, we printen alleen regels die
			// al zijn bepaald in de static initializer!
			System.err.println( outOfMemTekst[ x ] );	
		}
	}
	List<String> stackTrace = Util.getStackTraceAsList( aThrowable );
	for ( ListIterator<String> it = stackTrace.listIterator(); it.hasNext(); )
	{
		aLogger.log( aCaller, ( it.nextIndex() > 0 ? "\t" : "" ) + it.next(), null, aCategorie );
	}
}
private static String maakMessage( LogCategorie cat, Object msg )
{
	StringBuilder sb = new StringBuilder();
	sb.append( Globals.getTimeFormatter().format( new Date() ) )
	  .append( " " )
	  .append( cat.getLogType() );
	if ( LOG_THREAD )
	{
		sb.append( "[" )
		  .append( Thread.currentThread().getName() )
		  .append( "] " );
	}
	sb.append( msg );
	return sb.toString();
}

static String getClassOfObject( Object aCaller )
{
	if ( aCaller instanceof Class )
	{
		return ( (Class<?>) aCaller ).getName();
	}
	else
	{
		return aCaller.getClass().getName();
	}
}
public static void debug(Object aCaller, Object message)
{
	log( aCaller, DEBUG, message, null );
}
public static void debug( Object aCaller, Throwable e )
{
	log( aCaller, DEBUG, "Fout in " + getClassOfObject( aCaller ),  e );
}
public static void debug( Object aCaller, Object aMessage, Throwable e )
{
	log( aCaller, DEBUG, aMessage, e );
}
public static void error( Object aCaller, Object aMessage )
{
	log( aCaller, ERROR, aMessage, null );
}
public static void error( Object aCaller, Throwable e )
{
	log( aCaller, ERROR, "Fout in " + getClassOfObject( aCaller ),  e );
}
public static void error( Object aCaller, Object aMessage, Throwable e )
{
	log( aCaller, ERROR, aMessage, e );
}
public static void info( Object aCaller, Object message )
{
	log( aCaller, INFO, message, null );
}
public static void info( Object aCaller, Throwable e )
{
	log( aCaller, INFO, "Fout in " + getClassOfObject( aCaller ),  e );
}
public static void info( Object aCaller, Object aMessage, Throwable e )
{
	log( aCaller, INFO, aMessage, e );
}
public static void warning( Object aCaller, Object message )
{
	log( aCaller, WARNING, message, null );
}
public static void warning( Object aCaller, Throwable e )
{
	log( aCaller, WARNING, "Fout in " + getClassOfObject( aCaller ),  e );
}
public static void warning( Object aCaller, Object aMessage, Throwable e )
{
	log( aCaller, WARNING, aMessage, e );
}
}
