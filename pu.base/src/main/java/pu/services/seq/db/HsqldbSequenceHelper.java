/*
 * Created on 14-sep-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.services.seq.db;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 */
public class HsqldbSequenceHelper extends JdbcDaoSupport
{

public HsqldbSequenceHelper()
{
	super();
}

public void createTable()
{
	// Paar dingen:
	// - In een andere database dan HSQL moet je wellicht ook nog een schema maken
	// - Hoe groot is INTEGER eigenlijk in de verschillende databases?
	// - Het is ironisch dat nu juist SEQUENCE zelf geen integer key heeft!!

	getJdbcTemplate().update( "DROP TABLE " + JdbcLimitHandler.SEQUENCE_TABLE_NAME + " IF EXISTS" );

	StringBuffer sql = new StringBuffer()
	.append( "CREATE CACHED TABLE " )
	.append( JdbcLimitHandler.SEQUENCE_TABLE_NAME )
	.append( " ( " )
	.append( JdbcLimitHandler.COL_TABLE  ).append( " VARCHAR(100) NOT NULL" )
	.append( ", " )
	.append( JdbcLimitHandler.COL_NUMBER ).append( " INTEGER NOT NULL" )
	.append( ", " )
	.append( "CONSTRAINT PK_SEQUENCE PRIMARY KEY ( " )
	.append( JdbcLimitHandler.COL_TABLE )
	.append( " )" )
	.append( " )" )
	;
	getJdbcTemplate().update( sql.toString() );

	//System.out.println( "SEQUENCE table created" );
}

public void dropTableIfExists()
{
	getJdbcTemplate().update( "DROP TABLE " + JdbcLimitHandler.SEQUENCE_TABLE_NAME + " IF EXISTS" );
	//System.out.println( "SEQUENCE table dropped" );
}

}
