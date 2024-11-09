package pu.services.testing;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import pu.services.StopWatch;

public abstract class AbstractDaoTryout
{
	private static final Logger LOG = LogManager.getLogger( AbstractDaoTryout.class );

	private ApplicationContext applicationContext;
	
public AbstractDaoTryout()
{
	super();
}
protected void run()
{
	StopWatch timer = new StopWatch();
	
	LOG.info("Initializing application");
	LOG.info( "---------------------------------------------------------" );
	loadDatabaseScripts();

	applicationContext = new ClassPathXmlApplicationContext( getConfigLocations() );

	String setupTime = timer.getLapTimeMs();
	try
	{
		doTransaction();
	}
	catch ( Exception e )
	{
		e.printStackTrace();
	}
	
	LOG.info("Setup     : " + setupTime );
	LOG.info("Verwerking: " + timer.getElapsedMs() );
}

private void doTransaction() throws IOException, URISyntaxException
{
	TransactionTemplate tt = new TransactionTemplate( getTransactionManager() );
	tt.execute( new TransactionCallback<>()
	{
		@Override
		public Object doInTransaction( TransactionStatus aStatus )
		{
			try
			{
				verwerk();
			}
			catch ( Exception e )
			{
				throw new RuntimeException( e );
			}
			return null;
		}
	});
}

protected ApplicationContext getApplicationContext()
{
	return applicationContext;
}

protected PlatformTransactionManager getTransactionManager()
{
	return (PlatformTransactionManager) getApplicationContext().getBean( "transactionManager" );
}

protected void listCollection( Collection<?> aCollection )
{
	for ( Iterator<?> it = aCollection.iterator(); it.hasNext(); )
	{
		LOG.info( it.next() );
	}
	LOG.info( "---------------------------------------------------------" );
}

protected abstract void loadDatabaseScripts();
protected abstract String [] getConfigLocations();
protected abstract void verwerk() throws Exception;

}
