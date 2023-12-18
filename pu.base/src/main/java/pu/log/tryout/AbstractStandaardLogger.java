package pu.log.tryout;

import pu.services.StopWatch;

/**
 * Even een class die geheel standaard op de log4j-manier logt, om te kijken of we niet
 * van het rechte pad afdwalen
 */
public abstract class AbstractStandaardLogger implements Runnable
{

/**
 *
 */
public AbstractStandaardLogger()
{
	super();
}

@Override
public void run()
{
	doDebug( "Debug message  " );
	doError( "Error message  " );
	doFatal( "Fatal message  " );
	doInfo ( "Info message   " );
	doWarn ( "Warning message" );

	runLoggertje();

	try
	{
		throwException();
	}
	catch ( RuntimeException e )
	{
		doInfo( "Help, een RuntimeException", e );
	}
	final int ROUNDS = 500;
	StopWatch timer = new StopWatch();
	for ( int x = 0; x < ROUNDS; x++ )
	{
		doInfo( "timing " + x );
	}
	doInfo( ROUNDS + " rondjes duurde " + timer.getElapsedMs() );
	try
	{
		Thread.currentThread().join();
	}
	catch (InterruptedException ignore )
	{
	}
}
protected abstract void runLoggertje();

protected abstract void doDebug( String aMessage );
protected abstract void doError( String aMessage );
protected abstract void doFatal( String aMessage );
protected abstract void doInfo( String aMessage );
protected abstract void doInfo( String aMessage, Throwable aThrowable );
protected abstract void doWarn( String aMessage );

private void throwException()
{
	throw new RuntimeException( "Who's afraid of RuntimeExceptions?" );
}

}