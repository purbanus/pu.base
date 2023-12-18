package pu.services.seq.db;


import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import pu.db.util.DbUtil;
import pu.log.Log;
import pu.services.Range;
import pu.services.StringHelper;
import pu.services.seq.LimitHandler;

/**
 */

public class JdbcLimitHandler extends JdbcDaoSupport implements LimitHandler
{
// TODO configureerbaar maken
public static final String SEQUENCE_TABLE_NAME = "SEQUENCE_TABLE";
public static final String COL_TABLE  = "TABLE_NAME";
public static final String COL_NUMBER = "NEXTNUMBER";
public static final String [] COLUMNS = { COL_TABLE, COL_NUMBER };

private final PlatformTransactionManager transactionManager;
private final int blockSize;

/**
 *
 */
public JdbcLimitHandler( PlatformTransactionManager aTransactionManager, DataSource aDataSource, int aBlockSize )
{
	super();
	transactionManager = aTransactionManager;
	setDataSource( aDataSource );
	blockSize = aBlockSize;
}

private String createInsertStatement()
{
	// Session-id moet eruit want dat is een auto-increment field
	StringBuffer sb = new StringBuffer()
	.append( "INSERT INTO " )
	.append( SEQUENCE_TABLE_NAME )
	.append( " ( " )
	.append( StringHelper.implode( COLUMNS, ", " ) )
	.append( " )" )
	.append( " VALUES ( " )
	.append( DbUtil.questionMarkList( COLUMNS.length ) )
	.append( " )" )
	;
	return sb.toString();
}
private String createSelectStatement()
{
	StringBuffer sb = new StringBuffer()
	.append( "SELECT " )
	.append( COL_NUMBER )
	.append( " FROM " )
	.append( SEQUENCE_TABLE_NAME )
	.append( " WHERE " )
	.append( COL_TABLE ).append( " = ? " )
	;
	return sb.toString();
}

private String createSelectAllStatement()
{
	StringBuffer sb = new StringBuffer()
	.append( "SELECT " )
	.append( "*" )
	.append( " FROM " )
	.append( SEQUENCE_TABLE_NAME )
	.append( " ORDER BY " )
	.append( COL_TABLE )
	;
	return sb.toString();
}

private String createUpdateStatement()
{
	StringBuffer sb = new StringBuffer()
	.append( "UPDATE " )
	.append( SEQUENCE_TABLE_NAME )
	.append( " SET " )
	.append( COL_NUMBER ).append( " = ? " )
	.append( " WHERE " )
	.append( COL_TABLE ).append( " = ? " )
	;
	return sb.toString();
}

/**
 * @return
 */
public int getBlockSize()
{
	return blockSize;
}

private PlatformTransactionManager getTransactionManager()
{
	return transactionManager;
}


@Override
public Range newRange( final String aTableName, final int aOldEnd ) // throws DataAccessException
{
	TransactionTemplate tt = new TransactionTemplate( getTransactionManager() );
	Range ret = tt.execute( aStatus -> newRangeImpl( aTableName, aOldEnd ));
	Log.info( this, "JdbcLimithandler new Range: " + ret );
	return ret;
}


public Range newRangeImpl( String aTableName, int aOldEnd )
{
	// TODO Als de hele tabel nog niet bestaat kun je hem nu aanmaken

	try
	{
		int newStart = getJdbcTemplate().queryForObject(
				createSelectStatement(),
				new Object [] { aTableName },
				new int [] { Types.INTEGER },
				Integer.class
				);
		Range range = calcNewRange( newStart );
		getJdbcTemplate().update(
				createUpdateStatement(),
				new Object [] { Integer.valueOf( range.to + 1 ), aTableName },
				new int [] { Types.INTEGER, Types.VARCHAR }
				);
		return range;
	}
	catch ( IncorrectResultSizeDataAccessException e )
	{
		if ( e.getActualSize() > 1 )
		{
			Log.error( this, "Database fout: sequence for table " + aTableName + " returned " + e.getActualSize() + " rows, should have been 0 or 1" );
			throw e;
		}
		// e.getActualSize() moet nu 0 zijn, dwz er is nog geen record voor onze table
		Range range = calcNewRange( 1 );
		getJdbcTemplate().update(
				createInsertStatement(),
				new Object [] { aTableName, Integer.valueOf( range.to + 1 ) },
				new int [] { Types.VARCHAR, Types.INTEGER }
				);
		return range;
	}
}

private Range calcNewRange( int aStart )
{
	return new Range( aStart, aStart + getBlockSize() - 1 );
}

@Override
public boolean hasNext()
{
	return true;
}

public List<Map<String, Object>> getAllRows()
{
	return getJdbcTemplate().queryForList( createSelectAllStatement() );
}
}
