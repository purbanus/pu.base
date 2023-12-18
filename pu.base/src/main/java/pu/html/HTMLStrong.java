package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:37:58)
 * @author: Administrator
 * @deprecated Use HTMLEmphasis
 */
@Deprecated
public class HTMLStrong extends HTMLContainer
{
	private static final String TAG = "strong";

/**
 * Creates an empty HTMLString element, corresponding with the <code>string</code> tag
 */
public HTMLStrong()
{
	super( TAG );
}

/**
 * Creates a new HTMLStrong element, corresponding with the <code>strong</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLStrong( String aElement )
{
	super( TAG, aElement);
}

/**
 * Creates a new HTMLStrong element, corresponding with the <code>string</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLStrong( HTMLBodyElement aElement )
{
	super( TAG, aElement);
}
}
