package pu.services.comm;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import pu.log.Log;
import pu.services.Range;

/**
 * Utilities for Sockets
 */
public class SocketHelper
{
	public static final int DEFAULT_FIND_FREE_SERVER_SOCKET_MAX_TRIES = 100;
	public static final int LIST_USED_PORTS_MAX_IO_EXCEPTIONS = 10;

/**
 * private constructor, we don't want instances
 */
private SocketHelper()
{
	super();
}

public static ServerSocket findFreeServerSocket( int aStartPort )
{
	return findFreeServerSocket( aStartPort, DEFAULT_FIND_FREE_SERVER_SOCKET_MAX_TRIES );
}

public static ServerSocket findFreeServerSocket( int aStartPort, int aMaxTries )
{
	for ( int x = 0; x < aMaxTries; x++ )
	{
		try
		{
			ServerSocket sock = new ServerSocket( aStartPort + x );
			Log.debug(  SocketHelper.class, "Found free port: " + sock.getLocalPort() );
			return sock;
		}
		catch ( BindException e )
		{
		}
		catch ( IOException e )
		{
			Log.error( SocketHelper.class, e );
			return null;
		}
	}
	return null;
}

/**
 * Lists used ports in a certain range
 */
public static void listUsedPorts( Range aRange )
{
	List<Integer> used = new ArrayList<>();
	ServerSocket ss;
	int ioExceptions = 0;
	int port;
	for ( port = aRange.from; port <= aRange.to; port++ )
	{
		try
		{
			ss = new ServerSocket( port );
			ss.close();
		}
		catch ( BindException e )
		{
			used.add( port );
		}
		catch ( IOException e )
		{
			Log.error( SocketHelper.class, e );
			used.add( port );
			if ( ++ioExceptions >= LIST_USED_PORTS_MAX_IO_EXCEPTIONS )
			{
				break;
			}
		}
	}
	System.out.println( "Used sockets between " + aRange.from + " and " + port + ": " + used );
}
}
