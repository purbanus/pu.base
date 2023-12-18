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
public class HTMLSpan extends HTMLContainer
{
	private static final String TAG = "span";

/**
 * Creates an empty HTMLSpan element, corresponding with the <code>span</code> tag
 */
public HTMLSpan()
{
	super( TAG );
}

/**
 * Creates a new HTMLSpan element, corresponding with the <code>span</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLSpan( String aElement )
{
	super( TAG, aElement);
}

/**
 * Creates a new HTMLSpan element, corresponding with the <code>span</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLSpan( HTMLBodyElement aElement )
{
	super( TAG, aElement);
}
}
