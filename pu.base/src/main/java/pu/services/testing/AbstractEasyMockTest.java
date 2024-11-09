package pu.services.testing;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;

import pu.services.StringHelper;

// @@NOG In EasyMock 3.x hebben ze dit ook gedaan dus dan kan deze class opgeruimd worden
public abstract class AbstractEasyMockTest extends TestCase
{
	private List<Object> mocks = new ArrayList<>();
	private boolean replayAllCalled = false;
	private boolean verifyAllCalled = false;
	
public AbstractEasyMockTest( String aName )
{
	super( aName );
}


@Override
protected void tearDown() throws Exception
{
	super.tearDown();
	
	// Als je hier fouten genereert zoals b.v.
	//   assertTrue( "replayAll was not called", replayAllCalled );
	// Dan maskeer je eerdere fouten die waarschijnlijk interessanter zijn dan "replayAll was niet aangeroepen"
	
	if ( ! replayAllCalled )
	{
		System.out.println( StringHelper.getShortClassName( this ) + " replayAll was not called" );
	}
	if ( ! verifyAllCalled )
	{
		System.out.println( StringHelper.getShortClassName( this ) + " verifyAll was not called" );
	}
}

protected <T> T createMcMock( Class<T> aClass )
{
	T mock = EasyMock.createMock( aClass );
	mocks.add(  mock );
	return mock;
}

protected <T> T createMcStricktMock( Class<T> aClass )
{
	T mock = EasyMock.createStrictMock( aClass );
	mocks.add(  mock );
	return mock;
}

protected <T> T createMcNiceMock( Class<T> aClass )
{
	T mock = EasyMock.createNiceMock( aClass );
	mocks.add(  mock );
	return mock;
}

protected void replayAll()
{
	for ( Object mock : mocks )
	{
		EasyMock.replay( mock );
	}
	replayAllCalled = true;
}

protected void verifyAll()
{
	assertTrue( "verifyAll called without replayAll first", replayAllCalled );
	for ( Object mock : mocks )
	{
		EasyMock.verify( mock );
	}
	verifyAllCalled = true;
}
}
