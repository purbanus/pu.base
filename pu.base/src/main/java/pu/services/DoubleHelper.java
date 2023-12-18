package pu.services;

/**
 * Utility methods for doubles and floats
 */
public class DoubleHelper
{
/**
 * No DoubleUtil is ever created; all of its methods are static.
 */
private DoubleHelper()
{
	super();
}

/**
 * Tests two doubles for equality within a given precision. Tries to handle the special doubles
 * (infinities and NaN's) correctly.
 * @param a One double
 * @param b Another double
 * @param precision The precision. if a and b are normal doubles, they are considered to be equal to
 * each other if they differ less than <code>precision</code>
 * @return whether the two doubles are to be considered equal
 */
public static boolean equals( double a, double b, double precision )
{
	// We need to test for infinity since a - b = NaN if a or b is infinite
	if ( Double.isInfinite( a ) )
	{
		return a == b;
	}
	else 
	{
		// A NaN should not be equal to anything. The following test happens to handle NaN's correctly
		// since NaN - x = NaN and x - NaN = NaN as well as NaN - NaN = NaN. But see the test cases if
		// you don't believe me.
		return Math.abs( a - b ) <= precision;
	}
}

/**
 * Tests if one double is less than another double within a given precision. Tries to handle the special doubles
 * (infinities and NaN's) correctly.
 * @param a One double
 * @param b Another double
 * @param precision The precision. if a and b are normal doubles, they are considered to be equal to
 * each other if they differ less than <code>precision</code>
 * @return whether the two doubles are to be considered equal
 */
public static boolean lessThan( double a, double b, double precision )
{
	// This works because if a or b is something special (Infinity or NaN) then 
	// (b - precision) == b
	return a < ( b - precision );
}

/**
 * Tests if one double is between two other doubles within a given precision. Tries to handle the special doubles
 * (infinities and NaN's) correctly.
 * @param a a double
 * @param aMinimum The minimum value of a
 * @param aMaximum The maximum value of a
 * @param aPrecision The precision. if a and b are normal doubles, they are considered to be equal to
 * each other if they differ less than <code>precision</code>
 * @return whether the double is between the other two doubles
 */
public static boolean between( double a, double aMinimum, double aMaximum, double aPrecision )
{
	return ! lessThan( a, aMinimum, aPrecision ) && ! lessThan( aMaximum, a, aPrecision );
}


}
