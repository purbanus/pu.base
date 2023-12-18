/*
 * Created on 18-nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

/**
 * HTMLPanel is a tagless HTMLContainer that can contain arbitrary
 * html content. Useful if the parts of a page are produced by separate
 * methods.
 */
public class HTMLPanel extends HTMLContainer
{
	private static final String TAG = "";

/**
 * Creates a new empty HTMLPanel
 */
public HTMLPanel()
{
	super( TAG );
	setDisplayTag( false );
}

/**
 * Creates a new HTMLPanel
 * @param aElement
 */
public HTMLPanel( String aElement )
{
	super( TAG, aElement );
}

/**
 * Creates a new HTMLPanel
 * @param aElement
 */
public HTMLPanel( HTMLBodyElement aElement)
{
	super( TAG, aElement);
}

}
