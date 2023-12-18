/**
 *
 */
package pu.db.util;

/**
 * @author Peter Urbanus
 *
 */
public class DbUtil
{

/**
 *
 */
private DbUtil()
{
	super();
}

public static String questionMarkList( int aAantal )
{
	StringBuffer sb = new StringBuffer();
	if ( aAantal > 0 )
	{
		sb.append( "?" );
	}
	for ( int x = 1; x < aAantal; x++ )
	{
		sb.append( ", ?" );
	}
	return sb.toString();
}

}
