package pu.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for StringHlper
 * @@NOG Veel meer testen!
 */
public class TestStringHelper
{
	private static final String PROP_CLASS_PATH = "java.class.path";
    String [] xmlReplaceData =
   	{
   		"Piet & Riet",
   		"ba < bo",
   		"ba > bo",
   		"&",
   		"<",
   		">",
   		"",
   		null,
   	};
   String [] xmlReplaceExpected = 
   {
	   	"Piet &amp; Riet",
	    "ba &lt; bo"     ,
	    "ba &gt; bo"     ,
	    "&amp;"          ,
	    "&lt;"           ,
	    "&gt;"           ,
	    ""               ,
   		null,
   };
	
	String saveClassPath;
	
@Before
public void setUp() throws Exception
{
	saveClassPath = System.getProperty( PROP_CLASS_PATH );
}

@After
public void tearDown()
{
	System.setProperty( PROP_CLASS_PATH, saveClassPath );
}
private void checkArraysEqual( String aTestName, String[] aExpected, String[] aActual )
{
	assertEquals( aTestName + " size", aExpected.length, aActual.length );
	for ( int x = 0; x < aExpected.length; x++ )
	{
		assertEquals( aTestName + " index " + x, aExpected[x], aActual[x] );
	}
}

@Test
public void test()
{
	// HIGH deze nog omzetten naar echte tests!
	String [] s = new String []
	{
		"Een",
		"Twee",
		"Zeven",
		"Twaalf",
		"Dertien",
		"Vijftien",
		"Zeventien",
	};
	System.out.println( StringHelper.streep() );
	System.out.println( "vulUit(s, #, 5)" );
	System.out.println( StringHelper.streep() );
	for ( int x = 0; x < s.length; x++ )
	{
		System.out.println( s[x] + " ==> |" + StringHelper.vulUit( s[x], '#', 5 ) + '|' );
	}

	System.out.println( StringHelper.streep() ); 
	System.out.println( "vulUitMetSpaties(s, 5)" );
	System.out.println( StringHelper.streep() );
	for ( int x = 0; x < s.length; x++ )
	{
		System.out.println( s[x] + " ==> |" + StringHelper.vulUitMetSpaties( s[x], 5 ) + '|' );
	}

	System.out.println( StringHelper.streep() );
	System.out.println( "brengOpLengte(s, #, 5)" );
	System.out.println( StringHelper.streep() );
	for ( int x = 0; x < s.length; x++ )
	{
		System.out.println( s[x] + " ==> |" + StringHelper.brengOpLengte( s[x], '#', 5 )  + '|');
	}

	System.out.println( StringHelper.streep() ); 
	System.out.println( "brengOpLengteMetSpaties(s, 5)" );
	System.out.println( StringHelper.streep() );
	for ( int x = 0; x < s.length; x++ )
	{
		System.out.println( s[x] + " ==> |" + StringHelper.brengOpLengteMetSpaties( s[x], 5 )  + '|' );
	}

	testReplaceFirst();
}
@Test
public void testAlignment()
{
	String s = "Joop";
	int alignment;

	alignment = StringHelper.LEFT;
	assertEquals( ""      , StringHelper.align( s, 0, alignment ) ); 
	assertEquals( "J"     , StringHelper.align( s, 1, alignment ) ); 
	assertEquals( "Jo"    , StringHelper.align( s, 2, alignment ) ); 
	assertEquals( "Joo"   , StringHelper.align( s, 3, alignment ) ); 
	assertEquals( "Joop"  , StringHelper.align( s, 4, alignment ) ); 
	assertEquals( "Joop " , StringHelper.align( s, 5, alignment ) ); 
	assertEquals( "Joop  ", StringHelper.align( s, 6, alignment ) ); 

	alignment = StringHelper.RIGHT;
	assertEquals(       "", StringHelper.align( s, 0, alignment ) ); 
	assertEquals(      "p", StringHelper.align( s, 1, alignment ) ); 
	assertEquals(     "op", StringHelper.align( s, 2, alignment ) ); 
	assertEquals(    "oop", StringHelper.align( s, 3, alignment ) ); 
	assertEquals(   "Joop", StringHelper.align( s, 4, alignment ) ); 
	assertEquals(  " Joop", StringHelper.align( s, 5, alignment ) ); 
	assertEquals( "  Joop", StringHelper.align( s, 6, alignment ) ); 

	alignment = StringHelper.CENTER;
	assertEquals( ""      , StringHelper.align( s, 0, alignment ) ); 
	assertEquals( "J"     , StringHelper.align( s, 1, alignment ) ); 
	assertEquals( "Jo"    , StringHelper.align( s, 2, alignment ) ); 
	assertEquals( "Joo"   , StringHelper.align( s, 3, alignment ) ); 
	assertEquals( "Joop"  , StringHelper.align( s, 4, alignment ) ); 
	assertEquals( "Joop " , StringHelper.align( s, 5, alignment ) ); 
	assertEquals( " Joop ", StringHelper.align( s, 6, alignment ) ); 
}
@Test
public void testClassPathAsList()
{
	System.setProperty( PROP_CLASS_PATH, "d/e:a/b/c" );
	assertEquals( Arrays.asList( new String [] { "a/b/c", "d/e" } ), StringHelper.classPathAsList() );
}
@Test
public void testClassToPath()
{
	assertEquals( "pu/services", StringHelper.classToPath( StringHelper.class ) );
}
@Test
public void testContainsDigit()
{
	String [] testString =
	{
		null,
		"",
		"a",
		"0a",
		"a0",
		"123",
	};
	boolean [] expected =
	{
		false,
		false,
		false,
		true,
		true,
		true,
	};
	assertEquals( " testString en expected hebben niet dezelfde lengte", expected.length, testString.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		assertEquals( expected[x], StringHelper.containsDigit( testString [x] ) );
	}
}
@Test
public void testContainsMixedCase()
{
	String [] testString =
	{
		null,
		"",
		"a",
		"0a",
		"aB",
		"BBB",
	};
	boolean [] expected =
	{
		false,
		false,
		false,
		false,
		true,
		false,
	};
	assertEquals( " testString en expected hebben niet dezelfde lengte", expected.length, testString.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		assertEquals( "#" + x, expected[x], StringHelper.containsMixedCase( testString [x] ) );
	}
}
@Test
public void testConverteerTekst() 
{
	final int LENGTH = 10;
	String [] data =
	{
		/* 0 */ "123",
		/* 1 */ "1234567890",
		/* 2 */ "12345678901",
		/* 3 */ "123 567 901",
		/* 4 */ "123 567 9 1",
		/* 5 */ "123 567   1",
		/* 6 */ "123 567 9  2",
		/* 7 */ "123 567 901234\n  1\r\n  2\r  3\n\rBla.",
	};
	String [][] expected =
	{
		/* 0 */ { "123" },
		/* 1 */ { "1234567890" },
		/* 2 */ { "1234567890", "1" },
		/* 3 */ { "123 567 ", "901" },
		/* 4 */ { "123 567 9 ", "1" },
		/* 5 */ { "123 567   ", "1" },
		/* 6 */ { "123 567 9 ", " 2" },
		/* 7 */ { "123 567 ", "901234", "  1", "  2", "  3", "Bla." },
	};

	assertEquals( " data.length != expData.length", data.length, expected.length );
	
	for ( int x = 0; x < data.length; x++ )
	{
		List<String> actual = StringHelper.converteerTekst( data[x], LENGTH );
		assertEquals( " size#" + x, expected[x].length, actual.size() );
		
		for ( int r = 0; r < expected[x].length; r++ )
		{
			assertEquals( "#" + x + "#" + r, expected[x][r], actual.get( r ) );
		}
	}
}
@Test
public void testEquals()
{
	int test = 0;
	assertTrue( "#" + test++,   StringHelper.equals( null, null ) );
	assertTrue( "#" + test++, ! StringHelper.equals( null, "a" ) );
	assertTrue( "#" + test++, ! StringHelper.equals( "a", null ) );
	assertTrue( "#" + test++, ! StringHelper.equals( null, "" ) );
	assertTrue( "#" + test++, ! StringHelper.equals( "", null ) );
	assertTrue( "#" + test++,   StringHelper.equals( "", "" ) );
	assertTrue( "#" + test++,   StringHelper.equals( "a", "a" ) );
	assertTrue( "#" + test++, ! StringHelper.equals( "a", "b" ) );
	assertTrue( "#" + test++, ! StringHelper.equals( "a", "aab" ) );
	assertTrue( "#" + test++,   StringHelper.equals( "a", " a " ) );
	assertTrue( "#" + test++,   StringHelper.equals( " a ", "a" ) );
}
@Test
public void testEqualsIgnoreNull()
{
	int test = 0;
	assertTrue( "#" + test++,   StringHelper.equalsIgnoreNull( null, null ) );
	assertTrue( "#" + test++, ! StringHelper.equalsIgnoreNull( null, "a" ) );
	assertTrue( "#" + test++, ! StringHelper.equalsIgnoreNull( "a", null ) );
	assertTrue( "#" + test++,   StringHelper.equalsIgnoreNull( null, "" ) );
	assertTrue( "#" + test++,   StringHelper.equalsIgnoreNull( "", null ) );
	assertTrue( "#" + test++,   StringHelper.equalsIgnoreNull( "", "" ) );
	assertTrue( "#" + test++,   StringHelper.equalsIgnoreNull( "a", "a" ) );
	assertTrue( "#" + test++, ! StringHelper.equalsIgnoreNull( "a", "b" ) );
	assertTrue( "#" + test++, ! StringHelper.equalsIgnoreNull( "a", "aab" ) );
	assertTrue( "#" + test++,   StringHelper.equalsIgnoreNull( "a", " a " ) );
	assertTrue( "#" + test++,   StringHelper.equalsIgnoreNull( " a ", "a" ) );
}
@Test
public void testExplode()
{
	String [] testString =
	{
		"a;b;c;d;e",
		"a;bb;ccc;dddd;eeee",
		"a; bb;c c c;dddd  ;ee\tee",
		"a; bb;c c c;dddd  ;ee@ee",   // Twee verschillende delimiters: puntkomma en apestaartje
		"aa;;bb;;cc;;dd;;ee;;",       // Explode ziet meerdere delimiters achter elkaar als 1 delimiter
	};
	String [] delim =
	{
		";",
		";",
		";",
		";@",
		";",
	};
	String [][] expected =
	{
		{"a", "b", "c", "d", "e"},
		{"a", "bb", "ccc", "dddd", "eeee"},
		{"a", " bb", "c c c", "dddd  ", "ee\tee"},
		{"a", " bb", "c c c", "dddd  ", "ee", "ee"},
		{"aa", "bb", "cc", "dd", "ee"},
	};
	assertEquals( " testString en expected hebben niet dezelfde lengte", expected.length, testString.length );
	assertEquals( " testString en delim hebben niet dezelfde lengte", delim.length, testString.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		String [] res = StringHelper.explode( testString[x], delim[x] );
		assertEquals( " testString en geexplodeerde string hebben niet dezelfde lengte #" + x, expected[x].length, res.length );
		for ( int y = 0; y < expected[x].length; y++ )
			assertEquals( expected[x][y], res[y] );
	}
}
@Test
public void testExplode2()
{
	String [] testString = new String []
	{
		"apoepiebpoepiecpoepiedpoepiee",
		"apoepiebbpoepiecccpoepieddddpoepieeeee",
		"apoepie bbpoepiec c cpoepiedddd  poepieee\tee",
		"aapoepiepoepiebbpoepiepoepieccpoepiepoepieddpoepiepoepieeepoepiepoepie",
		"poepie",
		"",
	};
	String [][] expected = new String [][]
	{
		{ "a", "b", "c", "d", "e"},
		{ "a", "bb", "ccc", "dddd", "eeee"},
		{ "a", " bb", "c c c", "dddd  ", "ee\tee"},
		{ "aa", "", "bb", "", "cc", "", "dd", "", "ee", "", ""}, // Dit is het foute antwoord{"aa", "bb", "cc", "dd", "ee"},
		{ "", "" },
		{ "" },
	};
	assertEquals( " testString en expected hebben niet dezelfde lengte", expected.length, testString.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		String [] res = StringHelper.explode2( testString[x], "poepie" );
		assertEquals( " testString en geexplodeerde string hebben niet dezelfde lengte", expected[x].length, res.length );
		for ( int y = 0; y < expected[x].length; y++ )
			assertEquals( expected[x][y], res[y] );
	}
}
@Test
public void testGetShortClassName()
{
	/*************
	System.out.println( StringHelper.getShortClassName( new Integer( 1 ) ) );
	System.out.println( StringHelper.getShortClassName( Integer.class ) );
	System.out.println( StringHelper.getShortClassName( "Bla" ) );
	System.out.println( StringHelper.getShortClassName( String.class ) );
	System.out.println( StringHelper.getShortClassName( new Vector() ) );
	System.out.println( StringHelper.getShortClassName( new Vector().getClass() ) );
	System.out.println( StringHelper.getShortClassName( Vector.class ) );
	****************/
	
	assertEquals( "Integer", StringHelper.getShortClassName( Integer.valueOf( 1 ) ) );
	assertEquals( "Integer", StringHelper.getShortClassName( Integer.class ) );
	assertEquals( "String" , StringHelper.getShortClassName( "Bla" ) );
	assertEquals( "String" , StringHelper.getShortClassName( String.class ) );
	assertEquals( "Vector" , StringHelper.getShortClassName( new Vector<>() ) );
	assertEquals( "Vector" , StringHelper.getShortClassName( new Vector<>().getClass() ) );
	assertEquals( "Vector" , StringHelper.getShortClassName( Vector.class ) );
}
@Test
public void testImplodeStringArray()
{
	String [][] testString =
	{
		{ "a" },
		{ "a", "b", "c" },
		{ "a", null, "c" },
		{ null, null, "c" },
		{ null, null },
		{ },
	};
	String [] expected = 
	{
		"a",
		"a|b|c",
		"a||c",
		"||c",
		"|",
		"",
	};
	assertEquals( ": testString en expected hebben niet dezelfde lengte", testString.length, expected.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		assertEquals( expected[x], StringHelper.implode( testString[x], "|" ) );
	}
	assertEquals( " null array", "", StringHelper.implode( (String[]) null, "|" ) );
}
@Test
public void testImplodeStringThrowsIllegalArgumentExceptionOnNullSeparator()
{
	try
	{
		StringHelper.implode( new String [] {"a"}, null );
		fail( " should have thrown illegalArgumentException" );
	}
	catch ( IllegalArgumentException good ) {}
	try
	{
		StringHelper.implode( new ArrayList<>(), null );
		fail( " should have thrown illegalArgumentException" );
	}
	catch ( IllegalArgumentException good ) {}
}
@Test
public void testImplodeCollection()
{
	String [][] testString =
	{
		{ "a" },
		{ "a", "b", "c" },
		{ "a", null, "c" },
		{ null, null, "c" },
		{ null, null },
		{ },
	};
	String [] expected = 
	{
		"a",
		"a|b|c",
		"a||c",
		"||c",
		"|",
		"",
	};
	assertEquals( ": testString en expected hebben niet dezelfde lengte", testString.length, expected.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		assertEquals( expected[x], StringHelper.implode( Arrays.asList( testString[x] ), "|" ) );
	}
	assertEquals( " null collection", "", StringHelper.implode( (Collection<String>) null, "|" ) );
}
@Test
public void testInitCap()
{
	String [] testString = new String []
	{
		"a",
		"A",
		"peter",
		"Peter",
		"peterurbanus",
		"peterUrbanus",
		"PeTeRuRbAnUs",
		"pEtErUrBaNuS",
		"PETERURBANUS",
		"peter urbanus",
		"peter Urbanus",
		"PeTeR uRbAnUs",
		"pEtEr UrBaNuS",
		"PETER URBANUS",
	};
	String [] expected = new String []
	{
		"A",
		"A",
		"Peter",
		"Peter",
		"Peterurbanus",
		"PeterUrbanus",
		"PeTeRuRbAnUs",
		"PEtErUrBaNuS",
		"PETERURBANUS",
		"Peter urbanus",
		"Peter Urbanus",
		"PeTeR uRbAnUs",
		"PEtEr UrBaNuS",
		"PETER URBANUS",
	};
	assertEquals( ": testString en expected hebben niet dezelfde lengte", testString.length, expected.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		assertEquals( expected[x], StringHelper.initCap( testString[x] ) );
	}
}
@Test
public void testInitLow()
{
	String [] testString = new String []
	{
		"a",
		"A",
		"peter",
		"Peter",
		"peterurbanus",
		"peterUrbanus",
		"PeTeRuRbAnUs",
		"pEtErUrBaNuS",
		"PETERURBANUS",
		"peter urbanus",
		"peter Urbanus",
		"PeTeR uRbAnUs",
		"pEtEr UrBaNuS",
		"PETER URBANUS",
	};
	String [] expected = new String []
	{
		"a",
		"a",
		"peter",
		"peter",
		"peterurbanus",
		"peterUrbanus",
		"peTeRuRbAnUs",
		"pEtErUrBaNuS",
		"pETERURBANUS",
		"peter urbanus",
		"peter Urbanus",
		"peTeR uRbAnUs",
		"pEtEr UrBaNuS",
		"pETER URBANUS",
	};
	assertEquals( ": testString en expected hebben niet dezelfde lengte", testString.length, expected.length );
	for ( int x = 0; x < testString.length; x++ )
	{
		assertEquals( expected[x], StringHelper.initLow( testString[x] ) );
	}
}
@Test
public void testLeft()
{
	String test = "0123456789";
	String [] expected = new String []
	{
		"",
		"0",
		"01",
		"012",
		"0123",
		"01234",
		"012345",
		"0123456",
		"01234567",
		"012345678",
		"0123456789",
		"0123456789",
		"0123456789",
		"0123456789",
		"0123456789",
	};
	final int NUMCASES = 15;

	assertEquals( ": aantal cases niet gelijk aan expected", NUMCASES, expected.length ); 

	for ( int x = 0; x < NUMCASES; x++ )
	{
		assertEquals( expected[x], StringHelper.left( test, x ) );
	}
}
@Test
public void testMid()
{
	String test = "0123456789";
	String [][] expected = new String [][]
	{
		{
			"",
			"0",
			"01",
			"012",
			"0123",
			"01234",
			"012345",
			"0123456",
			"01234567",
			"012345678",
			"0123456789",
			"0123456789",
			"0123456789",
			"0123456789",
			"0123456789",
		},
		{
			"",
			"3",
			"34",
			"345",
			"3456",
			"34567",
			"345678",
			"3456789",
			"3456789",
			"3456789",
			"3456789",
			"3456789",
			"3456789",
			"3456789",
			"3456789",
		},
		
	};
	final int NUMTESTS =  2;
	final int NUMCASES = 15;

	
	assertEquals(  ": aantal tests niet gelijk aan expected", NUMTESTS, expected.length ); 
	for ( int x = 0; x < NUMTESTS; x++ )
	{
		assertEquals(  ": aantal testcases niet gelijk aan expected", NUMCASES, expected[x].length ); 
	}

	for ( int x = 0; x < NUMTESTS; x++ )
	{
		int start;
		if ( x == 0 )
		{
			start = 0;
		}
		else
		{
			start = 3;
		}
		for ( int y = 0; y < NUMCASES; y++ )
		{
			assertEquals(  expected[x][y], StringHelper.mid( test, start, y ) );
		}
	}
}
@Test
public void testReplaceFirst()
{
	String [] s = new String []
	{
		"koe",
		"koeien",
		"grotekoe",
		"grotekoeien",
		"hierzitniksin",
		"koe ende koeien",
	};
	String [] expected = new String []
	{
		"hond",
		"hondien",
		"grotehond",
		"grotehondien",
		"hierzitniksin",
		"hond ende koeien",

	};
	for ( int x = 0; x < s.length; x++ )
	{
		assertEquals( expected[x], StringHelper.replaceFirst( s[x], "koe", "hond" ) );
	}
}
@Test
public void testReplaceAll()
{
	String testString = "Groeten van Peter,Peter en Peter";
	String expected = "Groeten van Robert,Robert en Robert";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "Robert" ) );
	
	testString = "Groeten van Peter,Peter en Peter en George";
	expected = "Groeten van Robert,Robert en Robert en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "Robert" ) );

	testString = "Groeten van Peter,Peter en Peter";
	expected = "Groeten van RobertPeter,RobertPeter en RobertPeter";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "RobertPeter" ) );

	testString = "Groeten van Peter,Peter en Peter en George";
	expected = "Groeten van RobertPeter,RobertPeter en RobertPeter en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "RobertPeter" ) );

	testString = "Groeten van Peter,Peter en Peter";
	expected = "Groeten van PeterRobert,PeterRobert en PeterRobert";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "PeterRobert" ) );

	testString = "Groeten van Peter,Peter en Peter en George";
	expected = "Groeten van PeterRobert,PeterRobert en PeterRobert en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "PeterRobert" ) );

	testString = "Groeten van Peter,Peter en Peter en George";
	expected   = "Groeten van Peter,Peter en Peter en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Wim", "RobertPeter" ) );
	
	testString = "Peter,Peter en Peter";
	expected = "Robert,Robert en Robert";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "Robert" ) );
	
	testString = "Peter,Peter en Peter en George";
	expected = "Robert,Robert en Robert en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "Robert" ) );

	testString = "Peter,Peter en Peter";
	expected = "RobertPeter,RobertPeter en RobertPeter";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "RobertPeter" ) );

	testString = "Peter,Peter en Peter en George";
	expected = "RobertPeter,RobertPeter en RobertPeter en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "RobertPeter" ) );

	testString = "Peter,Peter en Peter";
	expected = "PeterRobert,PeterRobert en PeterRobert";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "PeterRobert" ) );

	testString = "Peter,Peter en Peter en George";
	expected = "PeterRobert,PeterRobert en PeterRobert en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Peter", "PeterRobert" ) );

	testString = "Peter,Peter en Peter en George";
	expected   = "Peter,Peter en Peter en George";
	assertEquals( expected, StringHelper.replaceAll( testString, "Wim", "RobertPeter" ) );

}
@Test
public void testQuoteXmlChars()
{
    assertEquals( " lengths match", xmlReplaceExpected.length, xmlReplaceData.length );
    for ( int x = 0; x < xmlReplaceData.length; x++ )
	{
        assertEquals( "#" + x, xmlReplaceExpected[x], StringHelper.quoteXmlChars( xmlReplaceData[x] )  );
	}
}
@Test
public void testQuoteXmlCharsArray()
{
    assertEquals( Arrays.asList( xmlReplaceExpected ), Arrays.asList( StringHelper.quoteXmlChars( xmlReplaceData ) ) );
}
@Test
public void testRight()
{
	String test = "0123456789";
	String [] expected = new String []
	{
		"",
		"9",
		"89",
		"789",
		"6789",
		"56789",
		"456789",
		"3456789",
		"23456789",
		"123456789",
		"0123456789",
		"0123456789",
		"0123456789",
		"0123456789",
		"0123456789",
	};
	final int NUMCASES = 15;

	assertEquals( ": aantal cases niet gelijk aan expected", NUMCASES, expected.length ); 

	for ( int x = 0; x < NUMCASES; x++ )
	{
		assertEquals( expected[x], StringHelper.right( test, x ) );
	}
}
@Test
public void testShowDiff() throws java.io.IOException
{
	//           0123456789012
	String s1 = "0CE123456789";
	String s2 = "0D1234A567B89";

	String actual = StringHelper.showDiff( s1, s2 );

	// Uncomment next line to create a new testfile
	//FileHelper.writeFile( TestHelper.getTestCaseFile( this, "testShowDiff" ), actual );
	String expected = FileHelper.readFile( TestHelper.getTestCaseFile( this, "testShowDiff" ) );
	
	assertEquals( expected, actual ); 
}
@Test
public void testTrimCharacters()
{
	String testString = "@@Groeten van George";
	String expected = "Groeten van George";
	assertEquals( expected, StringHelper.trimCharacters( testString, "@@" ) );
	
	testString = "@@ Groeten van George";
	expected = " Groeten van George";
	assertEquals( expected, StringHelper.trimCharacters( testString, "@@" ) );

	testString = "@Groeten van George@@";
	expected = "@Groeten van George";
	assertEquals( expected, StringHelper.trimCharacters( testString, "@@" ) );

	testString = "@@@Groeten van George@@@@";
	expected = "@Groeten van George";
	assertEquals( expected, StringHelper.trimCharacters( testString, "@@" ) );

	testString = "Groeten@@van George@@@";
	expected = "Groeten@@van George@";
	assertEquals( expected, StringHelper.trimCharacters( testString, "@@" ) );
	
	testString = "Groeten van George @ @";
	expected = "Groeten van George @ @";
	assertEquals( expected, StringHelper.trimCharacters( testString, "@@" ) );
	
	testString = "@ Groeten van George@@@@";
	expected = "@ Groeten van George";
	assertEquals( expected, StringHelper.trimCharacters( testString, "@@" ) );
}
@Test
public void testTrimFirstCharacters()
{
	String testString = "@@Groeten van George";
	String expected = "Groeten van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );
	
	testString = "@@ Groeten van George";
	expected = " Groeten van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

	testString = "@Groeten van George";
	expected = "@Groeten van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

	testString = "@@@Groeten van George";
	expected = "@Groeten van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

	testString = "@@@ Groeten van George";
	expected = "@ Groeten van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

	testString = "@@@@Groeten van George";
	expected = "Groeten van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

	testString = "@@@@ Groeten van George";
	expected   = " Groeten van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );
	
	testString = "Groeten@@van George";
	expected = "Groeten@@van George";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );
	
	testString = "Groeten van George@@";
	expected = "Groeten van George@@";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

	testString = "Groeten van George @@";
	expected = "Groeten van George @@";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

	testString = "Groeten van George @ @";
	expected = "Groeten van George @ @";
	assertEquals( expected, StringHelper.trimFirstCharacters( testString, "@@" ) );

}
@Test
public void testTrimLastCharacters()
{
	String testString = "@@Groeten van George";
	String expected = "@@Groeten van George";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@" ) );
	
	testString = "Groeten van George@@";
	expected = "Groeten van George";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@" ) );

	testString = "Groeten van George@";
	expected = "Groeten van George@";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@" ) );

	testString = "Groeten van George @@";
	expected = "Groeten van George ";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@" ) );

	testString = "Groeten van@@ George";
	expected = "Groeten van@@ George";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@" ) );

	testString = "Groeten van George@@@@";
	expected = "Groeten van George";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@" ) );

	testString = "Groeten van George@@@";
	expected   = "Groeten van George";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@@" ) );
	
	testString = "Groeten@@van George@@@@@@";
	expected = "Groeten@@van George";
	assertEquals( expected, StringHelper.trimLastCharacters( testString, "@@@" ) );
	

}
@Test
public void testTrimLeading()
{
	assertEquals( "Groeten van George", StringHelper.trimLeading( "Groeten van George" ) );
	assertEquals( "Groeten van George", StringHelper.trimLeading( " Groeten van George" ) );
	assertEquals( "Groeten van George", StringHelper.trimLeading( "  Groeten van George" ) );
	assertEquals( "Groeten van George", StringHelper.trimLeading( "\tGroeten van George" ) );
	assertEquals( "Groeten van George", StringHelper.trimLeading( " \tGroeten van George" ) );
	assertEquals( "Groeten van George", StringHelper.trimLeading( "\nGroeten van George" ) );
	assertEquals( "Groeten van George ", StringHelper.trimLeading( " Groeten van George " ) );
	assertEquals( null, StringHelper.trimLeading( null ) );
	assertEquals( "", StringHelper.trimLeading( " " ) );
	assertEquals( "", StringHelper.trimLeading( "\t\t" ) );
}
@Test
public void testTrimTrailing()
{
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George" ) );
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George " ) );
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George\t" ) );
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George \t" ) );
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George \n" ) );
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George  \n" ) );
	assertEquals( " Groeten van George", StringHelper.trimTrailing( " Groeten van George " ) );
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George" ) );
	assertEquals( "Groeten van George", StringHelper.trimTrailing( "Groeten van George\t" ) );
	assertEquals( null, StringHelper.trimTrailing( null ) );
	assertEquals( "", StringHelper.trimTrailing( " " ) );
	assertEquals( "", StringHelper.trimTrailing( "\t\t" ) );
}
@Test
public void testVerdeelInRegelsList() 
{
	String test = "1234567890 23456789 123 5678 90123 5678 01 34 67890 2345 789 123 567890123456 89012 456 890123456789 123456 0123";

	String verdeeld = StringHelper.verdeelInRegels( test, 30 );
	
	List<String> v = StringHelper.verdeelInRegelsList( test, 30 );
	StringBuffer buffy = new StringBuffer();
	
	for( int x = 0; x < v.size(); x++ )
	{
		if( x > 0 )
			buffy.append( "\n" );
		buffy.append( v.get( x ) );
	}	
	assertEquals( verdeeld, buffy.toString() ); 
}
@Test
public void testVerdeelInRegelsMetVasteKantlijn() 
{
	final int LENGTH = 10;
	String [] data =
	{
		/* 0 */ "123",
		/* 1 */ "1234567890",
		/* 2 */ "12345678901",
		/* 3 */ "123 567 901",
		/* 4 */ "123 567 9 1",
		/* 5 */ "123 567   1",
		/* 6 */ "123 567 9  2",
	};
	String [][] expected =
	{
		/* 0 */ { "123" },
		/* 1 */ { "1234567890" },
		/* 2 */ { "1234567890", "1" },
		/* 3 */ { "123 567 ", "901" },
		/* 4 */ { "123 567 9 ", "1" },
		/* 5 */ { "123 567   ", "1" },
		/* 6 */ { "123 567 9 ", " 2" },
	};

	assertEquals( " data.length != expData.length", data.length, expected.length );
	
	for ( int x = 0; x < data.length; x++ )
	{
		List<String> actual = StringHelper.verdeelInRegelsMetVasteKantlijn( data[x], LENGTH );
		//if ( x == 6 ) System.out.println( actual );
		assertEquals( " size#" + x, expected[x].length, actual.size() );
		
		for ( int r = 0; r < expected[x].length; r++ )
		{
			assertEquals( "#" + x + "#" + r, expected[x][r], actual.get( r ) );
		}
	}
}
@Test
public void testIsEmpty() 
{
	assertTrue( StringHelper.isEmpty( null ) ); 
	assertTrue( StringHelper.isEmpty( " " ) ); 
	assertTrue( ! StringHelper.isEmpty( "bla" ) ); 
	assertTrue( StringHelper.isEmpty( "" ) ); 
}
@Test
public void testSplit()
{
	 // Null of empty strings
	 checkArraysEqual( "#0", new String [] {}, StringHelper.split( null, new String [] { "x" } ) );
	 checkArraysEqual( "#1", new String [] {}, StringHelper.split( null, new String [] { "x", "y", "z" } ) );
	 checkArraysEqual( "#2", new String [] { "", "", "" }, StringHelper.split( "", new String [] { "", "" } ) );

	 checkArraysEqual( "#c", new String [] { "a", "b", "c" }, StringHelper.split( "axbyc", new String [] { "x", "y" } ) );

	 // null separator moet een RuntimeException geven
	 try
	 {
		 StringHelper.split( "abc", null );
		 fail( " null separators should throw a RuntimeException" );
	 }
	 catch ( RuntimeException e )
	 {
		 // good
	 }
	 try
	 {
		 StringHelper.split( "abc", new String [] { "a", null, "b" } );
		 fail( " null separators should throw a RuntimeException" );
	 }
	 catch ( RuntimeException e )
	 {
		 // good
	 }
 }
@Test
public void testSplitOnce()
{
	 // Null of empty strings
	 checkArraysEqual( "#0", new String [] {}            , StringHelper.splitOnce( null, "x" ) );
	 checkArraysEqual( "#1", new String [] { "" }        , StringHelper.splitOnce( "", "x" ) );
	 checkArraysEqual( "#2", new String [] { "", "abc" } , StringHelper.splitOnce( "abc", "" ) );

	 checkArraysEqual( "#3", new String [] { "a", "b" }  , StringHelper.splitOnce( "axb", "x" ) );
	 checkArraysEqual( "#4", new String [] { "a", "xb" } , StringHelper.splitOnce( "axxb", "x" ) );
	 checkArraysEqual( "#5", new String [] { "a", "b" }  , StringHelper.splitOnce( "axxb", "xx" ) );
	 checkArraysEqual( "#6", new String [] { "a", "bxc" }, StringHelper.splitOnce( "axbxc", "x" ) );

	 // null separator moet een RuntimeException geven
	 try
	 {
		 StringHelper.splitOnce( "abc", null );
		 fail( " null separator should throw a RuntimeException" );
	 }
	 catch ( RuntimeException e )
	 {
		 // good
	 }
}

}
