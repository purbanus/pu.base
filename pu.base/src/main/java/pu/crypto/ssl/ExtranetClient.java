package pu.crypto.ssl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ExtranetClient
{

public ExtranetClient()
{
	// TODO Auto-generated constructor stub
}

/**
 * @param args
 */
public static void main( String [] arstring )
{
	// https://rmis.extranet.mediacenter.nl/rvd-prd/home/index.html
	try
	{
		SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket sslsocket = (SSLSocket) sslsocketfactory.createSocket( "rmis.extranet.mediacenter.nl", 9999 );

		InputStream inputstream = System.in;
		InputStreamReader inputstreamreader = new InputStreamReader( inputstream );
		BufferedReader bufferedreader = new BufferedReader( inputstreamreader );

		OutputStream outputstream = sslsocket.getOutputStream();
		OutputStreamWriter outputstreamwriter = new OutputStreamWriter( outputstream );
		BufferedWriter bufferedwriter = new BufferedWriter( outputstreamwriter );

		String string = null;
		while ( ( string = bufferedreader.readLine() ) != null )
		{
			bufferedwriter.write( string + '\n' );
			bufferedwriter.flush();
		}
	}
	catch ( Exception exception )
	{
		exception.printStackTrace();
	}
	System.out.println( "Client klaar!" );
}
}
