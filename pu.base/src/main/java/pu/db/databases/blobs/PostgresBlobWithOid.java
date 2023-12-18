/*
 * Created on 4-jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db.databases.blobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialClob;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class PostgresBlobWithOid extends AbstractBlobTest
{
private static final String TABLE = "PU.BLOB_W_OID";

/**
 *
 */
public PostgresBlobWithOid()
{
	super();
}

public static void main(String[] args)
{
	new PostgresBlobWithOid().verwerk();
	System.exit(0);
}

@Override
protected void verwerkImpl(Connection c) throws SQLException, IOException
{
	long nietVergetenNieuwId = 1;
	insert( c, nietVergetenNieuwId );

	read( c );
}

/**
 * @param c
 * @throws SQLException
 * @throws IOException
 */
private void read( Connection c ) throws SQLException, IOException
{
	ResultSet rs = c.createStatement().executeQuery( "SELECT * FROM " + TABLE );
	while ( rs.next() )
	{
		System.out.println( "id=" + rs.getLong( ID_INDEX ) );

		Blob blob = rs.getBlob( BLOB_INDEX );
		byte [] blobBytes = blob.getBytes( 1, 100 ); // LET OP, getBytes telt vanaf 1, niet 0 !!!!!!!!

		print100Bytes( "bytes =", blobBytes );

		// Streamen?
		InputStream is = blob.getBinaryStream();
		print100Bytes( "stream=", is );
	}
}

/**
 * @param c
 * @throws FileNotFoundException
 * @throws SQLException
 * @throws IOException
 */
private void insert( Connection c, long newId ) throws FileNotFoundException, SQLException, IOException
{
	// Zie de Postgres documentatie: je mag niet met Large Objects werken met autocommit.
	// Logisch want je bent twee tabellen tegelijk aan het muteren
	c.setAutoCommit( false );

	File imgFile = new File( Constants.IMG_NAME );
	File txtFile = new File( Constants.TXT_NAME );
	try (
		FileInputStream imgInputStream = new FileInputStream( imgFile );
	    Reader txtReader = new InputStreamReader( new FileInputStream( txtFile ) );
		// PU heeft als search_path pu, public dus nu zou BLOB unqualified moeten kunnen. Maar werkt niet!
		PreparedStatement stm = c.prepareStatement( "INSERT INTO " + TABLE + " ( ID, BLOB_OID, CLOB_OID ) VALUES ( ?, ?, ? )" );
	)
	{
		stm.setLong( ID_INDEX, newId );
	
		// Dit kan niet meer, maar is wel een beetje jammer. Kan de jdbc-driver niet zien dat dit de kolom
		// een oid is en dan een Stream accepteren?
		// ERROR: column "blob_oid" is of type oid but expression is of type bytea
		// ERROR: column "clob_oid" is of type oid but expression is of type character varying
		// stm.setBinaryStream ( BLOB_INDEX, imgInputStream, (int) imgFile.length() );
		// stm.setCharacterStream( CLOB_INDEX, txtReader, (int) txtFile.length() );
	
		// Het eigenaardige is dat je nergens ter wereld een stm.setBlob() ziet, en ook nergens iemand
		// die een Blob maakt. De enige implementatie is SerialBlob in javax.sql, en niemand gebruikt dat!
		// Idem voor Clob
		{
			byte [] imgBytes = new byte [(int) imgFile.length()];
			imgInputStream.read( imgBytes, 0, (int) imgFile.length() );
			SerialBlob blob = new SerialBlob( imgBytes);
			stm.setBlob( BLOB_INDEX, blob );
		}
		{
			char [] txtChars = new char[(int) txtFile.length()];
			txtReader.read( txtChars );
			SerialClob clob = new SerialClob( txtChars );
			stm.setClob( CLOB_INDEX, clob );
		}
	
		int aantal = stm.executeUpdate();
		System.out.println( "Aantal inserted: " + aantal );

		c.commit();
	}
}

}
