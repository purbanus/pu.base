package pu.db.seq;

import javax.sql.DataSource;

public interface SequenceHelper
{
//public abstract String getNextValueSql( String aSequenceName );
public int getNextInt( String aSequenceName );
public long getNextLong( String aSequenceName );
public void setDataSource( DataSource aDataSource );
}
