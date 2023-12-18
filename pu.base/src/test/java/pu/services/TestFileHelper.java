package pu.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testing FileWalker
 */
public class TestFileHelper
{
	private static class FileSpec
	{
		@SuppressWarnings( "unused" )
		public boolean dir;
		@SuppressWarnings( "unused" )
		public String name;
		public FileSpec( boolean aDir, String aName )
		{
			dir = aDir;
			name = aName;
		}
	}
	FileSpec [] fileSpec = new FileSpec []
	{
		new FileSpec( false, "File1" ),
		new FileSpec( false, "File2" ),
		new FileSpec( false, "File3" ),
		new FileSpec( true , "Dir1" ),
		new FileSpec( false, "Dir1\\File1" ),
		new FileSpec( false, "Dir1\\File2" ),
		new FileSpec( false, "Dir1\\File3" ),
		new FileSpec( false, "File4" ),
	};
@Test
public void testReadAndWrite() throws IOException
{
	String testName = "ReadAndWrite";
	
	String fileNaam = TestHelper.getTestCaseFile( this, testName );

	StringBuffer sb = new StringBuffer();
	for ( char c = 'a'; c <= 'z'; c++ )
	{
		sb.append( StringHelper.repChar( c, 80 ) );
		sb.append( '\n' );
	}

	String s = sb.toString();
	FileHelper.writeFile( fileNaam, s );
	String s1 = FileHelper.readFile( fileNaam );

	//System.out.println( s );
	//System.out.println( s1 );

	assertEquals( s, s1 );
}
@Test
public void testCopyFile() throws IOException
{
	String testName = "CopyFile";
	
	String from = TestHelper.getTestCaseFile( this, testName );
	String to = TestHelper.getTestCaseFile( this, testName + "-to" );

	// from maken/vullen
	String expected = testName;
	FileHelper.writeFile( from, expected );
	
	// Copy
	FileHelper.copyFile( new File( from ), new File( to ) );
	
	// Lees file om te checken
	String actual = FileHelper.readFile( to );
	assertEquals( expected, actual );
}
@Test
public void testCopyStreamToFile() throws IOException
{
	String testName = "CopyStreamToFile";
	
	String from = TestHelper.getTestCaseFile( this, testName );
	String to = TestHelper.getTestCaseFile( this, testName + "-to" );

	// from maken/vullen
	String expected = testName;
	FileHelper.writeFile( from, expected );
	
	// Copy
	InputStream in = new FileInputStream( from );
	FileHelper.copyFile( in, new File( to ) );
	
	// Lees file om te checken
	String actual = FileHelper.readFile( to );
	assertEquals( expected, actual );
}
@Test
public void testCopyStream() throws IOException
{
	String testName = "CopyStream";
	
	String from = TestHelper.getTestCaseFile( this, testName );
	String to = TestHelper.getTestCaseFile( this, testName + "-to" );

	// from maken/vullen
	String expected = testName;
	FileHelper.writeFile( from, expected );
	
	// Copy
	InputStream in = new FileInputStream( from );
	OutputStream out = new FileOutputStream( to );
	FileHelper.copyStream( in, out );
	
	// Lees file om te checken
	String actual = FileHelper.readFile( to );
	assertEquals( expected, actual );
}
@Test
public void testWriteUtf8() throws IOException
{
	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><pipo>Dit is d� file</pipo>";
	String testName = "Write Encoding";
	
	String to = TestHelper.getTestCaseFile( this, testName );

	FileHelper.writeFileUtf8     ( to + "-utf8.xml", xml );
	FileHelper.writeFileIso8859_1( to + "-iso8859-1.xml", xml );
}

//@Test
public void ZZZtestReadFromWorkspace() throws IOException
{
	//String s1 = FileHelper.readFile( "resources/properties/ui.properties" );
	//String s1 = FileHelper.readFile( "M:\\Lotus\\Domino\\Data\\domino\\html\\Iris2\\Algemeen\\images\\irisback.jpg" );
	//System.out.println( s1 );
	String urlS = "file:///M:/Lotus/Domino/Data/domino/html/Iris2/Algemeen/images/irisback.jpg";
	URL url = new URL( urlS );
	@SuppressWarnings( "unused" )
	InputStream in = url.openStream();
}
}
