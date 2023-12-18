/*
 * Created on 23-nov-03
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.db.databases.cloudscape;

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

/**
 * h
 */
public Basics()
{
	super();
}

public static void main(String[] args)
{
	System.out.println( "hallo" );
	try
	{
		Class.forName( "com.ibm.db2j.jdbc.DB2jDriver" );
	}
	catch ( ClassNotFoundException e )
	{
		e.printStackTrace();
		System.exit(0);
	}

	try ( Connection con = DriverManager.getConnection( "jdbc:db2j:C:\\Projecten\\Cloudscape\\test1" );
	      Statement stm  = con.createStatement();
	      ResultSet rs   = stm.executeQuery( "select * from app.table1" );
	)
	{
		while ( rs.next() )
		{
			System.out.print( rs.getObject( 1 ) + "\t");
			System.out.println( rs.getObject( 2 ) );
		}
	}
	catch ( SQLException e )
	{
		e.printStackTrace();
		while ( e.getNextException() != null )
		{
			e = e.getNextException();
			e.printStackTrace();
		}
	}
}
}
