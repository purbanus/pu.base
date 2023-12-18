package pu.services;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

/**
 * Singleton die een aantal globale variabelen beheert
 * Wordt alleen nog gebruikt in:
 * - nl.mediacenter.services.Datum
 * - nl.mediacenter.ui.Fieldxxx
 * - nl.mediacenter.ui.TabelCelEditor
 * - nl.mediacenter.ui.TabelCelRenderer
 */
public class Globals implements Serializable
{
	private static Globals instance = null;

	// Standaard datumformaat. Het is jammer dat het niet gelokaliseerd is, maar
	// bij de standaard locale-formats zit gewoon niks bruikbaars. MEDIUM gaat nog wel
	// maar dan moet je ook 13-mrt-00 intikken en dan houdt het op.
//	private DateFormat   dateFormatter = DateFormat.getDateInstance( DateFormat.MEDIUM );
	private static final char DATE_SEPARATOR = '-';
	private static final char TIME_SEPARATOR = ':';
	private DateFormat   dateFormatterMonthYear  = new SimpleDateFormat( "MM" + DATE_SEPARATOR + "yyyy" );
	private DateFormat   dateFormatterZonderJaar = new SimpleDateFormat( "dd" + DATE_SEPARATOR + "MM" );
	private DateFormat   dateFormatterKort       = new SimpleDateFormat( "dd" + DATE_SEPARATOR + "MM" + DATE_SEPARATOR + "yy" );
	private DateFormat   dateFormatterLang       = new SimpleDateFormat( "dd" + DATE_SEPARATOR + "MM" + DATE_SEPARATOR + "yyyy" );
	private DateFormat   timeFormatter           = new SimpleDateFormat( "HH" + TIME_SEPARATOR + "mm" + TIME_SEPARATOR + "ss SSS" ); // SS" + "000" );
	private DateFormat   timeFormatterKort       = new SimpleDateFormat( "HH" + TIME_SEPARATOR + "mm" + TIME_SEPARATOR + "ss" );
	private DateFormat   timeFormatterZonderSep  = new SimpleDateFormat( "HHmmss" );
	private DateFormat   timeFormatterForFileNames = null;
	private DateFormat   dateTimeFormatterZonderJaar = new SimpleDateFormat( "dd" + DATE_SEPARATOR + "MM  HH" + TIME_SEPARATOR + "mm" + TIME_SEPARATOR + "ss" /* + "SSS" */ );

	private static final int JAAR_MINIMUM  = 1980;
	private static final int JAAR_MAXIMUM  = 2020;
	private static final int JAAR_WINDOW   = 80;
	
/**
 * Globals constructor. Is prive want alleen Globals zelf mag een instance maken; eentje.
 */
private Globals()
{
	super();
}

public static DateFormat getDateTimeFormatterZonderJaar()
{
	return getInstance().dateTimeFormatterZonderJaar;
}
/**
 * This method was created in VisualAge.
 * @return NumerFormat
 */
public static NumberFormat getBigDecimalFormatter()
{
	return( getBigDecimalFormatter( 9, 2 ) );
}
/**
 * This method was created in VisualAge.
 * @return NumerFormat
 */
public static NumberFormat getBigDecimalFormatter(int aMaxLengte, int aDecPos)
{
	NumberFormat format = NumberFormat.getNumberInstance();

	// Format verder instellen
	format.setParseIntegerOnly(false);
	format.setMaximumFractionDigits(aDecPos);
	format.setMaximumIntegerDigits(aMaxLengte - aDecPos);
	format.setMinimumFractionDigits(aDecPos);
	format.setGroupingUsed(false);

	// Als je dit soort dingen wil moet je casten naar DecimalFormat. Dat gaat meestal goed.

	//	format.setNegativePrefix("(");
	//	format.setNegativeSuffix(")");

	// Poging om geen punten (16.50) meer te zien in BigDecimalRenderers. De komma wordt al gebruikt als 'grouping_separator
 //	((DecimalFormat) format).getDecimalFormatSymbols().setDecimalSeparator( ',' );
 
	return format;
}
/**
 * This method was created in VisualAge.
 * @return java.text.DateFormat
 */
public static DateFormat getDateFormatter()
{
	// Voorlopig is dit formaat voor alle datums gelijk.
	return getDateFormatterLang();
}
/**
 * This method was created in VisualAge.
 * @return java.text.DateFormat
 */
public static DateFormat getDateFormatterKort()
{
	return getInstance().dateFormatterKort;
}
/**
 * This method was created in VisualAge.
 * @return java.text.DateFormat
 */
public static DateFormat getDateFormatterLang()
{
	return getInstance().dateFormatterLang;
}
/**
 * Get the data formatter that handles MM-YYYY
 *
 * @return java.text.DateFormat
 */
public static DateFormat getDateFormatterMonthYear()
{
	return getInstance().dateFormatterMonthYear;
}
/**
 * This method was created in VisualAge.
 * @return java.text.DateFormat
 */
public static DateFormat getDateFormatterZonderJaar()
{
	return getInstance().dateFormatterZonderJaar;
}
/**
 * This method was created in VisualAge.
 * @return char
 */
public static char getDateSeparator()
{
	return getInstance().DATE_SEPARATOR; // Er is geen eenvoudige manier om achter de separator te komen, dus maar effe zo.
}
/**
 * retourneer de (enige) instance van Globals.
 * @return nl.mediacenter.services.Globals
 */
public static Globals getInstance()
{
	if ( instance == null )
		instance = new Globals();
	return instance;
}
/**
 * This method was created in VisualAge.
 * @return java.text.NumberFormat
 */
public static NumberFormat getIntegerFormatter()
{
	NumberFormat format = NumberFormat.getNumberInstance();
	format.setParseIntegerOnly(true);
	return format;
}
/**
 * This method was created in VisualAge.
 * @return int
 */
public static int getJaarWindow()
{
	return JAAR_WINDOW;
}
/**
 * This method was created in VisualAge.
 * @return int
 */
public static int getMaximumJaar()
{
	return JAAR_MAXIMUM;
}
/**
 * This method was created in VisualAge.
 * @return int
 */
public static int getMinimumJaar()
{
	return JAAR_MINIMUM;
}
/**
 * This method was created in VisualAge.
 * @return java.text.DateFormat
 */
public static DateFormat getTimeFormatter()
{
	return getInstance().timeFormatter;
}
/**
 * Insert the method's description here.
 * Creation date: (8-11-02 0:17:53)
 * @return java.text.DateFormat
 */
public static DateFormat getTimeFormatterForFileNames()
{
	Globals glob = getInstance();
	if ( glob.timeFormatterForFileNames == null )
	{
		char ds = Globals.getDateSeparator();
		//char ts = Globals.getTimeSeparator(); Dubbele punten mogen niet in een filenaam!!
		char ts = '.';
		glob.timeFormatterForFileNames = new SimpleDateFormat( "yyyy" + ds + "MM" + ds + "dd  HH" + ts + "mm" + ts + "ss" );
	}
	return glob.timeFormatterForFileNames;
}
/**
 * This method was created in VisualAge.
 * @return java.text.DateFormat
 */
public static DateFormat getTimeFormatterKort()
{
	return getInstance().timeFormatterKort;
}
/**
 * This method was created in VisualAge.
 * @return java.text.DateFormat
 */
public static DateFormat getTimeFormatterZonderSep()
{
	return getInstance().timeFormatterZonderSep;
}
/**
 * This method was created in VisualAge.
 * @return char
 */
public static char getTimeSeparator()
{
	return getInstance().TIME_SEPARATOR; 
}
}
