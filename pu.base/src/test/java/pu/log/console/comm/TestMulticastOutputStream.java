package pu.log.console.comm;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

/**
 * @@NOG Veel meer testen!
 * Creation date: (5-7-01 16:00:16)
 * @author: Peter Urbanus
 */
public class TestMulticastOutputStream
{
@Test
public void test() throws Exception
{
	final String MSG = "Have a nice day";
	// Gek. moeten deze twee niet in een try-with-resources?
	ByteArrayOutputStream os1 = new ByteArrayOutputStream();
	ByteArrayOutputStream os2 = new ByteArrayOutputStream();
	
	try ( 	MulticastOutputStream multi = new MulticastOutputStream(); 
			PrintStream ps = new PrintStream( multi );
	)
	{
		multi.addStream( os1 );
		multi.addStream( os2 );
		
		ps.print( MSG );
	}
	assertEquals( MSG, os1.toString() );
	assertEquals( MSG, os2.toString() );
}
}
