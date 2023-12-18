package pu.html;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;


/**
 * Insert the type's description here.
 * Creation date: (4-3-2001 3:09:02)
 * @author: Administrator
 */
public abstract class HTMLTable extends HTMLBodyElement
{
	private static final String TAG = "table";
	
	// Een aantal kleuren etc die we nu liever met CSS doen
	public static final boolean USE_CSS = true;
	
//	private static final String ATTR_HEADERBG = "_HEADERBG_";
//	private static final String ATTR_BODYBG   = "_BODYBG_";
//	private static final String ATTR_FOOTERBG = "_FOOTERBG_";

	private static final String ATTR_BORDERWIDTH = "border";
	private static final String ATTR_CELLPADDING = "cellpadding";
	private static final String ATTR_CELLSPACING = "cellspacing";
	private static final String ATTR_RULES       = "rules";
	
	/*
	 * Rules bepalen wat de table moet doen met lijnen:
	 * <ul>
	 * <li>all = lijntjes om alle cellen
	 * <li>cols = lijntjes per kolom
	 * <li>rows = lijntjes per rij
	 * <li>groups = lijntjes per tabelsectie
	 * <li>none = geen lijntjes
	 * </ul>
	 * Vooral met groups (in combinatie met tabelsecties) kun je mooie rustige tabellen maken
	 */
	public static final Rules RULES_ALL    = new Rules( "all" );
	public static final Rules RULES_COLS   = new Rules( "cols" );
	public static final Rules RULES_ROWS   = new Rules( "rows" );
	public static final Rules RULES_GROUPS = new Rules( "groups" );
	public static final Rules RULES_NONE   = new Rules( "none" );
	
	public static final class Rules
	{
		final String waarde;
		private Rules( String aWaarde )
		{
			waarde = aWaarde;
		}
		@Override
		public String toString()
		{
			return waarde;
		}
	}
	/*
	 * Opzoektabel voor de alignment van objecten (getallen rechts, datums midden en de rest rechts b.v.)
	 */
	private static Map<Class<?>, HTMLConstants.Alignment> alignmentTabel = null;

	// @@NOG Dit moet hier nog maar zolang even want anders is het veel te veel werk voor nu
	private HTMLColor headerBackground = USE_CSS ? null : new HTMLColor( 0xCC, 0xCC, 0xFF ); // Sun Paars, bwah!
	private HTMLColor headerForeground = null; // Niks opgeven = zwart
	private HTMLColor[] bodyBackground = USE_CSS ? null : new HTMLColor [] { new HTMLColor( Color.white ) };
	
