package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the ColumnGrid class
 */
public class TestArrayHelper
{
	
private void deepEquals( Object [] a, Object [] b )
{
	assertEquals( a.length, b.length );
	for ( int x = 0; x < a.length; x++ )
	{
		assertEquals( a[x], b[x] );
	}
}

private void deepEquals( String [] a, String [] b )
{
	assertEquals( a.length, b.length );
	for ( int x = 0; x < a.length; x++ )
	{
		assertEquals( a[x], b[x] );
	}
}

@Test
public void testNormalCases()
{
    Object [] a = new Object [] { "a", "b" };
    Object [] b = new Object [] { "c", "d" };
    Object [] expected = new Object [] { "a", "b", "c", "d" };
    
    deepEquals( expected, ArrayHelper.combineArrays( a, b ) );
    
}
@Test
public void testNormalCasesString()
{
    String [] a = new String [] { "a", "b" };
    String [] b = new String [] { "c", "d" };
    String [] expected = new String [] { "a", "b", "c", "d" };
    
    deepEquals( expected, ArrayHelper.combineArrays( a, b ) );
    
}
@Test
public void testNulls()
{
    Object [] nullObj = null;
    Object [] obj = new Object[] { new Object() };
    
    assertArrayEquals( obj, ArrayHelper.combineArrays( nullObj, obj ) );
    assertArrayEquals( obj, ArrayHelper.combineArrays( obj, nullObj ) );
    assertArrayEquals( null, ArrayHelper.combineArrays( nullObj, nullObj ) );
    
}
@Test
public void testNullsString()
{
    String [] nullObj = null;
    String [] obj = new String[] { new String() };
    
    assertArrayEquals( obj, ArrayHelper.combineArrays( nullObj, obj ) );
    assertArrayEquals( obj, ArrayHelper.combineArrays( obj, nullObj ) );
    assertArrayEquals( null, ArrayHelper.combineArrays( nullObj, nullObj ) );
    
}

}
