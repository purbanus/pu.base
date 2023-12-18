/*
 * Created on 4-jan-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db.databases.hsql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import pu.db.SimpleDataResource;
import pu.db.SimpleDatabaseFramework;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CreateHsqldbMessages extends SimpleDatabaseFramework
{

/**
 *
 */
public CreateHsqldbMessages()
{
	super( SimpleDataResource.HSQL_NET );
}

public static void main(String[] args)
{
	new CreateHsqldbMessages().verwerk();
	System.exit(0);
}

@Override
protected void verwerkImpl(Connection c) throws SQLException
{
	final String TABLE = "HIBERNATE_MESSAGES";
	Statement stm = c.createStatement();
	String sql;

	//sql = "DROP TABLE " + TABLE;
	//stm.execute( sql );
	//System.out.println( "Table dropped" );

	sql = "CREATE TABLE " + TABLE + "(" +
			"MESSAGE_ID BIGINT IDENTITY" +
			", MESSAGE_TEXT VARCHAR(100)" +
			", NEXT_MESSAGE BIGINT" +
			//", CONSTRAINT PK_" + TABLE + " PRIMARY KEY (MESSAGE_ID)" +
			")"
			;
	stm.execute( sql );

	// Je mag een primary key niet achteraf toevoegen blijkbaar
	//sql = "ALTER TABLE Table1 ADD CONSTRAINT PK_NUMMER PRIMARY KEY (NUMMER)";
	//stm.execute( sql );
}

}
