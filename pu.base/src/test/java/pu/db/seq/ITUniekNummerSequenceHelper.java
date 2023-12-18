package pu.db.seq;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import junit.framework.TestCase;

// @@NOG AS400 -> PostgreSQL
public class ITUniekNummerSequenceHelper extends TestCase
{
	SingleConnectionDataSource dataSource;

@Override
public void setUp() throws Exception
{
//	// driver mag je niet meer opgeven
//	//String driver   = "com.ibm.as400.access.AS400JDBCDriver";
//	String url      = "jdbc:as400://mc.mch/mcmdtatst;errors=full;prompt=false;hold statements=true";
//	String username = "TS#PU";
//	String password = "QWERTYU";
//
//	// Gek, vroeger hoefde dat niet, registerDriver
//	//DriverManager.registerDriver((Driver) Resources.classForName(driver).newInstance());
//	DriverManager.registerDriver( new AS400JDBCDriver() );
//	//Connection conn = DriverManager.getConnection(url, username, password);
//	dataSource = new SingleConnectionDataSource( url, username, password, false );
}
public void testGetNextInt()
{
//	UniekNummerSequenceHelper helper = new UniekNummerSequenceHelper();
//	helper.setProcedureName( "ALG460" );
//	helper.setSchemaName( "MCMPGM" ); // Die moet je helaas keihard opgeven
//	helper.setDataSource( dataSource );
//	System.out.println( "Next int: " + helper.getNextInt( "" ) );
}
public void testGetNextIntFromWatUNR()
{
//	UniekNummerSequenceHelper helper = new UniekNummerSequenceHelper();
//	helper.setProcedureName( "W@UNR" );
//	helper.setSchemaName( "ALUPGM" ); // Die moet je helaas keihard opgeven
//	helper.setDataSource( dataSource );
//	System.out.println( "Next int: " + helper.getNextInt( "" ) );
}
public void testGetNextLong()
{
	// Die doet het niet
}

}
