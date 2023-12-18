/**
 * 
 */
package pu.services;

import java.io.File;
import java.util.List;

/**
 * Simple FileProducer that produces files from a List
 */
public class ListFileProducer extends FileProducer
{
	private final List<File> files;
/**
 * 
 */
public ListFileProducer( File aRootDirectory, List<File> aFiles )
{
	super( aRootDirectory );
	files = aFiles;
}

@Override
public void run()
{
	for ( File file : files )
	{
		setChanged();
		notifyObservers( file );
	}
}
}
