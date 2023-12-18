package pu.db.databases.blobs;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import pu.db.DataResource;
import pu.db.SimpleDataResource;
import pu.db.SimpleDatabaseFramework;

public abstract class AbstractBlobTest extends SimpleDatabaseFramework
{

public static final int ID_INDEX = 1;
public static final int BLOB_INDEX = 2;
public static final int CLOB_INDEX = 3;

public static final DataResource POSTGRES = new SimpleDataResource(
		"org.postgresql.Driver",
		"jdbc.url=jdbc:postgresql://localhost/pu",
		"postgres",
		"Pannekoek33"
		);

public static final DataResource HSQLDB = new SimpleDataResource(
		"org.hsqldb.jdbcDriver",
		"jdbc:hsqldb:hsql://localhost:63333/oswf",
		"sa",
		""
		);

/**
 * @param aDataResource
 */
public AbstractBlobTest()
{
	super( POSTGRES );
}

/**
 * @param aKopje
 * @param blobBytes
 */
protected void print100Bytes( String aKopje, byte [] blobBytes )
{
	System.out.print( aKopje );
	for ( int x = 0; x < 100; x++ )
	{
		System.out.print( blobBytes[x] + " " );
	}
}

protected void printReader( Reader reader ) throws IOException
{
	int ch;
	while ( -1 != ( ch = reader.read() ) )
	{
		System.out.print( (char) ch );
	}
}

protected void printInputStream( InputStream is ) throws IOException
{
	int ch;
	while ( -1 != ( ch = is.read() ) )
	{
		System.out.print( (char) ch );
	}
}

protected void print100Bytes( String aKopje, InputStream is ) throws IOException
{
	byte [] bytes = new byte[100];
	is.read( bytes );
	System.out.println();
	print100Bytes( aKopje, bytes );
}

}
