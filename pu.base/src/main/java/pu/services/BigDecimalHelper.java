package pu.services;

import java.math.BigDecimal;
import java.util.List;
/**
 * Helper class for BigDecimal calculations and <code>double</code> problems.
 */

// @@NOG add methods from DoubleAccumulator (nl.mediacenter.table.dm.dec.DoubleAccumulator),
//       see also nl.mediacenter.table.dm.test
public class BigDecimalHelper
{
	public static final BigDecimal NUL_0     = new BigDecimal(   "0"    );
	public static final BigDecimal NUL_2     = new BigDecimal(   "0.00" );
	public static final BigDecimal NUL_4     = new BigDecimal(   "0.0000" );
	public static final BigDecimal EEN_0     = new BigDecimal(   "1"    );
	public static final BigDecimal EEN_2     = new BigDecimal(   "1.00" );
	public static final BigDecimal EEN_4     = new BigDecimal(   "1.0000" );
	public static final BigDecimal TWEE_0    = new BigDecimal(   "2"    );
	public static final BigDecimal TWEE_2    = new BigDecimal(   "2.00" );
	public static final BigDecimal DRIE_0    = new BigDecimal(   "3"    );
	public static final BigDecimal DRIE_2    = new BigDecimal(   "3.00" );
	public static final BigDecimal VIER_0    = new BigDecimal(   "4"    );
	public static final BigDecimal VIER_2    = new BigDecimal(   "4.00" );
	public static final BigDecimal VIJF_0    = new BigDecimal(   "5"    );
	public static final BigDecimal VIJF_2    = new BigDecimal(   "5.00" );
	public static final BigDecimal TIEN_0    = new BigDecimal(  "10"    );
	public static final BigDecimal TIEN_2    = new BigDecimal(  "10.00" );
	public static final BigDecimal HONDERD_0 = new BigDecimal( "100"    );
	public static final BigDecimal HONDERD_2 = new BigDecimal( "100.00" );
	public static final BigDecimal HONDERD_4 = new BigDecimal( "100.0000" );
	
