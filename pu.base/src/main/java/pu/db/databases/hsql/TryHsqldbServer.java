/**
 *
 */
package pu.db.databases.hsql;

import pu.services.LogHelper;
import pu.services.SpringHelper;

/**
 * @author Peter Urbanus
 *
 */
public class TryHsqldbServer
{

/**
 * @param args
 */
public static void main( String [] args )
{
	LogHelper.setUpLog4j( TryHsqldbServer.class );
	SpringHelper.setupApplicationContext( TryHsqldbServer.class );
	try
	{
		Thread.currentThread().join();
	}
	catch ( InterruptedException e )
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}


}

}
