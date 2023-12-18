/*
 * Created on 23-nov-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db.databases.cloudscape;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CreateTest1Table
{

/**
 * h
 */
public CreateTest1Table()
{
	super();
}

public static void main(String[] args)
{
	System.out.println( "hallo" );
	try
	{
		Class.forName( "com.ibm.db2j.jdbc.DB2jDriver" ); // LOCAL
		//Class.forName( "com.ibm.db2.jcc.DB2Driver" ); //SERVER
	}
	catch ( ClassNotFoundException e )
	{
		e.printStackTrace();
		System.exit(0);
	}

	/******** SERVER. In de server-versie was de database al eerder gemaakt, alleen de tabel nog niet
	//con = DriverManager.getConnection( "jdbc:db2j:net://localhost" );
	//con = DriverManager.getConnection( "jdbc:db2j:net://localhost:1527" );
	//con = DriverManager.getConnection( "jdbc:db2j:net://localhost:1527/test1" );
	//con = DriverManager.getConnection( "jdbc:db2j:net://localhost:1527/test1:" );
	con = DriverManager.getConnection( "jdbc:db2j:net://localhost:1527/test1:user=APP;password=APP;" );
	//con = DriverManager.getConnection( "jdbc:db2j:net//localhost:1527/\"c:/projecten/cloudscape/test1\"" );
	 **************/

	/********LOCAL ******/
	// Deze maakt m in de workspace, rechstreeks onder je project
	//con = DriverManager.getConnection( "jdbc:db2j:local/test1;create=true" );
	// Dus...
	try (
		Connection con = DriverManager.getConnection( "jdbc:db2j:C:/Projecten/Cloudscape/local/test1;create=true" );
		Statement stm  = con.createStatement();
	)
	{
		// Maak mijn testtabelletje
		stm.executeUpdate(
				"create table app.table1"
						+ "("
						+	" nummer INTEGER NOT NULL,"
						+	" naam VARCHAR(50),"
						+	" CONSTRAINT PK_TABLE1 PRIMARY KEY (NUMMER)"
						+ ")"
				);
		System.out.println( "table gemaakt" );

	}
	catch ( SQLException e )
	{
		e.printStackTrace();
	}


}
}
