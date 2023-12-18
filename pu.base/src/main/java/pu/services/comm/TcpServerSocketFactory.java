/**
 * 
 */
package pu.services.comm;

import java.io.IOException;

/**
 * @author Peter Urbanus
 *
 */
public interface TcpServerSocketFactory
{
	/*************** vervallen
	public static final TcpServerSocketFactory DEFAULT = new TcpServerSocketFactory()
	{
		//	public TcpSocket createTcpSocket( Socket aSocket )
		//	{
		//		return new TcpSocketImpl( aSocket );
		//	}
		public TcpServerSocket createTcpServerSocket( int aPort ) throws IOException
		{
			return new TcpServerSocketImpl( aPort );
		}
	};
	**************/

public abstract int getPort();
public abstract TcpServerSocket createTcpServerSocket() throws IOException;

/**
 * Nog even achter de hand houden voor de exception-handling hierin
 * @param aPort The port to bind to
 * @return The newly created ServerSocket
 */
/***********
private static TcpServerSocket createServerSocket( int aPort )
{
	if ( aPort <= 0 )
	{
		throw new IllegalArgumentException( "Port must be a positive number" );
	}
	ServerSocket socket = null;
	try
	{
		socket = new ServerSocket( aPort );
	}
	catch ( IOException e )
	{
		Log.error( TcpServerSocketImpl.class, "Could not listen on port: " + aPort, e );
		throw new RuntimeException( e );
	}
	return new TcpServerSocketImpl( socket );
}
*************/

}
