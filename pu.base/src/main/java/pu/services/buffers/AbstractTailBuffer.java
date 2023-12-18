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
public abstract class AbstractTailBuffer // TODO implements Serializable
{
	/**
	 * Whether assertion checking is on.
	 * Since this slows it down greatly, only turn it on when testing.
	 */
	public static final boolean ASSERT = true;
	
	/**
	 * The default initial size of the buffer
	 */
	public static final int DEFAULT_SIZE = 1000;
	
	/**
	 * The maximum size of the buffer. If the buffer reaches this size, it will no longer grow.
	 */
	private final int maxSize;

	/**
	 * The current number of bytes in the buffer
	 */
	private int size;

	/**
	 * The current head of the buffer, where the next insertion will go.
	 */
	private int head = 0;

/**
 * Creates a TailBuffer with the specified maximum size, in bytes
 * @param   aMaxSize the maximum size.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 */
public AbstractTailBuffer( int aMaxSize )
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
public AbstractTailBuffer( int aMaxSize, int aInitialSize )
{
	super();
	if ( aMaxSize <= 0 )
	{
		throw new IllegalArgumentException( "MaxSize should be greater than 0, it is " + aMaxSize );
	}
	if ( aInitialSize <= 0 )
	{
		throw new IllegalArgumentException( "InitialSize should be greater than 0, it is " + aInitialSize );
	}
	if ( aInitialSize > aMaxSize )
	{
		throw new IllegalArgumentException( "InitialSize " + aInitialSize + " cannot be greater than MaxSize " + aMaxSize );
	}
		
	maxSize = aMaxSize;
	size = 0;
}
/**
 * Checks the invariants of the buffer
 */
protected void checkInvariants()
{
	if ( ASSERT )
	{
		if ( size > maxSize )
		{
			throw new RuntimeException( "Size may not be larger than maxSize: " + size + "<=" + maxSize );
		}
		if ( size < 0 )
		{
			throw new RuntimeException( "Size may not be negative: " + size );
		}
	}
}
/**
 * Clears the buffer, setting its size to zero.
 */
public synchronized void clear()
{
	head = 0;
	size = 0;
}
/**
 * @return The current head of the buffer (where the next insertion would go)
 */
protected synchronized int getHead()
{
	return head;
}
/**
 * Returns the maximum size of the buffer. If the buffer reaches this size it will no
 * longer grow and when new data arrives it will discard the oldest data,
 */
public synchronized int getMaxSize()
{
	return maxSize;
}
/**
 * Returns whether the buffer is empty.
 */
public synchronized boolean isEmpty()
{
	return size == 0;
}
/**
 * Returns whether the buffer is full, that is whether it has reached its maximum size.
 * Full buffers can still recieve data; the oldest data it contains is simply discarded
 * to make room for new data.
 */
public synchronized boolean isFull()
{
	return size == maxSize;
}
/**
 * @param i
 */
protected synchronized  void setHead( int i )
{
	head = i;
}

/**
 * @param i
 */
protected synchronized void setSize(int i)
{
	size = i;
}
/**
 * This method was created in VisualAge.
 * @return int
 */
public synchronized int size()
{
	return size;
}
}
