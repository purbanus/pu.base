/*
 * Copyright 2004 Clinton Begin Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */
package pu.db.scriptrunner;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.io.Resources;

/**
 * Tool to run database scripts
 */
public class ScriptRunner
{

// private static final Log log = LogFactory.getLog(ScriptRunner.class);

private Connection connection;
private String driver;
private String url;
private String username;
private String password;
private boolean stopOnError;
private boolean autoCommit;
private PrintWriter logWriter = new PrintWriter( System.out );
private PrintWriter errorLogWriter = new PrintWriter( System.err );

/**
 * Default constructor
 */
public ScriptRunner( Connection aConnection, boolean aAutoCommit, boolean aStopOnError )
{
	connection = aConnection;
	autoCommit = aAutoCommit;
	stopOnError = aStopOnError;
}

public ScriptRunner( String aDriver, String aUrl, String aUsername, String aPassword, boolean aAutoCommit,
        boolean aStopOnError )
{
	driver = aDriver;
	url = aUrl;
	username = aUsername;
	password = aPassword;
	autoCommit = aAutoCommit;
	stopOnError = aStopOnError;
}

/**
 * Setter for logWriter property
 *
 * @param aLogWriter
 *            - the new value of the logWriter property
 */
public void setLogWriter( PrintWriter aLogWriter )
{
	logWriter = aLogWriter;
}

/**
 * Setter for errorLogWriter property
 *
 * @param aErrorLogWriter
 *            - the new value of the errorLogWriter property
 */
public void setErrorLogWriter( PrintWriter aErrorLogWriter )
{
	errorLogWriter = aErrorLogWriter;
}

/**
 * Runs an SQL script (read in using the Reader parameter)
 *
 * @param reader
 *            - the source of the script
 */
public void runScript( Reader reader ) throws IOException, SQLException
{
	try
	{
		if ( connection == null )
		{
			DriverManager.registerDriver( (Driver) Resources.classForName( driver ).newInstance() );

			try ( Connection conn = DriverManager.getConnection( url, username, password ); )
			{
				if ( conn.getAutoCommit() != autoCommit )
				{
					conn.setAutoCommit( autoCommit );
				}
				runScript( conn, reader );
			}
		}
		else
		{
			boolean originalAutoCommit = connection.getAutoCommit();
			try
			{
				if ( originalAutoCommit != autoCommit )
				{
					connection.setAutoCommit( autoCommit );
				}
				runScript( connection, reader );
			}
			finally
			{
				connection.setAutoCommit( originalAutoCommit );
			}
		}
	}
	catch ( IOException e )
	{
		throw e;
	}
	catch ( SQLException e )
	{
		throw e;
	}
	catch ( Exception e )
	{
		throw new RuntimeException( "Error running script.  Cause: " + e, e );
	}
}

/**
 * Runs an SQL script (read in using the Reader parameter) using the connection passed in
 *
 * @param conn
 *            - the connection to use for the script
 * @param reader
 *            - the source of the script
 * @throws SQLException
 *             if any SQL errors occur
 * @throws IOException
 *             if there is an error reading from the Reader
 */
private void runScript( Connection conn, Reader reader ) throws IOException, SQLException
{
	StringBuffer command = null;
	try
	{
		LineNumberReader lineReader = new LineNumberReader( reader );
		String line = null;
		while ( ( line = lineReader.readLine() ) != null )
		{
			if ( command == null )
			{
				command = new StringBuffer();
			}
			String trimmedLine = line.trim();
			if ( trimmedLine.startsWith( "--" ) )
			{
				println( trimmedLine );
			}
			else if ( trimmedLine.length() < 1 || trimmedLine.startsWith( "//" ) )
			{
				// Do nothing
			}
			else if ( trimmedLine.length() < 1 || trimmedLine.startsWith( "--" ) )
			{
				// Do nothing
			}
			else if ( trimmedLine.endsWith( ";" ) )
			{
				command.append( line.substring( 0, line.lastIndexOf( ";" ) ) );
				command.append( " " );
				try ( Statement statement = conn.createStatement(); )
				{

					println( command );

					boolean hasResults = false;
					if ( stopOnError )
					{
						try
						{
							hasResults = statement.execute( command.toString() );
						}
						catch ( SQLDataException e )
						{
							e.printStackTrace();
						}
					}
					else
					{
						try
						{
							statement.execute( command.toString() );
						}
						catch ( SQLException e )
						{
							e.fillInStackTrace();
							printlnError( "Error executing: " + command );
							printlnError( e );
						}
					}

					if ( autoCommit && !conn.getAutoCommit() )
					{
						conn.commit();
					}

					ResultSet rs = statement.getResultSet();
					if ( hasResults && rs != null )
					{
						ResultSetMetaData md = rs.getMetaData();
						int cols = md.getColumnCount();
						for ( int i = 0; i < cols; i++ )
						{
							String name = md.getColumnName( i );
							print( name + "\t" );
						}
						println( "" );
						while ( rs.next() )
						{
							for ( int i = 0; i < cols; i++ )
							{
								String value = rs.getString( i );
								print( value + "\t" );
							}
							println( "" );
						}
					}

					command = null;
					Thread.yield();
				}
			}
			else
			{
				// PU 2007-08-12 end-of-line comments ook strippen
				int commentPos = line.indexOf( "--" );
				if ( commentPos >= 0 )
				{
					command.append( line.substring( 0, commentPos ) );
				}
				else
				{
					command.append( line );
				}
				command.append( " " );
			}
		}
		if ( !autoCommit )
		{
			conn.commit();
		}
	}
	catch ( SQLException e )
	{
		// e.fillInStackTrace();
		printlnError( "Error executing: " + command );
		printlnError( e );
		throw e;
	}
	catch ( IOException e )
	{
		e.fillInStackTrace();
		printlnError( "Error executing: " + command );
		printlnError( e );
		throw e;
	}
	finally
	{
		conn.rollback();
		flush();
	}
}

private void print( Object o )
{
	if ( logWriter != null )
	{
		System.out.print( o );
	}
}

private void println( Object o )
{
	if ( logWriter != null )
	{
		logWriter.println( o );
	}
}

private void printlnError( Object o )
{
	if ( errorLogWriter != null )
	{
		errorLogWriter.println( o );
	}
}

private void flush()
{
	if ( logWriter != null )
	{
		logWriter.flush();
	}
	if ( errorLogWriter != null )
	{
		errorLogWriter.flush();
	}
}

}
