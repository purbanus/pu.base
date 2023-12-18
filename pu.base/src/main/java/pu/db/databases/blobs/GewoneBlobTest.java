/*
 * Created on 4-jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db.databases.blobs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.util.FileCopyUtils;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GewoneBlobTest extends AbstractBlobTest
{
/**
 *
 */
public GewoneBlobTest()
{
	super();
}

public static void main(String[] args)
{
	new GewoneBlobTest().verwerk();
	System.exit(0);
}

@Override
protected void verwerkImpl(Connection c) throws SQLException, IOException
{

	//	long nietVergetenNieuwId = 1;
	//	insertValues( c, nietVergetenNieuwId );
	//	nietVergetenNieuwId++;
	//	insertStreams( c, nietVergetenNieuwId );

	//read( c );
	readImages( c );
}

/**
 * @param c
 * @throws SQLException
 * @throws IOException
 */
@SuppressWarnings( "unused" )
private void read( Connection c ) throws SQLException, IOException
{
	//ResultSet rs = c.createStatement().executeQuery( "SELECT * FROM PU.BLOB" );
	ResultSet rs = c.createStatement().executeQuery( "SELECT * FROM BLOB" );
	while ( rs.next() )
	{
		System.out.println( "id=" + rs.getLong( ID_INDEX ) );

		// N.B. Bij alle stream=methodes staat dat je de hele stream moet lezen voor je
		//      een andere kolom mag opvragen, of liever dat zodra je een andere kolom opvraagt,
		//      de stream gesloten wordt.
		//      En verder dat Blobs en Clobs alleen geldig zijn binnen de huidige transactie.

		//----------------------------------------------------------------------------------
		// BLOB
		//----------------------------------------------------------------------------------
		{
			// Dit mag niet met een BYTEA
			//Blob blob = rs.getBlob( BLOB_INDEX ); // Bad value for type int: <hele lange sliert blobdata>
			//byte [] bytes = blob.getBytes( 1, 100 );
		}
		{
			// Dit is ok
			byte [] bytes = rs.getBytes( BLOB_INDEX );
			System.out.println( "#bytes: " + bytes.length );
			print100Bytes( "bytes =", bytes );
		}
		{
			// Kunnen we ook streamen? Ja hoor.
			InputStream is = rs.getBinaryStream( BLOB_INDEX );
			print100Bytes( "stream=", is );
		}
		//----------------------------------------------------------------------------------
		// CLOB
		//----------------------------------------------------------------------------------
		{
			// Dit mag niet met een TEXT
			//Clob clob = rs.getClob( CLOB_INDEX ); // Bad value for type int: <hele lange sliert tekst>
			//System.out.println( clob.toString() );
		}
		{
			// Ok
			System.out.println( rs.getString( CLOB_INDEX ) );
		}
		{
			// Streamen?
			InputStream is = rs.getAsciiStream( CLOB_INDEX );
			// NIET DOEN, sluit out!!! FileHelper.copyStream( is, System.out );
			printInputStream( is );

			Reader reader = rs.getCharacterStream(  CLOB_INDEX );
			printReader( reader );
		}
	}
}

/**
 * @param c
 * @throws FileNotFoundException
 * @throws SQLException
 * @throws IOException
 */
@SuppressWarnings( "unused" )
private void insertValues( Connection c, long newId ) throws FileNotFoundException, SQLException, IOException
{
	File imgFile = new File( Constants.IMG_NAME );
	File txtFile = new File( Constants.TXT_NAME );
	try ( FileInputStream imgInputStream = new FileInputStream( imgFile );
	      Reader txtReader = new InputStreamReader( new FileInputStream( txtFile ) );
	      // PU heeft als search_path pu, public dus nu zou BLOB unqualified moeten kunnen. Maar werkt niet!
	      //PreparedStatement stm = c.prepareStatement( "INSERT INTO PU.BLOB ( ID, BLOB, CLOB ) VALUES ( ?, ?, ? )" );
	      PreparedStatement stm = c.prepareStatement( "INSERT INTO BLOB ( ID, BLOB, CLOB ) VALUES ( ?, ?, ? )" );
	)
	{
		stm.setLong( ID_INDEX, newId );
		{
			byte [] imgBytes = new byte[(int) imgFile.length()];
			imgInputStream.read( imgBytes );
			stm.setBytes( BLOB_INDEX, imgBytes );
		}
		{
			char [] txtChars = new char[(int) txtFile.length()];
			txtReader.read( txtChars );
			stm.setString( CLOB_INDEX, new String( txtChars ) );
		}
		int aantal = stm.executeUpdate();
		System.out.println( "Aantal inserted: " + aantal );
	}
}

/**
 * @param c
 * @throws FileNotFoundException
 * @throws SQLException
 * @throws IOException
 */
@SuppressWarnings( "unused" )
private void insertStreams( Connection c, long newId ) throws FileNotFoundException, SQLException, IOException
{
	File imgFile = new File( Constants.IMG_NAME );
	File txtFile = new File( Constants.TXT_NAME );
	try ( FileInputStream imgInputStream = new FileInputStream( imgFile );
	      Reader txtReader = new InputStreamReader( new FileInputStream( txtFile ) );
	      // PU heeft als search_path pu, public dus nu zou BLOB unqualified moeten kunnen. Maar werkt niet!
	      //PreparedStatement stm = c.prepareStatement( "INSERT INTO PU.BLOB ( ID, BLOB, CLOB ) VALUES ( ?, ?, ? )" );
	      PreparedStatement stm = c.prepareStatement( "INSERT INTO BLOB ( ID, BLOB, CLOB ) VALUES ( ?, ?, ? )" );
	)
	{
		stm.setLong( 1, newId );
		{
			stm.setBinaryStream   ( 2, imgInputStream   , (int) imgFile.length() );
		}
		{
			stm.setCharacterStream( 3, txtReader, (int) txtFile.length() );
		}
		int aantal = stm.executeUpdate();
		System.out.println( "Aantal inserted: " + aantal );
	}
}

private void readImages( Connection c ) throws SQLException, IOException
{
	int index = 1;
	//ResultSet rs = c.createStatement().executeQuery( "SELECT * FROM PU.BLOB" );
	ResultSet rs = c.createStatement().executeQuery( "SELECT * FROM BLOB" );
	while ( rs.next() )
	{
		System.out.println( "id=" + rs.getLong( ID_INDEX ) );

		{
			// Dit is ok
			byte [] bytes = rs.getBytes( BLOB_INDEX );
			System.out.println( "#bytes: " + bytes.length );
			InputStream is = new ByteArrayInputStream( bytes );
			OutputStream os = new FileOutputStream( "image" + index++ + ".jpg" );
			FileCopyUtils.copy( is, os );
		}
		{
			// Kunnen we ook streamen? Ja hoor.
			InputStream is = rs.getBinaryStream( BLOB_INDEX );
			OutputStream os = new FileOutputStream( "image" + index++ + ".jpg" );
			FileCopyUtils.copy( is, os );
		}
	}
}

}
