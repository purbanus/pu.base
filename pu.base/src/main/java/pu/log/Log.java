package pu.log;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * This type was created in VisualAge.
 */
// TODO String messages vervangen door Object messages.
//       Maar dan wel checken dat het geen throwable is want dan is het een oude log-message
// HIGH In mc.base heet warn() nog steeds warning(). Wordt waarschijnlijk niet veel gebruikt
// HIGH Zowel hier als in mc.base is van elke log-methode nog een variant met alleen een throwable. Moet dat? Er is nog een kleine kans op ambiguiteit met de Object methode.
public class Log
{
private static final String LOG_APP_ID_KEY = "LOG_APP_ID";
private static final String LOG_APP_ID_DEFAULT = "TEST";
private static final String LOGGER_FQCN = Log.class.getName();

private static boolean loggerInitialized = false;

/**
 * De Map met loggers, om niet steeds een logger in de hierarchie te hoeven opzoeken.
 * N.B. Mag alleen gebruikt worden in getLogger( Object ), zie het commentaar daar.
 */
private static final Map<Class<?>, Logger> loggerMap = new HashMap<>();

/**
 * Prevents instances from being created
 */
private Log()
{
	super();
}
/**
 *
 * @return The application-id to be logged. This can be set as a system property
 */
public static String getApplicationID()
{
	String id = System.getProperty( LOG_APP_ID_KEY );
	return id != null ? id : LOG_APP_ID_DEFAULT;
}

private static Logger getLogger( Object aClient )
{
	// Initialiseer ons systeem als dat nog niet eerder gebeurd is
	if ( ! loggerInitialized )
	{
		// Momenteel is dit leeg
		initializeLogger();
		loggerInitialized = true;
	}

	// Het schijnt nogal expensief te zijn om steeds die Logger.getLogger aan te roepen.
	// Dus maar even een HashMapje.
	// Effe oplette: we moeten met threads rekening houden.
	//   Simpel: Als dit het enige punt is waar we loggerMap gebruiken kunnen we gewoon synchroniseren
	//   op loggerMap.
	//   Volgens mij is het gebruik van een HashTable ZONDER hier te synchroniseren incorrect omdat je
	//   twee keer de Map gebruikt, en God weet wat er daartussen gebeurd is.
	//   Aan de andere kant, het enige wat er gebeurd kan zijn is dat een andere thread een map-entry toegevoegd
	//   heeft die precies gelijk is aan wat wij wilden toevoegen. Het kan zijn dat je hier helemaal niet hoeft te
	//   synchronizen, maar om dat zeker te weten moet je de implementatie van HashMap (of wat dat betreft, HasshTable)
	//   uiterst nauwkeurig bestuderen. Dat kost veel te veel tijd (nog los van de omstandigheid dat die implementatie
	//   kan wijzigen.
	//   De synchronized hieronder lijkt me hiermee adequaat verantwoord.
	//
	//   Er is echter nog iets anders dat niks met threads te makenn heeft. In commons-logging hebben ze ongeveer
	//   hetzelfde probleem als wij, namelijk dat het maken van loggers naar een centrale plaats gedirigeerd is.
	//   En als je dan kijkt hoe ze dat opgelost hebben, zitten ze voornamelijk ClassLoader-problemen op te lossen.
	//   Dat soort problemen zijn de ergste van allemaal zo'n beetje, dus:
	//   waarom doen we niet gewoon commons.logging.......?
	Logger logger;
	Class<?> clientClass = aClient.getClass();
	synchronized ( loggerMap )
	{
		logger = loggerMap.get( clientClass );
		if ( logger == null )
		{
			logger = Logger.getLogger( clientClass );
			loggerMap.put( clientClass, logger );
		}
	}
	return logger;
}

private static void initializeLogger()
{
	// Niks meer
}

private static void log( Object aClient, Level aLevel, Object aMessage, Throwable aThrowable )
{
	// Wat is de rol van die LOGGER_FQCN, die immers altijd "pu.log.Log" is? Ik denk dat het
	getLogger( aClient ).log( LOGGER_FQCN, aLevel, aMessage, aThrowable );
}

/**
 * Logs a message with serverity DEBUG
 */
public static void debug( Object aClient, Object aMessage )
{
	log( aClient, Level.DEBUG, aMessage, null );
}

/**
 * Logs a message with serverity DEBUG
 */
public static void debug( Object aClient, Object aMessage, Throwable aThrowable )
{
	log( aClient, Level.DEBUG, aMessage, aThrowable );
}

/**
 * Logs a message with serverity DEBUG
 */
public static void debug( Object aClient, Throwable aThrowable )
{
	log( aClient, Level.DEBUG, aThrowable.getMessage(), aThrowable );
}

/**
 * Logs a message with serverity ERROR
 */
public static void error( Object aClient, Object aMessage )
{
	log( aClient, Level.ERROR, aMessage, null );
}

/**
 * Logs a message with serverity ERROR
 */
public static void error( Object aClient, Object aMessage, Throwable aThrowable )
{
	log( aClient, Level.ERROR, aMessage, aThrowable );
}

/**
 * Logs a message with serverity ERROR
 */
public static void error( Object aClient, Throwable aThrowable )
{
	log( aClient, Level.ERROR, aThrowable.getMessage(), aThrowable );
}

/**
 * Logs a message with serverity FATAL
 */
public static void fatal( Object aClient, Object aMessage )
{
	log( aClient, Level.FATAL, aMessage, null );
}

/**
 * Logs a message with serverity FATAL
 */
public static void fatal( Object aClient, Object aMessage, Throwable aThrowable )
{
	log( aClient, Level.FATAL, aMessage, aThrowable );
}

/**
 * Logs a message with serverity FATAL
 */
public static void fatal( Object aClient, Throwable aThrowable )
{
	log( aClient, Level.FATAL, aThrowable.getMessage(), aThrowable );
}

/**
 * Logs a message with serverity INFO
 */
public static void info( Object aClient, Object aMessage )
{
	log( aClient, Level.INFO, aMessage, null );
}

/**
 * Logs a message with serverity INFO
 */
public static void info( Object aClient, Object aMessage, Throwable aThrowable )
{
	log( aClient, Level.INFO, aMessage, aThrowable );
}

/**
 * Logs a message with serverity INFO
 */
public static void info( Object aClient, Throwable aThrowable )
{
	log( aClient, Level.INFO, aThrowable.getMessage(), aThrowable );
}

/**
 * Logs a message with serverity WARNING
 */
public static void warn( Object aClient, Object aMessage )
{
	log( aClient, Level.WARN, aMessage, null );
}

/**
 * Logs a message with serverity WARNING
 */
public static void warn( Object aClient, Object aMessage, Throwable aThrowable )
{
	log( aClient, Level.WARN, aMessage, aThrowable );
}

/**
 * Logs a message with serverity WARNING
 */
public static void warn( Object aClient, Throwable aThrowable )
{
	log( aClient, Level.WARN, aThrowable.getMessage(), aThrowable );
}

}