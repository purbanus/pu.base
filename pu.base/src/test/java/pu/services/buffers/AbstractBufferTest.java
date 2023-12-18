package pu.services.buffers;

import static org.junit.Assert.*;

/**
 * Abstract testclass.
 */

public class AbstractBufferTest
{
protected void compare( byte [] a, byte [] b )
{
	assertEquals( " compare sizes",  a.length, b.length );
	for ( int x = 0; x < a.length; x++ )
		assertEquals( " compare#" + x, a[x], b[x] );
}
protected void compare( String aTestName, Object [] a, Object [] b )
{
	assertEquals( aTestName + " compare sizes",  a.length, b.length );
	for ( int x = 0; x < a.length; x++ )
		assertEquals( " compare#" + x, a[x], b[x] );
}
}
