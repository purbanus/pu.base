package pu.services.comm;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TcpSocketImpl implements TcpSocket
{
private final Socket socket;
public TcpSocketImpl( Socket aSocket )
{
	super();
	socket = aSocket;
}

@Override
public void close() throws IOException
{
	socket.close();
}

@Override
public InputStream getInputStream() throws IOException
{
	return socket.getInputStream();
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


}