	/**
	 * Of de oneven rijen class="odd" krijgen en de even rijen class="even".
	 * Heeft geen zin als je bodybackground opgeeft.
	 */
	private boolean oddEvenRows = true;
	
/********************
	private static class Header extends HTMLBodyElement
	{
		private final HTMLTable table;
		
		public Header( THMLTable aTable )
		{
			super();
			table = aTable;
		}
		public String getContent()
		{
			if ( table.getColumnCount() == 0 )
				return "";
			// THEAD tag
			StringBuffer sb = new StringBuffer();
			sb.append( "<THEAD" );
			sb.append( getAttributeString() );
			sb.append( ">\n" );

			// Voorlopig hebben we 1 rij in de header
			sb.append( "  <TR ALIGN=left" );
			sb.append( " BGCOLOR=" ); @@@@@@@hier was ik opgehouden
			sb.append( getHeaderBackground() );

			// En hier krijgen we de kolomkopjes	
			for ( int x = 0; x < getColumnCount(); x++ )
			{
				sb.append( "    <TH>" );
				sb.append( getColumnName( x ) );
				sb.append( "</TH>\n" );
			}
			sb.append( "  </TR>\n" );
			sb.append( "</THEAD>\n" ); // Einde headersectie
			return sb.toString();
		}
	}	
*********************/
/**
 * HTMLTabelMetModel constructor comment.
 */
public HTMLTable()
{
	super( TAG );

	// Mijn defaults, voorlopig maar hier neergezet
	if ( ! USE_CSS )
	{
		setBorderWidth( 1 );
		setCellPadding( 1 );
		setCellSpacing( 0 );
		setRules( RULES_GROUPS ); // ALL is de default, maar ik vind GROUPS beter
	}
	
	setEntersAfterEndTag( 1 );
}

private static Map<Class<?>, HTMLConstants.Alignment> getAlignmentTabel()
{
	if ( alignmentTabel == null )
	{
		Map<Class<?>, HTMLConstants.Alignment> newAlignmentTabel = new HashMap<>();
		newAlignmentTabel.put( Number.class, HTMLConstants.RIGHT );
		newAlignmentTabel.put( java.util.Date.class, HTMLConstants.CENTER );
		newAlignmentTabel.put( Object.class, HTMLConstants.LEFT );
		alignmentTabel = newAlignmentTabel;
	}
	return alignmentTabel;
}

protected static HTMLConstants.Alignment lookupAlignment( Class<?> aClass )
{
	// Probeer de class te vinden, dan z'n superclass, etc
	Class<?> klas = aClass;
	HTMLConstants.Alignment alignment = null;
	while ( null == ( alignment = getAlignmentTabel().get( klas ) ) )
	{
		klas = klas.getSuperclass();
	}
	return alignment;
}

protected abstract String getBody();

public HTMLColor[] getBodyBackground()
{
	return bodyBackground; 
}

public int getBorderWidth()
{
	return getIntAttribute( ATTR_BORDERWIDTH );
}

public int getCellPadding() 
{
	return getIntAttribute( ATTR_CELLPADDING );
}

public int getCellSpacing()
{
	return getIntAttribute( ATTR_CELLSPACING );
}

public abstract int getColumnCount();

public abstract String getColumnName( int kol );

@Override
protected String getTagContent()
{
	/*******
	StringBuffer sb = new StringBuffer();
	// no problem sb.append( getTableTag() );
	sb.append( getHeader() );
	sb.append( getBody() );
	// no problem sb.append( "</table>\n" );
	return sb.toString();
	******/
	return getHeader() + getBody();
}

/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:28:14)
 * @return java.lang.String
 */
// HIGH Aparte class ofzo
protected String getHeader()
{
	if ( getColumnCount() == 0 )
	{
		return "";
	}
	StringBuffer sb = new StringBuffer();
	sb.append( "<thead>\n" ); // Start een headersectie
	sb.append( "  <tr" );
	if ( ! USE_CSS )
	{
		sb.append( " align=\"left\"" );
	}
	sb.append( getHeaderBackground() == null ? "" : " bgcolor=\"" + getHeaderBackground() + "\"" );
	sb.append( ">\n" );

	// In tegenstelling tot de achtergrondkleur moet je een truc gebruiken voor de
	// voorgrondkleur. Het enige dat ik kon vinden dat werkt is <font> opgeven voor
	// elke header cell.
	// 2004-08-22: Of CSS gebruiken!
	for ( int x = 0; x < getColumnCount(); x++ )
	{
		sb.append( "    <th>" );
		if ( getHeaderForeground() != null )
		{
			sb.append( "<font color=\"" + getHeaderForeground() + "\">" );
		}
		sb.append( getColumnName( x ) );
		if ( getHeaderForeground() != null )
		{
			sb.append( "</font>" );
		}
		sb.append( "</th>\n" );
	}
	sb.append( "  </tr>\n" );
	sb.append( "</thead>\n" ); // Einde headersectie
	return sb.toString();
}

/**
 * Insert the method's description here.
 * Creation date: (5-3-2001 4:35:01)
 * @return java.lang.String
 */
public HTMLColor getHeaderBackground() 
{
	return headerBackground;
}

