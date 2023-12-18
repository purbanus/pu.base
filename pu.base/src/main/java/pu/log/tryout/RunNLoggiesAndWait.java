/*
 * Created on 12-nov-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.log.tryout;

import pu.log.Log;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RunNLoggiesAndWait implements Runnable
{
private static final int LOGGIES = 5;

/**
 *
 */
public RunNLoggiesAndWait()
{
	super();
}

public static void main(String[] args)
{
	System.getProperties().put( "log4j.debug", "true" );
	System.getProperties().put( "log4j.configuration", "pu/log/tryout/StandaardLog4JLogger.properties" );
	for ( int x = 0; x < LOGGIES; x++ )
	{
		new Thread( new RunNLoggiesAndWait(), "loggie " + x ).start();
	}
}
@Override
public void run()
{
	Log.debug  ( this, "Debug message  " );
	Log.error  ( this, "Error message  " );
	Log.fatal  ( this, "Fatal message  " );
	Log.info   ( this, "Info message   " );
	Log.warn( this, "Warning message" );

	try
	{
		Thread.currentThread().join();
	}
	catch (InterruptedException ignore )
	{
	}
}
}