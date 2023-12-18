package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:37:58)
 * @author: Administrator
 */
public class HTMLPreFormatted extends HTMLContainer
{
	private static final String TAG = "pre";

/**
 * Creates an empty HTMLPreFormatted element, corresponding with the <code>pre</code> tag
 */
public HTMLPreFormatted()
{
	super( TAG );
}

/**
 * Creates a new HTMLPreFormatted element, corresponding with the <code>pre</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLPreFormatted( String aElement )
{
	super( TAG, aElement);
}

/**
 * Creates a new HTMLPreFormatted element, corresponding with the <code>pre</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLPreFormatted( HTMLBodyElement aElement )
{
	super( TAG, aElement);
}
}
