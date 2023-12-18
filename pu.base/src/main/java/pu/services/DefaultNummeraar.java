package pu.services;

/**
 * This type was created in VisualAge.
 */
public class DefaultNummeraar implements Nummeraar
{
	private final int increment;
	private int nummer = 0;
/**
 * Nummeraar constructor comment.
 */
public DefaultNummeraar()
{
	this( 1, 1 );
}
/**
 * This method was created in VisualAge.
 * @param increment int
 */
public DefaultNummeraar( int aIncrement )
{
	this( aIncrement, aIncrement );
}
/**
 * This method was created in VisualAge.
 * @param increment int
 */
public DefaultNummeraar( int aIncrement, int aStartValue )
{
	super();
	increment = aIncrement;
	nummer = aStartValue - aIncrement;
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
@Override
public int nextNumber()
{
	nummer += increment;
	return nummer;
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
@Override
public String toString()
{
	return String.valueOf( nummer );
}
}
