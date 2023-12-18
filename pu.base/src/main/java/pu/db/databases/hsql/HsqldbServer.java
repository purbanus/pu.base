/*
 * Copyright 2004 The original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
 * Grappig: dit vond ik in het "spring" experiment van Ugo Cei
 * Zie:
 * C:\Install\Java\Apache\Cocoon\2.1.5.1\Uitpak\Cocoon Hibernate Spring\src\blocks\spring
 * en
 * C:\Install\Java\Apache\Cocoon\2.1.5.1\Uitpak\Cocoon Hibernate Spring\build\cocoon-2.1.5.1\blocks\spring\dest\org\apache\cocoon\petstore\model
 * voor de *.hbm.xml files. Ik denk datr die gegenereerd zijn/worden.
 */
package pu.db.databases.hsql;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.web.context.WebApplicationContext;

/*
Stukje uit org.hsqldb.test.TestBase:
====================================
if (isNetwork) {
    url         = "jdbc:hsqldb:hsql://localhost/test";
    server      = new EchoServer();
    server.setDatabaseName(0,"test");
    server.setDatabasePath(0,"mem:test;sql.enforce_strict_size=true");
    server.setLogWriter(null);
    server.setErrWriter(null);
    server.start();
} else {
    url = "jdbc:hsqldb:file:test;sql.enforce_strict_size=true";
}

Mijn server.properties
======================
server.port=44440
server.database.0=rlog
server.dbname.0=rlog
server.silent=false
server.trace=true

 */

/**
 * Description of HsqlServer.
 *
 * @version CVS $Id$
 */
public class HsqldbServer implements Runnable, ApplicationContextAware
{
public static final int DEFAULT_PORT = 9001;
public static final String THREAD_NAME = "hsqldb server";
public static final String SERVLET_CONTEXT_PREFIX = "servletcontext:";

private DataSource dataSource;
private String databasePath;
private String databaseAlias;

private boolean trace = false;
private boolean silent = true;
private int port = DEFAULT_PORT;
private boolean compactOnShutdown = false;

public HsqldbServer()
{
	super();
}

public static String [] mapToArgs( Map<String, Object> aMap )
{
	String [] args = new String [aMap.size() * 2];
	int index = 0;
	for ( Map.Entry<String, Object> entry : aMap.entrySet() )
	{
		args[index++] = "-" + entry.getKey();
		args[index++] = String.valueOf( entry.getValue() );
	}
	return args;
}

public void start()
{
	Thread server = new Thread(this);
	server.setPriority( Thread.currentThread().getPriority() );
	server.setDaemon( true );
	server.setName( THREAD_NAME );
	server.start();
}

public void stop()
{
	try(
		Connection connection = getDataSource().getConnection();
		Statement statement = connection.createStatement();
	)
	{
		statement.executeQuery( "SHUTDOWN" + ( isCompactOnShutdown() ? " COMPACT" : "" ) );
	}
	catch ( Exception e )
	{
		/* FIXME getLogger().error("Error while shutting down HSQLDB", e); */
	}
}

/* (non-Javadoc)
 * @see java.lang.Runnable#run()
 */
@Override
public void run()
{
	Map<String, Object> argMap = new HashMap<>();
	argMap.put( "port", getPort() );
	argMap.put( "silent", isSilent() );
	argMap.put( "trace", isTrace() );
	argMap.put( "no_system_exit", true );
	argMap.put( "database.0", getDatabasePath() );
	argMap.put( "dbname.0", getDatabaseAlias() );

	// FIXME: log
	System.err.println("Starting HSQL server...");
	// HIGH 2008-03-02 Ik kam die EchoServer nergens (meer) vinden
	//org.hsqldb.EchoServer.main( mapToArgs( argMap ) );
}

/* (non-Javadoc)
 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
 */
@Override
public void setApplicationContext( ApplicationContext appCtx ) throws BeansException
{
	if ( getDatabasePath().startsWith( SERVLET_CONTEXT_PREFIX ) )
	{
		if ( ! ( appCtx instanceof WebApplicationContext ) )
		{
			throw new RuntimeException( "When using \"servletcontext:\" in databasePath, we must be running as a servlet!" );
		}
		WebApplicationContext webCtx = (WebApplicationContext) appCtx;
		String dbPath = getDatabasePath().substring( SERVLET_CONTEXT_PREFIX.length() );
		dbPath = webCtx.getServletContext().getRealPath( dbPath );
		try
		{
			setDatabasePath( new File(dbPath).getCanonicalPath() );
		}
		catch ( IOException e )
		{
			throw new ApplicationContextException( "IOException on database path " + dbPath, e );
		}
	}
}

//============================================================================================
// Getters en setters
//============================================================================================

/**
 * @return Returns the databaseAlias.
 */
public String getDatabaseAlias()
{
	return databaseAlias;
}

/**
 * @return Returns the databasePath.
 */
public String getDatabasePath()
{
	return databasePath;
}

/**
 * @return Returns the dataResource.
 */
public DataSource getDataSource()
{
	return dataSource;
}

/**
 * @return Returns the port.
 */
public int getPort()
{
	return port;
}

/**
 * @return Returns the compactOnShutdown.
 */
public boolean isCompactOnShutdown()
{
	return compactOnShutdown;
}

/**
 * @return Returns the silent.
 */
public boolean isSilent()
{
	return silent;
}

/**
 * @return Returns the trace.
 */
public boolean isTrace() {
	return trace;
}

/**
 * @param aCompactOnShutdown The compactOnShut to set.
 */
public void setCompactOnShutdown( boolean aCompactOnShutdown )
{
	compactOnShutdown = aCompactOnShutdown;
}

/**
 * @param silent The silent to set.
 */
public void setSilent( boolean aSilent )
{
	silent = aSilent;
}

/**
 * @param trace The trace to set.
 */
public void setTrace( boolean aTrace )
{
	trace = aTrace;
}

/**
 * @param aDatabaseAlias The databaseAlias to set.
 */
public void setDatabaseAlias( String aDatabaseAlias )
{
	databaseAlias = aDatabaseAlias;
}

/**
 * @param aDatabasePath The databasePath to set.
 */
public void setDatabasePath( String aDatabasePath )
{
	databasePath = aDatabasePath;
}

/**
 * @param aDataSource The dataSource to set.
 */
public void setDataSource( DataSource aDataSource )
{
	dataSource = aDataSource;
}

/**
 * @param port The port to set.
 */
public void setPort( int aPort)
{
	port = aPort;
}


}
