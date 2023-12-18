package pu.log.log2.console.comm;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;

import pu.log.log2.Log;
import pu.services.StringHelper;

/**
 * An OutputStreamMulticasterException contains a list of exceptions, one for each exception
 * that was caught in an operation such as flush, close or write.
 */
public class MulticastOutputStreamException extends IOException
{
	/** The operation was a close operation */
	public static final String CLOSE = "close";

	/** The operation was a flush operation */
	public static final String FLUSH = "flush";

	/** The operation was a write operation */
	public static final String WRITE = "write";
	
	/**
	 * Short description of the operation during whioch the exceptions were caught.
	 * One of <code>CLOSE</code>, <code>FLUSH</code> or <code>WRITE</code>
	 */
	private final String operation;
	
	/**
	 * The list of exceptions
	 */
	private final List<Throwable> exceptionList;
/**
 * Creates a new OutputStreamMulticasterException with the specified operation and list of exceptions.
 * @param aOperation a String with the operation during which the exceptions were caught, such as close,
 * flush and write.
 * @param aExceptionList The list with exceptions that were caught.
 */
public MulticastOutputStreamException( String aOperation, List<Throwable> aExceptionList )
{
	super( "OutputStreamMulticasterException during " + aOperation + ": " + aExceptionList.toString() );
	operation = aOperation;
	exceptionList = aExceptionList;
}
/**
 * Returns the list of exceptions that were caught during a certain operation.
 */
public final List<Throwable> getExceptionList()
{
	return exceptionList;
}
/**
 * Returns a list of error messages and stack traces, one for each exception in the list
 */
@Override
public String getMessage()
{
	// @@NOG Niet helemaal overzichtelijk. Afmaken!
	
	CharArrayWriter cw = new CharArrayWriter();
	PrintWriter wr = new PrintWriter( cw );
	wr.println();
	List<Throwable> eList = getExceptionList();
	for ( ListIterator<Throwable> it = eList.listIterator(); it.hasNext(); )
	{
		int index = it.nextIndex();
		wr.println( StringHelper.repChar( '-', 80 ) );
		wr.println( "Message #" + index );
		Throwable exc = it.next();
		Log.error( this, exc );
	}
	wr.println( StringHelper.repChar( '-', 80 ) );
	
	return cw.toString();
}
/**
 * Returns the operation during which the exceptions were caught.
 * One of <code>CLOSE</code>, <code>FLUSH</code> or <code>WRITE</code>.
 */
public final String getOperation()
{
	return operation;
}
}
