package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:07:11)
 * @author: Administrator
 */
public class HTMLBase extends HTMLHeaderElement
{
	private static final String TAG = "base";
	private static final String HREF = "href";
	private static final String TARGET = "target";
	
/**
 * HTMLTitle constructor comment.
 */
public HTMLBase() 
{
	super( TAG );
	setIndent( 1 );
	setEntersAfterEndTag( 1 );
	setHasContent( false );
}
/**
 * HTMLTitle constructor comment.
 */
public HTMLBase( String aTarget ) 
{
	super( TAG );
	setTarget( aTarget );
}

/**
 * Returns the tag's content (the part between the start- and endtag.
 * @return The tag content
 */
@Override
protected String getTagContent()
{
	return "";
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:30:17)
 * @param newHref java.lang.String
 */
public HTMLBase setHref( String newHref )
{
	setAttribute( HREF, newHref );
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:29:59)
 * @param newTarget java.lang.String
 */
public HTMLBase setTarget( String newTarget )
{
	setAttribute( TARGET, newTarget );
	return this;
}
}
