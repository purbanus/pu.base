package pu.services.comm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import pu.log.Log;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import static net.schmizz.sshj.connection.channel.direct.LocalPortForwarder.Parameters;

/**
 * Stuurt TCP/IP verkeer van een locaLe port naar een remote server die het weer doorstuurt naar een (andere) port
 * op die server.
 * Misschien een tikje onhandig want dat ding van SSHJ heet ook LocalPortForwarder. Wij gebruiken alleen het Parameter classje
 * daarin dus het kan net.
 */
public class LocalPortForwarder implements Runnable
{
	private static final Logger LOG = LogManager.getLogger( LocalPortForwarder.class );

	private static final String LOCAL_HOST = "localhost";
	private static final String FORWARD_TO_HOST = "localhost";
	
    private String server = null;
    private String user = null;
    private String password = null;
    private int localPort = -31415;
    private int forwardToPort = -31415;

    private ServerSocket serverSocket = null;

public static void main( String... args ) throws IOException, InterruptedException
{
	LocalPortForwarder tunnel = new LocalPortForwarder( "omg-odin-test.global.com", "omgadmin", "kl4t3r44r", 3900, 3306 );
	tunnel.start();
	tunnel.stop();
}

public LocalPortForwarder()
{
	super();
}
public LocalPortForwarder( String aServer, String aUser, String aPassword, int aLocalPort, int aForwardToPort )
{
	super();
	server = aServer;
	user = aUser;
	password = aPassword;
	localPort = aLocalPort;
	forwardToPort = aForwardToPort;
}

public int getLocalPort()
{
	return localPort;
}
public void setLocalPort( int aLocalPort )
{
	localPort = aLocalPort;
}
public int getForwardToPort()
{
	return forwardToPort;
}
public void setForwardToPort( int aForwardToPort )
{
	forwardToPort = aForwardToPort;
}
private String getServer()
{
	return server;
}
public void setServer( String aServer )
{
	server = aServer;
}
private String getUser()
{
	return user;
}
public void setUser( String aUser )
{
	user = aUser;
}
private String getPassword()
{
	return password;
}
public void setPassword( String aPassword )
{
	password = aPassword;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Bizniz

public ServerSocket getServerSocket() throws IOException
{
	if ( serverSocket == null )
	{
		InetSocketAddress socketAddress = new InetSocketAddress( LOCAL_HOST, getLocalPort() );
		serverSocket = new ServerSocket();
        serverSocket.setReuseAddress( true );
        serverSocket.bind( socketAddress );
	}
	return serverSocket;
}
public void start()
{
	Thread t = new Thread( this, "LocalPortForwarder" );
	t.start();
}
public void stop()
{
	if ( serverSocket != null )
	{
    	try
        {
    		serverSocket.close();
        }
        catch ( IOException e )
        {
    		throw new LocalPortForwarderException( e );
        }
    	finally
    	{
    		serverSocket = null;
    	}
	}
}
@SuppressWarnings( "resource" )
@Override
public void run()
{
	

	try ( final SSHClient ssh = new SSHClient(); )
	{
    	ssh.addHostKeyVerifier( new PromiscuousVerifier() );
    	ssh.connect( getServer() );
		ssh.authPassword( getUser(), getPassword() );
		Parameters params = new Parameters( LOCAL_HOST, getLocalPort(), FORWARD_TO_HOST, getForwardToPort() );
        ssh.newLocalPortForwarder( params, getServerSocket() ).listen();
	}
    catch ( SocketException e )
    {
    	if ( "socket closed".equals( e.getMessage() ) )
    	{
    		// Volstrekt normaal
    		LOG.info( "Forwarding socket closed" );
    	}
    	else
    	{
    		LOG.error( "Fout tijdens forwarden", e );
    	}
    }
    catch ( IOException e )
    {
		LOG.error( "Fout tijdens forwarden", e );
    }
	finally
	{
    	if ( serverSocket != null )
    	{
            try
            {
    	        serverSocket.close();
            }
            catch ( IOException e )
            {
        	    Log.error( "Error during ServerSocket close", e );
            }
            serverSocket = null;
    	}
	}
}
}
