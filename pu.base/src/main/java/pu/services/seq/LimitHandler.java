/*
 * Created on 13-apr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.services.seq;

import pu.services.Range;



public interface LimitHandler
{
public static final LimitHandler NO_NEW_RANGE = new LimitHandler()
{
	@Override
	public Range newRange( String aSequenceId, int aEnd )
	{
		throw new RuntimeException( "Limit reached: " + aEnd );
	}
	@Override
	public boolean hasNext()
	{
		return false;
	}
};

/**
 * Called when the limit of a RangeSequence is reached. If no new end value is available,
 * the LimitHandler MUST throw a RuntimeException to indicate this. Otherwise, the
 * new Range is returned.
 * @param aEnd The old end value
 * @return The new Range
 * @throws RuntimeException if no new end value is available
 */
public abstract Range newRange( String aSequenceId, int aOldEnd );

/**
 * @return Whether a call to <code>handleLimit</code> will normally produce a new Range.
 */
public abstract boolean hasNext();
}