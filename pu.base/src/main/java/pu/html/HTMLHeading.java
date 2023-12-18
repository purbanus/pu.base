package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (26-2-2001 2:17:22)
 * @author: Administrator
 */
public class HTMLHeading extends HTMLContainer
{
	private static final String TAG = "h";
	
	private final int headingNumber;

	public static class h1 extends HTMLHeading
	{
		public h1( HTMLBodyElement aElement )
		{
			super( 1, aElement );
		}
		public h1( String aElement )
		{
			super( 1, aElement );
		}
	}
	public static class h2 extends HTMLHeading
	{
		public h2( HTMLBodyElement aElement )
		{
			super( 2, aElement );
		}
		public h2( String aElement )
		{
			super( 2, aElement );
		}
	}
	public static class h3 extends HTMLHeading
	{
		public h3( HTMLBodyElement aElement )
		{
			super( 3, aElement );
		}
		public h3( String aElement )
		{
			super( 3, aElement );
		}
	}
	public static class h4 extends HTMLHeading
	{
		public h4( HTMLBodyElement aElement )
		{
			super( 4, aElement );
		}
		public h4( String aElement )
		{
			super( 4, aElement );
		}
	}
	public static class h5 extends HTMLHeading
	{
		public h5( HTMLBodyElement aElement )
		{
			super( 5, aElement );
		}
		public h5( String aElement )
		{
			super( 5, aElement );
		}
	}
	public static class h6 extends HTMLHeading
	{
		public h6( HTMLBodyElement aElement )
		{
			super( 6, aElement );
		}
		public h6( String aElement )
		{
			super( 6, aElement );
		}
	}
		
/**
 * Maakt een HTMLHeading.
 */
private HTMLHeading( int aHeadingNumber, String aElement )
{
	super( TAG + aHeadingNumber, aElement );
	headingNumber = aHeadingNumber;
	setEntersAfterEndTag( 1 );
}
/**
 * Maakt een HTMLHeading.
 */
private HTMLHeading( int aHeadingNumber, HTMLBodyElement aElement )
{
	super( TAG + aHeadingNumber, aElement );
	headingNumber = aHeadingNumber;
	setEntersAfterEndTag( 1 );
}
/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 1:03:31)
 * @return PU.htmlgen.HTMLAlignment
 */
public HTMLConstants.Alignment getAlignment()
{
	return (HTMLConstants.Alignment) getAttribute( HTMLConstants.ATTR_ALIGN );
}
/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 1:04:47)
 * @return int
 */
public int getHeadingNumber()
{
	return headingNumber;
}
/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 1:03:31)
 * @param newAlignment PU.htmlgen.HTMLAlignment
 */
public HTMLHeading setAlignment( HTMLConstants.Alignment newAlignment )
{
	setAttribute( HTMLConstants.ATTR_ALIGN, newAlignment );
	return this;
}
}
