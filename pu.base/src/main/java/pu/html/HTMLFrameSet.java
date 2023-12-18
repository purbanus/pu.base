package pu.html;


/**
 * An html frameset
 */
public class HTMLFrameSet extends AbstractBody 
{
	private static final String TAG = "frameset";
	
/**
 * Creates a new HTMLFrameSet with the specified attributes
 * @param aAttribute the frameset attributes
 */
public HTMLFrameSet( HTMLFrameSetAttribute aAttribute )
{
	super( TAG );
	setAttribute( aAttribute );
	setEntersAfterStartTag( 1 );
	setEntersAfterEndTag( 1 );
}

/**
 * Adds a 
 * Creation date: (8-3-2001 1:07:51)
 * @param aFrame PU.htmlgen.HTMLFrame
 */
public void addFrame( HTMLFrame aFrame )
{
	getElements().add( aFrame );
}
/**
 * Insert the method's description here.
 * Creation date: (8-3-2001 1:07:51)
 * @param aFrame PU.htmlgen.HTMLFrame
 */
public void addFrameSet( HTMLFrameSet aFrameSet )
{
	getElements().add( aFrameSet );
}
/**
 * Insert the method's description here.
 * Creation date: (8-3-2001 1:04:21)
 * @return PU.htmlgen.HTMLFrameSetAttribute
 */
public final HTMLFrameSetAttribute XXXgetAttributes()
{
	//return attributes;
	return null;
}
public String XXXgetContent()
{
	StringBuffer sb = new StringBuffer( "<frameset" );
	/***********
	sb.append( getAttributes() );
	sb.append( ">\n" );
	
	for ( Iterator it = elements.iterator(); it.hasNext(); )
		sb.append( it.next() );

	sb.append( "</frameset>\n" );
	********/
	return sb.toString();
}

}
