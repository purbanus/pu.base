package pu.services.spring;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;


public class TableCopy
{
	private static final Logger LOG = LogManager.getLogger( TableCopy.class );

	private JdbcTemplate sourceJdbcTemplate = null;
	private JdbcTemplate targetJdbcTemplate = null;

public TableCopy()
{
	super();
}

public JdbcTemplate getSourceJdbcTemplate()
{
	return sourceJdbcTemplate;
}
public void setSourceJdbcTemplate( JdbcTemplate aSourceJdbcTemplate )
{
	sourceJdbcTemplate = aSourceJdbcTemplate;
}
public JdbcTemplate getTargetJdbcTemplate()
{
	return targetJdbcTemplate;
}
public void setTargetJdbcTemplate( JdbcTemplate aTargetJdbcTemplate )
{
	targetJdbcTemplate = aTargetJdbcTemplate;
}

public void copy( String aSourceTable, String aSqlTail, String aTargetTable )
{
	List<Map<String, Object>> data = getSourceJdbcTemplate().queryForList( "select * from " + aSourceTable + " " + aSqlTail );
	checkFields( aSourceTable, data );
	getTargetJdbcTemplate().execute( "truncate table " + aTargetTable );

	//LOG.info( "TableCopy before: connection=" + System.identityHashCode( getCurrentConnection() ) );

	JdbcBatcher batcher = new JdbcBatcher( getTargetJdbcTemplate(), aTargetTable, 1000 );
	int aantal = 0;
	for ( Map<String, Object> map : data )
    {
		batcher.add( map );
		aantal++;
    }
	batcher.finish();
	//LOG.info( "TableCopy after : connection=" + System.identityHashCode( getCurrentConnection() ) );
	LOG.info( aantal + " records toegevoegd aan " + aTargetTable );
}

@SuppressWarnings( "unused" )
private Connection getCurrentConnection()
{
	return DataSourceUtils.getConnection( getTargetJdbcTemplate().getDataSource() );
}
private void checkFields( String aSourceTable, List<Map<String, Object>> aData )
{
	// Vroeger was BNPOS# een veld, dat vervingen we hier door BNPOSN. Maar dat is aangepast op de AS/400
	/*************
	if ( ! aSourceTable.equals( "PTBNPOS" ) )
	{
		return;
	}
	for ( Map<String, Object> map : aData )
    {
	    Object obj = map.remove( "BNPOS#" );
	    map.put( "BNPOSN", obj );
    }
    ********************/
}

}
