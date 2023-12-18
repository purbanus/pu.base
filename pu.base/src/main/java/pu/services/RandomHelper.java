package pu.services;

/**
 * RandomHelper offers some useful methods to get random integers.
 */
public class RandomHelper
{
/**
 * This method was created in VisualAge.
 */
private RandomHelper()
{
}
/**
 * Genereert een int in de range [0,max], dus inclusief 0 en max.
 * @return int
 * @param max int
 */
public static int getRandom( int max )
{
	return getRandom( 0, max );
}
/**
 * Genereert een int in de range [min,max], dus inclusief min en max.
 * @return int
 * @param min int
 * @param max int
 */
public static int getRandom( int min, int max ) 
{
//	int random = min + round( Math.random() * ( max - min + 1 ) );
	int random = min + Double.valueOf( Math.random() * ( max - min + 1 ) ).intValue();

	if ( random < min )
	{
		System.out.println( "random < min" );
		return min;
	}
	else if ( random > max )
	{
		System.out.println( "random < min" );
		return max;
	}
	return random;
}
/**
 * Genereert een int in de range [0,max-1], dus inclusief 0 en EXCLUSIEF max.
 * @return int
 * @param max int
 */
public static int getRandom0( int max )
{
	return getRandom( 0, max - 1 );
}
}
