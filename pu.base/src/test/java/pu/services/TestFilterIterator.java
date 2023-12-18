package pu.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 */
public class TestFilterIterator
{
@Test
public void testBasics()
{
	String [] testData = new String [] { "a", "b", "c", "d", "e", };
	
	List<String> lis = Arrays.asList( testData );
	Iterator<String> it = new FilterIterator<>( lis.iterator(), obj -> obj.equals( "a" ) || obj.equals( "c" ) || obj.equals( "e" ));
	List<String> exp = Arrays.asList( new String [] { "a", "c", "e", } );
	List<String> act = new ArrayList<>();
	while ( it.hasNext() )
	{
		act.add( it.next() );
	}

	assertEquals( exp, act );
	
}
}
