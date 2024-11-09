package pu.services.testing;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.util.FileCopyUtils;

import pu.services.StringHelper;
import pu.services.spring.ApplicationContextLogger;

public abstract class McTransactionalSpringContextTests extends AbstractTransactionalJUnit4SpringContextTests
{
private static final Logger LOG = LogManager.getLogger( McTransactionalSpringContextTests.class );
//private static boolean databaseScriptsRan = false;

public static void printClassPath()
{
	StringHelper.printClassPath();
}
public McTransactionalSpringContextTests()
{
	super();
}
//@Before
public void printApplicationContext()
{
	ApplicationContextLogger.log( applicationContext );
}
protected ApplicationContext getApplicationContext()
{
	return applicationContext;
}

//@Before
//public void runScripts() throws Exception
//{
//	if ( ! databaseScriptsRan )
//	{
//	    ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//	    populator.addScripts(
//	        new ClassPathResource( "/dbscripts/testdata.sql" ),
//	        new ClassPathResource( "/dbscripts/standaarddata.sql" ) );
//	//    populator.setSeparator("@@");
//	    populator.execute( dataSource );
//	    databaseScriptsRan = true;
//	}
//}
protected String readFile( String aFileName ) throws IOException
{
	InputStream is = getClass().getResourceAsStream( aFileName );
	// Java 1.7+ Reader in = new BufferedReader( new InputStreamReader( is, StandardCharsets.UTF_8 ), 16384 );
	Reader in = new BufferedReader( new InputStreamReader( is, Charset.forName("UTF-8") ), 16384 );
	return FileCopyUtils.copyToString( in );
}
public void listCollection( Collection<?> aCollection )
{
	for ( Iterator<?> it = aCollection.iterator(); it.hasNext(); )
	{
		LOG.info(  it.next() );
	}
	LOG.info( "---------------------------------------------------------" );
}

}
