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
public class TestHTMLContainer
{
	static final String TAG = "mytag";
	static final String START = "<" + TAG + ">";
	static final String END   = "</" + TAG + ">";
	static final String ID    = "19";
	
	static final String START_MET_ID = "<" + TAG + " id=\"" + ID + "\">";

/////////////////////////////////////////////////////////////////////////
// Test methods
@Test
public void testBasics()
{
	HTMLContainer container = new HTMLContainer( TAG )
	{
	};
	// Empty container
	assertEquals( START + END, container.getContent() );
	
	// String erbij
	container.add( "popi" );
	assertEquals( START + "popi" + END, container.getContent() );

	// Andere String erbij
	container.add( "pipo" );
	assertEquals( START + "popipipo" + END, container.getContent() );
	
	// Een Emphasis erbij
	container.add( new HTMLEmphasis( "Jan" ) );
	assertEquals( START + "popipipo<em>Jan</em>" + END, container.getContent() );
	
}
@Test
public void testChainAdd()
{
	HTMLContainer container = new HTMLContainer( TAG )
	{
	};
	container.add( "popi" )
			 .add( new HTMLEmphasis( "Jan" ) );

	String expected = START + "popi" + "<em>Jan</em>" + END;
	assertEquals( expected, container.getContent() );
}
@Test
public void testChainAttributes()
{
	HTMLContainer container = new HTMLContainer( TAG )
	{
	};
	// HIGH Als de volgorde ander is werkt dit niet want setID retourneert een
	//       HTMLBodyElement en die ken geen add.
	container.add( "popi" )
			 .add( new HTMLEmphasis( "Jan" ) )
			 .setID( ID );

	String expected = START_MET_ID + "popi" + "<em>Jan</em>" + END;
	assertEquals( expected, container.getContent() );
}
@Test
public void testCtors()
{
	HTMLContainer container;
	
	// empty ctor
	container = new HTMLContainer( TAG )
	{
	};
	assertEquals( START + END, container.getContent() );

	// ctor met string
	container = new HTMLContainer( TAG, "popi" )
	{
	};
	assertEquals( START + "popi" + END, container.getContent() );

	// Andere String erbij
	container.add( "pipo" );
	assertEquals( START + "popipipo" + END, container.getContent() );

	// ctor met HTMLTag (In feite is <p> ook een HTMLContainer!)
	container = new HTMLContainer( TAG, new HTMLEmphasis( "Jan" ) )
	{
	};
	assertEquals( START + "<em>Jan</em>" + END, container.getContent() );
	
	// NBog eenparagraafje erbij
	container.add( new HTMLStrong( "Piet" ) );
	assertEquals( START + "<em>Jan</em><strong>Piet</strong>" + END, container.getContent() );
	
}
public void testMetAttribs()
{
	// ctor met String
	HTMLContainer container = new HTMLContainer( TAG, "popi" )
	{
	};
	assertEquals( START + "popi" + END, container.getContent() );

	// Set voorgedefinieerd attribute
	container.setID( "19" );
	assertEquals( START_MET_ID + "popi" + END, container.getContent() );
}
}
