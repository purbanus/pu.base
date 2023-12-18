package pu.crypto.ssl.https;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class CheckInstall
{

public CheckInstall()
{
	// TODO Auto-generated constructor stub
}

/**
 * @param args
 */
public static void main( String [] args )
{

	HttpClient httpclient = new HttpClient();
	GetMethod httpget = new GetMethod( "https://www.verisign.com/" );
	try
	{
		httpclient.executeMethod( httpget );
		System.out.println( httpget.getStatusLine() );
		System.out.println( httpget.getResponseBodyAsString() );
	}
	catch ( IOException e )
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finally
	{
		httpget.releaseConnection();
	}
}
}
