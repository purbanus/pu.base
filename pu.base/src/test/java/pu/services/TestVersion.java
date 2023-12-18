package pu.services;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests voor Version class
 */
public class TestVersion
{
@Test
public void testVersion()
{
	assertEquals( "Dit is voor de test", Version.getVersion( "pu.services" ) );
}
@Test
public void testPrintVersion()
{
	assertEquals( "0.0.0", Version.getVersion( "pu" ) );
}


}
