/*
 * Created on 22-aug-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

/**
 * HTMLCSSLink
 * TODO Class-commentaar maken, Administrator
 */
public class HTMLCSSLink extends HTMLLink
{

/**
 * Creates a new HTMLCSSLink
 * 
 */
public HTMLCSSLink( String aHref )
{
	super();
	setHref( aHref );
	setRel( "stylesheet" );
	setType( "text/css" );
}

}
