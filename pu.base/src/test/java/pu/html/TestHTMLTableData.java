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

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestHTMLTableData
{

/////////////////////////////////////////////////////////////////////////
// Test methods
@Test
public void testBasics()
{
	// Simpelste geval
	String expected = "    <td>data</td>" + NL;
	HTMLTableData td = new HTMLTableData( "data" );
	assertEquals( expected, td.getContent() );
}
@Test
public void testContent()
{
	String expected = "    <td align=\"center\">data</td>" + NL;
	HTMLTableData td = new HTMLTableData( "data" );
	td.setAlignment( HTMLConstants.CENTER );
	assertEquals( expected, td.getContent() );
}
}
