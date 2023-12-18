package pu.services.seq;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import pu.services.FileWalker;
import pu.services.MatrixFormatter;

public class DirTeller implements Observer
{
long fileCount = 0;
long dirCount = 0;
long size = 0;
long totalFileCount = 0;
long totalDirCount = 0;
long totalSize = 0;
private final DecimalFormat intFormat;
public static void main( String [] args )
{
	new DirTeller().printDirSize( "/mnt/nas2/photos/vrouwen/" );
}
public DirTeller()
{
	super();
	/*****************
	System.out.println( "default:" + Locale.getDefault() );
	Locale [] locales = Locale.getAvailableLocales();
	for ( int x = 0; x < locales.length; x++ )
    {
		System.out.println( locales[x] );
    }
	 ************************/
	intFormat = (DecimalFormat) NumberFormat.getInstance( Locale.GERMANY );
	//intFormat.setGroupingSize( 3 );
	intFormat.setGroupingUsed( true );

}
public void printDirSize( String aDir )
{
	dirCount = 0;
	fileCount = 0;
	size = 0;
	totalDirCount = 0;
	totalFileCount = 0;
	totalSize = 0;

	MatrixFormatter mf = new MatrixFormatter();
	mf.setAlignment( 1, MatrixFormatter.ALIGN_RIGHT );
	mf.setAlignment( 2, MatrixFormatter.ALIGN_RIGHT );
	mf.setAlignment( 3, MatrixFormatter.ALIGN_RIGHT );
	File [] dirs = new File( aDir ).listFiles();
	sortFileNames( dirs );
	for ( File dir : dirs )
	{
		if ( dir.isDirectory() )
		{
			FileWalker fileWalker = new FileWalker( dir );
			fileWalker.addObserver( this );
			fileWalker.run();
			mf.addDetail( new String []
					{
				dir.getName(),
				intFormat.format( dirCount ),
				intFormat.format( fileCount ),
				intFormat.format( size ),
					});
			totalDirCount += dirCount;
			totalFileCount += fileCount;
			totalSize += size;
			dirCount = 0;
			fileCount = 0;
			size = 0;
		}
	}
	mf.addDetail( new String []
			{
		"Totaal",
		intFormat.format( totalDirCount ),
		intFormat.format( totalFileCount ),
		intFormat.format( totalSize ),
			});
	System.out.println( mf.getOutput() );

}
private void sortFileNames( File [] aDirs )
{
	Arrays.sort( aDirs, ( f1, f2 ) -> f1.getName().compareTo( f2.getName() ));
}

@Override
public void update( Observable aO, Object aArg )
{
	File file = (File) aArg;
	if ( file.isDirectory() )
	{
		dirCount++;
	}
	if ( file.isFile() )
	{
		fileCount++;
		size += file.length();
	}
}
}
