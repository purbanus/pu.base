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
public class TestHTMLAnchor
{

/////////////////////////////////////////////////////////////////////////
// Test methods

@Test
public void testBasics()
{
	// Simpelste geval
	String expected = "<a href=\"http://joepie\">Klik hier</a>";
	HTMLAnchor anchor = new HTMLAnchor( "http://joepie", "Klik hier" );
	assertEquals( expected, anchor.getContent() );
}
@Test
public void testContent()
{
	// Nog wat content
	String expected = "<a href=\"http://joepie\">Klik hier<em>niet</em></a>";
	HTMLAnchor anchor = new HTMLAnchor( "http://joepie", "Klik hier" );
	anchor.add( new HTMLEmphasis( "niet" ) );
	assertEquals( expected, anchor.getContent() );
}
}
