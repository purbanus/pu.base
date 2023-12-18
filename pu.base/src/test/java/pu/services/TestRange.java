package pu.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Range
 */
public class TestRange
{	
	//SnelDatum [] snelDatums;
	final int [] values = { 1, 2, 3, 4, 5 };
	Range [] range;

// HIGH Deze methode zit al in nl.mediacenter.services.AbstractTest
public static boolean[][] maakBools( String[] s )
{
	boolean [][] b = new boolean[s.length][];
	for ( int x = 0; x < s.length; x++ )
	{
		b[x] = new boolean[s[x].length()]; // Kan iets te lang zijn ivm eventuele spaties in de string
		int bIndex = 0;
		for ( int y = 0; y < s[x].length(); y++ )
		{
			char c = s[x].charAt(y);
			if ( c == '0' || c == '1' )
			{
				b[x][bIndex++] = c == '1';
			}
		}
	}
	return b;
}

@Before
public void setUp() throws Exception
{
	range = new Range [5+4+3+2+1];
	int index = 0;
	for ( int x = 0; x < values.length; x++ )
	{
		for ( int y = x; y < values.length; y++ )
		{
			range[index++] = new Range( values[x], values[y] );
		}
	}
}

@Test
public void testBasics()
{
	Range r = new Range( 1, 3 );
	assertEquals( 1, r.from );
	assertEquals( 3, r.to );
}
@Test
public void testInvalidRange()
{
	try
	{
	    @SuppressWarnings( "unused" )
		Range r = new Range( 3, 1 );
	    fail( " should have thrown an excetion if from > to" );
	}
	catch ( RuntimeException e )
	{
	    // ok
	}
}
@Test
public void testContainsValue()
{
	String [] s = new String []
	{
	//   01234 5678 901 23 4
		"11111 0000 000 00 0",
		"01111 1111 000 00 0",
		"00111 0111 111 00 0",
		"00011 0011 011 11 0",
		"00001 0001 001 01 1",
	};
	boolean [][] expected = maakBools( s );
	for ( int x = 0; x < values.length; x++ )
	{
		for ( int y = 0; y < range.length; y++ )
		{
			assertEquals( " [" + x + "," + y + "]", expected[x][y], range[y].contains( values[x] ) );
		}
	}
}
@Test
public void testContainsRange()
{
	String [] s = new String []
	{
	//   0-0 0-1 0-2 0-3 0-4 | 1-1 1-2 1-3 1-4 | 2-2 2-3 2-4 | 3-3 3-4 | 4-4
	//   --------------------|-----------------|-------------|---------|------------
		" 1   1   1   1   1  |  0   0   0   0  |  0   0   0  |  0   0  |  0", // 0-0
		" 0   1   1   1   1  |  0   0   0   0  |  0   0   0  |  0   0  |  0", // 0-1
		" 0   0   1   1   1  |  0   0   0   0  |  0   0   0  |  0   0  |  0", // 0-2
		" 0   0   0   1   1  |  0   0   0   0  |  0   0   0  |  0   0  |  0", // 0-3
		" 0   0   0   0   1  |  0   0   0   0  |  0   0   0  |  0   0  |  0", // 0-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   1   1   1   1  |  1   1   1   1  |  0   0   0  |  0   0  |  0", // 1-1
		" 0   0   1   1   1  |  0   1   1   1  |  0   0   0  |  0   0  |  0", // 1-2
		" 0   0   0   1   1  |  0   0   1   1  |  0   0   0  |  0   0  |  0", // 1-3
		" 0   0   0   0   1  |  0   0   0   1  |  0   0   0  |  0   0  |  0", // 1-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   0   1   1   1  |  0   1   1   1  |  1   1   1  |  0   0  |  0", // 2-2
		" 0   0   0   1   1  |  0   0   1   1  |  0   1   1  |  0   0  |  0", // 2-3
		" 0   0   0   0   1  |  0   0   0   1  |  0   0   1  |  0   0  |  0", // 2-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   0   0   1   1  |  0   0   1   1  |  0   1   1  |  1   1  |  0", // 3-3
		" 0   0   0   0   1  |  0   0   0   1  |  0   0   1  |  0   1  |  0", // 3-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   0   0   0   1  |  0   0   0   1  |  0   0   1  |  0   1  |  1", // 4-4
	//   --------------------|-----------------|-------------|---------|------------
	};
	boolean [][] expected = maakBools( s );
	for ( int x = 0; x < range.length; x++ )
	{
		for ( int y = 0; y < range.length; y++ )
		{
			assertEquals( " [" + x + "," + y + "]", expected[x][y], range[y].contains( range[x] ) );
		}
	}
}
@Test
public void testEquals()
{
	for ( int x = 0; x < range.length; x++ )
	{
		for ( int y = 0; y < range.length; y++ )
		{
			assertEquals( x == y, range[x].equals( range[y] ) );
		}
	}
}
@Test
public void testLength()
{
	int [] expected = new int []
	{
		1, 2, 3, 4, 5,
		1, 2, 3, 4,
		1, 2, 3,
		1, 2,
		1,
	};
	assertEquals( expected.length, range.length );
	for ( int x = 0; x < expected.length; x++ )
	{
		assertEquals( expected[x], range[x].getLength() );
	}
}
@Test
public void testOverlapsWith()
{
	String [] s = new String []
	{
	//   0-0 0-1 0-2 0-3 0-4 | 1-1 1-2 1-3 1-4 | 2-2 2-3 2-4 | 3-3 3-4 | 4-4
	//   --------------------|-----------------|-------------|---------|------------
		" 1   1   1   1   1  |  0   0   0   0  |  0   0   0  |  0   0  |  0", // 0-0
		" 1   1   1   1   1  |  1   1   1   1  |  0   0   0  |  0   0  |  0", // 0-1
		" 1   1   1   1   1  |  1   1   1   1  |  1   1   1  |  0   0  |  0", // 0-2
		" 1   1   1   1   1  |  1   1   1   1  |  1   1   1  |  1   1  |  0", // 0-3
		" 1   1   1   1   1  |  1   1   1   1  |  1   1   1  |  1   1  |  1", // 0-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   1   1   1   1  |  1   1   1   1  |  0   0   0  |  0   0  |  0", // 1-1
		" 0   1   1   1   1  |  1   1   1   1  |  1   1   1  |  0   0  |  0", // 1-2
		" 0   1   1   1   1  |  1   1   1   1  |  1   1   1  |  1   1  |  0", // 1-3
		" 0   1   1   1   1  |  1   1   1   1  |  1   1   1  |  1   1  |  1", // 1-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   0   1   1   1  |  0   1   1   1  |  1   1   1  |  0   0  |  0", // 2-2
		" 0   0   1   1   1  |  0   1   1   1  |  1   1   1  |  1   1  |  0", // 2-3
		" 0   0   1   1   1  |  0   1   1   1  |  1   1   1  |  1   1  |  1", // 2-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   0   0   1   1  |  0   0   1   1  |  0   1   1  |  1   1  |  0", // 3-3
		" 0   0   0   1   1  |  0   0   1   1  |  0   1   1  |  1   1  |  1", // 3-4
	//   --------------------|-----------------|-------------|---------|------------
		" 0   0   0   0   1  |  0   0   0   1  |  0   0   1  |  0   1  |  1", // 4-4
	//   --------------------|-----------------|-------------|---------|------------
	};
	boolean [][] expected = maakBools( s );
	for ( int x = 0; x < range.length; x++ )
	{
		for ( int y = 0; y < range.length; y++ )
		{
			assertEquals( " [" + x + "," + y + "]", expected[x][y], range[y].overlapsWith( range[x] ) );
		}
	}
}
}
