/*
 * Created on 23-nov-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db.databases.mckoi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Basics
{
private static final String USER = "admin_user";
private static final String PWD  = "aupass00";

private Connection con = null;
private Statement stm  = null;
private ResultSet rs   = null;

/**
 * h
 */
public Basics()
{
	super();
}

public static void main(String[] args)
{
	new Basics().verwerk();
}
@SuppressWarnings( "unused" )
private void setConfig()
{
	// Hoeft niet als we de server gebruiken
	// Hm, misschien anders ook niet
	//System.setProperty()
}
private void checkDriver()
{
	try
	{
		Class.forName( "com.mckoi.JDBCDriver" );
	}
	catch ( ClassNotFoundException e )
	{
		e.printStackTrace();
		System.exit(0);
	}
}
private void close(Connection aCon, Statement aStm, ResultSet aRs)
{
	if ( aRs != null )
	{
		try
		{
			aRs.close();
		}
		catch ( SQLException ignore )
		{
		}
	}
	if ( aStm != null )
	{
		try
		{
			aStm.close();
		}
		catch ( SQLException ignore )
		{
		}
	}
	if ( aCon != null )
	{
		try
		{
			aCon.close();
		}
		catch ( SQLException ignore )
		{
		}
	}
}
private void createTable1() throws SQLException
{
	int res;
	res = stm.executeUpdate(
			"CREATE TABLE APP.TABLE1" +
					"( " +
					"NUMMER INTEGER NOT NULL," +
					"NAAM VARCHAR(50)," +
					"CONSTRAINT PK_TABLE1 PRIMARY KEY (NUMMER)" +
					" )"
			);
	System.out.println( "res create table=" + res ); // Should be 0
}

private void dropTable1() throws SQLException
{
	int res;
	res = stm.executeUpdate(
			"DROP TABLE APP.TABLE1"
			);
	System.out.println( "res drop table=" + res ); // Should be 0

}

private void handleSQLException(SQLException e)
{
	e.printStackTrace();
	while ( e.getNextException() != null )
	{
		e = e.getNextException();
		e.printStackTrace();
	}
}
private void insertIntoTable1( int aKey, String aName ) throws SQLException
{
	int res = stm.executeUpdate( "INSERT INTO APP.TABLE1 ( NUMMER, NAAM ) Values ( " + aKey + ", '" +  aName + "' )" );
	System.out.println( "res add Pietje=" + res ); // Should be 0 Ahem, but it is 1
}

private void printTable1() throws SQLException
{
	rs = stm.executeQuery( "select * from APP.TABLE1" );
	while ( rs.next() )
	{
		System.out.print( rs.getObject( 1 ) + "\t");
		System.out.println( rs.getObject( 2 ) );
	}
}


public void verwerk()
{
	checkDriver();

	try
	{
		//verwerkDropTable1Table1();
		//verwerk_1();
		verwerkTransactions();
	}
	catch ( SQLException e )
	{
		handleSQLException(e);
	}
	finally
	{
		close( con, stm, rs );
	}
}
public void verwerk_1() throws SQLException
{
	// NET
	//final String url = "jdbc:mckoi://localhost/";

	// APP
	//final String url = "jdbc:mckoi:local://c:/projecten/databases/mckoi/data/local/db.conf?create=true";
	//final String url = "jdbc:mckoi:local://c:/projecten/databases/mckoi/data/local/db.conf";
	final String url = "jdbc:mckoi:local://projecten/databases/mckoi/data/local/db.conf";

	// MEM
	//final String url = "jdbc:mckoi:local://./db.conf";

	// Daar gaattie dan
	con = DriverManager.getConnection( url, USER, PWD );
	stm = con.createStatement();

	// Drop de oude boel en maak de nieuwe
	dropTable1();
	createTable1();

	// Paar reccies
	insertIntoTable1( 1, "Pietje" );
	insertIntoTable1( 2, "Jantje" );

	printTable1();
}
public void verwerkDropTable1Table1() throws SQLException
{
	final String url = "jdbc:mckoi://localhost/";

	con = DriverManager.getConnection( url, USER, PWD );
	stm = con.createStatement();
	stm.executeUpdate( "DROP TABLE TEST1.TABLE1" );
}

public void verwerkTransactions() throws SQLException
{
	// Dit is een MEM url
	final String url = "jdbc:mckoi:local://projecten/databases/mckoi/data/mem/db.conf?create=true";

	// Daar gaattie dan
	con = DriverManager.getConnection( url, USER, PWD );
	con.setAutoCommit( false );

	stm = con.createStatement();

	// printTable1(); // table bestaat niet

	// Create table
	createTable1();
	con.commit();

	// Nu vragen we ons af: bestaat die table
	insertIntoTable1( 1, "Popie" );
	printTable1();
}

}