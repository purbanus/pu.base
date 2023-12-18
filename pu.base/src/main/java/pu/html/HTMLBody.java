package pu.html;


/**
 * An html body element, corresponding with the <code>body</code> tag
 */
public class HTMLBody extends AbstractBody
{
	private static final String TAG = "body";

/**
 * Creates an empty HTMLBody element, corresponding with the <code>body</code> tag
 */
public HTMLBody()
{
	super( TAG );
	setEntersAfterStartTag( 1 );
	setEntersAfterEndTag( 1 );
}

/**
 * Creates a new HTMLBody element, corresponding with the <code>body</code> tag
 * @param aElement The String that is the initial content of the tag
 */
public HTMLBody( String aElement )
{
	this();
	add( new HTMLText( aElement ) );
}

/**
 * Creates a new HTMLBody element, corresponding with the <code>body</code> tag
 * @param aElement The body element that is the initial content of the tag
 */
public HTMLBody( HTMLBodyElement aElement )
{
	this();
	add( aElement );
}

/**
 * Adds a String toe aan deze body
 * @param aElement 
 * @return this HTMLBody
 */
public HTMLBody add( String aElement )
{
	getElements().add( new HTMLText( aElement ) );
	return this;
}

/**
 * Voegt een HTMLElement toe aan deze body
 * @param aElement 
 * @return this HTMLBody
 */
// HIGH Ik durf te wedden dat dit niet wordt gebruikt
private HTMLBody add( HTMLText aElement )
{
	getElements().add( aElement );
	return this;
}

/**
 * Voegt een HTMLBodyElement toe aan deze body
 * @param aElement
 * @return this HTMLBody
 */
public HTMLBody add( HTMLBodyElement aElement )
{
	getElements().add( aElement );
	return this;
}
}
