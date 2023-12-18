package pu.services;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Observable;
import java.util.Observer;

import org.springframework.util.FileCopyUtils;

/**
 * Some utility methods for files and directories..
 */
public class FileHelper
{
	/**
	 * Default buffer size for buffered reads and writes
	 */
	public static final int BUFFER_SIZE = 8192;

/**
 * FileUtil constructor comment.
 */
private FileHelper()
{
	super();
}

/**
 * Copies a File to another File
 * @param bron java.io.File
 * @param doel java.io.File
 */
public static void copyFile( File bron, File doel ) throws IOException
{
	copyStream( new FileInputStream ( bron ), new FileOutputStream( doel ) );
}

/**
 * Copies an inputStream to a File
 * @param bron java.io.File
 * @param doel java.io.File
 */
public static void copyFile( InputStream bron, File doel ) throws IOException
{
	copyStream( bron, new FileOutputStream( doel ) );
}

/**
 * Copy the contents of the given InputStream to the given OutputStream.
 * Closes both streams when done.
 * @param in the stream to copy from
 * @param out the stream to copy to
 * @return the number of bytes copied
 * @throws IOException in case of I/O errors
 */
public static int copyStream( InputStream in, OutputStream out ) throws IOException
{
	try
	{
		return copyNoClose( in, out );
	}
	finally
	{
		try
		{
			in.close();
		}
		catch ( IOException e )
		{
			// ignore
		}
		try
		{
			out.close();
		}
		catch ( IOException e )
		{
			// ignore
		}
	}
}
public static void copyBuffered( InputStream bron, OutputStream doel ) throws IOException
{
	@SuppressWarnings( "hiding" )
	final int BUFFER_SIZE = 10000;
	BufferedInputStream  in  = null;
	BufferedOutputStream uit = null;
	try
	{
		// Maak twee gebufferde streams
		in  = new BufferedInputStream ( bron, BUFFER_SIZE );
		uit = new BufferedOutputStream( doel, BUFFER_SIZE );
	
		// Copieer de boel
		byte [] buf = new byte[BUFFER_SIZE];
		int atl;
		
		while ( ( atl = in.read( buf ) ) > 0 )
		{
			uit.write( buf, 0, atl );
		}
	}
	finally
	{
	    close( in  );
	    close( uit );
	}
}

public static void copyFile( InputStream bron, OutputStream doel ) throws IOException
{
	@SuppressWarnings( "hiding" )
	final int BUFFER_SIZE = 10000;
	BufferedInputStream  in  = null;
	BufferedOutputStream uit = null;
	try
	{
		// Maak twee gebufferde streams
		in  = new BufferedInputStream ( bron, BUFFER_SIZE );
		uit = new BufferedOutputStream( doel, BUFFER_SIZE );

		// Copieer de boel
		byte [] buf = new byte[BUFFER_SIZE];
		int atl;

		while ( ( atl = in.read( buf ) ) > 0 )
		{
			uit.write( buf, 0, atl );
		}
	}
	finally
	{
		close( in  );
		close( uit );
	}
}
public static int copyNoClose( InputStream in, OutputStream out ) throws IOException
{
	if ( in == null )
	{
		throw new RuntimeException( "No InputStream specified" );
	}
	if ( out == null )
	{
		throw new RuntimeException( "No OutputStream specified" );
	}
	int byteCount = 0;
	byte[] buffer = new byte[BUFFER_SIZE];
	int bytesRead = -1;
	while ( ( bytesRead = in.read( buffer ) ) != -1 )
	{
		out.write( buffer, 0, bytesRead );
		byteCount += bytesRead;
	}
	out.flush();
	return byteCount;
}

public static int copyLastPartOfFile( String aFileName, String aTargetFileName, long aTail ) throws IOException
{
	File inFile = new File( aFileName );
	System.out.println( inFile.exists() );
	FileInputStream in = null;
	FileOutputStream out = null;
	try
	{
		in = new FileInputStream( inFile );
		out = new FileOutputStream( aTargetFileName );
		long start = inFile.length() - aTail;
		if ( start < 0 )
		{
			start = 0;
		}
		in.skip( start );
		
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		int byteCount = 0;
		while ( ( bytesRead = in.read( buffer ) ) != -1 )
		{
			out.write( buffer, 0, bytesRead );
			byteCount += bytesRead;
		}
		out.flush();
		return byteCount;
	}
	finally
	{
		close( in );
		close( out );
	}
}


public static void delTree( File aRoot ) throws IOException
{
	// A few checks
	if ( ! aRoot.exists() )
	{
		throw new IOException( "File does not exist: " + aRoot.getName() );
	}
	if ( ! aRoot.isDirectory() )
	{
		throw new IOException( "Not a directory: " + aRoot.getName() );
	}

	// Walk the files with FileWalker
	FileWalker fw = new FileWalker( aRoot );
	fw.addObserver( new Observer()
	{
		@Override
		public void update( Observable o, Object arg )
		{
			File file = (File) arg;
			file.delete();
		}
	});
	fw.setReportDirs( fw.REPORTDIRS_AFTER );
	fw.run();
}

public static void delTree( String aRoot ) throws IOException
{
	delTree( new File( aRoot ) );
}
/**
 * Insert the method's description here.
 * Creation date: (17-2-2002 1:18:33)
 * @return java.lang.String
 */
public static String getFileSeparator()
{
	return System.getProperty( "file.separator" );
}
/**
 * Return the separator string used to separate lines in a text file.
 * In Windows this is "\r\n", for example.
 */
public static String getLineSeparator()
{
	return System.getProperty( "line.separator" );
}
/**
 * @return The separator between several different complete paths as in a path or classpath.
 * On Unix, it is the colon ":", in Windows the semicolon ";". What it is on the Mac I don't remember.
 */
public static String getPathSeparator()
{
	return System.getProperty( "path.separator" );
}

/**
 * Reads a file from a <code>Reader</code> into a String
 * @param aReader The Reader to read
 * @return The content of the file
 */
public static String readFile( Reader aReader ) throws IOException
{
	return FileCopyUtils.copyToString( new BufferedReader( aReader, BUFFER_SIZE ) );
}
/**
 * Reads a file from a <code>InputStream</code> into a String
 * Note: it is better to specify a Charset, for accented letters etc. etc.
 * @param aStream The InputStream to read
 * @return The content of the file
 * @deprecated use the variant with the CharSet
 */
@Deprecated
public static String readFile( InputStream aStream ) throws IOException
{
	return readFile( new InputStreamReader( aStream ) );
}
/**
 * Reads a file from a <code>InputStream</code> into a String using the specified Charset
 * @param aStream The InputStream to read
 * @param aCharset The specified Charset
 * @return The content of the file
 * @deprecated Gebruik een andere leverancier. Bijvoorbeld FileUtils of IOUtils van org.apache.commons.io
 */
@Deprecated
public static String readFile( InputStream aStream, Charset aCharset ) throws IOException
{
	return readFile( new InputStreamReader( aStream, aCharset ) );
}
/**
 * Reads a file from a <code>File</code> into a String
 * @param aFile The file to read
 * @return The content of the file
 * @deprecated Gebruik een andere leverancier. Bijvoorbeld FileUtils of IOUtils van org.apache.commons.io
 * Het schijnt overigens dat new FileReader() helemaal fout is, zie het sbs-inkopen project. Beter scheen new FileInputStream 
 */
@Deprecated
public static String readFile( File aFile ) throws IOException
{
	return readFile( new FileReader( aFile ) );
}

/**
 * Reads a file from the file system into a String
 * @param aFileName The name of the file to read
 * @return The content of the file
 * @deprecated Gebruik een andere leverancier. Bijvoorbeld FileUtils of IOUtils van org.apache.commons.io
 */
@Deprecated
public static String readFile( String aFileName ) throws IOException
{
	return readFile( new FileReader( aFileName ) );
}

/**
 * Writes a String to a <code>File</code>.
 * @param aFile The File to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFile( File aFile, String aInhoud ) throws IOException
{
	// @@NOG Moet dit niet met een Writer?
	try (BufferedOutputStream uit = new BufferedOutputStream( new FileOutputStream( aFile ) );)
	{
		uit.write( aInhoud.getBytes() );
	}
}

/**
 * Writes a String to a file in the file system.
 * @param aFileName The name of the file to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFile( String aFileNaam, String aInhoud ) throws IOException
{
	writeFile( new File( aFileNaam ), aInhoud );
}

/**
 * Writes a String to a <code>File</code> with UTF-8 encoding.
 * @param aFile The File to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFileWithEncoding( File aFile, String aInhoud, String aEncoding ) throws IOException
{
	try (	FileOutputStream fozz = new FileOutputStream( aFile );
			Writer ozz = new OutputStreamWriter( fozz, aEncoding ); 
			BufferedWriter uit = new BufferedWriter( ozz );
		)
	{
		uit.write( aInhoud );
	}
}

/**
 * Writes a String to a file in the file system with UTF-8 encoding.
 * @param aFileName The name of the file to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFileWithEncoding( String aFileNaam, String aInhoud, String aEncoding ) throws IOException
{
	writeFileWithEncoding( new File( aFileNaam ), aInhoud, aEncoding );
}
/**
 * Writes a String to a <code>File</code> with UTF-8 encoding.
 * @param aFile The File to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFileUtf8( File aFile, String aInhoud ) throws IOException
{
	writeFileWithEncoding( aFile, aInhoud, "UTF-8" );
}
/**
 * Writes a String to a file in the file system with UTF-8 encoding.
 * @param aFileName The name of the file to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFileUtf8( String aFileNaam, String aInhoud ) throws IOException
{
	writeFileWithEncoding( aFileNaam, aInhoud, "UTF-8" );
}
/**
 * Writes a String to a <code>File</code> with ISO-8859-1 encoding.
 * @param aFile The File to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFileIso8859_1( File aFile, String aInhoud ) throws IOException
{
	writeFileWithEncoding( aFile, aInhoud, "ISO-8859-1" );
}
/**
 * Writes a String to a file in the file system with ISO-8859-1 encoding.
 * @param aFileName The name of the file to be written
 * @param aInhoud The String to be written to the file
 */
public static void writeFileIso8859_1( String aFileNaam, String aInhoud ) throws IOException
{
	writeFileWithEncoding( aFileNaam, aInhoud, "ISO-8859-1" );
}
public static void download( String aUrl, String aOutPath ) throws IOException
{
	download( new URL( aUrl ), aOutPath );
}

public static void download( URL aUrl, String aOutPath ) throws IOException
{
	try (	InputStream  in  = aUrl.openStream();
			OutputStream out = new FileOutputStream( aOutPath );
	)
	{
		copyBuffered( in, out );
	}
}

private static void close( InputStream s )
{
    try
    {
        s.close();
    }
    catch ( IOException e )
    {
        //Log.error( FileDownloader.class, "Error in close()", e );
        System.err.println( "Error in close()" );
        e.printStackTrace();
    }
}

private static void close( OutputStream s )
{
    try
    {
        s.close();
    }
    catch ( IOException e )
    {
        //Log.error( FileDownloader.class, "Error in close()", e );
        System.err.println( "Error in close()" );
        e.printStackTrace();
    }
}
public static String extractFileNameFromPath( String aInvoiceFileName )
{
	int lastForwardSlash = aInvoiceFileName.lastIndexOf( '/' );
	int lastBackSlash    = aInvoiceFileName.lastIndexOf( '\\' );
	int lastColon        = aInvoiceFileName.lastIndexOf( ':' );
	int lastSeparator = Math.max( lastForwardSlash, lastBackSlash );
	lastSeparator = Math.max( lastSeparator, lastColon );
	String fileName = aInvoiceFileName;
	if ( lastSeparator >= 0 )
	{
		fileName = fileName.substring( lastSeparator + 1 );
	}
	return fileName;
}



}
