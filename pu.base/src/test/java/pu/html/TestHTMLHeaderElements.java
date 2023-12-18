/*
 * Created on 9-nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

import static org.junit.Assert.*;
import static pu.html.Constants.*;

import org.junit.Test;

public class TestHTMLHeaderElements
{
@Test
public void testBase()
{
	HTMLBase el = new HTMLBase();
	el.setHref  ( "hiep" );
	el.setTarget( "tiep" );
	
	String expected = 
		"  <base href=\"hiep\" target=\"tiep\" />" + NL
	;
	assertEquals( expected, el.getContent() );
}
@Test
public void testLink()
{
	HTMLLink el = new HTMLLink();
	el.setHref( "hiep" );
	el.setRel ( "riep" );
	el.setType( "tiep" );
	
	String expected = 
		"  <link href=\"hiep\" rel=\"riep\" type=\"tiep\" />" + NL
	;
	assertEquals( expected, el.getContent() );
}
@Test
public void testCSSLink()
{
	HTMLLink el = new HTMLCSSLink( "hiep" );
	
	String expected = 
		"  <link href=\"hiep\" rel=\"stylesheet\" type=\"text/css\" />" + NL
	;
	assertEquals( expected, el.getContent() );
}
@Test
public void testTitle()
{
	HTMLTitle el = new HTMLTitle( "titel" );
	
	String expected = 
		"  <title>titel</title>" + NL
	;
	assertEquals( expected, el.getContent() );
}

}
