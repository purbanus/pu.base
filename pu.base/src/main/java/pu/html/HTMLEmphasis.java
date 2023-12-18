package pu.html;

public class HTMLEmphasis extends HTMLContainer
{
	private static final String TAG = "em";

/**
 * Creates an empty HTMLEmphasis element, corresponding with the <code>em</code> tag
 */
public HTMLEmphasis()
{
	super( TAG );
}
/**
 * Creates a new HTMLEmphasis element, corresponding with the <code>em</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLEmphasis( String aElement )
{
	super( TAG, aElement );
}
/**
 * Creates a new HTMLEmphasis element, corresponding with the <code>em</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLEmphasis( HTMLBodyElement aElement )
{
	super( TAG, aElement );
}
}
