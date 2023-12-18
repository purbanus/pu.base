package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (8-3-2001 0:42:22)
 * @author: Administrator
 */
public class HTMLFrame extends HTMLTag
{
	public static final String TAG = "frame";
	
/**
 * HTMLFrame constructor comment.
 */
public HTMLFrame( String aSrc, String aName )
{
	super( TAG );
	if ( aSrc != null )
	{
		setAttribute( "src", aSrc );
	}
	if ( aName != null )
	{
		setAttribute( "name", aName );
	}
	setEntersAfterEndTag( 1 );
	setHasContent( false );
	setIndent( 1 );
}

/**
 * A frame has no content.
 * @return the empty String
 */
@Override
protected String getTagContent()
{
	return "";
}

}
