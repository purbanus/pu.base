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
public class TestHTMLList
{
private void checkList( String aTag, HTMLList aList )
{
	aList.add( "pipo" );
	aList.add( "joop" );
	
	String expected = 
		  "<" + aTag + ">" + NL
		+ "<li>pipo</li>" + NL
		+ "<li>joop</li>" + NL
		+ "</" + aTag + ">"  + NL
	;
	assertEquals( expected, aList.getContent() );
}
@Test
public void testOrderedList()
{
	checkList( "ol", new HTMLOrderedList() );
}
@Test
public void testUnorderedList()
{
	checkList( "ul", new HTMLUnorderedList() );
}

}
