package pu.html;

/**
 * Deze class lijkt ook wijnig nut te hebben. Even afwachten tot we meer header-delen krijgen.
 * Creation date: (26-2-2001 1:24:28)
 * @author: Administrator
 */
public abstract class HTMLHeaderElement extends HTMLTag
{

/**
 * Creates a new HTMLHeaderElement
 */
public HTMLHeaderElement( String aTag )
{
	super( aTag );
	setIndent( 1 );
	setEntersAfterEndTag( 1 );
}

}
