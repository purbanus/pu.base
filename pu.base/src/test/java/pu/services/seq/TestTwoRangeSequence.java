package pu.services.seq;

import org.junit.Before;

import pu.services.Range;

public class TestTwoRangeSequence extends AbstractSimpleSequenceTest
{
AbstractSimpleSequenceTest.SequenceFactory sequenceFactory;

private class SneakyLimitHandler implements LimitHandler
{
private final int increment;
public SneakyLimitHandler( int aIncrement )
{
	super();
	increment = aIncrement;
}

@Override
public boolean hasNext()
{
	return true;
}
@Override
public Range newRange( String aSequenceId, int aEnd )
{
	// Voor deze tests gaan we steeds op de helft over naar het tweede blok
	// Aangezien we steeds 4 waardes genereren nemen we de resterende twee erbij
	// Voorbeeld 1
	//  We moeten 1, 2, 3, 4 genereren. De eerste range was 1-2, nu moeten we 3-4 retourneren
	// Voorbeeld 2
	//  We moeten 10, 20, 30, 40 genereren. De eerste range was 10-20 en we hebben nu meerdere mogelijkheden.
	//  Als de start maar tussen 21 en 30 ligt en het einde >= 40 is, maakt her verder niet uit.
	return new Range( aEnd + increment, aEnd + 2 * increment );
}
}

private class OurSequenceFactory extends AbstractSimpleSequenceTest.SequenceFactory
{
/**********
		public Sequence createSequence() // start = 1, increment = 1
		{
			return createSequence( 1, 1 );
			//final LimitHandler limitHandler = new SneakyLimitHandler( 1 );
			//final RangeSequence.Range range = new RangeSequence.Range( 1, NUM_TESTS_2 );
			//return new RangeSequence( range, 1, limitHandler );
		}
		public Sequence createSequence( int aStart )
		{
			return createSequence( aStart, 1 );
			//final LimitHandler limitHandler = new SneakyLimitHandler( 1 );
			//final RangeSequence.Range range = new RangeSequence.Range( aStart, NUM_TESTS_2 );
			//return new RangeSequence( range, 1, limitHandler );
		}
 ****************/
@Override
public Sequence createSequence( int aStart, int aIncrement )
{
	final LimitHandler limitHandler = new SneakyLimitHandler( aIncrement );
	final Range range = new Range( aStart, aStart + aIncrement );
	return new RangeSequence( limitHandler, "pu", range, aIncrement );
}
}

@Before
public void setUp() throws Exception
{
	sequenceFactory = new OurSequenceFactory();
}

/* (non-Javadoc)
 * @see pu.da.seq.test.AbstractSimpleSequenceTest#getSequenceFactory()
 */
@Override
protected SequenceFactory getSequenceFactory()
{
	return sequenceFactory;
}

}