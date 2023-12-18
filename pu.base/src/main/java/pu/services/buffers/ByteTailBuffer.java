package pu.services.buffers;



/**
 * A tailbuffer keeps the last n bytes it receives. A ByteTailBuffer starts with a maximum
 * size and an initial size. As it receives elements, it grows until it reaches the
 * maximum size. From that point it only keeps the last n elements it receives, n being
 * the maximum size.
 */
public class ByteTailBuffer extends AbstractTailBuffer
{
	/**
	 * The data
	 */
	private byte [] data;

/**
 * Creates a ByteTailBuffer with the specified maximum size, in bytes.
 * @param   aMaxSize the maximum size.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 */
public ByteTailBuffer( int aMaxSize )
{
	this( aMaxSize, aMaxSize >= DEFAULT_SIZE ? DEFAULT_SIZE : aMaxSize );
}
/**
 * Creates a new ByteTailBuffer with the specified maximum and initial size, in bytes.
 * @param   aMaxSize the maximum size of the buffer.
 * @param   aInitialSize the initial size of the buffer. This number of bytes is immediately allocated.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is greater that aMaxSize.
 */
public ByteTailBuffer( int aMaxSize, int aInitialSize )
{
	super( aMaxSize, aInitialSize );

	data = new byte[aInitialSize];
	if ( ASSERT ) checkInvariants();
}
/**
 * Retrieves the current contents of the buffer
 */
public synchronized byte [] get()
{
	byte [] ret = new byte[size()];

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
 * Puts the specified array of bytes into the buffer.
 */
public synchronized void put( byte [] aBytes )
{
	put( aBytes, 0, aBytes.length );
}
/**
 * Puts bytes into the buffer, starting at <code>aOffset</code>. From that point <code>aLength</code>
 * bytes are copied into the buffer.
 * @param aBytes The array of bytes to copy from
 * @param aOffset The index in the array where copying starts
 * @param aLength The number of bytes to copy
 */
public synchronized void put( byte [] aBytes, int aOffset, int aLength )
{
	// Check offset and length
	// @@NOG Lijkt mij overbodig. Volgens mij komt er sowieso wel een exception
	if ((aOffset < 0) || (aOffset > aBytes.length) || (aLength < 0) || ((aOffset + aLength) > aBytes.length) || ((aOffset + aLength) < 0))
	{
		throw new IndexOutOfBoundsException();
	}

	// Zero length insertion
	if ( aLength == 0 )
	{
		return;
	}
	
	final int maxSize = getMaxSize();
	final int size = size();
	final int head = getHead();
	
	// Special case: the incoming bytes are larger than maxsize
	if ( aLength >= maxSize )
	{
		if ( data.length < maxSize )
		{
			data = new byte[maxSize];
		}
		System.arraycopy( aBytes, aLength - maxSize, data, 0, maxSize );
		setSize( maxSize );
		setHead( 0 );
		checkInvariants();
		return;
	}

	// Make room
	int newSize = Math.min( maxSize, size + aLength );
	if ( newSize > data.length )
	{
		int newLen = Math.max( newSize, data.length * 2 );
		if ( newLen > maxSize )
		{
			newLen = maxSize;
		}
		byte [] newData = new byte[newLen];
		System.arraycopy( get(), 0, newData, 0, size );
		data = newData;
		setHead( size );
	}
	
	// The first piece goes between head and end, the second piece at the beginning
	int firstPiece = Math.min( aLength, data.length - head );
	System.arraycopy( aBytes, aOffset, data, head, firstPiece );
	int secondPiece = aLength - firstPiece;
	if ( secondPiece <= 0 )
	{
		setHead( getHead() + firstPiece );
	}
	else
	{
		setHead( secondPiece );
		System.arraycopy( aBytes, aOffset + firstPiece, data, 0, secondPiece );
	}
	setSize( newSize );
	checkInvariants();
}
/**
 * Puts a single byte into the buffer.
 */
public synchronized void put( byte aByte )
{
	put( new byte[] { aByte }, 0, 1 );
}
}
