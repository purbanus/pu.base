package pu.services;

/**
 * A simple stopwatch to time operations. You can query the time elapsed since creation or
 * since <code>reset</code>, whichever came last, with <code>getElapsed</code>, and you can time the
 * individual parts of a series of operations by repeatedly calling <code>getLapTime</code>.
 */
public class StopWatch
{
	private long start;
	private long lap; 
/**
 * Creates a new StopWatch.
 */
public StopWatch()
{
	super();
	reset();
}
/**
 * Returns the elapsed time, in milliseconds, since this StopWatch was created
 * or since <code>reset</code> was called, whichever came last.
 */
public long getElapsed()
{
	return System.currentTimeMillis() - start;
}
/**
 * Returns the elapsed time, in milliseconds, since this StopWatch was created
 * or since <code>reset</code> was called, whichever came last.
 */
public String getElapsedMs()
{
	return getElapsed() + "ms";
}
/**
 * Returns the time, in milliseconds, that has passed since the previous
 * call to <code>getLapTime</code>, <code>reset</code>, or since construction,
 * whichever came last.
 * <p>
 * <code>getLaptime</code> is especially useful to time the parts of an operation
 * that consists of several parts. An example:
 * <pre>
 * 	void anOperation()
 *	{
 *		StopWatch sw = new StopWatch();
 *
 *		doSomething();
 *		System.out.println( "doSomething took " + sw.getLapTime() );
 *		doSomethingElse();
 *		System.out.println( "doSomethingElse took " + sw.getLapTime() );
 *		whileWereAtItDoThisAlso();
 *		System.out.println( "whileWereAtItDoThisAlso took " + sw.getLapTime() );
 *		nowWait_ShouldntWeAlsoDoThat_ButThenAgainWhoCares();
 *		System.out.println( "bozo code took " + sw.getLapTime() );
 *	}
 * </pre>
 */
public long getLapTime()
{
	long prevLap = lap;
	lap = System.currentTimeMillis();
	return lap - prevLap; 
}
public String getLapTimeMs()
{
	return getLapTime() + "ms"; 
}
/**
 * Resets the start time to now, and starts a new lap. This code
 * is equivalent to creating a new StopWatch.
 */
public void reset()
{
	start = System.currentTimeMillis();
	lap = start;
}
}
