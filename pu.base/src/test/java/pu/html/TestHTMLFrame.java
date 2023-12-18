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
public class TestHTMLFrame
{
@Test
public void testFrame()
{
	HTMLFrame frame = new HTMLFrame( "source", "framepie" );
	
	String expected = 
		  "  <frame src=\"source\" name=\"framepie\" />" + NL;
	assertEquals( expected, frame.getContent() );
}

}
