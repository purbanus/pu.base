package pu.services;

public class HexHelper
{
/**
 * Array of hex characters, used by for translations.
 */
private static final char hexChars[] =
{
	'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
};

/**
 * Converts an ascii string into a string containing its hex value.
 * 
 * @param ascii
 *            the ascii representation
 * @return the hex representation
 */
public static String asciiToHex( String ascii )
{

	StringBuffer hex = new StringBuffer();

	if ( ascii == null )
	{
		return null;
	}

	for ( int x = 0; x < ascii.length(); x++ )
	{
		char c = ascii.charAt( x );
		int index = ( c >> 4 ) & 0x000f;
		hex.append( hexChars[index] );
		index = c & 0x000f;
		hex.append( hexChars[index] );
	}

	return hex.toString();

} // asciiToHex

/**
 * Converts an ascii string into a string containing its hex value.
 * 
 * @param ascii
 *            the ascii representation
 * @return the hex representation
 */
public static String byteArrayToHex( byte [] bytes )
{

	StringBuffer hex = new StringBuffer();

	if ( bytes == null )
	{
		return null;
	}

	for ( int x = 0; x < bytes.length; x++ )
	{
		int index = ( bytes[x] >> 4 ) & 0x0f;
		hex.append( hexChars[index] );
		index = bytes[x] & 0x0f;
		hex.append( hexChars[index] );
	}

	return hex.toString();

} // asciiToHex

}
