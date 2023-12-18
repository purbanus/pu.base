package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 2:47:00)
 * @author: Administrator
 */
public abstract class AbstractDoc extends HTMLTag
{
	private static final String TAG = "html";
	
	private final HTMLHeader header;
	private final AbstractBody body;
	
/**
 * Creates a new AbstractDoc, corresponding with the <code>html</code> tag
 * @param aHeader The header, or &lt;head> part
 * @param aBody The body, which van be a HTMLBody os HTMLFrame
 */
public AbstractDoc( HTMLHeader aHeader, AbstractBody aBody )
{
	super( TAG );
	header = aHeader;
	body = aBody;
	setEntersAfterStartTag( 1 );
	setEntersAfterEndTag( 1 );
}
/**
 * @return The body
 */
protected AbstractBody getAbstractBody()
{
	return body;
}

@Override
public String getTagContent()
{
	return getHeader().getContent() + getAbstractBody().getContent();
}
/**
 * Insert the method's description here.
 * Creation date: (4-3-2001 0:44:46)
 * @return PU.htmlgen.HTMLHeader
 */
public HTMLHeader getHeader()
{
	return header;
}
}
