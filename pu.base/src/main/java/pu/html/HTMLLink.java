package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:07:11)
 * @author: Administrator
 */
public class HTMLLink extends HTMLHeaderElement
{
	private static final String TAG  = "link";
	private static final String REL  = "rel";
	private static final String TYPE = "type";
	private static final String HREF = "href";
	
/**
 * HTMLTitle constructor comment.
 */
public HTMLLink() 
{
	super( TAG );
	setHasContent( false );
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
public HTMLLink setHref( String newHref )
{
	setAttribute( HREF, newHref );
	return this;
}
/**
 * @param string
 */
public HTMLLink setRel(String string)
{
	setAttribute( REL, string );
	return this;
}

/**
 * @param string
 */
public HTMLLink setType(String string)
{
	setAttribute( TYPE, string );
	return this;
}

}
