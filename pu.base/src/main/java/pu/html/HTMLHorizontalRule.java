package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:16:39)
 * @author: Administrator
 */
public class HTMLHorizontalRule extends HTMLBodyElement
{
	private static final String TAG = "hr";

/**
 * Creates a new HTMLHorizontalRule element, corresponding with the <code>hr</code> tag
 */
public HTMLHorizontalRule()
{
	super( TAG );
	setHasContent( false );
	setEntersAfterEndTag( 1 );
}

/**
 * @return the horizontal alignment of the rule
 */
public HTMLConstants.Alignment getAlignment()
{
	return (HTMLConstants.Alignment) getAttribute( HTMLConstants.ATTR_ALIGN );
}

/**
 * A horizontal rule has no content.
 * @return the empty String
 */
@Override
protected String getTagContent()
{
	return "";
}

/**
 * Sets the horizontal alignment of the rule
 * @param The horizontal alignment of the rule
 */
public HTMLHorizontalRule setAlignment( HTMLConstants.Alignment newAlignment )
{
	setAttribute( HTMLConstants.ATTR_ALIGN, newAlignment );
	return this;
}

}
