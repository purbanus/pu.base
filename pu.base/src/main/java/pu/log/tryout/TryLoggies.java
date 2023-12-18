/*
 * Created on 12-nov-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.log.tryout;

import org.apache.log4j.LogManager;

import pu.log.Log;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TryLoggies
{

/**
 *
 */
public TryLoggies()
{
	super();
}

public static void main(String[] args)
{
	new TryLoggies().verwerk();
}
public void verwerk()
{
	// LET OP: De laatste test van TryLoggies was met log4j_04.properties

	// NDC Gaan we gebruiken in de remotelog server denk ik. Maar voorlopig gaat het effe met MDC
	// ==> Eigenlijk lijkt NDC mij een slecht idee.
	//NDC.push( "pipo" );

	// Dit is vervallen
	//MDC.put( "ip", "10.4.200.1" );
	//MDC.put( "username", "peter#u" );

	// HIGH isStarted werkt niet!
	//       Betere manier: AbstracServer.isStarted met connListener != null
	//if ( ! Log4jServer.isStarted() )
	//{
	//	Log4jServerRunner.main( new String [] {} );
	//}

	// Probeer de levels1
	Log.debug  ( this, "Debug message  " );
	Log.error  ( this, "Error message  " );
	Log.fatal  ( this, "Fatal message  " );
	Log.info   ( this, "Info message   " );
	Log.warn( this, "Warning message" );

	Log.debug  ( this, "Debug message met  ", new Throwable( "Een debug Throwable  " ) );
	//Log.logError  ( this, "Error message met  ", new Throwable( "Een error Throwable  " ) );
	//Log.logFatal  ( this, "Fatal message met  ", new Throwable( "Een fatal Throwable  " ) );
	//Log.logInfo   ( this, "Info message met   ", new Throwable( "Een info Throwable   " ) );
	//Log.logWarning( this, "Warning message met", new Throwable( "Een warning Throwable" ) );

	// HIGH Probleem: we zitten met SQL met quotes oim tekstvelden
	//Log.logInfo( this, "'Twas brillig, and the slithy toves");
	Log.info( this, "Twas brillig, and the slithy toves");
	Log.info( this, "Did gyre and gimble in the wabe");
	Log.info( this, "all mimsy were the borogoves");
	Log.info( this, "and the mome raths outgrabe");
	LogManager.shutdown();
}
}
