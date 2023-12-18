package pu.services.buffers;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 */
public class TestTailBuffer extends AbstractBufferTest
{
private void checkCtor1Parm( int aMaxSize )
{
	TailBuffer cbb = new TailBuffer( aMaxSize );
	checkSizes( cbb, aMaxSize, 0 );
}
private void checkCtor1ParmException( int aMaxSize )
{
	try
	{
		@SuppressWarnings( "unused" )
		TailBuffer cbb = new TailBuffer( aMaxSize );
		fail( "Should have thrown an IllegalArgumentException" );
	}
	catch ( IllegalArgumentException good )
	{
	}
}
private void checkCtor2Parm( int aMaxSize, int aInitialSize )
{
	TailBuffer cbb = new TailBuffer( aMaxSize, aInitialSize );
	checkSizes( cbb, aMaxSize, 0 );
}
private void checkCtor2ParmException( int aMaxSize, int aInitialSize )
{
	try
	{
		@SuppressWarnings( "unused" )
		TailBuffer cbb = new TailBuffer( aMaxSize, aInitialSize );
		fail( "should have thrown an IllegalArgumentException" );
	}
	catch ( IllegalArgumentException good )
	{
	}
}
private void checkSizes( TailBuffer aTailBuffer, int aMaxSize, int aSize )
{
	assertEquals( "maxSize", aMaxSize, aTailBuffer.getMaxSize() );
	assertEquals( "etSize", aSize, aTailBuffer.size() );
}

/**
 */
private void checkState( String aTestName, TailBuffer aTailBuffer, boolean aEmpty, boolean aFull, Object [] aContent )
{
	assertEquals( "empty" , aEmpty  , aTailBuffer.isEmpty() );
	assertEquals( "full"  , aFull   , aTailBuffer.isFull() );
	assertEquals( "length", aContent.length, aTailBuffer.size() );
	compare( aTestName, aContent, aTailBuffer.get() );
}

private Object [] slice( Object [] aObject, int aStart, int aEnd )
{
	Object [] ret = new Object [aEnd - aStart + 1];
	System.arraycopy( aObject, aStart, ret, 0, ret.length );
	return ret;
}
@Test
public void testConstructor1()
{
	int dftInit = TailBuffer.DEFAULT_SIZE;

	// ctor w/maxsize
	checkCtor1Parm(            1 );
	checkCtor1Parm(          500 );
	checkCtor1Parm( dftInit-1    );
	checkCtor1Parm( dftInit      );
	checkCtor1Parm( dftInit+1    );
	checkCtor1Parm( dftInit+1000 );
	
	checkCtor1ParmException( -1 );
	checkCtor1ParmException(  0 );
}
@Test
public void testConstructor2()
{
	// ctor w/maxsize and initialsize
	//             mxS iniS ems
	checkCtor2Parm(  1,  1 );
	checkCtor2Parm( 10,  1 );
	checkCtor2Parm( 10,  5 );
	checkCtor2Parm( 10, 10 );
	
	checkCtor2ParmException( -1, -1 );
	checkCtor2ParmException( -1,  0 );
	checkCtor2ParmException( -1,  1 );
	checkCtor2ParmException(  0,  0 );
	checkCtor2ParmException(  0,  1 );
	checkCtor2ParmException(  1,  2 );
}
@Test
public void testBasicAdd()
{
	TailBuffer buffer = new TailBuffer( 2, 1 );
	checkState( " empty", buffer, true, false, new Object [] {} );
	
	buffer.put( "a" );
	checkState( " a", buffer, false, false, new Object [] { "a" } );
	buffer.put( "b" );
	checkState( " a,b", buffer, false, true, new Object [] { "a", "b" } );
	buffer.put( "c" );
	checkState( " a,b,c", buffer, false, true, new Object [] { "b", "c" } );
}
@Test
public void testExpand()
{
	final int MAX = 10;
	TailBuffer buffer = new TailBuffer( MAX, 1 );
	checkState( " empty", buffer, true, false, new Object [] {} );
	
	String [] data = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m" };
	for ( int x = 0; x < data.length; x++ )
	{
		buffer.put( data[x] );

		boolean empty = false;
		boolean full  = x >= 9;
		int start = x - MAX + 1;
		if ( start < 0 )
		{
			start = 0;
		}
		checkState( "#" + x, buffer, empty, full, slice( data, start, x ) );
	}
}
}
