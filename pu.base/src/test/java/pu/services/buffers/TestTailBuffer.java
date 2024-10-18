package pu.services.buffers;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 */
public class TestTailBuffer extends AbstractBufferTest
{
private void checkCtor1Parm( int aMaxSize )
{
	TailBuffer<String> cbb = new TailBuffer<>( String.class, aMaxSize );
	checkSizes( cbb, aMaxSize, 0 );
}
private void checkCtor1ParmException( int aMaxSize )
{
	try
	{
		@SuppressWarnings( "unused" )
		TailBuffer<String> cbb = new TailBuffer<>( String.class, aMaxSize );
		fail( "Should have thrown an IllegalArgumentException" );
	}
	catch ( IllegalArgumentException good )
	{
	}
}
private void checkCtor2Parm( int aMaxSize, int aInitialSize )
{
	TailBuffer<String> cbb = new TailBuffer<>( String.class, aMaxSize, aInitialSize );
	checkSizes( cbb, aMaxSize, 0 );
}
private void checkCtor2ParmException( int aMaxSize, int aInitialSize )
{
	try
	{
		@SuppressWarnings( "unused" )
		TailBuffer<String> cbb = new TailBuffer<>( String.class, aMaxSize, aInitialSize );
		fail( "should have thrown an IllegalArgumentException" );
	}
	catch ( IllegalArgumentException good )
	{
	}
}
private void checkSizes( TailBuffer<String> aTailBuffer, int aMaxSize, int aSize )
{
	assertEquals( "maxSize", aMaxSize, aTailBuffer.getMaxSize() );
	assertEquals( "getSize", aSize, aTailBuffer.size() );
}

/**
 */
private void checkState( String aTestName, TailBuffer<String> aTailBuffer, boolean aEmpty, boolean aFull, String [] aContent )
{
	assertEquals( "empty" , aEmpty  , aTailBuffer.isEmpty() );
	assertEquals( "full"  , aFull   , aTailBuffer.isFull() );
	assertEquals( "length", aContent.length, aTailBuffer.size() );
	compare( aTestName, aContent, aTailBuffer.get() );
}

private String [] slice( String [] aObject, int aStart, int aEnd )
{
	String [] ret = new String [aEnd - aStart + 1];
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
	TailBuffer<String> buffer = new TailBuffer<>( String.class, 2, 1 );
	checkState( " empty", buffer, true, false, new String [] {} );
	
	buffer.put( "a" );
	checkState( " a", buffer, false, false, new String [] { "a" } );
	buffer.put( "b" );
	checkState( " a,b", buffer, false, true, new String [] { "a", "b" } );
	buffer.put( "c" );
	checkState( " a,b,c", buffer, false, true, new String [] { "b", "c" } );
}
@Test
public void testExpand()
{
	final int MAX = 10;
	TailBuffer<String> buffer = new TailBuffer<>( String.class, MAX, 1 );
	checkState( " empty", buffer, true, false, new String [] {} );
	
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
@Test
public void testClear()
{
	TailBuffer<String> buffer = new TailBuffer<>( String.class, 100 );
	checkState( "empty", buffer, true, false, new String [] {} );
	
	String [] data = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m" };
	for ( int x = 0; x < data.length; x++ )
	{
		buffer.put( data[x] );
	}
	checkState( " full", buffer, false, false, buffer.get() );
	assertEquals( "empty" , false  , buffer.isEmpty() );
	assertEquals( "full"  , false  , buffer.isFull() );
	assertEquals( "length", data.length, buffer.size() );
	compare( "clear", data, buffer.get() );

	buffer.clear();
	checkState( "empty", buffer, true, false, new String [] {} );
}
@Test
public void testGet()
{
	TailBuffer<String> buffer = new TailBuffer<>( String.class, 100 );
	checkState( "empty", buffer, true, false, new String [] {} );
	
	String [] data = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m" };
	for ( int x = 0; x < data.length; x++ )
	{
		buffer.put( data[x] );
	}
	checkState( " full", buffer, false, false, buffer.get() );
	assertEquals( "empty" , false  , buffer.isEmpty() );
	assertEquals( "full"  , false  , buffer.isFull() );
	assertEquals( "length", data.length, buffer.size() );
	compare( "get", data, buffer.get() );

	String [] strings = buffer.get();
	compare( "get", data, strings );

}
}
