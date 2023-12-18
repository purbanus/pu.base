package pu.services.seq;

import pu.services.Range;

//import static org.junit.Assert.*;

import org.junit.Before;
//import org.junit.Test;

/**
 */
public class TestSimpleRangeSequence extends AbstractSimpleSequenceTest
{
AbstractSimpleSequenceTest.SequenceFactory sequenceFactory;

private class OurSequenceFactory extends AbstractSimpleSequenceTest.SequenceFactory
{
private static final int LIMIT = 1000000; // erg ruim
@Override
public Sequence createSequence( int aStart, int aIncrement )
{
	final Range range = new Range( aStart, LIMIT );
	return new RangeSequence( LimitHandler.NO_NEW_RANGE, "pu", range, aIncrement );
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