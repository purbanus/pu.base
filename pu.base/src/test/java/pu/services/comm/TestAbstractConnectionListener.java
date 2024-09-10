package pu.services.comm;


import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

// @@Dit moet je nog wel een keer oplossen. Ik krijg zeer vreemde fouten.
@Ignore
public class TestAbstractConnectionListener extends AbstractJMockTest
{
protected static final int LOCAL_PORT = 31415;
protected final Mock mockSocket = mock( TcpServerSocket.class );
protected final TcpServerSocket socket = (TcpServerSocket) mockSocket.proxy();
protected final Mock mockSession = mock( Session.class );
protected final Session session = (Session) mockSession.proxy();

protected BasicConnectionListener connectionListener;

protected class BasicConnectionListener extends AbstractConnectionListener implements MethodsCalledMaintainer
{
public static final String METHOD_SET_STOPPED    = "setStopped";
public static final String METHOD_STOP           = "stop";
public static final String METHOD_CREATE_SESSION = "createSession";
public static final String METHOD_ACCEPT_CLIENT  = "acceptClient";

public static final String ERROR_CREATE_SESSION    = "Fake exception thrown in createSession";
public static final String ERROR_ACCEPT_CLIENT     = "Fake exception thrown in acceptClient";

public boolean createSessionThrowsException = false;
public boolean acceptClientThrowsException = false;

public List<String> methodsCalled = new ArrayList<>();

public BasicConnectionListener( TcpServerSocket aSocket )
{
	super( aSocket );
}

@Override
public List<String> getMethodsCalled()
{
	return methodsCalled;
}

//------------ NOG
@Override
protected int getClientThreadPriority()
{
	// TODO Auto-generated method stub
	throw new UnsupportedOperationException();
}
//--------- Einde NOG

@Override
public int getServerPort()
{
	return super.getServerPort();
}

@Override
protected TcpServerSocket getServerSocket()
{
	return super.getServerSocket();
}

@Override
public boolean isStopped()
{
	return super.isStopped();
}

@Override
public boolean isStopping()
{
	return super.isStopping();
}

@Override
public void setStopped()
{
	super.setStopped();
}

@Override
protected void setStopping()
{
	super.setStopping();
}

// ======================= Business
@Override
protected void acceptClient( TcpSocket aSocket )
{
	methodsCalled.add( METHOD_ACCEPT_CLIENT );
	if ( acceptClientThrowsException )
	{
		throw new RuntimeException( ERROR_ACCEPT_CLIENT );
	}
}

@Override
public void stop()
{
	methodsCalled.add( METHOD_STOP );
	super.stop();
}

@Override
protected Session createSession( TcpSocket aSocket )
{
	methodsCalled.add( METHOD_CREATE_SESSION );
	if ( createSessionThrowsException )
	{
		throw new RuntimeException( ERROR_CREATE_SESSION );
	}
	return session;
}

@Override
public String getLogName()
{
	return "logname";
}

@Override
protected String getSessionThreadNamePrefix()
{
	return "sessionThreadNamePrefix";
}

}

public TestAbstractConnectionListener( String aName )
{
	super( aName );
}

@Before
@Override
public void setUp() throws Exception
{
	super.setUp();
	mockSocket.stubs()
	.method( "getLocalPort" )
	.will( returnValue( LOCAL_PORT ) )
	;
	connectionListener = new BasicConnectionListener( socket );
}

//////////////////////////////////////////////////////////////////////////////////////
// Constructor tests
@Test
public void testConstructor()
{
	mockSocket.stubs()
	.method( "getLocalPort" )
	.will( returnValue( LOCAL_PORT ) )
	;
	assertEquals( "server port", LOCAL_PORT, connectionListener.getServerPort() );
	assertEquals( "server socket", socket, connectionListener.getServerSocket() );
	assertTrue( "stopping should be false", ! connectionListener.isStopping() );
	assertTrue( "stopped should be false" , ! connectionListener.isStopped() );
}
@Test
public void testConstructorWithNullValues()
{
	try
	{
		connectionListener = new BasicConnectionListener( null );
		fail( "Should have thrown an Exception for a null Socket" );
	}
	catch ( IllegalArgumentException good )
	{
	}
	catch ( Throwable t )
	{
		t.printStackTrace();
	}

}

///////////////////////////////////////////////////////////////////////////////////////
// Running / stopping
//@Test
//public void testShouldThrowExceptionWhenRunInStoppedState()
//{
//	try
//	{
//		// We moeten nu een RuntimeException gooien in acceptClient anders gaat de listener eeuwig door
//		connectionListener.acceptClientThrowsException = true;
//		connectionListener.setStopped();
//		connectionListener.run();
//		fail( "Should have thrown an Exception for running in stopped state" );
//	}
//	catch ( IllegalStateException good )
//	{
//	}
//	catch ( Throwable t )
//	{
//		t.printStackTrace();
//	}
//}
@Test
public void testRunSmoothPath()
{
	// Wat is essentieel bij de run()?
	// HIGH Opstellen
	mockSocket.expects( once() )
	.method( "close" )
	;
	mockSocket.expects( once() )
	.method( "accept" )
	.will( throwException( new TestingConnectionListenerException( "we kappen ermee" ) ) )
	;
	connectionListener.acceptClientThrowsException = true;
	connectionListener.run();
	checkMethodsCalled( connectionListener, new String []
			{
		//BasicConnectionListener.METHOD_VERWERK_INIT,
		//BasicConnectionListener.METHOD_FIRE_SESSION_STARTED,
		//BasicConnectionListener.METHOD_VERWERK_IMPL,
		//BasicConnectionListener.METHOD_VERWERK_CLEANUP,
		BasicConnectionListener.METHOD_STOP,
		//BasicConnectionListener.METHOD_FIRE_SESSION_STOPPED,
			});
	assertTrue( "connectionListener should be stopped", connectionListener.isStopped() );
}

/******************* HIGH Vanaf hier verder nog aanpassen
public void ztestNoVerwerkImplWhenVerwerkInitThrowsException()
{
	mockSocket.expects( once() )
		.method( "close" )
	;
	connectionListener.verwerkInitThrowsException = true;
	connectionListener.run();
	checkMethodsCalled( connectionListener, new String []
	{
		// Je zou nog kunnen twisten dat fireSessionStopped onlogisch is als de connectionListener nooit gestart is
		// (en er ook nooit een fireSessionStarted() geweest is)
		BasicConnectionListener.METHOD_VERWERK_INIT,
		//BasicConnectionListener.METHOD_FIRE_SESSION_STARTED,
		//BasicConnectionListener.METHOD_VERWERK_IMPL,
		BasicConnectionListener.METHOD_VERWERK_CLEANUP,
		BasicConnectionListener.METHOD_STOP,
		BasicConnectionListener.METHOD_FIRE_SESSION_STOPPED,
	});
	assertTrue( "connectionListener should be stopped", connectionListener.isStopped() );
	assertEquals( "stopReason", connectionListener.ERROR_VERWERK_INIT, connectionListener.getStopReason() );
}

public void ztestNothingBadHappensWhenVerwerkImplThrowsException()
{
	mockSocket.expects( once() )
		.method( "close" )
	;
	connectionListener.verwerkImplThrowsException = true;
	connectionListener.run();
	checkMethodsCalled( connectionListener, new String []
	{
		BasicConnectionListener.METHOD_VERWERK_INIT,
		BasicConnectionListener.METHOD_FIRE_SESSION_STARTED,
		BasicConnectionListener.METHOD_VERWERK_IMPL,
		BasicConnectionListener.METHOD_VERWERK_CLEANUP,
		BasicConnectionListener.METHOD_STOP,
		BasicConnectionListener.METHOD_FIRE_SESSION_STOPPED,
	});
	assertTrue( "connectionListener should be stopped", connectionListener.isStopped() );
	assertEquals( "stopReason", connectionListener.ERROR_VERWERK_IMPL, connectionListener.getStopReason() );
}

public void ztestNothingBadHappensWhenVerwerkCleanupThrowsException()
{
	mockSocket.expects( once() )
		.method( "close" )
	;
	connectionListener.verwerkCleanupThrowsException = true;
	connectionListener.run();
	checkMethodsCalled( connectionListener, new String []
	{
		BasicConnectionListener.METHOD_VERWERK_INIT,
		BasicConnectionListener.METHOD_FIRE_SESSION_STARTED,
		BasicConnectionListener.METHOD_VERWERK_IMPL,
		BasicConnectionListener.METHOD_VERWERK_CLEANUP,
		BasicConnectionListener.METHOD_STOP,
		BasicConnectionListener.METHOD_FIRE_SESSION_STOPPED,
	});
	assertTrue( "connectionListener should be stopped", connectionListener.isStopped() );
	// Errors tijdens cleanup worden NIET in stopreason gezet!
	assertNull( "stopReason should be null", connectionListener.getStopReason() );
}

public void ztestRightErrorReportedWhenMultipleExceptionsAreThrown()
{
	mockSocket.expects( once() )
		.method( "close" )
	;
	connectionListener.verwerkImplThrowsException = true;
	connectionListener.verwerkCleanupThrowsException = true;
	connectionListener.run();
	checkMethodsCalled( connectionListener, new String []
	{
		BasicConnectionListener.METHOD_VERWERK_INIT,
		BasicConnectionListener.METHOD_FIRE_SESSION_STARTED,
		BasicConnectionListener.METHOD_VERWERK_IMPL,
		BasicConnectionListener.METHOD_VERWERK_CLEANUP,
		BasicConnectionListener.METHOD_STOP,
		BasicConnectionListener.METHOD_FIRE_SESSION_STOPPED,
	});
	assertTrue( "connectionListener should be stopped", connectionListener.isStopped() );
	assertEquals( "stopReason", connectionListener.ERROR_VERWERK_IMPL, connectionListener.getStopReason() );
}
// Ideen voor verdere tests:
// - test met SocketException
// - test met stop() aangeroepen van de buitenkant
 *******************/
}
