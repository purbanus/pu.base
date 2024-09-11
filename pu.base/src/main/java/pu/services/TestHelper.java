package pu.services;

import java.io.File;
/**
 * Insert the type's description here.
 * Creation date: (16-2-2002 23:00:41)
 * @author: Administrator
 */
public class TestHelper
{
	/**
	 * The root of the directory with testcases
	 */
public static final String USER_NAME = System.getProperty(  "user.name" );
private static final String TESTROOT = ( Util.isWindows() ? "C:\\projecten\\iris\\Testcases\\" : "/home/" + USER_NAME + "/projecten/iris/Testcases/"  );

/**
 * Returns the default testcase file for a particular method of a particular class.
 * @param aClass the class
 * @param aMethod the method
 * @return the default testcase file for this method of the class
 */
public static String getTestCaseFile( Class<?> aClass, String aMethod )
{
	return getTestCaseRoot( aClass ) + aMethod + ".testcase";
}
/**
 * Returns the default testcase file for a particular method of the class to which the object belongs.
 * @param aObject the object
 * @param aMethod the method
 * @return the default testcase file for this method of the object
 */
public static String getTestCaseFile( Object o, String aMethod )
{
	return getTestCaseFile( o.getClass(), aMethod );
}
/**
 * Returns the root directory for testcase files for a particular class.
 * The directory is created if it does not exist.
 * The string returned ends with a path separator.
 * @param aClass the class
 * @return the root for testcase files for the class
 */
public static String getTestCaseRoot( Class<?> c )
{
	String root = TESTROOT + c.getName();
	File f = new File( root );
	if ( ! f.exists() )
		f.mkdirs();
	return root + FileHelper.getFileSeparator();
}
/**
 * Returns the root directory for testcase files for the class to which the passed-in object belongs.
 * The string returned ends with a path separator.
 * @param aObject the object
 * @return the root for testcase files for the class to which the object belongs
 */
public static String getTestCaseRoot( Object o )
{
	return getTestCaseRoot( o.getClass() );
}
/**
 * Converts an array of strings into a 2D array of booleans, where each 0 is turned
 * into <code>false</code> and each 1 into <code>true</code>. Other characters are simply
 * ignored. This is useful if you want to specify lots of booleans, because
 * <pre>
 * new String []
 * {
 * 	"001",
 *	"101",
 * }
 * </pre>
 * is a lot easier to write than
 * <pre>
 * new boolean [][]
 * {
 *	new boolean [] { false, false, true },
 *	new boolean [] { true, false, true },
 * }
 * </pre>
 * You can add spaces and other characters to the strings for readabilily
 */
public static boolean[][] maakBools( String[] s )
{
	boolean [][] b = new boolean[s.length][];
	for ( int x = 0; x < s.length; x++ )
	{
		b[x] = new boolean[s[x].length()]; // Kan iets te lang zijn ivm eventuele spaties in de string
		int bIndex = 0;
		for ( int y = 0; y < s[x].length(); y++ )
		{
			char c = s[x].charAt(y);
			if ( c == '0' || c == '1' )
				b[x][bIndex++] = c == '1';
		}
	}
	return b;
}
/**
 * Converts an array of strings into a 2D array of 'comparison values', that is ints with values
 * -1, 0 or 1. The character '-' is mapped to -1, '=' to 0 and '+' to 1.
 * Characters other than '-', '=' and '+' are simply ignored. This is useful if you want to specify
 * lots of comparisons, because
 * <pre>
 * new String []
 * {
 * 	"++=",
 *	"-=+",
 * }
 * </pre>
 * is somewhat easier to write than
 * <pre>
 * new int [][]
 * {
 *	new int [] {  1, 1, 0 },
 *	new int [] { -1, 0, 1 },
 * }
 * </pre>
 * Please be advised that <code>compare</code> and <code>compareTo</code> of Comparator
 * and Comparable are not required to return -1, 0 or 1; they can return any integer.
 * <p>
 * You can add spaces and other characters to the strings for readabilily
 */
public static int[][] maakCompares( String[] s )
{
	int [][] i = new int[s.length][];
	for ( int x = 0; x < s.length; x++ )
	{
		i[x] = new int[s[x].length()];
		for ( int y = 0; y < s[x].length(); y++ )
		{
			char c = s[x].charAt(y);
			i[x][y] = c == '-' ? -1 : ( c == '=' ? 0 : 1 );
		}
	}
	return i;
}
/**
 * Converts an array of strings into a 2D array of ints in the range 0-9. Characters other than
 * digits are simply ignored. This is useful if you want to specify lots of ints, because
 * <pre>
 * new String []
 * {
 * 	"134",
 *	"738",
 * }
 * </pre>
 * is somewhat easier to write than
 * <pre>
 * new int [][]
 * {
 *	new int [] { 1, 3, 4 },
 *	new int [] { 7, 3, 8 },
 * }
 * </pre>
 * You can add spaces and other characters to the strings for readabilily
 */
public static int[][] maakInts( String[] s )
{
	int [][] i = new int[s.length][];
	for ( int x = 0; x < s.length; x++ )
	{
		i[x] = new int[s[x].length()]; // Kan iets te lang zijn ivm eventuele spaties in de string
		int iIndex = 0;
		for ( int y = 0; y < s[x].length(); y++ )
		{
			char c = s[x].charAt(y);
			if ( c >= '0' && c <= '9' )
				i[x][iIndex++] = c - '0';
		}
	}
	return i;
}
}
