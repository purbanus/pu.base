package pu.services.buffers;

public class TestByteTailBuffer extends AbstractByteTailBufferTest
{
	static class MyTailThing implements ByteBuffer
	{
	private final ByteTailBuffer tbos;
	public MyTailThing( int aMax )
	{
		super();
		tbos = new ByteTailBuffer( aMax );
	}
	public MyTailThing( int aMax, int aInit )
	{
		super();
		tbos = new ByteTailBuffer( aMax, aInit );
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
		return tbos.get();
	}
	@Override
	public void put( byte [] b )
	{
		tbos.put( b );
	}
	@Override
	public void put( byte [] b, int off, int len )
	{
		tbos.put( b, off, len );
	}
	}
@Override
ByteBuffer createTailThing_1( int aMaxSize )
{
	return new MyTailThing( aMaxSize );
}
@Override
ByteBuffer createTailThing_2( int aMaxSize, int aInitialSize )
{
	return new MyTailThing( aMaxSize, aInitialSize );
}
}
