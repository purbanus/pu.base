package pu.services;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

/**
 * Utility class that walks through files and directories, reporting each file
 * and optionally each directory it finds.
 * <p>
 * FileWalker is an Observable, so clients should implement the <code>Observable</code>
 * interface; they will be notified of every file or directory FileWalker finds.
 * <p>
 * The directory tree is walked in a depth-first manner. For example, with a directory tree of
 * <pre>
 * dir
 * +--file1
 * +--file2
 * +--dir
 * |  + subfile1
 * |  + subfile2
 * +  file3
 * </pre>
 * The files will be walked in this order:
 * <pre>
 * subfile1
 * subfile2
 * file1
 * file2
 * file3
 * </pre>
 * When directories are reported depends on the <code>reportDirs</code> property
 * <p>
 * Optionally, a number of patterns can be specified to which the reported files must comply.
 * The patterns describe the ending of a file, e.g. "txt" will match filenames like "nicetxt"
 * or "readme.txt". You will usually want to specify a dot as in ".txt". The patterns are not
 * applied to directory names, that is all directories are searched regardless of the patterns.
 * <p>
 * Similarly, you can specify a number of exclusion patterns, specifying which patterns NOT to include.
 * <p>
 * In addition, you can specify a date range.
 */
public class FileWalker extends FileProducer
{
	public static final int REPORTDIRS_NONE   = 1;
	public static final int REPORTDIRS_BEFORE = 2;
	public static final int REPORTDIRS_AFTER  = 3;
	
	private int reportDirs = REPORTDIRS_AFTER;
	private int maxDepth = Integer.MAX_VALUE;
	private MyFileFilter fileFilter = new MyFileFilter();
	
