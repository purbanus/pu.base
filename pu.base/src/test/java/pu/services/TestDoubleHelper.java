package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 */
public class TestDoubleHelper
{
	static final double DELTA = 0.0001D;

	static final double d0  = 0.0D;
	static final double d10 = 1.0D;
	static final double d11 = 1.0D;
	static final double d20 = 2.0D;
	static final double d30 = 1.0000D;
	static final double d31 = 1.0001D;
	static final double d32 = 1.0002D;

	static final double nan = Double.NaN;
	static final double pif = Double.POSITIVE_INFINITY;
	static final double nif = Double.NEGATIVE_INFINITY;
@Test
public void testEquals()
{
	assertTrue(   DoubleHelper.equals( d10, d10, DELTA ) );
	assertTrue(   DoubleHelper.equals( d10, d11, DELTA ) );
	assertTrue( ! DoubleHelper.equals( d10, d0 , DELTA ) );

	assertTrue(   DoubleHelper.equals( d30, d31, DELTA ) );
	assertTrue( ! DoubleHelper.equals( d30, d32, DELTA ) );

	// NaN is not equal to anything
	assertTrue( ! DoubleHelper.equals( d0 , nan, DELTA ) );
	assertTrue( ! DoubleHelper.equals( nan, nan, DELTA ) );

	// Infinities should only be equal to themselves
	assertTrue(   DoubleHelper.equals( pif, pif, DELTA ) );
	assertTrue(   DoubleHelper.equals( nif, nif, DELTA ) );
	assertTrue( ! DoubleHelper.equals( pif, nif, DELTA ) );
	assertTrue( ! DoubleHelper.equals( pif, nan, DELTA ) );
	assertTrue( ! DoubleHelper.equals( nif, nan, DELTA ) );
	assertTrue( ! DoubleHelper.equals( pif, d0 , DELTA ) );
	assertTrue( ! DoubleHelper.equals( nif, d0 , DELTA ) );
	
}
@Test
public void testLessThan()
{
	assertTrue( ! DoubleHelper.lessThan( d10, d10, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( d10, d11, DELTA ) );
	assertTrue(   DoubleHelper.lessThan( d0 , d10, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( d30, d31, DELTA ) );
	assertTrue(   DoubleHelper.lessThan( d30, d32, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( d10, nan, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( nan, d10, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( nan, pif, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( pif, nan, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( nan, nif, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( nif, nan, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( nif, nif, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( pif, pif, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( d10, nif, DELTA ) );
	assertTrue(   DoubleHelper.lessThan( d10, pif, DELTA ) );
	assertTrue(   DoubleHelper.lessThan( nif, pif, DELTA ) );
	assertTrue( ! DoubleHelper.lessThan( pif, nif, DELTA ) );
}
@Test
public void testBetween()
{
	// 0 <= 1 <= 2 
	assertTrue(   DoubleHelper.between( d10, d0, d20, DELTA ) );
	// 1 <= 1 <= 1
	assertTrue(   DoubleHelper.between( d10, d10, d10, DELTA ) );
	// 2 <= 1 <= 1
	assertTrue( ! DoubleHelper.between( d10, d20, d10, DELTA ) );
	// 0 <= 1 <= 0
	assertTrue( ! DoubleHelper.between( d10, d0, d0, DELTA ) );
}
}
