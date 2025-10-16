/**
 * 
 */
package pu.services;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

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
	return ArrayUtils.addAll(a, b);
}

public static String [] combineArrays( String [] a, String [] b )
{
	return ArrayUtils.addAll(a, b);
}
}
