package pu.services.comm;

import java.io.IOException;

public class DefaultTcpServerSocketFactory implements TcpServerSocketFactory
{
	private final int port;
	
/**
 * @param aPort
 */
public DefaultTcpServerSocketFactory( final int aPort )
{
	super();
	port = aPort;
}

@Override
public int getPort()
{
	return port;
}

@Override
public TcpServerSocket createTcpServerSocket() throws IOException
{
	return new TcpServerSocketImpl( getPort() );
}

}
