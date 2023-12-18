package pu.services;

import java.util.Iterator;

public abstract class SyncLoop<T, U>
{

	private final Iterator<T> masterIterator;
	private final Iterator<U> slaveIterator;

	private final T MASTERSENTINEL = createMasterSentinel();
	private final U SLAVESENTINEL = createSlaveSentinel();
/**
 * Creates a new SyncLoop.
 */
public SyncLoop( Iterator<T> aMaster, Iterator<U> aSlave )
{
	super();

	masterIterator = aMaster;
	slaveIterator  = aSlave;
}

/**
 * Compares two elements. A SENTINEL is considered greater than any
 * element. If none of the elements is a SENTINEL, then the abstract
 * <code>compare</code> method is  called.
 * @return The result of  the comparison
 * @param L One element
 * @param R The other element
 */
private int doCompare( T L, U R )
{
	if ( L == MASTERSENTINEL )
	{
		return R == SLAVESENTINEL ? 0 : 1;
	}
	if ( R == SLAVESENTINEL )
	{
		return -1;
	}
	return compare( L, R );
}

/**
 * @return The master iterator
 */
private Iterator<T> getMasterIterator()
{
	return masterIterator;
}
/**
 * @return The slave iterator
 */
private final Iterator<U> getSlaveIterator()
{
	return slaveIterator;
}

/**
 * Gets the next element from an iterator. If the iterator is empty, the
 * SENTINEL is returned
 * @param The next element of the iterator, or a SENTINEL
 */
private T nextMaster( Iterator<T> it )
{
	return it.hasNext() ? it.next() : MASTERSENTINEL;
}
/**
 * Gets the next element from an iterator. If the iterator is empty, the
 * SENTINEL is returned
 * @param The next element of the iterator, or a SENTINEL
 */
private U nextSlave( Iterator<U> it )
{
	return it.hasNext() ? it.next() : SLAVESENTINEL;
}

/**
 * Runs the syncloop
 * throws Exception
 */
public void run() throws Exception
{
	T master = nextMaster( getMasterIterator() );
	U slave  = nextSlave( getSlaveIterator() );
	while ( master != MASTERSENTINEL || slave != SLAVESENTINEL )
	{
		int compare = doCompare( master, slave );
		if ( compare < 0 )
		{
			add( master );
			master = nextMaster( getMasterIterator() );
		}
		else if ( compare > 0 )
		{
			delete( slave );
			slave = nextSlave( getSlaveIterator() );
		}
		else
		{
			update( master, slave );
			master = nextMaster( getMasterIterator() );
			slave  = nextSlave( getSlaveIterator() );
		}
	}
}

////////////////////////////////////////////////////////////////////////
// Abstract methods

/**
 * Creates a Sentinel object of the required type
 */
protected abstract T createMasterSentinel();
protected abstract U createSlaveSentinel();

/**
 * Compares two elements. You only have to do a simple comparison as in
 * a <code>Comparable</code>
 */
protected abstract int compare( T aMaster, U aSlave );

/**
 * This method is called if the master iterator contains an element that
 * the slave iterator does not. Consequently, it should be added to whatever
 * collection represents the slave elements.
 * <p>
 * @throws Exception
 */
protected abstract void add( T aMaster ) throws Exception;

/**
 * This method is called if the master iterator does not contain an element that
 * the slave iterator does. Consequently, it should be deleted from whatever
 * collection represents the slave elements.
 * <p>
 * @throws Exception
 */
protected abstract void delete( U aSlave ) throws Exception;

/**
 * This method is called if the master iterator and the slave iterator contain the
 * same element.
 * @throws Exception
 */
protected abstract void update( T aMaster, U aSlave ) throws Exception;
}
