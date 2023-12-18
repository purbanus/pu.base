package pu.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests the ListFileProducer class
 */
public class TestListFileProducer
{
@SuppressWarnings( "unused" )
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
public void test()
{
    String root = "/tmp";
    List<File> expected = Arrays.asList( new File []
    {
    	new File( root + "/a" ),
    	new File( root + "/b" ),
    	new File( root + "/c" ),
    });
    File rootDirectory = new File( root );
	final FileProducer fileProducer = new ListFileProducer( rootDirectory, expected );
	final List<File> actual = new ArrayList<>();
	fileProducer.addObserver( new Observer()
	{
		@Override
		public void update( Observable aO, Object arg )
		{
			assertEquals( fileProducer, aO );
			actual.add( (File) arg );
		}
	
	});
	fileProducer.run();
    assertEquals( expected, actual );
}

}
