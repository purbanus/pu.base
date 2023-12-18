package pu.services.comm.cradle;

import java.io.Serializable;

/**
 * A bounded queue that tries to re-use its memory efficiently.
 * A CircularByteQueue starts with an initial capacity and when it fills up,
 * grows to a certain maximum capacity. After that, it throws a RuntimeException
 * if you try to add more to the queue.
 */
public class CircularByteQueue implements Serializable
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
 * The data
 */
private byte [] data;

/**
 * The current number of bytes in the buffer
 */
private int size;

/**
 * The current head of the buffer. head and tail have the following meaning:
 * <ul>
 * <li>head is the place where the next <code>put</code> would put a byte.
 * <li>tail is the place where <code>get</code> would read the next byte
 * <li>if tail == -1, the buffer is empty
 * <li>if head == tail, the buffer is full and old entries will be overwritten
 * </ul>
 */
private int head = 0;

/**
 * The current head of the buffer. head and tail have the following meaning:
 * <ul>
 * <li>head is the place where the next <code>put</code> would put a byte.
 * <li>tail is the place where <code>get</code> would read the next byte
 * <li>if tail == -1, the buffer is empty
 * <li>if head == tail, the buffer is full and old entries will be overwritten
 * </ul>
 */
private int tail = -1;

/**
 * Creates a TailBuffer with the specified maximum size, in bytes
 * @param   aMaxSize the maximum size.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 */
public CircularByteQueue( int aMaxSize )
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
public CircularByteQueue( int aMaxSize, int aInitialSize )
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
	data = new byte[aInitialSize];
	if ( ASSERT )
	{
		checkInvariants();
	}
}
/**
 * This method was created in VisualAge.
 */
private int advanceHead()
{
	// Zet head eentje verder. Als de queue leeg was, geef tail nu een geldig startpunt.
	int ret = head;
	if ( tail == -1 )
	{
		tail = head;
	}
	head = ++head % data.length;
	size++;
	if ( ASSERT )
	{
		checkInvariants();
	}
	return ret;
}
/**
 * This method was created in VisualAge.
 */
private int advanceTail()
{
	// Zet tail eentje verder. Als de queue leeg wordt, geef tail nu een ongeldig startpunt (-1).
	int ret = tail;
	tail = ++tail % data.length;
	if ( head == tail )
	{
		tail = -1;
	}
	size--;
	if ( ASSERT )
	{
		checkInvariants();
	}
	return ret;
}
/**
 * Checks the invariants of the buffer
 */
private void checkInvariants()
{
	if ( ASSERT )
	{
		if ( size > maxSize )
		{
			throw new RuntimeException( "Size may not be larger than maxSize: " + size + "<=" + maxSize );
		}
		if ( size != checkSize() )
		{
			throw new RuntimeException( "Size and checkSize do not match (" + size + "!=" + checkSize() + ")" );
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (9-11-2002 21:45:29)
 */
private int checkSize()
{
	if ( isEmpty() )
	{
		return 0;
	}
	if ( head > tail )
	{
		return head - tail;
	}
	return head + data.length - tail;
}
/**
 * Clears the buffer, setting its size to zero.
 */
public void clear()
{
	// @@NOG Als je dit ding weer opppakt: dit is niet getest
	head = 0;
	tail = -1;
	size = 0;
}
/**
 * This method was created in VisualAge.
 * @return java.lang.Object
 */
public synchronized byte get() // throws Exception
{
	if ( isEmpty() )
	{
		throw new RuntimeException( "Buffer is empty" );
	}
	return data[advanceTail()];
}
/**
 * Returns the maximum size of the buffer. If the buffer reaches this size it will no
 * longer grow and when new data arrives it will doscard the oldest data,
 */
public synchronized int getMaxSize()
{
	return maxSize;
}
/**
 * Returns the current size of the buffer, that is the number of bytes it currently
 * contains.
 */
public synchronized int getSize()
{
	return size;
}
/**
 * Returns whether the buffer is empty.
 */
public synchronized boolean isEmpty()
{
	// @@NOG is een test op size niet beter??? En moet size niet gesynchroniseerd blijven met de conditie tail==-1???
	return tail == -1;
}
/**
 * Returns whether the buffer is full, that is whether it has reached its maximum size.
 * Full buffers can still recieve data; the oldest data it contains is simply discarded
 * to make room for new data.
 */
public synchronized boolean isFull()
{
	// @@NOG Is een size == maxSize niet voldoende?
	return head == tail && size == maxSize;
}
/**
 * Breid de queue uit als hij vol is
 */
protected boolean makeRoom()
{
	// Niks doen als de queue niet vol is
	if ( head != tail )
	{
		return true;
	}

	// Mogen we nog uitbreiden?
	// @@NOG moet je niet size == maxSize checken?
	if ( data.length == maxSize )
	{
		return false;
	}

	int oldLen = data.length;
	int newLen = 2 * oldLen;
	if ( newLen > maxSize )
	{
		newLen = maxSize;
	}

	// Als head voor tail zit, dan moeten we het begin naar het midden verplaatsen
	if ( head <= tail )
	{
		System.arraycopy( data, 0, data, oldLen, head );
		head += oldLen;
	}
	return true;
}
/**
 * This method was created in VisualAge.
 * @param obj java.lang.Object
 */
public synchronized void put( byte aByte ) // throws Exception
{
	// @@NOG Exception al in makeRoom gooien
	if ( ! makeRoom() )
	{
		throw new RuntimeException( "Buffer is full" );
	}
	data[advanceHead()] = aByte;
}
/**
 * This method was created in VisualAge.
 * @return int
 */
public synchronized int size()
{
	return size;
}
/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
@Override
public synchronized String toString()
{
	return super.toString();
	/********** ##NOG
	StringBuffer s = new StringBuffer( "Queue; Head = " + head + " Tail = " + tail + " Size = " + size + " Data: ");
	if ( isEmpty() )
	{
		s.append( "queue is leeg" );
	}
	else
	{
		Vector q = getData();
		s.append( q.elementAt( tail ) );
		for ( int x = ( tail + 1 ) % size; x != head; x = ++x % size )
		{
			s.append( "," );
			s.append( q.elementAt( x ) );
		}
	}
	return s.toString();
	 **********/
}
}
