package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 2:47:00)
 * @author: Administrator
 */
public class HTMLFrameDoc extends AbstractDoc
{
/**
 * Insert the method's description here.
 * Creation date: (6-4-2001 1:22:25)
 * @param aHeader java.lang.String
 */
public HTMLFrameDoc( String aHeader, HTMLFrameSet aBody )
{
	this( new HTMLHeader( aHeader ), aBody );
}
public HTMLFrameDoc( HTMLHeader aHeader, HTMLFrameSet aBody )
{
	super( aHeader, aBody );
}
/**
 * Insert the method's description here.
 * Creation date: (8-3-2001 0:35:23)
 * @return PU.htmlgen.HTMLBase
 */
public HTMLFrameSet getFrameSet()
{
	return (HTMLFrameSet) getAbstractBody();
}
}
