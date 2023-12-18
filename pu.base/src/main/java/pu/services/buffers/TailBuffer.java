package pu.services.buffers;



/**
 * A ByteTailBuffer is a buffer that remembers the last N items that were added to it.
 * This implies that you can't remove something from a ByteTailBuffer, you can only query
 * it for the last N elements added to it. You can see a ByteTailBuffer as a window upon
 * a stream of elements showing the last N of them.
 * <p>
 * A TailBuffer is a ByteTailBuffer that arranges the elements it buffers in a
 * circle, keeping pointers to the current head (latest insertion) and tail (oldest insertion). 
 * This arrangement is probably the most efficient way to run a ByteTailBuffer; all it needs
 * to do is to maintain head- and tail-pointers that continuously run in circles as new data arrives.
 * <p>
 * This implementation starts the buffer with an initial size, and it can grow to a maximum
 * size when it fills up. Before the maximum size is reached, the buffer shows ALL the elements
 * that have been added to it; as soon as the maximum size has been reached and another 
 * element comes in, the oldest element is discarded.
 * <p>
 * This implementation is specialized for <code>byte</code> elements.
 */
public class TailBuffer extends AbstractTailBuffer
{
	/**
	 * The data
	 */
	private Object [] data;

/**
 * Creates a TailBuffer with the specified maximum size, in bytes
 * @param   aMaxSize the maximum size.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 */
public TailBuffer( int aMaxSize )
{
	this( aMaxSize, aMaxSize >= DEFAULT_SIZE ? DEFAULT_SIZE : aMaxSize );
}
/**
 * Creates a TailBuffer with the specified maximum size and initialSize, in bytes.
 * @param   aMaxSize the maximum size of the buffer.
 * @param   aInitialSize the initial size of the buffer. This number of bytes is immediately allocated.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is greater that aMaxSize.

 */
public TailBuffer( int aMaxSize, int aInitialSize )
{
	super( aMaxSize, aInitialSize );

	data = new Object[aInitialSize];
	if ( ASSERT ) checkInvariants();
}
/**
 * Clears the buffer, setting its size to zero.
 */
@Override
public synchronized void clear()
{
	super.clear();
	for ( int x = 0; x < data.length; x++ )
	{
		data[x] = null;
	}
}

/**
 * @return The current contents of the ByteTailBuffer
 */
public synchronized Object [] get()
{
	Object [] ret = new Object[size()];

	int atEnd = size() - getHead();
	if ( atEnd > 0 )
	{
		System.arraycopy( data, data.length - atEnd, ret, 0, atEnd );
	}
	else
	{
		atEnd = 0;
	}
	int start = Math.max( 0, getHead() - size() );
	System.arraycopy( data, start, ret, atEnd, getHead() - start );
	
	return ret;
}
/**
 * Puts bytes into the buffer, starting at <code>aOffset</code>. From that point <code>aLength</code>
 * bytes are copied into the buffer.
 * @param aBytes The array of bytes to copy from
 * @param aOffset The index in the array where copying starts
 * @param aLength The number of bytes to copy
 */
public synchronized void put( Object aObject )
{
	final int max = getMaxSize();
	final int siz = size();
	
	if ( siz < max )
	{
		// Make room
		int newSize = Math.min( max, siz + 1 );
		if ( newSize > data.length )
		{
			int newLen = Math.max( newSize, data.length * 2 );
			if ( newLen > max )
			{
				newLen = max;
			}
			Object [] newData = new Object[newLen];
			System.arraycopy( get(), 0, newData, 0, siz );
			data = newData;
			setHead( siz );
		}
		setSize( newSize );
	}
	// Insert the new one
	data[getHead()] = aObject;
	setHead( ( getHead() + 1 ) % data.length );
	
	checkInvariants();
}

}
