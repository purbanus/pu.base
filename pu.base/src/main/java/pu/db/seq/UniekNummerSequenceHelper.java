package pu.db.seq;

import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * SequenceHelper voor de AS/400 die ALG460 aanroept voor een nieuw uniek nummer
 */
public class UniekNummerSequenceHelper extends JdbcDaoSupport implements SequenceHelper
{
private SimpleJdbcCall caller = null;
private String procedureName;
private String schemaName;

public UniekNummerSequenceHelper()
{
	super();
}
public String getSchemaName()
{
	return schemaName;
}
public String getProcedureName()
{
	return procedureName;
}
public void setProcedureName( String aProcedureName )
{
	procedureName = aProcedureName;
}
public void setSchemaName( String aSchemaName )
{
	schemaName = aSchemaName;
}
public SimpleJdbcCall getCaller()
{
	if ( caller == null )
	{
		// Je moet hier helaas keihard de schemanaam opgeven
		caller = new SimpleJdbcCall( getJdbcTemplate() ).withProcedureName( getProcedureName() ).withSchemaName( getSchemaName() ); // AL77 in Iris
	}
	return caller;
}

@Override
public int getNextInt( String aDummySequenceName )
{
	Integer out = getCaller().executeFunction( Integer.class );
	return out;
}

@Override
public long getNextLong( String aSequenceName )
{
	throw new UnsupportedOperationException( "Next long wordt niet ondersteund door UniekNummerSequenceHelper" );
}

}
