/*
 * Created on 4-jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db.databases.postgresql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import pu.db.DataResource;
import pu.db.SimpleDataResource;
import pu.db.SimpleDatabaseFramework;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PostgresBlob extends SimpleDatabaseFramework
{
private static final DataResource POSTGRES = new SimpleDataResource(
		"org.postgresql.Driver",
		"jdbc:postgresql://localhost/imagedb",
		//"jdbc:postgresql://localhost/",
		"postgres",
		"Pannekoek33"
		);

/**
 *
 */
public PostgresBlob()
{
	super( POSTGRES );
}

public static void main(String[] args)
{
	new PostgresBlob().verwerk();
	System.exit(0);
}

@Override
protected void verwerkImpl(Connection c) throws SQLException, IOException
{
	File image = new File( "/home/purbanus/Pictures/vrouwen/great-timing-photos16.jpg" );
	FileInputStream imageIs = new FileInputStream( image );
	File text = new File( "/home/purbanus/Documents/taal/3000scheldwoorden.txt" );

	try ( 
		Reader textReader = new InputStreamReader( new FileInputStream( text ) );
		// PU heeft als search_path pu, public dus nu zou BLOB unqualified moeten kunnen. Maar werkt niet!
		PreparedStatement stm = c.prepareStatement( "INSERT INTO IMAGEDB ( IMAGE_NAME, CONTENT, DESCRIPTION ) VALUES ( ?, ?, ? )" );
	)
	{
		stm.setString( 1, "Great Timing" );
		stm.setBinaryStream   ( 2, imageIs   , (int) image.length() );
		stm.setCharacterStream( 3, textReader, (int) text.length() );
		stm.executeUpdate();
	}
}

}
