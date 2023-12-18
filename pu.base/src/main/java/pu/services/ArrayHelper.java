/**
 * 
 */
package pu.services;

/**
 * @author Peter Urbanus
 *
 */
public class ArrayHelper
{

/**
 * 
 */
private ArrayHelper()
{
	super();
}

public static Object [] combineArrays( Object [] a, Object [] b )
{
	if ( a == null )
	{
		return b;
	}
	if ( b == null )
	{
		return a;
	}
	Object [] res = new Object [a.length + b.length];
	System.arraycopy( a, 0, res, 0       , a.length );
	System.arraycopy( b, 0, res, a.length, b.length );
	return res;
}

public static String [] combineArrays( String [] a, String [] b )
{
	if ( a == null )
	{
		return b;
	}
	if ( b == null )
	{
		return a;
	}
	String [] res = new String [a.length + b.length];
	System.arraycopy( a, 0, res, 0       , a.length );
	System.arraycopy( b, 0, res, a.length, b.length );
	return res;
}
}
