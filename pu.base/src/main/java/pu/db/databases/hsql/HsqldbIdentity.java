/**
 *
 */
package pu.db.databases.hsql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.object.StoredProcedure;

/**
 * this calls the HSQLDB identity for recent transaction.
 * @author knguyen
 * created on Sep 16, 2004
 * Later commentaar van de verbeteraar:
 * You can get the identity using this jdbc syntax:
 * [{call IDENTITY()}]
 * You can also retrieve HSQLDB IDENTITY value using
 * select identity()
 */
public class HsqldbIdentity extends StoredProcedure
{
private class DemoRowMapper implements RowMapper<Integer>
{
@Override
public Integer mapRow( ResultSet rs, int rowNum ) throws SQLException
{
	return rs.getInt( 1 );
}
}

public HsqldbIdentity( DataSource ds )
{
	super( ds, "IDENTITY" );
	declareParameter( new SqlReturnResultSet( "p0", new DemoRowMapper() ) );

	compile();
}

@SuppressWarnings("rawtypes")
public Integer getIdentityAsInteger()
{
	Map<String, Object> out = execute( new HashMap<>() );
	List lst = (List) out.get( "p0" );

	return (Integer) lst.get( 0 );
}

}

