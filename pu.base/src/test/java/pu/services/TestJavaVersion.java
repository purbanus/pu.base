package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @@NOG Veel meer testen!
 * Creation date: (5-7-01 16:00:16)
 * @author: Peter Urbanus
 */
public class TestJavaVersion
{
@Test
public void testJavaVersion()
{
	// De beste manier om te testen is, denk ik, om nog een class te pakken die alleen in JAVA2 zit
	JavaVersion expected;
	try
	{
		Class.forName( "java.util.Collection" );
		expected = JavaVersion.JAVAVERSION1_2;
	}
	catch ( ClassNotFoundException e )
	{
		expected = JavaVersion.JAVAVERSION1_1;
	}
	
	assertEquals( expected, JavaVersion.getJavaVersion() );
}
@Test
public void testVersionTriplet()
{
	assertTrue( JavaVersion.V131.compareTo( JavaVersion.V122 ) > 0 );
	assertTrue( JavaVersion.V131.compareTo( JavaVersion.V131 ) == 0 );
	assertTrue( JavaVersion.V131.compareTo( JavaVersion.V142 ) < 0 );
	assertTrue( JavaVersion.V131.compareTo( JavaVersion.V150 ) < 0 );

	assertTrue( JavaVersion.V131.compareTo( new JavaVersion.VersionTriplet( 1, 3, 0 ) ) > 0 );
	assertTrue( JavaVersion.V131.compareTo( new JavaVersion.VersionTriplet( 1, 3, 1 ) ) == 0 );
	assertTrue( JavaVersion.V131.compareTo( new JavaVersion.VersionTriplet( 1, 3, 2 ) ) < 0 );
}

}
