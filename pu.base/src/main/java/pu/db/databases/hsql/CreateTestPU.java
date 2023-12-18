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
public class CreateTestPU extends SimpleDatabaseFramework
{

/**
 *
 */
public CreateTestPU()
{
	super( SimpleDataResource.HSQL_NET );
}

public static void main(String[] args)
{
	new CreateTestPU().verwerk();
	System.exit(0);
}

@Override
protected void verwerkImpl(Connection c) throws SQLException
{
	Statement stm = c.createStatement();
	String sql;

	//sql = "DROP TABLE Table1";
	//stm.execute( sql );
	//System.out.println( "Table dropped" );

	sql = "CREATE TABLE Table1 (" +
			"nummer INTEGER NOT NULL" +
			", naam VARCHAR(50)" +
			", CONSTRAINT PK_TABLE1 PRIMARY KEY (NUMMER)" +
			")"
			;
	stm.execute( sql );

	// Je mag een primary key niet achteraf toevoegen blijkbaar
	//sql = "ALTER TABLE Table1 ADD CONSTRAINT PK_NUMMER PRIMARY KEY (NUMMER)";
	//stm.execute( sql );
}

}
