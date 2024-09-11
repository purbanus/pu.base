package pu.services.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import pu.log.Log;
import pu.services.FileHelper;
import pu.services.FileProducer;


/**
 * Zipper zips a complete directorystructure to a zipfile.
 * <p>
 * Note: Zipper is not at all threadsafe!
 */
public class Zipper implements Observer, Runnable
{
	//private static final Logger LOG = LogManager.getLogger( Zipper.class );
	
	private final FileProducer fileProducer;
	private final String outputFileName;
	private final String canonicalDirectoryPath;
	private final ZipOutputStream zipOutputStream;
	private final CopyOnWriteArrayList<ZipErrorListener> zipErrorListeners = new CopyOnWriteArrayList<>();

/**
 * Creates a new Zipper.
 * @param  aDirectoryName The directory to zip
 * @param  aOutputFileName The filename of the zip archive.
 * @throws FileNotFoundException  if the <code<aOutputFileName</code> exists but is a directory
 *          rather than a regular file, does not exist but cannot be created, or cannot be opened for any other reason
 * @throws IOException
 *          If an I/O error occurs during the construction of the canonical path of <code<aOutputFileName</code>, 
 *          which is possible because the construction of the canonical pathname may require filesystem queries.
 */
public Zipper( FileProducer aFileProducer, String aOutputFileName ) throws IOException
{
	super();
	fileProducer = aFileProducer;
    fileProducer.addObserver( this );
	outputFileName = aOutputFileName;
	canonicalDirectoryPath = aFileProducer.getRootDirectory().getCanonicalPath();
	
    // Create zip output stream.
	zipOutputStream = new ZipOutputStream( new FileOutputStream( getOutputFileName() ) );
    zipOutputStream.setLevel( Deflater.BEST_COMPRESSION ); // Scheelt, maar niet veel
}

//////////////////////////////////////////////////////////////////////////////////////////
// Getters en setters

/**
 * @return Returns the canonicalDirectoryPath.
 */
private String getCanonicalDirectoryPath()
{
	return canonicalDirectoryPath;
}

/**
 * @return Returns the fileProducer.
 */
private FileProducer getFileProducer()
{
	return fileProducer;
}

/**
 * @return Returns the outputFileName.
 */
public String getOutputFileName()
{
	return outputFileName;
}

/**
 * @return Returns the zipOutputStream.
 */
private ZipOutputStream getZipOutputStream()
{
	return zipOutputStream;
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
// ZipErrorListeners

private CopyOnWriteArrayList<ZipErrorListener> getZipErrorListeners()
{
	return zipErrorListeners;
}

public void addZipErrorListener( ZipErrorListener aZipErrorListener )
{
	getZipErrorListeners().add( aZipErrorListener );
}

public void removeZipErrorListener( ZipErrorListener aZipErrorListener )
{
	getZipErrorListeners().remove( aZipErrorListener );
}

private void fireZipError( File aFile, Exception aException )
{
	ZipErrorEvent zipErrorEvent = new ZipErrorEvent( aFile, aException );
	for ( Iterator<ZipErrorListener> it = getZipErrorListeners().iterator(); it.hasNext(); )
	{
		it.next().zipError( zipErrorEvent );
	}
}


/////////////////////////////////////////////////////////////////////////////////////////////////
// Work

@Override
public void run()
{
	try
	{
	    getFileProducer().run();
	}
	finally
	{
    	if ( zipOutputStream != null )
		{
	        try
			{
        		zipOutputStream.close();
			}
			catch ( IOException e )
			{
				Log.error( this, "Error during close", e );
			}
		}
	}
}

/**
 * This method is called for each file that the fileproducer discovers.
 */
@Override
public void update( Observable o, Object arg )
{
	File file = (File) arg;
	try
	{
		ZipEntry ze = createZipEntry( file );
		getZipOutputStream().putNextEntry( ze );
		copyFile( file, getZipOutputStream() );
		getZipOutputStream().closeEntry();
		// System.out.println( ze.getName() + " (" + ze.getCompressedSize() * 100 / ze.getSize() + "%)" );
	}
	catch ( IOException e )
	{
		Log.error( this, "Error zipping file " + file, e );
		fireZipError( file, e );
	}
}

/**
 * Creates a new ZipEntry from a File
 * @param f The File from which to create z ZipEntry
 * @return The new ZipEntry
 * @throws IOException
 */
private ZipEntry createZipEntry( File f ) throws IOException
{
	ZipEntry ze = new ZipEntry( convertToZipName( f ) );

	ze.setTime( f.lastModified() );
	ze.setMethod( ZipEntry.DEFLATED );
	ze.setComment( f.getCanonicalPath() );
	
	// Niet duidelijk waar dit voor is
	//ze.setExtra( new byte []
	//{
	//	(byte) 'X'
	//});
	return ze;
}

/**
 * Copies a File to an OutputStream
 * @param from The File to be copied from
 * @throws FileNotFoundException
 * @throws IOException
 */
private void copyFile( File from, OutputStream os ) throws FileNotFoundException, IOException
{
	try ( FileInputStream is = new FileInputStream( from );)
	{
		FileHelper.copyNoClose( is, os );
	}
}

/**
 * Converts a file's pathname to a form acceptable to ZIP files.
 * It also reroots the path to the last directory name of the directory to zip.
 * <p>
 * Example:
 * <p>
 * Assume the directory to zip is c:\temp\crawler and we need to include a file c:\temp\crawler\subdir\file.txt.
 * Then the zipName returned is crawler/subdir/file.txt
 * @param aFileName
 * @return The zipfile name
 * @throws IOException When an error occurred during the construction of the canonical pathname of <code>aFileName</code>
 */
private String convertToZipName( File aFileName ) throws IOException
{
	String root = getCanonicalDirectoryPath();                                      // c:\temp\crawler
    String pname = aFileName.getCanonicalPath();                                    // c:\temp\crawler\subdir\file.txt
    String rootname = root.substring( root.lastIndexOf( File.separatorChar ) + 1 ); // crawler
    pname = pname.substring( root.length() + 1 );                                   // subdir\file.txt
    pname = pname.replace(File.separatorChar, '/');                                 // subdir/file.txt
    return rootname + "/" + pname;                                                 // crawler/subdir/file.txt
}

}
