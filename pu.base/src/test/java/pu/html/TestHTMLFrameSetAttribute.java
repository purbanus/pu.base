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
public class TestHTMLFrameSetAttribute
{

/////////////////////////////////////////////////////////////////////////
// Test methods
@Test
public void testCols()
{
	String expected = " cols=\"1*,4*\"";
	HTMLAttribute attr = new HTMLFrameSetAttribute( HTMLFrameSetAttribute.COLS, "1*,4*" );
	assertEquals( expected, attr.getContent() );
}
@Test
public void testRows()
{
	String expected = " rows=\"1*,4*\"";
	HTMLAttribute attr = new HTMLFrameSetAttribute( HTMLFrameSetAttribute.ROWS, "1*,4*" );
	assertEquals( expected, attr.getContent() );
}
}
