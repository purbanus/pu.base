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

public class TestTextMarkup
{
	static final String CONTENT = "mycontent";

/////////////////////////////////////////////////////////////////////////
// Test methods

private void check( String aTag, HTMLContainer aElement )
{
	// HIGH Een paragraph is eigenlijk geen text markup. We moeten hier een uitzondering maken
	//       Voor div en pre en hN geldt eigenlijk hetzelfde.....
	String indent = "";
	String endOfTag = "";
	if ( aElement instanceof HTMLParagraph )
	{
		//indent = "  ";
		endOfTag = NL;
	}
	if ( aElement instanceof HTMLHeading )
	{
		//indent = "  ";
		endOfTag = NL;
	}
	
	// Zonder attributes
	String expected = indent + "<" + aTag + ">" + CONTENT + "</" + aTag + ">" + endOfTag;
	assertEquals( expected, aElement.getContent() );
	
	// Met attributes
	expected = indent + "<" + aTag + " class=\"pipo\">" + CONTENT + "</" + aTag + ">" + endOfTag;
	aElement.setCSSClass( "pipo" );
	assertEquals( expected, aElement.getContent() );
}
@Test
public void testDiv()
{
	check( "div", new HTMLDiv( CONTENT ) );
}
@Test
public void testEmphasis()
{
	check( "em", new HTMLEmphasis( CONTENT ) );
}
@Test
public void testHeadings()
{
	check( "h1", new HTMLHeading.h1( CONTENT ) );
	check( "h2", new HTMLHeading.h2( CONTENT ) );
	check( "h3", new HTMLHeading.h3( CONTENT ) );
	check( "h4", new HTMLHeading.h4( CONTENT ) );
	check( "h5", new HTMLHeading.h5( CONTENT ) );
	check( "h6", new HTMLHeading.h6( CONTENT ) );
}
@Test
public void testItalic()
{
	check( "i", new HTMLItalic( CONTENT ) );
}
@Test
public void testParagraph()
{
	check( "p", new HTMLParagraph( CONTENT ) );
}
@Test
public void testPre()
{
	check( "pre", new HTMLPreFormatted( CONTENT ) );
}
@Test
public void testSpan()
{
	check( "span", new HTMLSpan( CONTENT ) );
}
@Test

public void testStrong()
{
	check( "strong", new HTMLStrong( CONTENT ) );
}
}
