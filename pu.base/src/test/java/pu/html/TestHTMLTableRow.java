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
public class TestHTMLTableRow
{
@Test
public void test()
{
	HTMLTable parent = null; // HIGH effe
	HTMLTableRow row1 = new HTMLTableRow( parent );
	row1.add( new HTMLTableData( "pipo" ) );
	row1.add( new HTMLTableData( "joop" ) );
	HTMLTableRow row2 = new HTMLTableRow( parent );
	row2.add( new HTMLTableData( "hipo" ) );
	row2.add( new HTMLTableData( "hoop" ) );
	
	String expected = 
		  "  <tr>" + NL
		+ "    <td>pipo</td>" + NL
		+ "    <td>joop</td>" + NL
		+ "  </tr>  <tr>" + NL        // HIGH Misschien njog een schoonheidsfoutje die twee spaties maar hoe los je dat op?
		+ "    <td>hipo</td>" + NL
		+ "    <td>hoop</td>" + NL
		+ "  </tr>"
	;
	assertEquals( expected, row1.getContent() + row2.getContent() );
}

}
