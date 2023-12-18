/*
 * Created on 4-jan-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.xml.xmlmaker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Testing XmlElement
 */
public class TestXmlElement
{
StringBuffer sb = null;


/*
 * @see TestCase#setUp()
 */
@Before
public void setUp()
{
	sb = new StringBuffer();
}
@Test
public void testAttributes()
{
	XmlElement el = new XmlElement( "bla" );
	el.addAttribute( "pipo", "koeie" );
	el.getOutput( sb );
	assertEquals( "attributes", "<bla pipo=\"koeie\" />" + XmlBase.SEP, sb.toString() );
}
@Test
public void testBasics()
{
	XmlElement el = new XmlElement( "bla" );
	el.getOutput( sb );
	assertEquals( "basics", "<bla />" + XmlBase.SEP, sb.toString() );
}
@Test
public void testDocument()
{
	XmlElement el = new XmlElement( "bla" );
	XmlDocument doc = new XmlDocument( el );
	doc.getOutput( sb );
	assertEquals( "doc", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + XmlBase.SEP + "<bla />" + XmlBase.SEP, sb.toString() );
}

}
