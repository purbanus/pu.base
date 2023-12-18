/*
 * Created on 8-nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HTMLDiv extends HTMLContainer
{
	private static final String TAG = "div";

/**
 * Creates an empty HTMLDiv element, corresponding with the <code>div</code> tag
 */
public HTMLDiv()
{
	super( TAG );
}

/**
 * Creates a new HTMLDiv element, corresponding with the <code>div</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLDiv( String aElement )
{
	super( TAG, aElement);
}

/**
 * Creates a new HTMLDiv element, corresponding with the <code>div</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLDiv( HTMLBodyElement aElement )
{
	super( TAG, aElement);
}
}