/**
 * Insert the method's description here.
 * Creation date: (12-2-2002 1:26:04)
 * @return PU.htmlgen.HTMLColor
 */
public HTMLColor getHeaderForeground()
{
	return headerForeground;
}

/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:19:05)
 * @return int
 */
public abstract int getRowCount();

/**
 * Insert the method's description here.
 * Creation date: (6-3-2001 2:18:33)
 * @return PU.htmlgen.HTMLTabelMetModel.Rules
 */
public Rules getRules()
{
	return (Rules) getAttribute( ATTR_RULES );
}

/**
 * @return
 */
public boolean isOddEvenRows()
{
	return oddEvenRows;
}

/**
 * Met deze methode geef je een serie kleuren op voor de body-achtergrond. Deze kleuren worden een voor een herhaald
 * bij het renderen van de achtergrond.
 * Creation date: (6-3-2001 3:42:11)
 * @param newBodyBackground PU.htmlgen.HTMLColor[]
 */
public HTMLTable setBodyBackground( HTMLColor[] newBodyBackground )
{
	bodyBackground = newBodyBackground;
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (6-3-2001 3:42:11)
 * @param newBodyBackground PU.htmlgen.HTMLColor[]
 */
public HTMLTable setBodyBackground( HTMLColor newBodyBackground )
{
	bodyBackground = new HTMLColor [] { newBodyBackground };
	return this;
}

/**
 * Zet de border-omvang voor de tabel.
 * <br> - 0 = Geen border
 * <br> - 1 = Zo dun mogelijke border
 * <br> - >1 = Bredere border
 * <p>
 * N.B. Om een mooie border te krijgen moet je ook setCellSpacing(0) doen - ik weet ook niet waarom.
 * Creation date: (5-3-2001 4:17:27)
 * @param newBorderWidth int
 */
public HTMLTable setBorderWidth( int newBorderWidth )
{
	setIntAttribute( ATTR_BORDERWIDTH, newBorderWidth );
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (5-3-2001 4:23:37)
 * @param newCellPadding int
 */
public HTMLTable setCellPadding( int newCellPadding )
{
	setIntAttribute( ATTR_CELLPADDING, newCellPadding );
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (5-3-2001 4:22:45)
 * @param newCellSpacing int
 */
public HTMLTable setCellSpacing( int newCellSpacing )
{
	setIntAttribute( ATTR_CELLSPACING, newCellSpacing );
	return this;
}

/**
 * Creation date: (5-3-2001 4:35:01)
 * @param newHeaderBackgroundColor java.lang.String
 */
public HTMLTable setHeaderBackground( Color c )
{
	headerBackground = new HTMLColor( c );
	return this;
}

/**
 * Creation date: (5-3-2001 4:35:01)
 * @param newHeaderBackgroundColor java.lang.String
 */
public HTMLTable setHeaderBackground( HTMLColor c )
{
	headerBackground = c;
	return this;
}

/**
 * Creation date: (5-3-2001 4:35:01)
 * @param newHeaderBackgroundColor java.lang.String
 */
public HTMLTable setHeaderForeground( Color c )
{
	headerForeground = new HTMLColor( c );
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (12-2-2002 1:26:04)
 * @param newHeaderForeground PU.htmlgen.HTMLColor
 */
public HTMLTable setHeaderForeground( HTMLColor c )
{
	headerForeground = c;
	return this;
}

/**
 * @param b
 */
public HTMLTable setOddEvenRows(boolean b)
{
	oddEvenRows = b;
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (6-3-2001 2:18:33)
 * @param newRules PU.htmlgen.HTMLTabelMetModel.Rules
 */
public HTMLTable setRules( Rules newRules )
{
	setAttribute( ATTR_RULES, newRules );
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:08:52)
 * @return java.lang.String
 */
protected String XXXgetTableTag()
{
	return "\n<table" + getAttributeString() + ">\n";
}

}
