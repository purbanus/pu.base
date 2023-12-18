package pu.services.seq;

import pu.services.Range;


/**
 * A Sequence that doles out its numbers from a range with a specific
 * start and en number. What happens when the end is reached, is determined
 * by a LimitHandler strategy. There are two obvious possibilities:
 * <ul>
 * <li> The limit is absolute, and attempts top obtain a next number cause some
 *      exception to be thrown. This is useful if you know the exact amount of
 *      numbers that you need in advance, and receiving an exception when the
 *      limit is exceeded is welcome, since that is probably a bug.
 * <li> The LimitHandler obtains a new range in some way, and the
 *      RangeSequence proceeds as if nothing happened.
 * </ul>
 */
public class RangeSequence implements Sequence
{
private final LimitHandler limitHandler;
private final String sequenceId;
private final int increment;

private Range currentRange = null;
private int lastNumber = 0;

/**
 * Creates a new RangeSequence with an invalid range, and a limitHandler that will hopefully
 * solve that
 */
public RangeSequence( LimitHandler aLimitHandler, String aSequenceId )
{
	this( aLimitHandler, aSequenceId, Range.EMPTY_RANGE, 1 );
}
/**
 * Creates a new RangeSequence with the specified start and end values, and a limitHandler
 */
public RangeSequence( LimitHandler aLimitHandler, String aSequenceId, Range aRange )
{
	this( aLimitHandler, aSequenceId, aRange, 1 );
}

/**
 * This method was created in VisualAge.
 * @param increment int
 */
public RangeSequence( LimitHandler aLimitHandler, String aSequenceId, Range aRange, int aIncrement )
{
	super();
	limitHandler = aLimitHandler;
	sequenceId = aSequenceId;
	increment = aIncrement;
	currentRange = aRange;
	lastNumber = currentRange.from - increment;
}

private String getSequenceId()
{
	return sequenceId;
}

@Override
public boolean hasNext()
{
	return lastNumber + increment <= currentRange.to ? true : limitHandler.hasNext();
}
/**
 * This method was created in VisualAge.
 * @return int
 */
// LOW Kijken of we een kleiner deel synchronized kunnen maken
@Override
public synchronized int next()
{
	int newNumber = lastNumber + increment;
	if ( newNumber > currentRange.to )
	{
		// LimitHandler MUST throw a RuntimeException when no new range is available
		int oldEnd = currentRange.to;
		currentRange = limitHandler.newRange( getSequenceId(), currentRange.to );
		lastNumber = currentRange.from - increment;

		// Calculate newNumber again because we might be in a totally different range now.
		newNumber = lastNumber + increment;

		// LimitHandler should make newEnd big enough for newNumber
		// In practice it should be MUCH larger
		if ( newNumber > currentRange.to )
		{
			throw new RuntimeException( "LimitHandler produced too small an end: oldEnd=" + oldEnd + " newEnd=" + currentRange.to );
		}

	}
	lastNumber = newNumber;
	return lastNumber;
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
@Override
public String toString()
{
	return "RangeSequence: lastNumber=" + lastNumber + " end=" + currentRange.to + " limitHandler has next: " + limitHandler.hasNext();
}
}

