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
public class TestHTMLDocument
{

@Test
public void testHTMLDoc()
{
	//HTMLDoc doc = new HTMLDoc( "headertje" );
	
	HTMLHeader head = new HTMLHeader( "headertje" );
	HTMLBody body = new HTMLBody( new HTMLParagraph( "joepie" ) );
	HTMLDoc doc = new HTMLDoc( head, body );
	
	String expected = 
		  "<html>" + NL
		+ "<head>" + NL
		+ "  <title>headertje</title>" + NL
		+ "</head>" + NL
		+ "<body>"  + NL
		+ "<p>joepie</p>" + NL
		+ "</body>" + NL
		+ "</html>" + NL
	;
	assertEquals( expected, doc.getContent() );
}
@Test
public void testHTMLDoc2()
{
	HTMLDoc doc = new HTMLDoc( "headertje" );
	// TODO Ook doc.setBody()?
	doc.getBody().add( new HTMLParagraph( "joepie" ) );
	
	String expected = 
		  "<html>" + NL
		+ "<head>" + NL
		+ "  <title>headertje</title>" + NL
		+ "</head>" + NL
		+ "<body>"  + NL
		+ "<p>joepie</p>" + NL
		+ "</body>" + NL
		+ "</html>" + NL
	;
	assertEquals( expected, doc.getContent() );
}

@Test
public void testHTMLFrameDoc()
{
	HTMLHeader head = new HTMLHeader( "headertje" );
	HTMLFrameSetAttribute attr = new HTMLFrameSetAttribute( HTMLFrameSetAttribute.ROWS, "150" );
	HTMLFrameSet body = new HTMLFrameSet( attr );
	HTMLFrameDoc doc = new HTMLFrameDoc( head, body );
	
	String expected = 
		  "<html>" + NL
		+ "<head>" + NL
		+ "  <title>headertje</title>" + NL
		+ "</head>" + NL
		+ "<frameset rows=\"150\">"  + NL
		+ "</frameset>" + NL
		+ "</html>" + NL
	;
	assertEquals( expected, doc.getContent() );
}

}
