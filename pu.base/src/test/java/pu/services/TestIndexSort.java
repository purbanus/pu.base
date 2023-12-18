package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @@NOG Veel meer testen!
 * Creation date: (5-7-01 16:00:16)
 * @author: Peter Urbanus
 */
public class TestIndexSort extends AbstractSortTest
{
	IndexSort indexSort = new IndexSort()
	{
		@Override
		public int compareElements( int l, int r )
		{
			// De compiler is af en toe mesjogge. Een hele tijd deed-ie het zo,
			// daarna kreeg ik consequent NoSuchMethodException en werkte de boel
			// alleen met die truc met effeCompare, en nou doet ie het wel weer!
			return Util.compare( testData[l],  testData[r] );
			//return effeCompare( l, r );
		}
	};
	IndexSort numberNameSort = new IndexSort()
	{
		@Override
		public int compareElements( int l, int r )
		{
			return Util.compare( numberName[l].number, numberName[r].number );
		}
	};

public void checkNumberNameSort( int [] aExpected, NumberName [] aData )
{
	assertEquals( " Lengths should be equal", aExpected.length, aData.length );

	// Sort the data. No need to make a copy, since we're only sorting indices.
	int [] actual = numberNameSort.sort( aData );

	// Print it out for visual check
	//printNumberNames( aData );
	//System.out.print( " --> " );
	//printSortedNumberNames( aData, actual );
	//System.out.println();

	// Check if the sorted data has the expected order
	checkTwoArrays( aExpected, actual );
}
/**
 * Insert the method's description here.
 * Creation date: (11-11-2001 20:22:55)
 */
public void checkSort( int [] aExpected, Integer [] aData )
{
	// aData can be null
	int dataLen;
	if ( aData == null )
	{
		dataLen = 0;
	}
	else
	{
		dataLen = aData.length;
	}

	// Lengths must be equal
	assertEquals( " Lengths should be equal", aExpected.length, dataLen );

	// Sort the data. No need to make a copy, since we're only sorting indices.
	int [] actual = indexSort.sort( aData );

	// Print it out for visual check
	//printIntegers( aData );
	//System.out.print( " --> " );
	//printSortedIntegers( aData, actual );
	//System.out.println();

	// Check if the sorted data has the expected order
	checkTwoArrays( aExpected, actual );
}
/**
 * Insert the method's description here.
 * Creation date: (11-11-2001 0:40:49)
 * @param a int[]
 * @param b int[]
 */
public void checkTwoArrays( int[] a, int[] b)
{
	assertEquals( " Lengths should be equal", a.length, b.length );
	for ( int x = 0; x < a.length; x++ )
	{
		assertEquals( a[x], b[x] );
	}
}
public void printIntegers( Integer [] aData )
{
	if ( aData == null )
	{
		System.out.print( "<null>" );
	}
	else
	{
		for ( int x = 0; x < aData.length; x++ )
		{
			System.out.print( aData[x] );
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (11-11-2001 0:53:09)
 */
public void printNumberNames( NumberName [] aData )
{
	for ( int x = 0; x < aData.length; x++ )
	{
		System.out.print( aData[x] + " " );
	}
}
/**
 * Insert the method's description here.
 * Creation date: (11-11-2001 0:53:09)
 */
public void printSortedIntegers( Integer [] aData, int [] aIndices )
{
	if ( aData == null )
	{
		System.out.print( "<null>" );
	}
	else
	{
		for ( int x = 0; x < aIndices.length; x++ )
		{
			System.out.print( aData[aIndices[x]] );
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (11-11-2001 0:53:09)
 */
public void printSortedNumberNames( NumberName [] aData, int [] aIndices )
{
	for ( int x = 0; x < aData.length; x++ )
	{
		System.out.print( aData[aIndices[x]] + " " );
	}
}
@Test
public void testBasics()
{
	int [] expected;

	// Already sorted	
	testData = createTestData( new int [] { 0, 1, 2, 3, 4 } );
	expected = new int [] { 0, 1, 2, 3, 4 };
	checkSort( expected, testData );

	// Reverse order
	testData = createTestData( new int [] { 4, 3, 2, 1, 0 } );
	expected = new int [] { 4, 3, 2, 1, 0 };
	checkSort( expected, testData );

	// Random order
	testData = createTestData( new int [] { 2, 3, 1, 4, 0 } );
	expected = new int [] { 4, 2, 0, 1, 3 };
	checkSort( expected, testData );
}
@Test
public void testBoundaryConditions()
{
	int [] expected;

	// Null testData
	testData = null;
	expected = new int [0];
	checkSort( expected, testData );

	// One element
	testData = createTestData( new int [] { 5 } );
	expected = new int [] { 0 };
	checkSort( expected, testData );

	// Two equal elements
	testData = createTestData( new int [] { 5, 5 } );
	expected = new int [] { 0, 1 };
	checkSort( expected, testData );

	// Two unequal elements in sorted order
	testData = createTestData( new int [] { 5, 9 } );
	expected = new int [] { 0, 1 };
	checkSort( expected, testData );

	// Two unequal elements in reverse order
	testData = createTestData( new int [] { 9, 5 } );
	expected = new int [] { 1, 0 };
	checkSort( expected, testData );
}
/**
 * Test whether equal elements will retain their order.
 * In this test, an array of number/name pairs will be created, with all
 * the numbers the same and the names different. The compareElements only
 * tests the number, not the name, and since these are all the same, the
 * elements of the array should retain their order. If the sort is stable,
 * that is.
 */
@Test
public void testStability()
{
	int [] expected;

	// Already sorted	
	numberName = createTestNumberName( new String [] { "Abe", "Bert", "Carla", "Dirk", "Ed" } );
	expected = new int [] { 0, 1, 2, 3, 4 };
	checkNumberNameSort( expected, numberName );

	// Reverse order
	numberName = createTestNumberName( new String [] { "Ed", "Dirk", "Carla", "Bert", "Abe" } );
	expected = new int [] { 0, 1, 2, 3, 4 };
	checkNumberNameSort( expected, numberName );

	// Random order
	numberName = createTestNumberName( new String [] { "Carla", "Dirk", "Bert", "Ed", "Abe" } );
	expected = new int [] { 0, 1, 2, 3, 4 };
	checkNumberNameSort( expected, numberName );
}
}
