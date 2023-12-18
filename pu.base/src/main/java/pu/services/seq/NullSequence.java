package pu.services.seq;

/**
 * Sequence die een runtime exception gooit als je om een nieuw nummer vraagt
 */
public class NullSequence implements Sequence
{
/**
 * NullNummeraar constructor comment.
 */
public NullSequence()
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
public int next()
{
	throw new RuntimeException( "nextNumber may not be called" );
}
}
