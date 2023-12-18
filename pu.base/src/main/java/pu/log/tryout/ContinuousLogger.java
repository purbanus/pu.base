package pu.log.tryout;

import pu.log.Log;

/**
 * @author Peter Urbanus
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ContinuousLogger
{
private int counter = 0;
/**
 *
 */
public ContinuousLogger()
{
	super();
}

public static void main( String [] args )
{
	new ContinuousLogger().run();
}

public void run()
{
	for ( ;; )
	{
		if ( counter % 2 == 0 )
		{
			Log.info( this, "Opmerking " + ++counter );
		}
		else
		{
			Log.error( this, "Opmerking " + ++counter );
		}
		try
		{
			Thread.sleep( 1000 );
		}
		catch ( InterruptedException e )
		{
			Log.error( this, e );
		}
	}
}

}
