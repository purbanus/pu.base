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
public class TestHTMLAttribute
{
/////////////////////////////////////////////////////////////////////////
// Test methods

@Test
public void testAttribute()
{
	String expected = " bla=\"pipo\"";
	HTMLAttribute attr = new HTMLAttribute( "bla", "pipo" );
	assertEquals( expected, attr.getContent() );
}
}
