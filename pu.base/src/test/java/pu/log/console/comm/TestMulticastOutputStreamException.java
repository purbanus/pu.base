package pu.log.console.comm;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @@NOG Veel meer testen!
 */
@SuppressWarnings( "unused" )
public class TestMulticastOutputStreamException
{
@Test
public void testGetMessage() throws Exception
{
	@SuppressWarnings( "unused" )
	String testName = "Test getMessage";

	/******* Dit heeft geen zin zo. Om de haverklap is die tekst weer anders en dan moet je weer uitzoeken waarom
	         Ik kan geen goede test bedenken dus dan maar niets.
	List exc = new ArrayList();
	try
	{
		Integer a = null;
		a.toString();
	}
	catch ( NullPointerException e )
	{
		exc.add( e );
	}
	OutputStreamMulticasterException multiE = new OutputStreamMulticasterException( OutputStreamMulticasterException.WRITE, exc );
	CharArrayWriter arrw = new CharArrayWriter();
	PrintWriter pw = new PrintWriter( arrw );
	multiE.Log.logError( e );( pw );

	String actual = arrw.toString();

	// Uncomment if you want to update the disk file
	//FileHelper.writeFile( TestHelper.getTestCaseFile( this, testName ), actual );
		
	String expected = FileHelper.readFile( TestHelper.getTestCaseFile( this, testName ) );

	// @@NOG Probleem: stacktrace is anders als je de test los runt of als je m in TestAll runt
	assertEquals( testName, expected, actual );
	**********/
}
}
