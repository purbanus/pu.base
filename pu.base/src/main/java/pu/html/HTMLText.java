package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:16:39)
 * @author: Administrator
 */
public class HTMLText extends HTMLElement
{
	private final String text;
/**
 * HTMLString constructor comment.
 */
public HTMLText( String aText )
{
	super();
	text = aText;
}
/**
 * Insert the method's description here.
 * Creation date: (4-3-2001 1:17:27)
 * @return java.lang.String
 */
@Override
public String getContent()
{
	return text;
}
}
