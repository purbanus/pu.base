package pu.services.buffers;

/**
 */
public class TestTailBufferOutputStream extends AbstractByteTailBufferTest
{
	static class MyTailThing implements ByteBuffer
	{
	private final TailBufferOutputStream tbos;
	public MyTailThing()
	{
		super();
		tbos = new TailBufferOutputStream();
	}
	public MyTailThing( int aMax )
	{
		super();
		tbos = new TailBufferOutputStream( aMax );
	}
	public MyTailThing( int aMax, int aInit )
	{
		super();
		tbos = new TailBufferOutputStream( aMax, aInit );
	}
	@Override
	public int size()
	{
		return tbos.size();
	}
	@Override
	public int getMaxSize()
	{
		return tbos.getMaxSize();
	}
	@Override
	public boolean isEmpty()
	{
		return tbos.isEmpty();
	}
	@Override
	public boolean isFull()
	{
		return tbos.isFull();
	}
	@Override
	public byte [] get()
	{
		return tbos.toByteArray();
	}
	@Override
	public void put( byte [] b )
	{
		tbos.write( b );
	}
	@Override
	public void put( byte [] b, int off, int len )
	{
		tbos.write( b, off, len );
	}
	}
@Override
ByteBuffer createTailThing_1( int aMaxSize )
{
	return new MyTailThing( aMaxSize );
}
/**
 * Insert the method's description here.
 * Creation date: (4-12-2002 1:09:52)
 * @return ByteBuffer
 * @param aMaxSize int
 */
@Override
ByteBuffer createTailThing_2( int aMaxSize, int aInitialSize )
{
	return new MyTailThing( aMaxSize, aInitialSize );
}
}
