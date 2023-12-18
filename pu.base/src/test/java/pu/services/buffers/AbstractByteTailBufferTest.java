package pu.services.buffers;

import static org.junit.Assert.*;

import org.junit.Test;

import pu.services.RandomHelper;

/**
 */
public abstract class AbstractByteTailBufferTest extends AbstractBufferTest
{
	static interface ByteBuffer
	{
		abstract int size();
		abstract int getMaxSize();
		abstract boolean isEmpty();
		abstract boolean isFull();
		abstract byte [] get();
		abstract void put( byte [] b );
		abstract void put( byte [] b, int off, int len );
	}
private void checkCtor1Parm( int aMaxSize )
{
	ByteBuffer tt = createTailThing_1( aMaxSize );
	checkSizes( tt, aMaxSize, 0 );
}
private void checkCtor1ParmException( int aMaxSize )
{
	try
	{
		@SuppressWarnings( "unused" )
		ByteBuffer tt = createTailThing_1( aMaxSize );
		fail( " should have thrown an IllegalArgumentException" );
	}
	catch ( IllegalArgumentException good )
	{
	}
}
private void checkCtor2Parm( int aMaxSize, int aInitialSize )
{
	ByteBuffer cbb = createTailThing_2( aMaxSize, aInitialSize );
	checkSizes( cbb, aMaxSize, 0 );
}
private void checkCtor2ParmException( int aMaxSize, int aInitialSize )
{
	try
	{
		@SuppressWarnings( "unused" )
		ByteBuffer cbb = createTailThing_2( aMaxSize, aInitialSize );
		fail( " should have thrown an IllegalArgumentException" );
	}
	catch ( IllegalArgumentException good )
	{
	}
}
private void checkSizes( ByteBuffer aTailThing, int aMaxSize, int aSize )
{
	assertEquals( " maxSize", aMaxSize, aTailThing.getMaxSize() );
	assertEquals( " getSize", aSize, aTailThing.size() );
}
private void checkState( ByteBuffer aTailThing, boolean aEmpty, boolean aFull, byte[] aContent )
{
	assertEquals( aEmpty  , aTailThing.isEmpty() );
	assertEquals( aFull   , aTailThing.isFull() );
	assertEquals( aContent.length, aTailThing.size() );
	compare( aContent, aTailThing.get() );
}

private byte [] createByteArray( int aStart, int aEnd )
{
	try
	{
		byte [] ret = new byte[aEnd - aStart + 1];
		for ( int x = aStart; x <= aEnd; x++ )
			ret[x-aStart] = (byte) x;
		return ret;
	}
	catch ( NegativeArraySizeException e )
	{
		System.out.println( aStart + " - " + aEnd );
		throw e;
	}
}
abstract ByteBuffer createTailThing_1(int aMaxSize);
abstract ByteBuffer createTailThing_2( int aMaxSize, int aInitialSize );
@SuppressWarnings( "unused" )
private byte[] join( byte[] a, byte[] b )
{
	byte [] ret = new byte [a.length + b.length];
	System.arraycopy( a, 0, ret, 0, a.length );
	System.arraycopy( b, 0, ret, a.length, b.length );
	return ret;
}
@Test
public void testBase_BigInsert() //throws IOException
{
	byte [] testData1 = createByteArray(  0,  7 );
	byte [] testData2 = createByteArray(  8, 15 );
	byte [] testData3 = createByteArray( 16, 23 );
	byte [] bigData = createByteArray(  0, 100 );
	byte [] expectedData = createByteArray( 81, 100 );
	ByteBuffer tb;
	
	tb = createTailThing_2( 20, 8 );
	tb.put( bigData );
	compare( expectedData, tb.get() );
	
	tb = createTailThing_2( 20, 8 );
	tb.put( testData1 );
	tb.put( bigData );
	compare( expectedData, tb.get() );
	
	tb = createTailThing_2( 20, 8 );
	tb.put( testData1 );
	tb.put( testData2 );
	tb.put( bigData );
	compare( expectedData, tb.get() );
	
	tb = createTailThing_2( 20, 8 );
	tb.put( testData1 );
	tb.put( testData2 );
	tb.put( testData3 );
	tb.put( bigData );
	compare( expectedData, tb.get() );
}
@Test
public void testBase_Constructor1()
{
	int D = 1000;

	// ctor w/maxsize
	checkCtor1Parm( 1      );
	checkCtor1Parm( 500    );
	checkCtor1Parm( D-1    );
	checkCtor1Parm( D      );
	checkCtor1Parm( D+1    );
	checkCtor1Parm( D+1000 );
	
	checkCtor1ParmException( -1 );
	checkCtor1ParmException(  0 );
}
@Test
public void testBase_Constructor2()
{
	// ctor w/maxsize and initialsize
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
public void testBase_Insert()
{
	byte [] testData1 = createByteArray(  0,  7 );
	byte [] testData2 = createByteArray(  8, 15 );
	byte [] testData3 = createByteArray( 16, 23 );
	
	ByteBuffer tb = createTailThing_2( 20, 8 );
	
	checkState( tb, true, false, new byte[0] );
	
	tb.put( testData1 );
	checkState( tb, false, false, testData1 );

	tb.put( testData2 );
	checkState( tb, false, false, createByteArray( 0, 15 ) );

	tb.put( testData3 );
	checkState( tb, false, true, createByteArray( 4, 23 ) );
	
}
@Test
public void testBase_InsertWithOffsetAndLength()
{
	byte [] testData = createByteArray(  0, 23 );
	
	ByteBuffer tb = createTailThing_2( 20, 8 );
	
	checkState( tb, true, false, new byte[0] );
	
	tb.put( testData, 0, 8 );
	checkState( tb, false, false, createByteArray( 0, 7 ) );

	tb.put( testData, 8, 8 );
	checkState( tb, false, false, createByteArray( 0, 15 ) );

	tb.put( testData, 16, 8 );
	checkState( tb, false, true, createByteArray( 4, 23 ) );
	
}
@Test
public void testBase_RandomInsertions()
{
	final int MIN = 1;
	final int MAX = 5;
	final int NUMTESTS = 1000;
	final int BUFSIZE = 20;
	final int INITIALSIZE = 8;

	ByteBuffer tb = createTailThing_2( BUFSIZE, INITIALSIZE );

	int nextValue = 0;
	for ( int test = 0; test < NUMTESTS; test++ )
	{
		int size = RandomHelper.getRandom( MIN, MAX );
		assertTrue( size >= MIN );
		assertTrue( size <= MAX );
		
		byte [] b = createByteArray( nextValue, nextValue + size - 1 );
		tb.put( b );
		nextValue += size;

		int start = Math.max( 0, nextValue - BUFSIZE );
		int end = nextValue - 1;
		compare( createByteArray( start, end ), tb.get() );
	}
}
@Test
public void testBase_RandomInsertionsWithOffsetAndLength()
{
	final int MIN = 1;
	final int MAX = 5;
	final int NUMTESTS = 1000;
	final int BUFSIZE = 20;
	final int INITIALSIZE = 8;

	final byte [] testData = createByteArray( 0, NUMTESTS * MAX );
	ByteBuffer tb = createTailThing_2( BUFSIZE, INITIALSIZE );

	int index = 0;
	for ( int test = 0; test < NUMTESTS; test++ )
	{
		int size = RandomHelper.getRandom( MIN, MAX );

		//byte [] b = createByteArray( nextValue, nextValue + size - 1 );
		tb.put( testData, index, size );

		//int start = Math.max( 0, nextValue - BUFSIZE );
		//int end = nextValue - 1;
		index += size;
		int start = Math.max( 0, index - BUFSIZE );
		int end   = index - 1;
		compare( createByteArray( start, end ), tb.get() );
	}
}
}
