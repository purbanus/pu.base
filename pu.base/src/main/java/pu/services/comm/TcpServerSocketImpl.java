package pu.services.comm;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpServerSocketImpl implements TcpServerSocket
{
	private final ServerSocket socket;
	
TcpServerSocketImpl( ServerSocket aSocket )
{
	super();
	socket = aSocket;
}

TcpServerSocketImpl( int aPort ) throws IOException
{
	super();
	if ( aPort <= 0 )
	{
		throw new IllegalArgumentException( "Port must be a positive number" );
	}
	socket = new ServerSocket( aPort );
}

@Override
public void close() throws IOException
{
	socket.close();
}

@Override
public String getIpAddress()
{
	return socket.getInetAddress().getHostAddress();
}

@Override
public int getLocalPort() 
{
	return socket.getLocalPort();
}

@Override
public TcpSocket accept() throws IOException
{
	return new TcpSocketImpl( socket.accept() );
}

}
