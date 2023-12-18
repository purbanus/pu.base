package pu.services.seq;

import static org.junit.Assert.*;
import org.junit.Test;

import pu.services.Range;

/**
 * De simpele gevallen zijn al getest met TestSimpleRangeSequence:
 * <ul>
 * <li>1, 2, 3, ...
 * <li>10, 20, 30, ...
 * <li>101, 102, 103, ...
 * <li>Plus die gevallen waarbij de range 2 elementen bevat zodat er een tweede gemaakt
 * moet worden voor de resterende twee gevallen.
 * </ul>
 * Wat blijft er over?
 * <ul>
 * <li>Het geval dat de range op is, en de nieuwe range niet aansluit op de oude.
 * <li>Als limithandler weigert een nieuwe range te produceren
 * </ul>
 */
public class TestRangeSequence
{
String testName;

LimitHandler plus1000LimitHandler = new LimitHandler()
{
	@Override
	public boolean hasNext()
	{
		return true;
	}
	@Override
	public Range newRange( String aSequenceId, int aOldEnd )
	{
		return new Range( aOldEnd + 1000, aOldEnd + 1001 );
	}
};

@Test
public void testDisjunctRange()
{
	testName = "DisjunctRange";
	Range startRange = new Range( 1, 2 );
	Sequence seq = new RangeSequence( plus1000LimitHandler, "pu", startRange );

	int [] expected = { 1, 2, 1002, 1003 };

	for ( int x = 0; x < expected.length; x++ )
	{
		assertEquals( testName + "#" + x, expected[x], seq.next() );
	}
}

public void testLimitExceeded()
{
	testName = "LimitExceeded";
	LimitHandler limitHandler = LimitHandler.NO_NEW_RANGE;
	Range startRange = new Range( 1, 2 );
	Sequence seq = new RangeSequence( limitHandler, "pu", startRange );

	int [] expected = { 1, 2, 1002, 1003 };

	assertEquals( testName + "#" + 0, expected[0], seq.next() );
	assertEquals( testName + "#" + 1, expected[1], seq.next() );
	try
	{
		seq.next();
		fail( testName + "#" + 2 + " Should have raised a RuntimeException" );
	}
	catch ( RuntimeException good )
	{
	}
}

/**
 * We testen de situatie die je bij JdbcLimitHandlers hebt: je begint met een RangeSequence die een
 * <code>null</code> Range heeft en zodra je next() doet haalt hij een nieuwe Range uit de database op.
 * Maar dan moet je wel de eerste waarde van die range overnemen, en dat was een buggie!
 */
public void testStartValue()
{
	testName = "StartValue";

	Sequence seq = new RangeSequence( plus1000LimitHandler, "pu" );

	int [] expected = { 1000, 1001 };

	assertEquals( testName + "#" + 0, expected[0], seq.next() );
	assertEquals( testName + "#" + 1, expected[1], seq.next() );
}
/****************
public void testEmptyRange()
{
	testName = "EmptyRange";
	LimitHandler limitHandler = LimitHandler.NO_NEW_RANGE;
	Range startRange = RangeSequence.EMPTY_RANGE;
	Sequence seq = new RangeSequence( startRange, limitHandler );

	assertTrue( testName + " hasNext", ! seq.hasNext() );

	try
	{
		seq.next();
		fail( testName + "#" + 2 + " Should have raised a RuntimeException" );
	}
	catch ( RuntimeException good )
	{
	}
}
 ****************/
}