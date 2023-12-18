package pu.log.log2.console.comm;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * MulticastOutputStream sends all the bytes it receives to a series of
 * outputstreams. Streams can be added and removed with <code>addStream</code> and
 * <code>removeStream</code>.
 * <p>
 * Each operation (close, flush, write) could result in a series of exceptions,
 * one for each stream. So all methods throw MulticastOutputStreamException, which contains a
 * list of exceptions.
 * <p>
 * <h3>MultiThread remarks</h3>
 * <ul>
 * <li> Streams can be added or removed in the middle of other operations. An unknown number of threads
 * may be adding and removing streams, and performing output.
 * <li> For addStream and removeStream we use a pattern by Doug Lea. It assumes adds and removes are
 * rare compared to the other operations (close, flush and write). The performance is optimal for those
 * other operations, while still being reasonable for adds and removes.
 * <li> All methods take care to synchronize as shortly as possible, and not to call open methods while
 * holding locks. 
 * <li> The truly important object protected by synchronization is the list of streams. All methods
 * that work on this list quickly make a (synchronized) copy of this list, and then proceed to work on it.
 * Only addStream and removeStream work differently, since they modify the list.
 * </ul>
 */
public class MulticastOutputStream extends OutputStream
{
	/**
	 * The OutputStreams. Using a pattern by Doug Lea
	 */
	private OutputStream [] streams = new OutputStream [0];
/**
 * Creates a new MulticastOutputStream.
 */
public MulticastOutputStream()
{
	super();
}
/**
 * Add an OutputStream to the list of streams.
 * This method is synchronized because we are modifying the list.
 * @see removeStream(OutputStream)
 */
public synchronized void addStream( OutputStream aStream )
{
	if ( aStream == null )
	{
		throw new NullPointerException( "Cannot add a null OutputStream" );
	}

	int len = streams.length;
	OutputStream [] newArray = new OutputStream[len + 1];
	if ( len > 0 )
	{
		System.arraycopy( streams, 0, newArray, 0, len );
	}
	newArray[len] = aStream;
	streams = newArray;
}
/**
 * Closes all the outputstreams in our list. All outputstreams are flushed before they are closed.
 * Any error that occurs during the flush is ignored, but all errors that ensue from the actual close
 * are gathered in a MulticastOutputStreamException.
 * @exception  MulticastOutputStreamException if an I/O error occurs in one of the streams.
 *             In particular, an <code>IOException</code> may be thrown if the 
 *             output stream has already been closed.
 */
@Override
public void close() throws MulticastOutputStreamException
{
	List<Throwable> exceptions = null;

	// Only synchronize long enough to make a local copy of the streams field.
	OutputStream [] array;
	synchronized( this )
	{
		array = streams;
	}
	for ( int x = 0; x < array.length; x++ )
	{
		try
		{
			array[x].flush();
		}
		catch ( IOException ignore )
		{
			// This is the way they do it in FilterOutputStream
		}
		try
		{
			array[x].close();
		}
		catch ( IOException e )
		{
			if ( exceptions == null )
			{
				exceptions = new ArrayList<>( 4 );
			}
			exceptions.add( e );
		}
	}
	if ( exceptions != null )
	{
		throw new MulticastOutputStreamException( MulticastOutputStreamException.CLOSE, exceptions );
	}
}
/**
 * Flushes all the outputstreams in our list.
 * @exception  MulticastOutputStreamException if an I/O error occurs in one of the streams.
 *             In particular, an <code>IOException</code> may be thrown if the 
 *             output stream has already been closed.
 */
@Override
public void flush() throws MulticastOutputStreamException
{
	List<Throwable> exceptions = null;

	// Only synchronize long enough to make a local copy of the streams field.
	OutputStream [] array;
	synchronized( this )
	{
		array = streams;
	}
	for ( int x = 0; x < array.length; x++ )
	{
		try
		{
			array[x].flush();
		}
		catch ( IOException e )
		{
			if ( exceptions == null )
			{
				exceptions = new ArrayList<>( 4 );
			}
			exceptions.add( e );
		}
	}
	if ( exceptions != null )
	{
		throw new MulticastOutputStreamException( MulticastOutputStreamException.FLUSH, exceptions );
	}
}
/**
 * Removes an OutputStream from the list of streams.
 * This method is synchronized because we are modifying the list.
 * @see addStream(OutputStream)
 */
public synchronized void removeStream( OutputStream aStream )
{
	int newlen = streams.length - 1;
	if (newlen < 0 || aStream == null)
	{
		return;
	}

	// Copy while searching for element to remove. Notice that when aListener
	// is not found, nothing happens.

	OutputStream [] newArray = new OutputStream[newlen];

	for ( int i = 0; i < newlen; ++i )
	{
		if ( aStream.equals( streams[i] ) )
		{
			//  copy remaining and exit
			for ( int k = i + 1; k <= newlen; ++k )
				newArray[k - 1] = streams[k];
			streams = newArray;
			return;
		}
		else
			newArray[i] = streams[i];
	}

	// special-case last cell
	if ( aStream.equals( streams[newlen] ) )
		streams = newArray;

}
/**
 * Writes <code>b.length</code> bytes from the specified byte array 
 * to this output stream. The general contract for <code>write(b)</code> 
 * is that it should have exactly the same effect as the call 
 * <code>write(b, 0, b.length)</code>.
 *
 * @param      b   the data.
 * @exception  MulticastOutputStreamException if an I/O error occurs in one of the streams.
 *             In particular, an <code>IOException</code> may be thrown if the 
 *             output stream has been closed.
 * @see        java.io.OutputStream#write(byte[], int, int)
 */
@Override
public void write( byte b[] ) throws MulticastOutputStreamException
{
	write( b, 0, b.length );
}
/**
 * Writes <code>len</code> bytes from the specified byte array 
 * starting at offset <code>off</code> to this output stream. 
 * The general contract for <code>write(b, off, len)</code> is that 
 * some of the bytes in the array <code>b</code> are written to the 
 * output stream in order; element <code>b[off]</code> is the first 
 * byte written and <code>b[off+len-1]</code> is the last byte written 
 * by this operation.
 * <p>
 * The <code>write</code> method of <code>OutputStream</code> calls 
 * the write method of one argument on each of the bytes to be 
 * written out. Subclasses are encouraged to override this method and 
 * provide a more efficient implementation. 
 * <p>
 * If <code>b</code> is <code>null</code>, a 
 * <code>NullPointerException</code> is thrown.
 * <p>
 * If <code>off</code> is negative, or <code>len</code> is negative, or 
 * <code>off+len</code> is greater than the length of the array 
 * <code>b</code>, then an <tt>IndexOutOfBoundsException</tt> is thrown.
 *
 * @param      b     the data.
 * @param      off   the start offset in the data.
 * @param      len   the number of bytes to write.
 * @exception  MulticastOutputStreamException if an I/O error occurs in one of the streams.
 *             In particular, an <code>IOException</code> may be thrown if the 
 *             output stream has been closed.
 */
@Override
public void write( byte b[], int off, int len ) throws MulticastOutputStreamException
{
	List<Throwable> exceptions = null;
	
	// Only synchronize long enough to make a local copy of the streams field.
	OutputStream [] array;
	synchronized( this )
	{
		array = streams;
	}
	for ( int x = 0; x < array.length; x++ )
	{
		try
		{
			array[x].write( b, off, len );
		}
		catch ( IOException e )
		{
			if ( exceptions == null )
			{
				exceptions = new ArrayList<>( 4 );
			}
			exceptions.add( e );
		}
		/**************
		// @@TEST
		catch ( Throwable e )
		{
			Log.logError( e );
		}
		**************/
	}
	if ( exceptions != null )
	{
		throw new MulticastOutputStreamException( MulticastOutputStreamException.WRITE, exceptions );
	}
}
/**
 * Writes the specified byte to this output stream. The general 
 * contract for <code>write</code> is that one byte is written 
 * to the output stream. The byte to be written is the eight 
 * low-order bits of the argument <code>b</code>. The 24 
 * high-order bits of <code>b</code> are ignored.
 * <p>
 * Subclasses of <code>OutputStream</code> must provide an 
 * implementation for this method. 
 *
 * @param      b   the <code>byte</code>.
 * @exception  MulticastOutputStreamException if an I/O error occurs in one of the streams.
 *             In particular, an <code>IOException</code> may be thrown if the 
 *             output stream has been closed.
 */
@Override
public void write( int b ) throws MulticastOutputStreamException
{
	write( new byte [] { (byte) b }, 0, 1 );
}
}
