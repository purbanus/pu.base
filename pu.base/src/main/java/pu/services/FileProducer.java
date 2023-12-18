/**
 * 
 */
package pu.services;

import java.io.File;
import java.util.Observable;

/**
 * @author Peter Urbanus
 *
 */
public abstract class FileProducer extends Observable implements Runnable
{
	private final File rootDirectory;
	
public FileProducer( File aRootDirectory )
{
	super();
	rootDirectory = aRootDirectory;
	if ( ! aRootDirectory.isDirectory() )
	{
		throw new RuntimeException( "Must be a directory: " + aRootDirectory );
	}
}


/**
 * @return Returns the rootDirectory.
 */
public File getRootDirectory()
{
	return rootDirectory;
}

}
