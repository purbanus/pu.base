/**
 *
 */
package pu.services;

import java.util.Date;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/**
 * @author Peter Urbanus
 *
 */
public class LogHelper
{
private static final String DEFAULT_LOG4J_PROPERTIES = "log4j.properties";
/** Application properties */
public static final String PROP_APPLICATION_ID        = "APPLICATION_ID";

/**
 *
 */
private LogHelper()
{
	super();
}

public static String getVerboseEvent( LoggingEvent aEvent )
{
	// LOW Het zou handig zijn om hier de complete MDC & NDC te printen. Echter, zo te zien is dat afgeschermd
	//      Misschien later nog es kijken, eventueel aanmelden als bug/wens
	StringBuffer sb = new StringBuffer();
	sb.append( "-------------" ).append( '\n' );

	// Vars
	sb.append( "time         :" ).append( new Date( aEvent.timeStamp ) ).append( '\n' );
	sb.append( "fqcnCat      :").append( aEvent.fqnOfCategoryClass ).append( '\n' );

	// Methods
	sb.append( "Level        :" ).append( aEvent.getLevel() ).append( '\n' );
	sb.append( "LoggerName   :" ).append( aEvent.getLoggerName() ).append( '\n' );
	//sb.append( "MDC-app      :" ).append( aEvent.getMDC( "app" ) ).append( '\n' );
	//sb.append( "MDC-user     :" ).append( aEvent.getMDC( "user" ) ).append( '\n' );
	sb.append( "Message      :" ).append( aEvent.getMessage() ).append( '\n' );
	sb.append( "NDC          :" ).append( aEvent.getNDC() ).append( '\n' );
	sb.append( "RenderedMsg  :" ).append( aEvent.getRenderedMessage() ).append( '\n' );
	sb.append( "ThreadName   :" ).append( aEvent.getThreadName() ).append( '\n' );

	// Samengesteld
	LocationInfo locInfo = aEvent.getLocationInformation();
	sb.append( "LocationInfo :" ).append( '\n' );
	sb.append( "  ClassName  :" ).append( locInfo.getClassName()  ).append( '\n' );
	sb.append( "  FileName   :" ).append( locInfo.getFileName()   ).append( '\n' );
	sb.append( "  LineNumber :" ).append( locInfo.getLineNumber() ).append( '\n' );
	sb.append( "  MethodName :" ).append( locInfo.getMethodName() ).append( '\n' );

	ThrowableInformation throwableInfo = aEvent.getThrowableInformation();
	sb.append( "ThrowableInfo:" );
	if ( throwableInfo == null )
	{
		sb.append( "null\n" );
	}
	else
	{
		sb.append( "\n" );
		sb.append( "  Throwable  :" ).append( throwableInfo.getThrowable() ).append( '\n' );
		sb.append( "  StringRep  :" );
		String [] str = aEvent.getThrowableStrRep();
		if ( str == null )
		{
			sb.append( "null\n" );
		}
		else
		{
			for ( String element : str )
			{
				sb.append( '\t' ).append( element ).append( '\n' );
			}
		}
	}

	return sb.toString();
}

/**
 * Configureert log4j voor startup debugging met de standaard "log4j.properties" file.
 * Roep dit aan in een class die niet een Log4j logger heeft, en als allereerste, bijvoorbeeld
 * als eerste statement in je main().
 * Zie meer commentaar in Log4jServerStarter
 * @param aConfigFile Dit is het classpath naar je configuratiefile
 * @param aApplicationId
 */
public static void setUpLog4j( Class<?> aClass )
{
	setUpLog4j( aClass, DEFAULT_LOG4J_PROPERTIES );
}

/**
 * Configureert log4j voor startup debugging met een afwijkende properties file.
 * Roep dit aan in een class die niet een Log4j logger heeft, en als allereerste, bijvoorbeeld
 * als eerste statement in je main().
 * Zie meer commentaar in Log4jServerStarter
 * @param aConfigFile Dit is het classpath naar je configuratiefile
 * @param aApplicationId
 */
public static void setUpLog4j( Class<?> aClass, String aConfigFile )
{
	setUpLog4j( StringHelper.classToPath( aClass ) + "/" + aConfigFile, StringHelper.getShortClassName( aClass ) );
}

/**
 * Configureert log4j voor startup debugging.
 * Roep dit aan in een class die niet een Log4j logger heeft, en als allereerste, bijvoorbeeld
 * als eerste statement in je main().
 * Zie meer commentaar in Log4jServerStarter
 * @param aConfigFile Dit is het volledige classpath naar je configuratiefile
 * @param aApplicationId Het application id voor logging, bijvoorbeeld "Uren", Rlog", "Dtp", etc.
 */
public static void setUpLog4j( String aConfigFile, String aApplicationId )
{
	System.getProperties().put( "log4j.debug", "true" );
	System.getProperties().put( "mc.log4j.root", "C:/Projecten/WSAD/workspace-320/pu.logserver.simple/log" );
	if ( aConfigFile != null )
	{
		System.getProperties().put( "log4j.configuration", aConfigFile );
	}
	if ( aApplicationId != null )
	{
		System.getProperties().put( PROP_APPLICATION_ID, aApplicationId );
	}

}

}
