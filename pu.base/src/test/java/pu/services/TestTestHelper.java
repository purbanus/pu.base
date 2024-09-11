package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @@NOG Veel meer testen!
 */
public class TestTestHelper
{
@Test
public void testBasics()
{
	TestHelper th = new TestHelper();

	// Change this if your path is different
	// @@NOG Omzetten naar Linux
	String expectedPath;
	if ( Util.isWindows() )
	{
		expectedPath = "C:\\Projecten\\Iris\\Testcases\\nl.mediacenter.services.test.TestTestHelper\\";
	}
	else
	{
		String userName = System.getProperty(  "user.name" );
		expectedPath = "/home/" + userName + "/projecten/iris/Testcases/pu.services.TestTestHelper/";
	}

	assertEquals( expectedPath, th.getTestCaseRoot( this ) );
	assertEquals( expectedPath + "test.testcase", th.getTestCaseFile( this, "test" ) );

	assertEquals( th.getTestCaseRoot( this ), th.getTestCaseRoot( this.getClass() ) );
	assertEquals( th.getTestCaseFile( this, "test" ), th.getTestCaseFile( this.getClass(), "test" ) );
}
}
