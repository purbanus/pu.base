package pu.services.comm;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;

public class PortFinder
{
public static void main( String [] args )
{
	final int START_PORT = 8000;
	final int END_PORT = 9000;
	ServerSocket sock = null;
	System.out.println( "We zoeken poorten tussen " + START_PORT + " en " + END_PORT );
	boolean printBezet = false;
	for ( int x = START_PORT; x <= END_PORT; x++ )
	{
		try
		{
			sock = new ServerSocket( x );
			// Als je vrije ports wilt afdrukken
			//System.err.println( sock.getLocalPort() );
			sock.close();
		}
		catch ( BindException e )
		{
			// Als een port bezet is krijg je een BindException
			if ( ! printBezet )
			{
				System.err.print( "Bezet: " );
				printBezet = true;
			}
			System.err.print( x + " " );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
			printBezet = false;
		}
	}
	System.out.println( "\nKlaar!" );
}

}
	