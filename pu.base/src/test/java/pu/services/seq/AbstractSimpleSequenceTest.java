package pu.services.seq;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Abstracte testclass voor simpele sequence-tests. Als je een ruime Range neemt namelijk,
 * is een RangeSequence niet anders dan een SimpleSequence. Dus testen we de eenvoudige
 * methodes van die twee in een keer door. Het verschil geef je aan met een SequenceFactory.
 */
public abstract class AbstractSimpleSequenceTest
{
public abstract class SequenceFactory
{
/**
 * Creates a sequence that starts at 1 with an increment of 1.
 */
public Sequence createSequence()
{
	return createSequence( 1, 1 );
}

/**
 * Creates a sequence that starts at <code>aStart</code> with an increment of 1.
 */
public Sequence createSequence( int aStart )
{
	return createSequence( aStart, 1 );
}

/**
 * Creates a sequence that starts at <code>aStart</code> with an increment of <code>aIncrement</code>.
 */
public abstract Sequence createSequence( int aStart, int aIncrement );
}

protected abstract SequenceFactory getSequenceFactory();

/**
 * Tests sequence with increment 1
 */
protected void checkSequence( String aTestName, Sequence aSequence, int aStart, int aIncrement, int aNumTests )
{
	int cur = aStart;
	for ( int x = 0; x < aNumTests; x++ )
	{
		assertTrue  ( aTestName + "#" + x + " hasnext", aSequence.hasNext() );
		assertEquals( aTestName + "#" + x, cur, aSequence.next() );
		cur += aIncrement;
	}
}

/**
 * Tests sequence with empty constructor
 */
@Test
public void testEmptyConstructor()
{
	String testName = "EmptyConstructor";

	final int NUMTESTS = 4;

	Sequence seq = getSequenceFactory().createSequence();
	checkSequence( testName, seq, 1, 1, NUMTESTS );
}

/**
 * Tests sequence with (start) constructor
 */
@Test
public void testStartConstructor()
{
	String testName = "StartConstructor";

	final int NUMTESTS = 4;
	int [] start = new int [] { 1, 10, 100 };

	for ( int s = 0; s < start.length; s++ )
	{
		Sequence seq = getSequenceFactory().createSequence( start[s] );
		checkSequence( testName + "#" + s, seq, start[s], 1, NUMTESTS );
	}
}

/**
 * Tests sequence with (start,increment) constructor
 */
@Test
public void testFullConstructor()
{
	String testName = "FullConstructor";

	final int NUMTESTS = 4;
	int [] start = new int [] { 1, 10, 100 };
	int [] increment = new int [] { 1, 5, 10 };

	for ( int s = 0; s < start.length; s++ )
	{
		for ( int i = 0; i < increment.length; i++ )
		{
			Sequence seq = getSequenceFactory().createSequence( start[s], increment[i] );
			checkSequence( testName + "#" + s + "," + i, seq, start[s], increment[i], NUMTESTS );
		}
	}
}
}