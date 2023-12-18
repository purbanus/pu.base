package pu.log.tryout;

import pu.log.SimpleRemoteLogStarter;
import pu.log.log2.Log;

/**
 * Even een class die geheel standaard op de log4j-manier logt, om te kijken of we niet
 * van het rechte pad afdwalen
 */
public class StandaardRLogLogger extends AbstractStandaardLogger
{

/**
 *
 */
public StandaardRLogLogger()
{
	super();
}

public static void main(String[] args)
{
	SimpleRemoteLogStarter.start();
	new StandaardRLogLogger().run();
}

@Override
protected void runLoggertje()
{
}

@Override
protected void doDebug( String aMessage )
{
	Log.debug( this, aMessage );
}

@Override
protected void doError( String aMessage )
{
	Log.error( this, aMessage );
}

@Override
protected void doFatal( String aMessage )
{
	// Er is geen fatal
	Log.error( this, aMessage );
}

@Override
protected void doInfo( String aMessage )
{
	Log.info( this, aMessage );
}

@Override
protected void doInfo( String aMessage, Throwable aThrowable )
{
	Log.info( this, aMessage, aThrowable );
}

@Override
protected void doWarn( String aMessage )
{
	Log.warning( this, aMessage );
}

}