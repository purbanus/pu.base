package pu.services;

import java.io.File;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the FileProducer class
 */
public class TestFileProducer
{

/**
 * @param file
 */
private FileProducer createFileProducer( File file )
{
	return new FileProducer( file )
	{
		@Override
		public void run()
		{
		}
	
	};
}
@Test
public void testCtorShouldThrowRuntimeExceptionUponNoDirectory()
{
    File file = new File( "x:\\asdfg" );
    try
    {
    	createFileProducer( file );
    	fail( " Should have throw RuntimeException " );
    }
    catch ( RuntimeException e )
    {
    	// good
    }
}
@Test
public void testCtorGoodDirectory()
{
	String fileName = Util.isWindows() ? "c:\\temp" : "/tmp";
    File file = new File( fileName );	FileProducer fileProducer = createFileProducer( file );
    assertEquals( file, fileProducer.getRootDirectory() );
}

}
