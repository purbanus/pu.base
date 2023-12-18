/*
 * Created on 14-nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

/**
 * An HtmlAttibute holds a key / value pair
 */
public class HTMLAttribute extends HTMLElement
{
	private final String key;
	private final Object value;

/**
 * Creates a new HTMLAttribute with the specified key and value
 * @param aKey The key
 * @param aCValue The value
 */
public HTMLAttribute( String aKey, Object aValue )
{
	super();
	key = aKey;
	value = aValue;
}

/**
 * @return The content of this HTMLAttirbute in the form of a String
 * @see pu.html.HTMLElement#getContent()
 */
@Override
public String getContent()
{
	return " " + key + "=\"" + value + "\"";
}

public String getKey()
{
	return key;
}
public Object getValue()
{
	return value;
}
}
