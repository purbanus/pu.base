package pu.log.tryout;

import org.apache.log4j.Logger;

/**
 * Even een class die geheel standaard op de log4j-manier logt, om te kijken of we niet
 * van het rechte pad afdwalen
 */
public class StandaardLog4JLogger extends AbstractStandaardLogger
{
// Als je deze static maakt komen die System.getProps().put... statements te laat want dan is het
// al gebeurd.
public final Logger logger = Logger.getLogger( StandaardLog4JLogger.class );

class Loggertje1
{
final Logger eigenLogger = Logger.getLogger( getClass() );
final Logger nogEenLogger = Logger.getLogger( "pipo.de.clown" );
public void run()
{
	doInfo( "Loggertje gebruikt buitenste logger");
	eigenLogger.info( "Loggertje gebruikt eigen logger");
	nogEenLogger.info( "Loggertje gebruikt nog een andere logger");
}
}

/**
 *
 */
public StandaardLog4JLogger()
{
	super();
}

public static void main(String[] args)
{
	System.getProperties().put( "log4j.debug", "true" );
	System.getProperties().put( "log4j.configuration", "pu/log/tryout/StandaardLog4JLogger.properties" );
	new StandaardLog4JLogger().run();
}

@Override
protected void runLoggertje()
{
	new Loggertje1().run();
}

@Override
protected void doDebug( String aMessage )
{
	logger.debug( aMessage );
}

@Override
protected void doError( String aMessage )
{
	logger.error( aMessage );
}

@Override
protected void doFatal( String aMessage )
{
	logger.fatal( aMessage );
}

@Override
protected void doInfo( String aMessage )
{
	logger.info( aMessage );
}

@Override
protected void doInfo( String aMessage, Throwable aThrowable )
{
	logger.info( aMessage, aThrowable );
}

@Override
protected void doWarn( String aMessage )
{
	logger.warn( aMessage );
}

}