package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:11:23)
 * @author: Administrator
 */
public class HTMLAnchor extends HTMLContainer
{
	private static final String TAG = "a";
	private static final String HREF = "href";

/**
 * Creates a new HTMLAnchor element, corresponding with the <code>a</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLAnchor( String aHref, String aElement )
{
	super( TAG, aElement );
	setAttribute( HREF, aHref );
}

/**
 * Creates a new HTMLAnchor element, corresponding with the <code>a</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLAnchor( String aHref, HTMLBodyElement aElement )
{
	super( TAG, aElement );
	setAttribute( HREF, aHref );
}

/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
//public String getContent()
//{
//	return "<a href=\"" + hRef + "\">" + super.getContent() + "</a>"; //@@ NOG Wel of niet \n achter de </a>, dat is de kwestie
//}
}
