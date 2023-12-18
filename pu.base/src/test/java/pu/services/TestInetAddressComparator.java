package pu.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestInetAddressComparator
{
@Test
public void test() throws UnknownHostException
{
	List<String> inetAddressStrings = Arrays.asList( 
		  "123.4.245.23"
		, "104.244.253.29"
		, "1.198.3.93"
		, "32.183.93.40"
		, "104.30.244.2"
		, "104.244.4.1"
	);
	List<String> expectedSortedInetAddressStrings = Arrays.asList( 
		  "1.198.3.93"
		, "32.183.93.40"
		, "104.30.244.2"
		, "104.244.4.1"
		, "104.244.253.29"
		, "123.4.245.23"
	);
	List<InetAddress> inetAddresses = new ArrayList<>();
	for ( String inetAddress : inetAddressStrings )
    {
	    inetAddresses.add( InetAddress.getByName( inetAddress ) );
    }
	Collections.sort( inetAddresses, new InetAddressComparator() );
	List<String> sortedInetAddressStrings = new ArrayList<>();
	for ( InetAddress inetAddress : inetAddresses )
    {
	    sortedInetAddressStrings.add( inetAddress.getHostAddress() );
    }
	assertEquals( expectedSortedInetAddressStrings, sortedInetAddressStrings );
}

}