	public static final BigDecimal KWART_2   = new BigDecimal(   "0.25" );
	public static final BigDecimal HALF_2    = new BigDecimal(   "0.50" );
/**
 * Private constructor because we don't want any instances 
 */
private BigDecimalHelper()
{
	super();
}
/**
 * Adds two BigDecimals, treating <code>null</code> as a zero with 0 decimal positions.
 * No attempt is made to round the result to any decimal positions.
 * @param a
 * @param b
 * @return
 */
public static BigDecimal add0( BigDecimal a, BigDecimal b )
{
	if ( a == null )
	{
		a = NUL_0;
	}
	if ( b == null )
	{
		b = NUL_0;
	}
	return a.add( b );
}
/**
 * Adds two BigDecimals, treating <code>null</code> as a zero with 2 decimal positions.
 * No attempt is made to round the result to any decimal positions.
 * @param a
 * @param b
 * @return
 */
public static BigDecimal add2( BigDecimal a, BigDecimal b )
{
	return safe2( a ).add( safe2( b ) );
}
public static BigDecimal berekenPercentageBedrag( BigDecimal aPercentage, BigDecimal aBedrag ) 
{
	return berekenPercentageBedrag( aPercentage, aBedrag, 2 );
}
/**
 * Creation date: (5-10-01 19:54:03)
 * @return java.math.BigDecimal
 * @param aPercentage java.math.BigDecimal
 * @param aBedrag java.math.BigDecimal
 */
public static BigDecimal berekenPercentageBedrag( BigDecimal aPercentage, BigDecimal aBedrag, int aScale ) 
{
	return aPercentage.divide( HONDERD_4, aScale + 2, BigDecimal.ROUND_HALF_UP ).multiply( aBedrag ).setScale( aScale, BigDecimal.ROUND_HALF_UP );
}
/**
 * Creation date: (5-10-01 19:27:48)
 * @return java.math.BigDecimal
 * @param bruto java.math.BigDecimal
 * @param aPercentage java.math.BigDecimal
 */
public static BigDecimal bruteer( BigDecimal aBedrag, BigDecimal aPercentage ) 
{
	return aBedrag.divide( percentageNaarFactor( aPercentage ), BigDecimal.ROUND_HALF_UP ).setScale( 2 );
}
/**
 * Compares two BigDecimals. If either of then is <code>null</code> it is treated as 0.
 * @param One BigDecimal
 * @param Another BigDecimal
 * @return -1 if a < b, 0 if a == b, 1 if a > b
 */
public static int compare( BigDecimal a, BigDecimal b )
{
	BigDecimal aa = a == null ? NUL_0 : a;
	BigDecimal bb = b == null ? NUL_0 : b;
	return aa.compareTo( bb );
}
/**
 * Compares two lists of BigDecimals for equality useing the BigDecimal.comparTo() method.
 * Both lists must contain equal numbers of elements. None of the
 * BigDecimals may be <code>null</code>.
 * @param b1 The first list
 * @param b2 The second list
 * @return whether correspondinng BigDecimals are equal according to the compareTo
 * method in BigDecimal.
 * @exception IllegalArgumentException if the sizes of the lists are not the same
 * @exception NullPointerException if one of the elements is <code>null</code>
 * @exception ClassCastException If any of the elements is not a BigDecimal.
 */
public static boolean compare( List<BigDecimal> b1, List<BigDecimal> b2 )
{
	if ( b1.size() != b2.size() )
	{
		throw new IllegalArgumentException( "List sizes do not match" ); 
	}
	for ( int x = 0; x < b1.size(); x++ )
	{
		BigDecimal bd1 = b1.get( x );
		BigDecimal bd2 = b2.get( x );
		if ( bd1.compareTo( bd2 ) != 0 )
		{
			return false;
		}
	}
	return true;
}

/**
 * Returns whether two BigDecimals are equal.
 * If one of the BigDecimals is <code>null</code> this method returns true
 * only if the other one is also <code>null</code>. If both are non-null then
 * the compare method is called. 
 * @param a One BigDecimal
 * @param b Another BigDecimal
 * @return whether two BigDecimal are equal.
 */
public static boolean equals( BigDecimal a, BigDecimal b )
{
	return a == null || b == null ? a == b : compare( a, b ) == 0;
}

/**
 * Creation date: (5-10-01 16:54:29)
 * @return java.math.BigDecimal
 * @param factor java.math.BigDecimal
 */
public static BigDecimal factorNaarPercentage( BigDecimal aFactor ) 
{
	// default retourneren van 2 decimalen:
	return factorNaarPercentage( aFactor, 2 );
}
private static BigDecimal factorNaarPercentage( BigDecimal aFactor, int aScale ) 
{
	return aFactor.multiply( HONDERD_4 ).subtract( HONDERD_4 ).setScale( aScale, BigDecimal.ROUND_HALF_UP );
}
/**
 * Returns whether a BigDecimal is zero, where null values also count as zero.
 * Creation date: (9-8-01 19:10:52)
 * @return boolean
 */
public static boolean isNul( BigDecimal bd )
{
	return bd == null ? true : bd.compareTo( NUL_0 ) == 0;
}
/**
 * Insert the method's description here.
 * Creation date: (4-1-02 16:47:20)
 * @return int
 * @param aUren java.math.BigDecimal
 */
public static BigDecimal kwartierenNaarUren( int aKwartieren )
{
	BigDecimal retVal = new BigDecimal( aKwartieren ).setScale( 0, BigDecimal.ROUND_HALF_UP );
	retVal = retVal.setScale( 2 );
	return retVal.divide( VIER_0, 2, BigDecimal.ROUND_HALF_UP ); 
}
/**
 * Creation date: (5-10-01 16:54:29)
 * @return java.math.BigDecimal
 * @param factor java.math.BigDecimal
 */
public static BigDecimal percentageNaarFactor( BigDecimal aPercentage ) 
{
	// default: 4 decimalen voor een factor
	return percentageNaarFactor( aPercentage, 4 );
}
private static BigDecimal percentageNaarFactor( BigDecimal aPercentage, int aScale ) 
{
	// liever geen enorme scales gebruiken, want hier zijn er maar 4 decimalen
	if ( aScale >= 5 )
	{
		throw new RuntimeException( "Er worden teveel decimalen gevraagd van BigDecimalHelper, namelijk: " + aScale );
	}
	
	return aPercentage.add( HONDERD_4 ).divide( HONDERD_4, BigDecimal.ROUND_HALF_UP ).setScale( aScale, BigDecimal.ROUND_HALF_UP );			
}
/**
 * Converts a double to a BigDecimal with a maximum scale, dropping as much decimals as possible.
 * For instance, the Notes function getItemValueDouble returns 10.0999999999999996447286321199499070644378662109375
 * when the field is 10.1. If you call this method with a maxScale of, say, 4, The BigDecimal returned will be 10.1.
 * The same result will be obtained for maxScale values of 1 through 10, but when maxScale is 0, the BigDecimal 10 will
 * be returned.
 * <p>
 * This method is useful if you know that the double is really a fixed-point number, but you don't know how many decimal positions
 * it has. If 6 is the largest number of decimal positions that you  will ever encounter, then you can use 6
 * for maxScale. The number of decimal position obtained with this method will often be too small. In the example above the desired
 * value might be 10.10, but we return 10.1 because we drop as many decimals as we can.
 * Howeve, having a decimal too few occasionally is a lot better than having too many decimal positions.
 *
 * @param d The double to be rounded
 * @param aMaxScale The maximum number of decimal positions of the result.
 * @return The rounded BigDecimal
 */
public static BigDecimal roundDouble( double d, int aMaxScale )
{
	// First round to the maximum number of decimal positions
	BigDecimal tempBD = new BigDecimal( d ).setScale( aMaxScale, BigDecimal.ROUND_HALF_UP );
	String tempS = tempBD.toString();
	int tempSlen = tempS.length();
	int scale = aMaxScale;
	for ( int x = tempSlen - 1; x > tempSlen - 1 - aMaxScale; x-- )
	{
		if ( tempS.charAt( x ) != '0' )
		{
			break;
		}
		scale--;
	}
	return tempBD.setScale( scale, BigDecimal.ROUND_HALF_UP );

}
/**
 * Rounds a number of hours with two decimal positions to the nearest
 * quarter of an hour. For instance, 0,33 will round to 0,25 and 0,45 to 0,50.
 * Creation date: (30-7-02 0:49:01)
 * @return java.math.BigDecimal
 * @param aUren java.math.BigDecimal
 */
public static BigDecimal roundNaarKwartieren( BigDecimal aUren )
{
	BigDecimal retVal = aUren.setScale( 2, BigDecimal.ROUND_HALF_UP );
	retVal = retVal.multiply( VIER_0 );
	retVal = retVal.setScale( 0, BigDecimal.ROUND_HALF_UP );
	retVal = retVal.setScale( 2 ); // @@NOG necessary?
	return retVal.divide( VIER_0, 2, BigDecimal.ROUND_HALF_UP );
}
/**
 * Returns a safe value to calculate with, with a scale of 0 decimals.
 * Creation date: (9-8-01 19:08:26)
 * @return java.math.BigDecimal
 * @param aUnsafe java.math.BigDecimal
 */
public static BigDecimal safe0( BigDecimal aUnsafe )
{
	return aUnsafe == null ? NUL_0 : aUnsafe;
}
/**
 * Returns a safe value to calculate with, with a scale of 2 decimals.
 * Creation date: (9-8-01 19:08:26)
 * @return java.math.BigDecimal
 * @param aUnsafe java.math.BigDecimal
 */
public static BigDecimal safe2( BigDecimal aUnsafe )
{
	return aUnsafe == null ? NUL_2 : aUnsafe;
}
/**
 * Insert the method's description here.
 * Creation date: (4-1-02 16:47:20)
 * @return int
 * @param aUren java.math.BigDecimal
 */
public static int urenNaarKwartieren( BigDecimal aUren )
{
	if ( aUren == null )
	{
		return 0;
	}
		
	BigDecimal retVal = aUren.setScale( 2, BigDecimal.ROUND_HALF_UP );
	retVal = retVal.multiply( VIER_0 );
	retVal = retVal.setScale( 0, BigDecimal.ROUND_HALF_UP );
	return retVal.intValue();
}
}
