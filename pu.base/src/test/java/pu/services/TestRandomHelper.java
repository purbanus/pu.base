package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @@NOG Veel meer testen!
 * Creation date: (5-7-01 16:00:16)
 * @author: Peter Urbanus
 */
public class TestRandomHelper
{
/**
 * Fills buckets with random ints.
 * @return 
 */
public int [] fillBuckets( int aMin, int aMax, int aLoop )
{
	int [] buckets = new int[ aMax - aMin + 1 ];
	for ( int x = 0; x < aLoop; x++ )
	{
		int rand = RandomHelper.getRandom( aMin, aMax );
		buckets[rand - aMin]++;
	}
	return buckets;
}
@Test
public void testBounds()
{
	final int MIN = 1;
	final int MAX = 5;
	final int LOOP = 100000;

	for ( int x = 0; x < LOOP; x++ )
	{
		int rand = RandomHelper.getRandom( 1, 5 );
		assertTrue( rand >= MIN );
		assertTrue( rand <= MAX );
	}
}
/**
 * Checks whether the calculation in RandomHelper does not cause any sudden
 * drops of precision
 */
@Test
public void testCalculation()
{
	final int MIN = 1;
	final int MAX = 10;
	final int LOOP = 100000;
	
	for ( int x = 0; x < LOOP; x++ )
	{
		// Calculate it in small steps
		double drand = Math.random();
		double drand10 = drand * ( MAX - MIN + 1 );
		int expected = MIN + Double.valueOf( drand10 ).intValue();

		// This is the way it's done in RandomHelper
		int actual = MIN + Double.valueOf( drand * ( MAX - MIN + 1 ) ).intValue();

		// Compare the results
		assertEquals( expected, actual );
		
		// Print out the first 20
		//if ( x < 20 ) 
		//	System.out.println( drand + "\t" + drand10 + "\t" + expected + "\t" + actual );

		// Vroeger rondden we af maar nu niet meer. Ik geloof ook dat dat incorrect was.
		// int random = min + round( Math.random() * ( max - min + 1 ) );
	}
}
@Test
public void tryMeerRandom()
{
	final int MIN = 1;
	final int MAX = 5;
	final int LOOP = 1000;

	// @@NOG Hier test van maken?
	for ( int x = 0; x < 10; x++ )
	{
		int [] buckets = fillBuckets( MIN, MAX, LOOP );
		for ( int b = 0; b < buckets.length; b++ )
		{
			System.out.print( buckets[b] + " " );
		}
		System.out.println();
	}
}
}
