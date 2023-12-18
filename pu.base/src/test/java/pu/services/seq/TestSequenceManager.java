package pu.services.seq;

import static org.junit.Assert.*;
import org.junit.Test;

import pu.services.Range;

/**
 */
public class TestSequenceManager
{
String testName;
@Test
public void testBasics()
{
	testName = "basics";
	final LimitHandler limitHandler = new LimitHandler()
	{
		@Override
		public boolean hasNext()
		{
			return true;
		}
		@Override
		public Range newRange( String aSequenceId, int aOldEnd )
		{
			return new Range( aOldEnd + 1, aOldEnd + 4 );
		}
	};
	SequenceManager sequenceManager = new SequenceManager( limitHandler );
	int [] expected = { 1, 2, 3, 4, 5, 6, 7, 8 };
	for ( int x = 0; x < expected.length; x++ )
	{
		assertEquals( testName + "#a" + x, expected[x], sequenceManager.next( "a" ) );
		assertEquals( testName + "#b" + x, expected[x], sequenceManager.next( "b" ) );
	}
	assertEquals( testName + "#c", 1, sequenceManager.next( "c" ) );
}

}