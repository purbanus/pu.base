package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 2:47:00)
 * @author: Administrator
 */
public class HTMLDoc extends AbstractDoc
{
/**
 * Insert the method's description here.
 * Creation date: (6-4-2001 1:22:25)
 * @param aHeader java.lang.String
 */
public HTMLDoc( String aHeader )
{
	this( new HTMLHeader( aHeader ), new HTMLBody() );
}
public HTMLDoc( HTMLHeader aHeader, HTMLBody aBody )
{
	super( aHeader, aBody );
}
public HTMLDoc( String aHeader, HTMLBody aBody )
{
	this( new HTMLHeader( aHeader ), aBody );
}
/**
 * Insert the method's description here.
 * Creation date: (8-3-2001 0:35:23)
 * @return PU.htmlgen.HTMLBase
 */
public HTMLBody getBody()
{
	return (HTMLBody) getAbstractBody();
}
}
