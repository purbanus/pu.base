package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:16:39)
 * @author: Administrator
 */
public class HTMLLineBreak extends HTMLBodyElement
{
	private static final String TAG = "br";

/**
 * Creates a new HTMLLineBreak element, corresponding with the <code>br</code> tag
 */
public HTMLLineBreak()
{
	super( TAG );
	setHasContent( false );
	setEntersAfterEndTag( 1 );
}

/**
 * A line break has no content.
 * @return the empty String
 */
@Override
protected String getTagContent()
{
	return "";
}


}
