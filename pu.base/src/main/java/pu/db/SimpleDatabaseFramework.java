/*
 * Created on 4-jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import pu.services.StopWatch;



/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class SimpleDatabaseFramework
{
private final DataResource dataResource;
/**
 *
 */
public SimpleDatabaseFramework( DataResource aDataResource )
{
	super();
	dataResource = aDataResource;
}

protected void verwerk()
{
	loadDriver();
	try
	{
		Connection c = createConnection();
		StopWatch timer = new StopWatch();
		verwerkImpl(c);
		System.out.println( "Verwerkt in " + timer.getElapsedMs() );
		c.commit();
		System.out.println( "Commit in " + timer.getLapTimeMs() );
	}
	catch ( Exception e )
	{
		e.printStackTrace();
	}
}

protected abstract void verwerkImpl(Connection c) throws Exception;


protected Connection createConnection() throws SQLException
{
	Connection c = DriverManager.getConnection( getDataResource().getUrl(), getDataResource().getUsername(), getDataResource().getPassword() );
	return c;
}
private DataResource getDataResource()
{
	return dataResource;
}
private void loadDriver()
{
	try
	{
		Class.forName ( getDataResource().getDriver() );
	}
	catch ( Exception e )
	{
		System.out.println( "Kan geen driver vinden voor DataResource " + getDataResource().getName() + ". Staat de jar in het classpath?" );
		e.printStackTrace();
	}
}
}
