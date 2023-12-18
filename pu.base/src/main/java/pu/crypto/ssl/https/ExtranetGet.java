package pu.crypto.ssl.https;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

public class ExtranetGet
{

public ExtranetGet()
{
	// TODO Auto-generated constructor stub
}

/**
 * @param args
 */
public static void main( String [] args )
{

	HttpClient httpclient = new HttpClient();
	GetMethod httpget = new GetMethod( "https://rmis.extranet.mediacenter.nl/rvd-prd/home/index.html" );
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
