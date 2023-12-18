package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:37:58)
 * @author: Administrator
 * @deprecated Use HTMLEmphasis
 */
@Deprecated
public class HTMLItalic extends HTMLContainer
{
	private static final String TAG = "i";

/**
 * Creates an empty HTMLITalic element, corresponding with the <code>i</code> tag
 */
public HTMLItalic()
{
	super( TAG );
}
/**
 * Creates a new HTMLItalic element, corresponding with the <code>i</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLItalic( String aElement )
{
	super( TAG, aElement );
}
/**
 * Creates a new HTMLItalic element, corresponding with the <code>i</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLItalic( HTMLBodyElement aElement )
{
	super( TAG, aElement );
}
}
