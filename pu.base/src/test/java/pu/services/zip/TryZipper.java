package pu.services.zip;

import java.io.File;
import java.io.IOException;

import pu.services.FileWalker;
import pu.services.StopWatch;


public class TryZipper
{

/**
 * @param args
 */
public static void main( String [] args )
{
	StopWatch timer = new StopWatch();
	try
	{
		String root = "c:\\temp\\crawler";
		FileWalker fw = new FileWalker( new File( root ) );
		fw.setReportDirs( fw.REPORTDIRS_NONE );
		Zipper zipper = new Zipper( fw, "c:\\temp\\crawler.zip" );
		zipper.run();
	}
	catch ( IOException e )
	{
		e.printStackTrace();
	}
	System.out.println( timer.getElapsedMs() );
//	FileFilter x;
//	FilenameFilter y;
}

}
