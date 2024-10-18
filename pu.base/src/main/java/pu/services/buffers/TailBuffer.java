package pu.services.buffers;

import java.lang.reflect.Array;

/**
 * A TailBuffer is a buffer that remembers the last N items that were added to it.
 * This implies that you can't remove something from a TailBuffer, you can only query
 * it for the last N elements added to it. You can see a TailBuffer as a window upon
 * a stream of elements showing the last N of them.
 * <p>
 * A TailBuffer is a buffer that arranges the elements it buffers in a
 * circle, keeping pointers to the current head (latest insertion) and tail (oldest insertion). 
 * This arrangement is probably the most efficient way to run a TailBuffer; all it needs
 * to do is to maintain head- and tail-pointers that continuously run in circles as new data arrives.
 * <p>
 * This implementation starts the buffer with an initial size, and it can grow to a maximum
 * size when it fills up. Before the maximum size is reached, the buffer shows ALL the elements
 * that have been added to it; as soon as the maximum size has been reached and another 
 * element comes in, the oldest element is discarded.
 */
public class TailBuffer <T> extends AbstractTailBuffer
{
/**
 * The default maximum size
 */
public static final int DEFAULT_MAX = 10000;

	/**
	 * The data
	 */
	private T [] data;

/**
 * Creates a TailBuffer with the default maximum size
 * @param   aMaxSize the maximum size.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 */
public TailBuffer( Class<T> aTypeSpec )
{
	this( aTypeSpec, DEFAULT_MAX );
}
/**
 * Creates a TailBuffer with the specified maximum size
 * @param   aMaxSize the maximum size.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 */
public TailBuffer( Class<T> aTypeSpec, int aMaxSize )
{
	this( aTypeSpec, aMaxSize, aMaxSize >= DEFAULT_SIZE ? DEFAULT_SIZE : aMaxSize );
}

/**
 * Creates a TailBuffer with the specified maximum size and initialSize.
 * @param   aMaxSize the maximum size of the buffer.
 * @param   aInitialSize the initial size of the buffer. This number of items is immediately allocated.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is greater that aMaxSize.

 */
@SuppressWarnings( "unchecked" )
public TailBuffer( Class<T> aTypeSpec, int aMaxSize, int aInitialSize )
{
	super( aMaxSize, aInitialSize );
    data = (T []) Array.newInstance( aTypeSpec, aInitialSize );
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
public synchronized T [] get()
{
	@SuppressWarnings( "unchecked" )
	T [] ret = (T []) Array.newInstance( data.getClass().getComponentType(), size() );

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
 * Puts an element into the buffer.
 * @param aObject The element to be put in the buffer
 */
public synchronized void put( T aObject )
{
	final int max = getMaxSize();
	final int size = size();
	
	if ( size < max )
	{
		// Make room
		int newSize = Math.min( max, size + 1 );
		if ( newSize > data.length )
		{
			int newLen = Math.max( newSize, data.length * 2 );
			if ( newLen > max )
			{
				newLen = max;
			}
			@SuppressWarnings( "unchecked" )
			T [] newData = (T []) Array.newInstance( data.getClass().getComponentType(), newLen );
			System.arraycopy( get(), 0, newData, 0, size );
			data = newData;
			setHead( size );
		}
		setSize( newSize );
	}
	// Insert the new one
	data[getHead()] = aObject;
	setHead( ( getHead() + 1 ) % data.length );
	
	checkInvariants();
}

}
