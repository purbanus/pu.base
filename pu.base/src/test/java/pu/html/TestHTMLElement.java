/*
 * Created on 9-nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestHTMLElement
{

/////////////////////////////////////////////////////////////////////////
// Test methods

@Test
public void testBasics()
{
	HTMLBodyElement el = new HTMLBodyElement( "niks" )
	{
		@Override
		protected String getTagContent()
		{
			return "pipo";
		}
	};
	
	el.setCSSClass( "a" );
	el.setDir     ( HTMLConstants.RTL );
	el.setID      ( "c" );
	el.setLang    ( "d" );
	el.setStyle   ( "e" );
	el.setTitle   ( "f" );
	
	assertEquals( "a", el.getCSSClass() );
	assertEquals( HTMLConstants.RTL, el.getDir() );
	assertEquals( "c", el.getID() );
	assertEquals( "d", el.getLang() );
	assertEquals( "e", el.getStyle() );
	assertEquals( "f", el.getTitle() );

	// @@ Dit geldt alleen voor Linux, in Windows is het \r\n
	assertEquals( "\n", el.NEWLINE );
	
}
}
