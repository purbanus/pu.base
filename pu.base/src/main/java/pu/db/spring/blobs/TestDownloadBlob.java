package pu.db.spring.blobs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.LobRetrievalFailureException;
import org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.FileCopyUtils;

public class TestDownloadBlob extends JdbcDaoSupport
{
private LobHandler lobHandler;

public static void main( String [] args ) throws DataAccessException, IOException
{
	@SuppressWarnings( "resource" )
	ApplicationContext applicationContext = new ClassPathXmlApplicationContext( "pu/db/spring/blobs/applicationContext.xml" );
	TestDownloadBlob app = (TestDownloadBlob) applicationContext.getBean( "testDownloadBlob" );
	app.run();
}

public LobHandler getLobHandler()
{
	return lobHandler;
}
public void setLobHandler( LobHandler aLobHandler )
{
	lobHandler = aLobHandler;
}

private void run() throws DataAccessException, IOException
{
	try ( FileOutputStream achterwerkje = new FileOutputStream( "achterwerkje.jpg" ) )
	{
		streamImage( "Leuk achterwerkje", achterwerkje );
	}
}

public void streamImage( final String name, final OutputStream contentStream ) throws DataAccessException
{
	getJdbcTemplate().query( "SELECT content FROM imagedb WHERE image_name=?", new Object [] { name }, new AbstractLobStreamingResultSetExtractor<>()
	{
		@Override
		protected void handleNoRowFound() throws LobRetrievalFailureException
		{
			throw new EmptyResultDataAccessException( "Image with name '" + name + "' not found in database", 1 );
		}

		@Override
		public void streamData( ResultSet rs ) throws SQLException, IOException
		{
			try ( InputStream is = lobHandler.getBlobAsBinaryStream( rs, 1 ); )
			{
				if ( is != null )
				{
					FileCopyUtils.copy( is, contentStream );
				}
			}
		}
	} );
}

}
