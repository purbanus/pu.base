package pu.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import pu.log.log2.Log;

public class Version
{
	public static final boolean DEBUG = false;
	private static final String VERSION_FILE_NAME = "version.id";
	
public Version()
{
	super();
}

public static String getVersion( String aPackageName )
{
	String slashedPackageName = StringHelper.replaceAll( aPackageName, ".", "/" );
	String resourcePath = "/" + slashedPackageName + "/" + VERSION_FILE_NAME;
	URL url = Version.class.getResource( resourcePath );
	if ( DEBUG ) Log.debug( Version.class, "version url=" + url );
	
	try
	{
		if ( url == null )
		{
			throw new IOException( "Resource " + resourcePath + " t.o.v. " + Version.class.getName() + " niet gevonden" );
		}
		InputStream in = url.openStream();
		return FileHelper.readFile( in );
	}
	catch ( IOException e )
	{
		Log.error( Version.class, e.getMessage(), e );
		return "Eror reading: " + aPackageName + ". De fout is " + e.getMessage();
	}
}
}
