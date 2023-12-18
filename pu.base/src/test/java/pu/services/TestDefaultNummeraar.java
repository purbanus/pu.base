package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @@NOG Veel meer testen!
 * Creation date: (5-7-01 16:00:16)
 * @author: Peter Urbanus
 */
public class TestDefaultNummeraar
{
@Test
public void testBasics()
{
	// 1, 2, 3, ...
	Nummeraar nummeraar1 = new DefaultNummeraar();
	for ( int x = 0; x < 10; x++ )
	{
		assertEquals( " default constructor#" + x, x + 1, nummeraar1.nextNumber() );
	}

	// 5, 10, 15, ...
	Nummeraar nummeraar5 = new DefaultNummeraar( 5 );
	for ( int x = 0; x < 10; x++ )
	{
		assertEquals( " one-arg constructor#" + x, 5 * ( x + 1 ), nummeraar5.nextNumber() );
	}

	// 400, 404, 408, ...
	Nummeraar nummeraar4 = new DefaultNummeraar( 4, 400 );
	for ( int x = 100; x < 110; x++ )
	{
		assertEquals( " two-arg constructor#" + x, 4 * x, nummeraar4.nextNumber() );
	}
}
}
