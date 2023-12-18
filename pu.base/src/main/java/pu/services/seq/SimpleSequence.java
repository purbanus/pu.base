package pu.services.seq;

/**
 * This type was created in VisualAge.
 */
public class SimpleSequence implements Sequence
{
private final int increment;
private int number = 0;
/**
 * Nummeraar constructor comment.
 */
public SimpleSequence()
{
	this( 1 );
}
/**
 * This method was created in VisualAge.
 * @param increment int
 */
public SimpleSequence( int aStart )
{
	this( aStart, 1 );
}
/**
 * This method was created in VisualAge.
 * @param increment int
 */
public SimpleSequence( int aStart, int aIncrement )
{
	super();
	increment = aIncrement;
	number = aStart - aIncrement;
}
/**
 * This method was created in VisualAge.
 * @return int
 */
public final int getIncrement()
{
	return increment;
}
@Override
public boolean hasNext()
{
	return true;
}
/**
 * This method was created in VisualAge.
 * @return int
 */
// Als je deze niet synchronized maakt kunnen twee threads hetzelfde nummer krijgen!
@Override
public synchronized int next()
{
	number += increment;
	return number;
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
@Override
public String toString()
{
	return String.valueOf( number );
}
}
