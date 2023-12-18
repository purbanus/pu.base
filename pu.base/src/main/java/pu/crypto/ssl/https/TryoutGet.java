package pu.crypto.ssl.https;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class TryoutGet
{

public TryoutGet()
{
	// TODO Auto-generated constructor stub
}

/**
 * @param args
 */
public static void main( String [] args )
{
	if ( args.length == 0 )
	{
		System.err.println( "Je moet als eerste parm de URI opgeven" );
		System.exit(1);
	}
	HttpClient httpclient = new HttpClient();
	GetMethod httpget = new GetMethod( args[0] );
	try
	{
		httpclient.executeMethod( httpget );
		System.out.println( httpget.getStatusLine() );
		System.out.println( httpget.getResponseBodyAsString() );
	}
	catch ( IOException e )
	{
		e.printStackTrace();
	}
	finally
	{
		httpget.releaseConnection();
	}
}
}
