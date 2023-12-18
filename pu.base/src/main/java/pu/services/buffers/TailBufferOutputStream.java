package pu.services.buffers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * This class is closely modeled after ByteArrayOutputStream. But instead of maintaining an
 * ever growing array of bytes, it uses a TailBuffer for storage. This imposes a strict limit
 * on the buffer size, When this limit is reached, old bytes are discarded top make room for 
 * new ones. So in a way, a TailBufferOutputStream maintains a window on an outputstream, the
 * window always showing the last n bytes, n being the maximum size of the TailBuffer.
 * <p>
 * The data can be retrieved using <code>toByteArray()</code> and
 * <code>toString()</code>, or written to another outputstream using <code>writeTo</code>.
 * <p>
 * In this implementation, <code>flush</code> and <code>close</code> do nothing. There is a
 * precedent for this: a ByteArrayOutputSTream also does nothing. Besides, we feel that
 * close has no meaning in memory-based output streams, and there is nothing in this stream
 * to flush. After close, you can continue to use the stream as if nothing happened.
 */

public class TailBufferOutputStream extends OutputStream
{
	/**
	 * The default maximum size
	 */
	public static final int DEFAULT_MAX = 100000;
	
	/** 
	 * The buffer where data is stored. 
	 */
	private final ByteTailBuffer buffer;

	/**
	 * Flag indicating whether the stream has been closed.
	 */
	//private boolean isClosed = false;

/**
 * Creates a new TailBufferOutputStream with the default maximum and initial
 * sizes of 100000 bytes and 1000 bytes, respectively.
 */
public TailBufferOutputStream()
{
	this( DEFAULT_MAX );
}
/**
 * Creates a TailBufferOutputStream with the specified maximum size, in bytes.
 * @param   aMaxSize the maximum size.
 * @exception  IllegalArgumentException if aMaxSize is negative.
 */
public TailBufferOutputStream( int aMaxSize )
{
	super();
	buffer = new ByteTailBuffer( aMaxSize );
}
/**
 * Creates a new TailBufferOutputStream with the specified maximum and initial size, in bytes.
 * @param   aMaxSize the maximum size of the buffer.
 * @param   aInitialSize the initial size of the buffer. This number of bytes is immediately allocated.
 * @exception  IllegalArgumentException if aMaxSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is negative or zero.
 * @exception  IllegalArgumentException if aInitialSize is greater that aMaxSize.
 */
public TailBufferOutputStream( int aMaxSize, int aInitialSize )
{
	super();
	buffer = new ByteTailBuffer( aMaxSize, aInitialSize );
}
/**
 * Closes this output stream and releases any system resources 
 * associated with this stream. A closed stream cannot perform 
 * output operations and cannot be reopened.
 * <p>
 *
 */
@Override
public synchronized void close() throws IOException
{
	//isClosed = true;
}
/**
 * Check to make sure that the stream has not been closed
 */
private void ensureOpen()
{
	/* This method does nothing for now.  Once we add throws clauses
	* to the I/O methods in this class, it will throw an IOException
	* if the stream has been closed.
	*/
}
/**
 * Returns the maximum size of the buffer.
 */
public int getMaxSize()
{
	return buffer.getMaxSize();
}
/**
 * Returns whether the buffer is empty.
 */
public synchronized boolean isEmpty()
{
	return buffer.isEmpty();
}
/**
 * Returns whether the buffer is full, that is whether it has reached its maximum size.
 * Full buffers can still recieve data; the oldest data it contains is simply discarded
 * to make room for new data.
 */
public synchronized boolean isFull()
{
	return buffer.isFull();
}
/**
 * Resets the <code>count</code> field of this output 
 * stream to zero, so that all currently accumulated output in the 
 * ouput stream is discarded. The output stream can be used again, 
 * reusing the already allocated buffer space. 
 *
 * @see     java.io.ByteArrayInputStream#count
 */
public synchronized void reset()
{
	ensureOpen();
	buffer.clear();
}
/**
 * Returns the current size of the buffer.
 */
public int size()
{
	return buffer.size();
}
/**
 * Creates a newly allocated byte array. Its size is the current 
 * size of this output stream and the valid contents of the buffer 
 * have been copied into it. 
 *
 * @return  the current contents of this output stream, as a byte array.
 * @see     TailBufferOutputStream#size()
 */
public synchronized byte toByteArray()[]
{
	return buffer.get();
}
/**
 * Converts the buffer's contents into a string, translating bytes into
 * characters according to the platform's default character encoding.
 */
@Override
public String toString()
{
	// @@NOG Dit betekent dat het spul tweemaal gekopieerd wordt, denk ik
	return new String( buffer.get() );
}
/**
 * Converts the buffer's contents into a string, translating bytes into
 * characters according to the specified character encoding.
 *
 * @param   enc  a character-encoding name.
 */
public String toString(String enc) throws UnsupportedEncodingException
{
	return new String( buffer.get(), enc);
}
/**
 * Writes <code>b.length</code> bytes from the specified byte array 
 * to this output stream. The general contract for <code>write(b)</code> 
 * is that it should have exactly the same effect as the call  
 * <code>write(b, 0, b.length)</code>.
 *
 * @param      b   the data.
 * @see        java.io.OutputStream#write(byte[], int, int)
 */
@Override
public void write( byte b[] )
{
	write( b, 0, b.length );
}
/**
 * Writes <code>len</code> bytes from the specified byte array 
 * starting at offset <code>off</code> to this byte array output stream.
 *
 * @param   b     the data.
 * @param   off   the start offset in the data.
 * @param   len   the number of bytes to write.
 */
@Override
public synchronized void write( byte aBytes[], int aOffset, int aLength )
{
	ensureOpen();
	buffer.put( aBytes, aOffset, aLength );
}
/**
 * Writes the specified byte to this byte array output stream. 
 *
 * @param   b   the byte to be written.
 */
@Override
public synchronized void write( int aByte )
{
	ensureOpen();
	buffer.put( (byte) aByte );
}
/**
 * Writes the complete contents of this byte array output stream to 
 * the specified output stream argument, as if by calling the output 
 * stream's write method using <code>out.write(buf, 0, count)</code>.
 *
 * @param      out   the output stream to which to write the data.
 * @exception  IOException  if an I/O error occurs.
 */
public synchronized void writeTo( OutputStream out ) throws IOException
{
	out.write( buffer.get() );
}
}
