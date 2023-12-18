/**
 * 
 */
package pu.services.comm;

import pu.services.Range;


/**
 * @author Peter Urbanus
 *
 */
public class UsedSocketsPrinter
{
public static void main( String [] args )
{
	SocketHelper.listUsedPorts( new Range( 10000, 10010 ) );
}
}