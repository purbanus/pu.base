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
public class TestHTMLColor
{

/////////////////////////////////////////////////////////////////////////
// Test methods
@Test
public void testBasics()
{
	assertEquals( "#000000", new HTMLColor(   0,   0,   0 ).getContent() );
	assertEquals( "#ffffff", new HTMLColor( 255, 255, 255 ).getContent() );
	assertEquals( "#010101", new HTMLColor(   1,   1,   1 ).getContent() );
	assertEquals( "#808080", new HTMLColor( 128, 128, 128 ).getContent() );
}
}
