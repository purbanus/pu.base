package pu.log.log2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import pu.services.Globals;
public class FileLogger extends AbstractLogger
{
	private FileWriter writer = null;

public FileLogger()
{
	super();
}
public FileLogger( FileWriter aWriter )
{
	startWriter( writer );
}
public FileLogger( String aFileNaam )
{
	startWriter( aFileNaam );
}
@Override
public void finalize()
{
	if ( writer != null )
	{
		try
		{
			writer.close();
		}
		catch( IOException e )
		{
			// Het zal wel
		}
	}
}
public FileWriter getWriter()
{
	if ( writer == null )
	{
		// Maak de formatters
		/*****************
		// Spaties tussen de uren/minuten/seconden want dubbele punten mogen niet in een filenaam!
		DateFormat dateFormatter = Globals.getDateFormatter();
		DateFormat timeFormatter = new SimpleDateFormat( "HH mm ss" );

		// Maak datum en tijd
		java.util.Date d = new java.util.Date();
		String datum = dateFormatter.format( d );
		String tijd = timeFormatter.format( d );

		// Zet de logfilenaam in elkaar
		String logFileNaam = "Log van " + datum + " " + tijd + ".txt";
		**************/

		String logFileNaam = Globals.getTimeFormatterForFileNames().format( new java.util.Date() ) + ".log";

		// Probeer een writer te starten
		startWriter( logFileNaam );		
	}
	return writer;
}
public void handleStartException( Exception e, String aFileNaam )
{
	System.out.println( "Kon geen logfile maken: " + aFileNaam );
	Log.error( this, e );
}
@Override
public void logImpl( String s )
{
	try
	{
		getWriter().write( s + '\n' );
		getWriter().flush();
	}
	catch( Throwable e )
	{
		Log.error( this, e );
		System.out.println( "Kan niet op de normale wijze loggen: " + s );
	}
}
public void startWriter( FileWriter aWriter )
{
	writer = aWriter;
	logImpl( "Logging gestart op " + new Date() );
}
public FileWriter startWriter( String aFileNaam )
{
	try
	{
		FileWriter fw = new FileWriter( aFileNaam );
		startWriter( fw );
		return fw;
	}
	catch( IOException e )
	{
		handleStartException( e, aFileNaam );
		return null;
	}
}
}
