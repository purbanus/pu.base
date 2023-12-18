package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (26-2-2001 1:24:28)
 * @author: Administrator
 */
public abstract class HTMLBodyElement extends HTMLTag
{
/**
 * HTMLDocPart constructor comment.
 */
public HTMLBodyElement( String aTag )
{
	super( aTag );
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public String getCSSClass()
{
	return (String) getAttribute( HTMLConstants.ATTR_CLASS );
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public HTMLConstants.Dir getDir()
{
	return (HTMLConstants.Dir) getAttribute( HTMLConstants.ATTR_DIR );
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:47:57)
 * @return String
 */
public String getID()
{
	return (String) getAttribute( HTMLConstants.ATTR_ID );
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:47:57)
 * @return String
 */
public String getLang()
{
	return (String) getAttribute( HTMLConstants.ATTR_LANG );
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:47:57)
 * @return String
 */
public String getStyle()
{
	return (String) getAttribute( HTMLConstants.ATTR_STYLE );
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:47:57)
 * @return String
 */
// HIGH Wat is title??????????
public String getTitle()
{
	return (String) getAttribute( HTMLConstants.ATTR_TITLE );
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public HTMLBodyElement setCSSClass( String aClass )
{
	setAttribute( HTMLConstants.ATTR_CLASS, aClass );
	return this;
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public HTMLBodyElement setDir( HTMLConstants.Dir aDir )
{
	setAttribute( HTMLConstants.ATTR_DIR, aDir );
	return this;
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public HTMLBodyElement setID( String aID )
{
	setAttribute( HTMLConstants.ATTR_ID, aID );
	return this;
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public HTMLBodyElement setLang( String aLang )
{
	setAttribute( HTMLConstants.ATTR_LANG, aLang );
	return this;
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public HTMLBodyElement setStyle( String aStyle )
{
	setAttribute( HTMLConstants.ATTR_STYLE, aStyle );
	return this;
}
/**
 * Insert the method's description here.
 * Creation date: (26-3-2001 22:36:41)
 * @param aClass java.lang.String
 */
public HTMLBodyElement setTitle( String aTitle )
{
	setAttribute( HTMLConstants.ATTR_TITLE, aTitle );
	return this;
}
}