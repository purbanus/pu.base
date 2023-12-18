package pu.html;

/**
 * De verzameling HTML-constantes. De meeste hebben met attributes en de waardes van attributes te maken.
 * <p>
 * Creation date: (25-3-2001 1:11:19)
 * @author: Administrator
 */
public class HTMLConstants
{
	/**
	 * Number of spaces to indent per indent unit. That is, if an html element
	 * specifies taht it should inden 1 unit, this constant specifies how many
	 * spaces the indent actually is.
	 */
	protected static final int INDENT_UNITS = 2;
	
	// De namen van de attributes. protected omdat niemand een moer te maken heeft met hoe die dingen heten
	protected static final String ATTR_ALIGN = "align";
	protected static final String ATTR_CLASS = "class";
	protected static final String ATTR_STYLE = "style";
	protected static final String ATTR_DIR   = "dir";
	protected static final String ATTR_LANG  = "lang";
	protected static final String ATTR_ID    = "id";
	protected static final String ATTR_TITLE = "title";
	protected static final String ATTR_BACKGROUND = "bgcolor";
	

	// Hier volgt een serie classes voor attributes die een vast, beperkt aantal waardes hebben

	// Alignment
	public static final Alignment LEFT    = new Alignment( "left" );
	public static final Alignment CENTER  = new Alignment( "center" );
	public static final Alignment RIGHT   = new Alignment( "right" );
	public static final Alignment DEFAULT = new Alignment( null );
	public static final class Alignment
	{
		private final String value;
		private Alignment( String aValue ) { value = aValue; }
		@Override
		public String toString() { return value; }
	}

	// Dir
	public static final Dir LTR = new Dir( "ltr" );
	public static final Dir RTL = new Dir( "rtl" );
	public static final class Dir
	{
		private final String value;
		private Dir( String aValue ) { value = aValue; }
		@Override
		public String toString() { return value; }
	}

	// De javascript-events
	// Tijdelijke class voor JavaScript
/******* EFFE NIET
	public static class JavaScript extends HTMLText
	{
		public JavaScript( String aText )
		{
			super( aText );
		}
	}
******/
/**
 * HTMLAttribute constructor comment.
 */
private HTMLConstants()
{
	super();
}
}
