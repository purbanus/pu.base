package pu.services;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

// HIGH Zie Util!
/**
 * Insert the type's description here.
 * Creation date: (24-11-2002 1:54:40)
 * @author: Peter Urbanus
 */
public class FormatHelper
{
	// Een van de dingen die je ook moet overnemen naar de echte Globals is het elimineren
	// van de instance. Slecht voor multithreading en onnodig.
	// Maar veel erger is dat DateFormat NIET threadsafe is (staat in de docu). Voor zover ik kan zien
	// is de enige manier om dat op te lossen, niet een DateFormat retourneren maar synchronized formatXXXXX
	// methodes maken. Dat is hier nog niet gedaan.
	// Verder zou ik de class FormatHelper noemen o.i.d.
	// En tja, eigenlijk zou je ook de separators niet hier moeten definieren maar uit de huidige locale halen.
	private static final char DATSEP = Globals.getDateSeparator();
	private static final char TIMSEP = Globals.getTimeSeparator();
	private static final char SAFE_TIMSEP = '.';
	
	private static final DateFormat dateTimeFormatter              = new SimpleDateFormat( "yyyy" + DATSEP + "MM" + DATSEP + "dd HH" + TIMSEP + "mm" + TIMSEP + "ss" );
	private static final DateFormat dateKortTimeFormatter          = new SimpleDateFormat( "dd" + DATSEP + "MM  HH" + TIMSEP  + "mm" + TIMSEP + "ss" );
	private static final DateFormat dateKortTimeFormatterMetMillis = new SimpleDateFormat( "dd" + DATSEP + "MM  HH" + TIMSEP + "mm" + TIMSEP + "ss" + " SSS" );
	private static final DateFormat timeFormatterForFileNames      = new SimpleDateFormat( "yyyy" + DATSEP + "MM" + DATSEP + "dd-HH" + SAFE_TIMSEP + "mm" + SAFE_TIMSEP + "ss" );

/**
 * NaarGlobals constructor comment.
 */
private FormatHelper()
{
	super();
}

/**
 * @return a date/time formatter that formats like 2087-05-14 13:05:15
 */
public static DateFormat getDateTimeFormatter()
{
	return dateTimeFormatter;
}

/**
 * @return a date/time formatter that formats like 14-05 13:05:15
 */
public static DateFormat getDateKortTimeFormatter()
{
	return dateKortTimeFormatter;
}

private static Format getTimeFormatter()
{
	return getDateKortTimeFormatter();
}

private static Format getTimeFormatterMetMillis()
{
	return getDateKortTimeFormatterMetMillis();
}

/**
 * @return a date/time formatter that formats like 14-05 13:05:15 097
 */
public static DateFormat getDateKortTimeFormatterMetMillis()
{
	return dateKortTimeFormatterMetMillis;
}

/**
 * @return a date/time formatter that formats like 2006-14-05-13.05.15, thus avoiding the
 * colon, which is a problem in Windows and Mac filenames (I'm not quite sure if this really is a problem
 * on the Mac, but best be safe).
 */
public static DateFormat getTimeFormatterForFileNames()
{
	return timeFormatterForFileNames;
}

public static String formatDateTime( Date aDate )
{
	return getDateTimeFormatter().format( aDate );
}

public static String formatTime( long aMillis )
{
	return getTimeFormatter().format( new Date( aMillis ) );
}

public static String formatTime( Long aMillis )
{
	return formatTime( aMillis.longValue() );
}

public static String formatTimeMetMillis( long aMillis )
{
	return getTimeFormatterMetMillis().format( new Date( aMillis ) );
}

public static String formatTimeMetMillis( Long aMillis )
{
	return formatTimeMetMillis( aMillis.longValue() );
}

}