	public static class MyFileFilter implements FileFilter
	{
		private long timeFrom = Long.MIN_VALUE;
		private long timeTo   = Long.MAX_VALUE;
		private String[] includePatterns = new String[0];
		private String[] excludePatterns = new String[0];
		private boolean acceptAllDirectories = false;
		/**
		 * @return Returns the dateFrom.
		 */
		public long getTimeFrom()
		{
			return timeFrom;
		}
		/**
		 *  @return Returns the dateTo.
		 */
		public long getTimeTo()
		{
			return timeTo;
		}
		/**
		 * @param aDateFrom The dateFrom to set.
		 */
		public void setDateFrom( Date aDate )
		{
			timeFrom = aDate.getTime();
		}
		/**
		 * @param aDateTo The dateTo to set.
		 */
		public void setDateTo( Date aDate )
		{
			timeTo = aDate.getTime();
		}
		/**
		 * @return The inclusionPatters. This method never returns <code>null</code>, although it may return an empty array.
		 */
		public final String[] getIncludePatterns()
		{
			return includePatterns;
		}
		/**
		 * @return The exclusionPatters. This method never returns <code>null</code>, although it may return an empty array.
		 */
		public final String[] getExcludePatterns()
		{
			return excludePatterns;
		}
		/**
		 * @param aIncludePatterns The includePatterns to set.
		 * We turn them into lowercase.
		 */
		public void setIncludePatterns( String [] aIncludePatterns )
		{
			if ( aIncludePatterns != null )
			{
				includePatterns = aIncludePatterns;
				for ( int x = 0; x < includePatterns.length; x++ )
				{
					includePatterns[x] = includePatterns[x].toLowerCase();
				}
			}
		}
		/**
		 * @param aExcludePatterns The excludePatterns to set.
		 * We turn them into lowercase.
		 */
		public void setExcludePatterns( String [] aExcludePatterns )
		{
			if ( aExcludePatterns != null )
			{
				excludePatterns = aExcludePatterns;
				for ( int x = 0; x < excludePatterns.length; x++ )
				{
					excludePatterns[x] = excludePatterns[x].toLowerCase();
				}
			}
		}
		public boolean isAcceptAllDirectories()
		{
			return acceptAllDirectories;
		}
		public void setAcceptAllDirectories( boolean aAcceptAllDirectories )
		{
			acceptAllDirectories = aAcceptAllDirectories;
		}
		@Override
		public boolean accept( File aFile )
		{
			if ( aFile.isDirectory() && acceptAllDirectories )
			{
				return true;
			}
			if ( ! matchIncludePatterns( aFile.getName() ) || matchExcludePatterns( aFile.getName() ) )
			{
				return false;
			}
			if ( aFile.isDirectory() )
			{
				return true;
			}
			if ( getTimeFrom() > aFile.lastModified() )
			{
				return false;
			}
			if ( getTimeTo() < aFile.lastModified() )
			{
				return false;
			}
			return true;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//Matching
		
		/**
		* Returns <code>true</code> if <code>aString</code> matches one of the patterns, of if no patterns are
		* specified.
		* @param aString The string to be matched
		* @param aPattern The pattern
		* @return Whether the string matches one of the patterns.
		*/
		private boolean match( String aString, String [] aPattern )
		{
			if ( aPattern.length == 0 )
			{
				return true;
			}
			final String lowS = aString.toLowerCase();
			for ( int i = 0; i < aPattern.length; i++ )
			{
				if ( lowS.endsWith( aPattern[i] ) )
				{
				return true;
				}
			}
			return false;
		}

		/**
		* @param aString The String to be matched
		* @return <code>true</code> if <code>aString</code> matches one of the exclusion patterns, and <code>false</code> if no exclusion patterns are
		* specified.
		*/
		private boolean matchExcludePatterns( String aString )
		{
			return getExcludePatterns().length == 0 ? false : match( aString, getExcludePatterns() );
		}
		
		/**
		* @param aString The String to be matched
		* @return <code>true</code> if <code>aString</code> matches one of the inclusion patterns, of if no inclusion patterns are
		* specified.
		*/
		private boolean matchIncludePatterns( String aString )
		{
			return match( aString, getIncludePatterns() );
		}
	}

/**
 * Creates a new FileWalker without any inclusion- or exclusion patterns
 */
public FileWalker( File aRootDirectory )
{
	this( aRootDirectory, new String [0] );
}
/**
 * Creates a new FileWalker with the specified inclusionpatterns
 * @param aIncludePatterns The inclusionpatterns. A value of <code>null</code> means no inclusionPatterns.
 */
public FileWalker( File aRootDirectory, String [] aIncludePatterns )
{
	this( aRootDirectory, aIncludePatterns, new String [0] );
}
/**
 * Creates a new FileWalker with the specified inclusion- and exclusion patterns
 * @param aIncludePatterns The inclusionpatterns. A value of <code>null</code> means no inclusionPatterns.
 * @param aExcludePatterns The exclusionpatterns. A value of <code>null</code> means no exclusionPatterns.
 */
public FileWalker( File aRootDirectory, String [] aIncludePatterns, String [] aExcludePatterns )
{
	super( aRootDirectory );

	getFileFilter().setIncludePatterns( aIncludePatterns );
	getFileFilter().setExcludePatterns( aExcludePatterns );
}

/////////////////////////////////////////////////////////////////////////////////////////////////////
// Getters and setters

/**
 * 
 * @return the FileFilter we use
 */
public MyFileFilter getFileFilter()
{
	return fileFilter;
}

/**
 * @return the maximum depth of the directory tree to walk. A value of 0 means only the start directory, 
 * 1 means the start directory and its direct children, etc.
 */
public int getMaxDepth()
{
	return maxDepth;
}
/**
 * Returns whether listeners should be notified of directories, and when.
 * There are three possibilities:
 * <ul>
 * <li> REPORTDIRS_NONE No directories are reported, only files
 * <li> REPORTDIRS_BEFORE Direcories are reported before they are recursed into
 * <li> REPORTDIRS_AFTER Direcories are reported after they have been recursed into
 * </ul>
 */
public int getReportDirs()
{
	return reportDirs;
}
/**
 * @return Returns the dateFrom.
 */
public Date getDateFrom()
{
	return new Date( getFileFilter().getTimeFrom() );
}
/**
 *  @return Returns the dateTo.
 */
public Date getDateTo()
{
	return new Date( getFileFilter().getTimeTo() );
}
/**
 * Sets the maximum depth of the directory tree to walk. A value of 0 means only the start directory, 
 * 1 means the start directory and its direct children, etc.
 * @param aMaxDepth The maximum depth
 */
public void setMaxDepth( int aMaxDepth )
{
	maxDepth = aMaxDepth;
}

/**
 * Sets whether listeners should be notified of directories, and when.
 * There are three possibilities:
 * <ul>
 * <li> REPORTDIRS_NONE No directories are reported, only files
 * <li> REPORTDIRS_BEFORE Direcories are reported before they are recursed into
 * <li> REPORTDIRS_AFTER Direcories are reported after they have been recursed into
 * </ul>
 */
public void setReportDirs(int newReportDirs)
{
	reportDirs = newReportDirs;
}
/**
 * @param aDateFrom The dateFrom to set.
 */
public void setDateFrom( Date aDate )
{
	getFileFilter().setDateFrom( aDate );
}
/**
 * @param aDateTo The dateTo to set.
 */
public void setDateTo( Date aDate )
{
	getFileFilter().setDateTo( aDate );

}
/**
 * @param aIncludePatterns The includePatterns to set.
 * We turn them into lowercase.
 */
public void setIncludePatterns( String [] aIncludePatterns )
{
	getFileFilter().setIncludePatterns( aIncludePatterns );
}
/**
 * @param aExcludePatterns The excludePatterns to set.
 * We turn them into lowercase.
 */
public void setExcludePatterns( String [] aExcludePatterns )
{
	getFileFilter().setExcludePatterns( aExcludePatterns );
}
public void setAcceptAllDirectories( boolean aAcceptAllDirectories )
{
	getFileFilter().setAcceptAllDirectories( aAcceptAllDirectories );
}
public void setFileFilter( MyFileFilter aFileFilter )
{
	fileFilter = aFileFilter;
}
/**
 * Walks through a directory, reporting each file found that matches the patterns and optionally each directory.
 * In this version, regardless of whether directories are reported, it recursively
 * enters subdirectories and reports the files (and optionally directories) there.
 * <p>
 * Subdirectories are walked depth-first. That is, if a subdirectory is encountered,
 * <code>walk</code> immediately enters the subdirectory and starts reporting files there,
 * while other files in the current directory will be reported later, when <code>walk</code>
 * is finished with the subdirectory.
 * @param aFile The starting directory
 */
@Override
public void run()
{
	run_1( getRootDirectory(), 0 );
}

private void run_1( File aDirectory, int aDepth )
{
	if ( getReportDirs() == REPORTDIRS_BEFORE )
	{
		setChanged();
		notifyObservers( aDirectory );
	}
	
	if ( aDepth <= getMaxDepth() )
	{
		File [] files = aDirectory.listFiles( getFileFilter() );
		sortFiles( files );
		if ( files != null ) // )
		{
			for ( int x = 0; x < files.length; x++ )
			{
				if ( files[x].isDirectory() )
				{
					run_1( files[x], aDepth + 1 );
				}
				else
				{
					setChanged();
					notifyObservers( files[x] );
				}
			}
		}
	}

	if ( getReportDirs() == REPORTDIRS_AFTER )
	{
		setChanged();
		notifyObservers( aDirectory );
	}
}
public void sortFiles( File [] aFiles )
{
	Comparator<File> fileComparator = new Comparator<>()
	{
		@Override
		public int compare( File aFile1, File aFile2 )
		{
			// Dirs voorop
			if ( aFile1.isDirectory() && ! aFile2.isDirectory() )
			{
				return -1;
			}
			if ( ! aFile1.isDirectory() && aFile2.isDirectory() )
			{
				return 1;
			}
			// Als beide directories zijn of beide files, dan sorteren op naam
			int compare =  aFile1.getName().compareTo( aFile2.getName() );
			return compare;
		}
	};
	Arrays.sort( aFiles, fileComparator );
}
}
