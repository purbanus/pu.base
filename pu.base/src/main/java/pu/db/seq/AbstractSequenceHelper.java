package pu.db.seq;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public abstract class AbstractSequenceHelper extends JdbcDaoSupport implements SequenceHelper
{
public AbstractSequenceHelper()
{
	super();
}

protected abstract String getNextValueSql( String aSequenceName );

@Override
public int getNextInt( String aSequenceName )
{
	return getJdbcTemplate().queryForObject( getNextValueSql( aSequenceName ), Integer.class );
}

@Override
public long getNextLong( String aSequenceName )
{
	return getJdbcTemplate().queryForObject( getNextValueSql( aSequenceName ), Long.class );
}
}
