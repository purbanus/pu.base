package pu.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testing the MatrixFormatter class.
 */
public class TestMatrixFormatter
{
@Test
public void test() throws IOException
{
	MatrixFormatter mf = new MatrixFormatter();
	mf.addHeader( "Dit is de test" );
	mf.addHeader( "====================================================" );
	mf.addDetail( "Aatjes\tBeetjes\tCeetjes" );
	mf.addHeader( "====================================================" );
	mf.addDetail( new String [] { "a"  , "bb" , "ccc" } );
	mf.addDetail( new String [] { "aaa", "bsb", "c" } );
	List<String> strings = new ArrayList<>();
	strings.add( "aa" );
	strings.add( "b" );
	mf.addDetail( strings );
	
	mf.setColumnSpacing( 2 );
	mf.setDefaultAlignment( MatrixFormatter.ALIGN_RIGHT );
	mf.setAlignment( 2, MatrixFormatter.ALIGN_CENTER );
	mf.setAlignment( 8, MatrixFormatter.ALIGN_CENTER );
	
	String actual = mf.getOutput();

	// Uncomment this line to create a new testfile
	//FileHelper.writeFile( TestHelper.getTestCaseFile( this, "test" ), actual );
	
	String expected = FileHelper.readFile( TestHelper.getTestCaseFile( this, "test" ) );
	// Dit filetje is ooit op Windows gemaakt dus de line endings zijn onjuist
	expected = expected.replaceAll( "\r\n", "\n" );
	
	if ( ! expected.equals( actual ) )
		System.out.println( StringHelper.showDiff( expected, actual ) );
		
/*************
	//System.out.println( actual );
	int length = expected.length();
	if ( expected.length() > actual.length() )
		length = actual.length();
	for ( int x = 0; x < length; x++ )
	{
		char e = expected.charAt( x );
		char a = actual.charAt( x );
		//if ( e != a )
		//{
			int eint = e;
			int aint = a;
			System.out.println( x + "\t" + e + "\t(" + eint + ")\t" + a + "\t(" + aint + ")" );
		//}
	}
****************/

	assertEquals( expected, actual );
}
}
