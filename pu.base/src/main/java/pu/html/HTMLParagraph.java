package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:37:58)
 * @author: Administrator
 */
public class HTMLParagraph extends HTMLContainer
{
	private static final String TAG = "p";
	
/**
 * HTMLParagraph constructor comment.
 */
public HTMLParagraph()
{
	super( TAG );
//	setIndent( 1 );
	setEntersAfterEndTag( 1 );
}
/**
 * HTMLParagraph constructor comment.
 */
public HTMLParagraph( String aElement )
{
	super( TAG, aElement );
//	setIndent( 1 );
	setEntersAfterEndTag( 1 );
}
/**
 * HTMLParagraph constructor comment.
 */
public HTMLParagraph( HTMLBodyElement aElement )
{
	super( TAG, aElement );
//	setIndent( 1 );
	setEntersAfterEndTag( 1 );
}
/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 1:03:31)
 * @return PU.htmlgen.HTMLAlignment
 */
public HTMLConstants.Alignment getAlignment()
{
	return (HTMLConstants.Alignment) getAttribute( HTMLConstants.ATTR_ALIGN );
}
/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 1:03:31)
 * @param newAlignment PU.htmlgen.HTMLAlignment
 */
public HTMLParagraph setAlignment( HTMLConstants.Alignment newAlignment )
{
	setAttribute( HTMLConstants.ATTR_ALIGN, newAlignment );
	return this;
}
}
