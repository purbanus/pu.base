package pu.services.seq;

//import static org.junit.Assert.*;

import org.junit.Before;
//import org.junit.Test;


/**
 */
public class TestSimpleSequence extends AbstractSimpleSequenceTest
{

AbstractSimpleSequenceTest.SequenceFactory sequenceFactory;

public class OurSequenceFactory extends AbstractSimpleSequenceTest.SequenceFactory
{
@Override
public Sequence createSequence( int aStart, int aIncrement )
{
	return new SimpleSequence( aStart, aIncrement );
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