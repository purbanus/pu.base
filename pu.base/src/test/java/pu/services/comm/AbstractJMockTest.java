/**
 *
 */
package pu.services.comm;

import java.util.List;

import org.jmock.cglib.MockObjectTestCase;
import org.junit.Before;
import org.junit.Ignore;

import pu.log.log2.Log;

//@@Dit moet je nog wel een keer oplossen. Ik krijg zeer vreemde fouten.
@Ignore
public class AbstractJMockTest extends MockObjectTestCase
{

protected interface MethodsCalledMaintainer
{
public abstract List<String> getMethodsCalled();
}

public AbstractJMockTest( String aName )
{
	super( aName );
}
@Before
@Override
public void setUp() throws Exception
{
	enableConsoleLogging( false );
}

//////////////////////////////////////////////////////////////////////////////////////
//Utilities

protected void checkMethodsCalled( MethodsCalledMaintainer aMethodsCalledMaintainer, String [] aExpected )
{
	assertEquals( "checkMethodsCalled size", aExpected.length, aMethodsCalledMaintainer.getMethodsCalled().size() );
	for ( int x = 0; x < aExpected.length; x++ )
	{
		assertEquals( aExpected[x], aMethodsCalledMaintainer.getMethodsCalled().get( x ) );
	}
}

private void enableConsoleLogging( boolean b )
{
	Log.enableConsoleLogger( b );
}



}
