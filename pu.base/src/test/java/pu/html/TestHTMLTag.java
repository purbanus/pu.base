/*
 * Created on 9-nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

import static org.junit.Assert.*;
import static pu.html.Constants.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestHTMLTag
{
	private static final String MYTAG = "mytag";
	private static final String MYCONTENT = "Bla";
	
	static class MyHTMLTag extends HTMLTag
	{
		private String content = MYCONTENT;
		public MyHTMLTag()
		{
			super( MYTAG );
		}

		// Truc: alles public maken zodat we er bij kunnen
		@Override
		public Object getAttribute( String key )
		{
			return super.getAttribute( key );
		}
		@Override
		public String getAttributeString()
		{
			return super.getAttributeString();
		}
		@Override
		public int getIntAttribute( String key )
		{
			return super.getIntAttribute( key );
		}
		@Override
		public String getTagContent()
		{
			return content;
		}
		@Override
		public HTMLTag setAttribute( String key, Object value )
		{
			return super.setAttribute( key, value );
		}
		@Override
		public HTMLTag setEntersAfterEndTag( int value )
		{
			return super.setEntersAfterEndTag( value );
		}
		@Override
		public HTMLTag setEntersAfterStartTag( int value )
		{
			return super.setEntersAfterStartTag( value );
		}
		@Override
		public HTMLTag setIndent( int value )
		{
			return super.setIndent( value );
		}
		@Override
		public HTMLTag setIndentEndTag( boolean value )
		{
			return super.setIndentEndTag( value );
		}
		@Override
		public HTMLTag setIntAttribute( String key, int value )
		{
			return super.setIntAttribute( key, value );
		}
		
		// Voor de test
		public void setContent( String aContent )
		{
			content = aContent;
		}
	}
	MyHTMLTag testTag;

@Before
public void setup()
{
	testTag = new MyHTMLTag();
}
/////////////////////////////////////////////////////////////////////////
// Test methods
@Test
public void testAttributes()
{
	// getAttributes when empty	
	assertEquals( "", testTag.getAttributeString() );

	// getAttributes w/2 values
	testTag.setAttribute( "bla", "gepraat" );
	testTag.setAttribute( "pip", "gepipo" );
	assertEquals( "gepraat", testTag.getAttribute( "bla" ) );
	assertEquals( "gepipo" , testTag.getAttribute( "pip" ) );
	assertEquals( " bla=\"gepraat\" pip=\"gepipo\"", testTag.getAttributeString() );
}
@Test
public void testEnters()
{
	final String START = "<" + MYTAG + ">";
	final String END   = "</" + MYTAG + ">";
	//final String CONTENT = "pipo koeie";
	
	assertEquals( START + MYCONTENT + END, testTag.getContent() );
	
	testTag.setEntersAfterEndTag( 2 );
	assertEquals( START + MYCONTENT + END + NL + NL, testTag.getContent() );

	testTag.setEntersAfterStartTag( 1 );
	assertEquals( START + NL + MYCONTENT + END + NL + NL, testTag.getContent() );
}
@Test
public void testIndent()
{
	final String START = "<" + MYTAG + ">";
	final String END   = "</" + MYTAG + ">";
	//final String CONTENT = "pipo koeie";
	
	assertEquals( START + MYCONTENT + END, testTag.getContent() );
	
	// Let op: indent 2 wil zeggen 2 units. Het aantal spaties per unit zit in HTMNLContstants.INDENT_UNITS
	testTag.setIndent( 2 );
	assertEquals( "    " + START + MYCONTENT + END, testTag.getContent() );

	testTag.setIndentEndTag( true );
	assertEquals( "    " + START + MYCONTENT + "    " + END, testTag.getContent() );
}
@Test
public void testIntAttributes()
{
	// getAttributes when empty	
	assertEquals( "", testTag.getAttributeString() );

	// getAttributes w/2 values
	testTag.setIntAttribute( "bla", 12 );
	testTag.setIntAttribute( "pip", 13 );
	assertEquals( 12, testTag.getIntAttribute( "bla" ) );
	assertEquals( 13, testTag.getIntAttribute( "pip" ) );
	// Let op: de volgorde is anders omdat Tag een Map gebruikt
	assertEquals( " bla=\"12\" pip=\"13\"", testTag.getAttributeString() );
}
@Test
public void testTag()
{
	final String START = "<" + MYTAG + ">";
	final String END   = "</" + MYTAG + ">";
	final String CONTENT = "pipo koeie";
	
	assertEquals( START + MYCONTENT + END, testTag.getContent() );

	testTag.setContent( CONTENT );
	assertEquals( START + CONTENT + END, testTag.getContent() );
}
}
