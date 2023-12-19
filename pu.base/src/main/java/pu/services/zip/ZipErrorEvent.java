/**
 * 
 */
package pu.services.zip;

import java.io.File;

/**
 */
public class ZipErrorEvent
{
	private final File file;
	private final Exception exception;
public ZipErrorEvent( File aFile, Exception aException )
{
	super();
	file = aFile;
	exception = aException;
}
/**
 * @return the exception
 */
public Exception getException()
{
	return exception;
}
/**
 * @return the file
 */
public File getFile()
{
	return file;
}

}
