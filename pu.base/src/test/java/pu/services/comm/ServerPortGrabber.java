/*
 * Created on 5-mrt-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pu.services.comm;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

import pu.log.Log;

/**
 * @author Peter Urbanus
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServerPortGrabber
{
static final int PORT = 44443;

/**
 *
 */
public ServerPortGrabber()
{
	super();
}

public static void main( String [] args )
{
	new ServerPortGrabber().grab( PORT );
	try
	{
		Thread.currentThread().join();
	}
	catch ( InterruptedException e )
	{
		Log.info( ServerPortGrabber.class, "Interrupted! ", e );
	}
	System.out.println( "Finished" );
}

public void grab( int aPort )
{
	try ( ServerSocket serverSocket = new ServerSocket( aPort ); )
	{
		Log.info( this, "Port grabbed: " + aPort );
	}
	catch ( BindException e )
	{
		// Probably address in use
		Log.info( this, "Could not listen on port: " + aPort, e );
	}
	catch ( IOException e )
	{
		Log.info( this, "Geheimzinnige IOException ontvangen: ", e );
	}
}
}
