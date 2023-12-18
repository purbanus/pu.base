package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 3:07:11)
 * @author: Administrator
 */
public class HTMLTitle extends HTMLHeaderElement
{
	private static final String TAG = "title";

	private final String title;
/**
 * HTMLTitle constructor comment.
 */
public HTMLTitle( String aTitle ) 
{
	super( TAG );
	title = aTitle;
}

/**
 * Insert the method's description here.
 * Creation date: (24-2-2001 3:07:32)
 * @return java.lang.String
 */
@Override
public String getTagContent()
{
	return title;
}
}
