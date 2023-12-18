package pu.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * Testing FileWalker
 */
public class TestFileWalker
{
	static final String ROOT = TestHelper.getTestCaseRoot( TestFileWalker.class ); // Eindigt op FS;

	private static class FileSpec
	{
		public boolean dir;
		public String name;
		public FileSpec( boolean aDir, String aName )
		{
			dir = aDir;
			name = aName;
		}
	}
	static String FS = File.separator;
	static FileSpec f1     = new FileSpec( false, "File1" );
	static FileSpec f2     = new FileSpec( false, "File2" );
	static FileSpec f3     = new FileSpec( false, "File3" );
	static FileSpec d1     = new FileSpec( true , "Dir1" );
	static FileSpec d1f1   = new FileSpec( false, "Dir1" + FS + "File1" );
	static FileSpec d1f2   = new FileSpec( false, "Dir1" + FS + "File2" );
	static FileSpec d1f3   = new FileSpec( false, "Dir1" + FS + "File3" );
	static FileSpec f4     = new FileSpec( false, "File4" );
	static FileSpec rootFS = new FileSpec( true, "" );
	

	// Create all files and dirs
	static
	{
		FileSpec [] allFileSpecs = { f1, f2, f3, d1, d1f1, d1f2, d1f3, f4 };
		for ( int x = 0; x < allFileSpecs.length; x++ )
		{
			if ( allFileSpecs[x].dir )
			{
				File f = new File( ROOT + allFileSpecs[x].name );
				f.mkdirs();
			}
		}
		for ( int x = 0; x < allFileSpecs.length; x++ )
		{
			if ( ! allFileSpecs[x].dir )
			{
				File f = new File( ROOT + allFileSpecs[x].name );
				if ( ! f.exists() )
				{
					try
					{
						f.createNewFile();
					}
					catch ( IOException e )
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

@Test
public void aaatestListFiles()
{
	final MatrixFormatter mf = new MatrixFormatter();
	FileWalker fw = new FileWalker( new File( ROOT ) );
	fw.addObserver( new Observer()
	{
		@Override
		public void update( Observable aO, Object arg )
		{
			File file = (File) arg;
			mf.addDetail( new String []
			{
				file.getName(),
				new Date( file.lastModified() ).toString(),
			});
		}
	});
	fw.run();
	System.out.println( mf.getOutput() );
}

private List<File> createFileList( int aRepDirs )
{
	FileSpec [] fileSpecs;
	switch ( aRepDirs )
	{
		case FileWalker.REPORTDIRS_AFTER:
			fileSpecs = new FileSpec [] { d1f1, d1f2, d1f3, d1, f1, f2, f3, f4, rootFS};
			break;
		case FileWalker.REPORTDIRS_BEFORE:
			fileSpecs = new FileSpec [] { rootFS, d1, d1f1, d1f2, d1f3, f1, f2, f3, f4, };
			break;
		case FileWalker.REPORTDIRS_NONE:
			fileSpecs = new FileSpec [] { d1f1, d1f2, d1f3, f1, f2, f3, f4, };
			break;
		default:
			throw new RuntimeException( "Ongeldige waarde voor ReportDirs: " + aRepDirs );
	}
	return createFileList( fileSpecs );
}

private List<File> createFileList( FileSpec [] aFileSpecs )
{
	List<File> ret = new ArrayList<>();
	for ( int x = 0; x < aFileSpecs.length; x++ )
	{
		if ( aFileSpecs[x] != null )
		{
			ret.add( new File( ROOT + aFileSpecs[x].name ) );
		}
	}
	return ret;
}

private FileWalker createFileWalker( final int REP_DIRS )
{
	FileWalker fw = new FileWalker( new File( ROOT ) );
	fw.setReportDirs( REP_DIRS );
	return fw;
}

private long createMiddleDate()
{
	// Datums zijn een beetje lastig om te testen. File2 en File3 zijn later dan 
	// de andere omdat ik ze een keer heb weggegooid. Ik neem nu de datum van File1
	// plus een uurtje, voor de zekerheid.
	File file1 = new File( ROOT + f1.name );
	//return file1.lastModified() + 3600 * 1000; // uurtje erbij voor de zekerheid
	return file1.lastModified() + 15000; // kwartiertje erbij voor de zekerheid
}

private void checkReportDirs( final int REP_DIRS )
{
	FileWalker fw = createFileWalker( REP_DIRS );
	List<File> actualFiles = runFileWalker( fw );
	List<File> expectedFiles = createFileList( REP_DIRS );
	assertEquals( expectedFiles, actualFiles );
}

private void checkFiles( List<File> aExpectedFiles, List<File> aActualFiles )
{
	Comparator<File> fileComparator = new Comparator<>()
	{
		@Override
		public int compare( File aFileL, File aFileR )
		{
			try
			{
				return aFileL.getCanonicalPath().compareTo( aFileR.getCanonicalPath() );
			}
			catch ( IOException e )
			{
				throw new RuntimeException( e );
			}
		}
	};
	aExpectedFiles.sort( fileComparator );
	aActualFiles.sort( fileComparator );
	assertEquals( aExpectedFiles, aActualFiles );
}

private List<File> runFileWalker( FileWalker aFileWalker )
{
	final List<File> ret = new ArrayList<>();
	aFileWalker.addObserver( new Observer()
	{
		@Override
		public void update( Observable aObservable, Object aObject )
		{
			// aObject is a File
			ret.add( (File) aObject );
		}
	});
	aFileWalker.run();
	return ret;
	
}
// @@NOG Retourneert nu alleen de directories, omdat acceptAllDirectories = true, en alles heeft dezelfde datum/tijd
// @Test
public void testDateFrom()
{
	// Beetje lastig om te testen, File2 en File3 zijn later dan de andere omdat ik ze een keer heb weggegooid
	long time1 = createMiddleDate();

	FileWalker fw = createFileWalker( FileWalker.REPORTDIRS_BEFORE );
	fw.setDateFrom( new Date( time1 ) );
	List<File> actualFiles = runFileWalker( fw );
	
	FileSpec [] fs = { rootFS, d1, f2, f3 };
	List<File> expectedFiles = createFileList( fs );
	checkFiles( expectedFiles, actualFiles );
}
// @@NOG Retourneert nu alles, want alles heeft dezelfde datum
@Test
public void testDateTo()
{
	// Beetje lastig om te testen, File2 en File3 zijn later dan de andere omdat ik ze een keer heb weggegooid
	long time1 = createMiddleDate();

	FileWalker fw = createFileWalker( FileWalker.REPORTDIRS_BEFORE );
	fw.setDateTo( new Date( time1 ) );
	List<File> actualFiles = runFileWalker( fw );
	
	FileSpec [] fs = { rootFS, d1, d1f1, d1f2, d1f3, f1, f2, f3, f4 };
	List<File> expectedFiles = createFileList( fs );
	checkFiles( expectedFiles, actualFiles );
}
@Test
public void testExclude()
{
	// 2023-12-169 Hier waren twee dingen mis:
	// - Je moet acceptAllDirectories doen anders gaat hij niet eens die subdirectory in. 
	//   @@NOG Dit lijkt me een bug trouwens. Misschien moet je aparte excludeDirectoriers etcc. hebben
	FileWalker fw = createFileWalker( FileWalker.REPORTDIRS_NONE );
	fw.setExcludePatterns( new String [] { "1", "4" } );
	fw.setAcceptAllDirectories( true );
	List<File> actualFiles = runFileWalker( fw );
	//actualFiles.sort( Comparator.comparing( File::getName ) );
	FileSpec [] fs = { d1f2, d1f3, f2, f3 };
	List<File> expectedFiles = createFileList( fs );
	assertEquals( expectedFiles, actualFiles );
}
@Test
public void testInclude()
{
	FileWalker fw = createFileWalker( FileWalker.REPORTDIRS_NONE );
	fw.setIncludePatterns( new String [] { "1", "4" } );
	fw.setAcceptAllDirectories( true );
	List<File> actualFiles = runFileWalker( fw );
	FileSpec [] fs = { d1f1, f1, f4 };
	List<File> expectedFiles = createFileList( fs );
	assertEquals( expectedFiles, actualFiles );
}
@Test
public void testIncludeExclude()
{
	FileWalker fw = createFileWalker( FileWalker.REPORTDIRS_NONE );
	fw.setIncludePatterns( new String [] { "1", "2", "4" } );
	fw.setExcludePatterns( new String [] { "1", "3" } );
	List<File> actualFiles = runFileWalker( fw );
	
	FileSpec [] fs = { f2, f4 };
	List<File> expectedFiles = createFileList( fs );
	assertEquals( expectedFiles, actualFiles );
}
@Test
public void testIncludeExcludeNotAffectingDir()
{
	FileWalker fw = createFileWalker( FileWalker.REPORTDIRS_NONE );
	fw.setExcludePatterns( new String [] { "1", "3" } );
	List<File> actualFiles = runFileWalker( fw );
	
	FileSpec [] fs = { f2, f4 };
	List<File> expectedFiles = createFileList( fs );
	assertEquals( expectedFiles, actualFiles );
}
@Test
public void testMaxDepth()
{
	FileWalker fw = createFileWalker( FileWalker.REPORTDIRS_NONE );
	fw.setMaxDepth( 0 );
	List<File> actualFiles = runFileWalker( fw );
	
	FileSpec [] fs = { f1, f2, f3, f4 };
	List<File> expectedFiles = createFileList( fs );
	assertEquals( expectedFiles, actualFiles );
}
@Test
public void testReportDirsBefore()
{
	checkReportDirs( FileWalker.REPORTDIRS_BEFORE );
}
@Test
public void testReportDirsAfter()
{
	checkReportDirs( FileWalker.REPORTDIRS_AFTER );
}
@Test
public void testReportDirsNone()
{
	checkReportDirs( FileWalker.REPORTDIRS_NONE );
}
@Test
public void testSortFiles()
{
	FileSpec [] actualFileSpecs = new FileSpec [] { f2, f1, d1, f4, f3 };
	File [] actualFiles = createFileList( actualFileSpecs ).toArray( new File [0] );

	FileWalker fileWalker =  new FileWalker(  new File( ROOT ) );
	fileWalker.sortFiles( actualFiles );

	FileSpec [] expectedFileSpecs = new FileSpec [] { d1, f1, f2, f3, f4 };
	File [] expectedFiles = createFileList( expectedFileSpecs ).toArray( new File [0] );
	
	assertArrayEquals( expectedFiles, actualFiles );

}

}
