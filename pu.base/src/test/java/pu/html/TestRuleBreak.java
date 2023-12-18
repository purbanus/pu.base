/*
 * Created on 9-nov-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.html;

import pu.services.StringHelper;

import static org.junit.Assert.*;
import static pu.html.Constants.*;

import org.junit.Test;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestRuleBreak
{

/////////////////////////////////////////////////////////////////////////
// Test methods

private void check( String aTag, String aAttributes, HTMLBodyElement aElement )
{
	boolean hasAttributes = ! StringHelper.isEmpty( aAttributes );
	String expected = "<" + aTag + ( hasAttributes ? " " + aAttributes : "" ) + " />" + NL;
	assertEquals( expected, aElement.getContent() );
}
@Test
public void testBR()
{
	check( "br", "", new HTMLLineBreak() );
}
@Test
public void testHR()
{
	check( "hr", "", new HTMLHorizontalRule() );
}
}
