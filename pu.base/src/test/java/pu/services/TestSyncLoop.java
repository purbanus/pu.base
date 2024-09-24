package pu.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class TestSyncLoop
{
	String testName;
	Collection<Obj> masters;
	Collection<Obj> slaves;
	
	class Obj implements Comparable<Obj> 
	{
		final int key;
		String value;
		Obj( int aKey, String aValue )
		{
			super();
			key = aKey;
			value = aValue;
		}
		@Override
		public int compareTo( Obj obj )
		{
			return key - obj.key;
		}
		public void delete()
		{
			slaves.remove( this );
		}
		public void update( Obj aObject )
		{
			value = aObject.value;
		}
		@Override
		public boolean equals( Object obj )
		{
			if ( obj == null || ! ( obj instanceof Obj ) )
			{
				return false;
			}
			return key == ( (Obj) obj ).key;
		}
		@Override
		public int hashCode()
		{
			return key;
		}
		@Override
		public String toString()
		{
			return key + "-" + value;
		}
	}

public void checkSizes(String aTestName, int aSize )
{
	assertEquals( testName + " master size", aSize, masters.size() );
	assertEquals( testName + " slave size" , aSize, slaves.size() );
}
private void printElements( String aTestName )
{
	/*********/
	System.out.println( "Test results for " + aTestName );
	System.out.println( "Masters: " + masters );
	System.out.println( "Slaves : " + slaves );
	/********/
}
private void runSyncLoop() throws Exception
{
	final List<Obj> toAdd = new ArrayList<>();
	final List<Obj> toDelete = new ArrayList<>();
	SyncLoop<Obj, Obj> sl = new SyncLoop<>( masters.iterator(), slaves.iterator() )
	{
		public int compare( Obj aMaster, Obj aSlave )
		{
			return aMaster.compareTo( aSlave );
		}
		public void add( Obj aMaster )
		{
			toAdd.add( aMaster );
		}
		public void delete( Obj aSlave )
		{
			toDelete.add( aSlave );
		}
		public void update( Obj aMaster, Obj aSlave )
		{
			aSlave.update( aMaster );
		}
		@Override
        protected Obj createMasterSentinel()
        {
	        return new Obj( 0, null );
        }
		@Override
        protected Obj createSlaveSentinel()
        {
	        return new Obj( 0, null );
        }
	};
	sl.run();
	for ( Obj obj : toAdd )
    {
	    slaves.add( obj );
    }
	for ( Obj obj : toDelete )
    {
	    slaves.remove( obj );
    }
	assertEquals( testName, masters, slaves );
}
@Before
public void setUp()
{
	masters = new TreeSet<>();
	slaves  = new TreeSet<>();
}
@Test
public void testEmpty() throws Exception
{
	runSyncLoop();
	String [] expected = {};
	checkSizes( testName, 0 );
	assertArrayEquals( expected, slaves.toArray() );
	assertArrayEquals( expected, masters.toArray() );
}
@Test
public void testMasterEmpty() throws Exception
{
	slaves.add( new Obj( 1, "Een" ) );
	slaves.add( new Obj( 2, "Twee" ) );
	String [] expected = {};
	runSyncLoop();
	checkSizes( testName, 0 );
	assertArrayEquals( expected, slaves.toArray() );
	assertArrayEquals( expected, masters.toArray() );
}
@Test
public void testMixed() throws Exception
{
	testName = "Mixed";

	masters.add( new Obj( 1, "Een" ) );
	masters.add( new Obj( 2, "Twee" ) );
	masters.add( new Obj( 5, "Vijf" ) );
	masters.add( new Obj( 7, "Zeven" ) );
	
	slaves.add( new Obj( 1, "One" ) );
	slaves.add( new Obj( 2, "Two" ) );
	slaves.add( new Obj( 3, "Three" ) );
	slaves.add( new Obj( 4, "Four" ) );
	slaves.add( new Obj( 6, "Six" ) );
	slaves.add( new Obj( 7, "Zeven" ) );
	slaves.add( new Obj( 8, "Eight" ) );

	String expected = "[1-Een, 2-Twee, 5-Vijf, 7-Zeven]";
	runSyncLoop();
	checkSizes( testName, 4 );
	assertEquals( expected, slaves.toString() );
	assertEquals( expected, masters.toString() );
}
@Test
public void testSlaveEmpty() throws Exception
{
	masters.add( new Obj( 1, "Een" ) );
	masters.add( new Obj( 2, "Twee" ) );
	String expected = "[1-Een, 2-Twee]";
	runSyncLoop();
	checkSizes( testName, 2 );
	assertEquals( expected, slaves.toString() );
	assertEquals( expected, masters.toString() );
}
@Test
public void testUpdate() throws Exception
{
	testName = "Update";

	masters.add( new Obj( 1, "Een" ) );
	masters.add( new Obj( 2, "Twee" ) );
	slaves.add( new Obj( 1, "Uno" ) );
	slaves.add( new Obj( 2, "Dos" ) );
	String expected = "[1-Een, 2-Twee]";
	runSyncLoop();
	checkSizes( testName, 2 );
	assertEquals( expected, slaves.toString() );
	assertEquals( expected, masters.toString() );
}
}
