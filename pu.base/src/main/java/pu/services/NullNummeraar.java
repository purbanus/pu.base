package pu.services;

/**
 * Nummeraar die een runtime exception gooit als je om een nieuw nummer vraagt
 */
public class NullNummeraar implements Nummeraar
{
/**
 * NullNummeraar constructor comment.
 */
public NullNummeraar()
{
	super();
}
/**
 * Always returns false
 */
@Override
public boolean hasNext()
{
	return false;
}
/**
 * Always throws a RuntimeException
 */
@Override
public int nextNumber()
{
	throw new RuntimeException( "nextNumber may not be called" );
}
}
