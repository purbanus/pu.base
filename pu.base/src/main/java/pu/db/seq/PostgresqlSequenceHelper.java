package pu.db.seq;

/**
 * SequenceHelper die werkt voor Postgresql.
 */
public class PostgresqlSequenceHelper extends AbstractSequenceHelper
{

public PostgresqlSequenceHelper()
{
	super();
}

@Override
protected String getNextValueSql( String aSequenceName )
{
	return "SELECT nextval(' " + aSequenceName + "' )";
}

}
