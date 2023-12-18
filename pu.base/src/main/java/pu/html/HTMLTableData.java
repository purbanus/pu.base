package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (7-3-2001 3:19:29)
 * @author: Administrator
 */
public class HTMLTableData extends HTMLContainer
{
	private static final String TAG = "td";
	private static final int INDENT = 2;

/**
 * Creates a new HTMLTableData element, corresponding with the <code>td</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLTableData( String aElement )
{
	super( TAG, aElement );
	setIndent( INDENT );
	setEntersAfterEndTag( 1 );
}

/**
 * Creates a new HTMLTableData element, corresponding with the <code>td</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLTableData( HTMLBodyElement aElement )
{
	super( TAG, aElement );
}

/**
 * @return the horizontal alignment of the cell data
 */
public HTMLConstants.Alignment getAlignment()
{
	return (HTMLConstants.Alignment) getAttribute( HTMLConstants.ATTR_ALIGN );
}

/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 3:19:29)
 * @return java.lang.String
 */
//public String getContent()
//{
//	return "    <td" + getAttributeString() + ">" + super.getContent() + "</td>\n";
//}

/**
 * Sets the horizontal alignment of the cell data
 * @param The horizontal alignment of the cell data
 */
public HTMLTableData setAlignment( HTMLConstants.Alignment newAlignment )
{
	setAttribute( HTMLConstants.ATTR_ALIGN, newAlignment );
	return this;
}
}
