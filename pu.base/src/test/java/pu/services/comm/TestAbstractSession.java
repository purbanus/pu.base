package pu.services.comm;


import java.util.ArrayList;
import java.util.List;

import org.jmock.Mock;
import org.junit.Before;
import org.junit.Ignore;

//@@Dit moet je nog wel een keer oplossen. Ik krijg zeer vreemde fouten.
@Ignore
public class TestAbstractSession extends AbstractJMockTest
{
// Ik zie niet hoe get getInetAddress kan testen. De laatste fout was
//   java.lang.IllegalAccessError-->tried to access method java.net.InetAddress.<init>()V from class $java.net.InetAddress$$EnhancerByCGLIB$$bfb9279c
//protected final Mock mockInetAddress = mock( InetAddress.class );
//protected final InetAddress iNetAddress = (InetAddress) mockInetAddress.proxy();
//public void testConstructor()
//{
//	mockSocket.expects( once() )
//		.method( "getInetAddress" )
//		.will( returnValue( iNetAddress ) )
//	;
//	RLogSession session = new RLogSession( socket, SESSION_ID );
//	assertEquals( iNetAddress, session.getIPAddress() );
//}
protected static final String IP_ADDRESS = "ip address";
protected final Mock mockSocket = mock( TcpSocket.class );
protected final TcpSocket socket = (TcpSocket) mockSocket.proxy();

protected final Integer SESSION_ID = 1;
protected BasicSession session;

protected static class BasicSession extends AbstractSession implements MethodsCalledMaintainer
{
public static final String METHOD_VERWERK_IMPL    = "verwerkImpl";
public static final String METHOD_VERWERK_CLEANUP = "verwerk_Cleanup";
public static final String METHOD_VERWERK_INIT    = "verwerk_init";
public static final String METHOD_SET_STOPPED = "setStopped";
public static final String METHOD_STOP = "stop";
public static final String METHOD_FIRE_SESSION_CHANGED = "fireSessionChanged";
public static final String METHOD_FIRE_SESSION_STARTED = "fireSessionStarted";
public static final String METHOD_FIRE_SESSION_STOPPED = "fireSessionStopped";

public static final String ERROR_VERWERK_IMPL    = "Fake exception thrown in verwerkImpl";
public static final String ERROR_VERWERK_CLEANUP = "Fake exception thrown in verwerk_Cleanup";
public static final String ERROR_VERWERK_INIT    = "Fake exception thrown in verwerk_init";
public static final String ERROR_FIRE_SESSION_CHANGED = "Fake exception thrown in fireSessionChanged";
public static final String ERROR_FIRE_SESSION_STARTED = "Fake exception thrown in fireSessionStarted";
public static final String ERROR_FIRE_SESSION_STOPPED = "Fake exception thrown in fireSessionStopped";

public boolean verwerkInitThrowsException = false;
public boolean verwerkImplThrowsException = false;
public boolean verwerkCleanupThrowsException = false;

public List<String> methodsCalled = new ArrayList<>();

public BasicSession( TcpSocket aSocket, Integer aSessionID )
{
	super( aSocket, aSessionID );
}
@Override
public List<String> getMethodsCalled()
{
	return methodsCalled;
}

@Override
public TcpSocket getSocket()
{
	return super.getSocket();
}

@Override
public void fireSessionChanged()
{
	methodsCalled.add( METHOD_FIRE_SESSION_CHANGED );
	super.fireSessionChanged();
}

@Override
public void fireSessionStarted()
{
	methodsCalled.add( METHOD_FIRE_SESSION_STARTED );
	super.fireSessionStarted();
}

@Override
public void fireSessionStopped()
{
	methodsCalled.add( METHOD_FIRE_SESSION_STOPPED );
	super.fireSessionStopped();
}

@Override
public void setStopped()
{
	// Te gedetailleerd methodsCalled.add( METHOD_SET_STOPPED );
	super.setStopped();
}

@Override
public void stop()
{
	methodsCalled.add( METHOD_STOP );
	super.stop();
}

@Override
protected void verwerkImpl() throws Exception
{
	methodsCalled.add( METHOD_VERWERK_IMPL );
	if ( verwerkImplThrowsException )
	{
		throw new RuntimeException( ERROR_VERWERK_IMPL );
	}
}

@Override
protected void verwerk_cleanup()
{
	methodsCalled.add( METHOD_VERWERK_CLEANUP );
	if ( verwerkCleanupThrowsException )
	{
		throw new RuntimeException( ERROR_VERWERK_CLEANUP );
	}
}

@Override
protected void verwerk_init() throws Exception
{
	methodsCalled.add( METHOD_VERWERK_INIT );
	if ( verwerkInitThrowsException )
	{
		throw new RuntimeException( ERROR_VERWERK_INIT );
	}
}

}
protected class StoringSessionListener implements SessionListener
{
public final List<SessionEvent> events = new ArrayList<>();
@Override
public void sessionEvent( SessionEvent aE )
{
	events.add( aE );
}
}

public TestAbstractSession()
{
	super( "TestAbstractSession" );
}

@Before
@Override
public void setUp() throws Exception
{
	super.setUp();
	session = new BasicSession( socket, SESSION_ID );
}

//////////////////////////////////////////////////////////////////////////////////////
// Constructor tests

public void testConstructor()
{
	mockSocket.stubs()
	.method( "getIpAddress" )
	.will( returnValue( IP_ADDRESS ) )
	;
	assertEquals( "socket", socket, session.getSocket() );
	assertEquals( "ip address", IP_ADDRESS, session.getIpAddress() );
	assertEquals( "session id", SESSION_ID, session.getSessionId() );
	assertNull( "stopreason should be null", session.getStopReason() );
	assertTrue( "stopping should be false", ! session.isStopping() );
	assertTrue( "stopped should be false" , ! session.isStopped() );
}

public void testConstructorWithNullValues()
{
	try
	{
		session = new BasicSession( null, SESSION_ID );
		fail( "Should have thrown an Exception for a null Socket" );
	}
	catch ( IllegalArgumentException good )
	{
	}
	try
	{
		session = new BasicSession( socket, null );
		fail( "Should have thrown an Exception for a null Session id" );
	}
	catch ( IllegalArgumentException good )
	{
	}
}

///////////////////////////////////////////////////////////////////////////////////////
// Session listeners

public void testSessionListeners()
{
	StoringSessionListener sessionListener1 = new StoringSessionListener();
	StoringSessionListener sessionListener2 = new StoringSessionListener();
	session.addSessionListener( sessionListener1 );
	session.addSessionListener( sessionListener2 );
	session.fireSessionStarted();
	session.fireSessionChanged();
	session.removeSessionListener( sessionListener2 );
	session.fireSessionStopped();

	assertEquals( "sessionlistener1 eventcount", 3, sessionListener1.events.size() );
	assertEquals( new SessionEvent( SessionEvent.SESSION_STARTED, session ), sessionListener1.events.get( 0 ) );
	assertEquals( new SessionEvent( SessionEvent.SESSION_CHANGED, session ), sessionListener1.events.get( 1 ) );
	assertEquals( new SessionEvent( SessionEvent.SESSION_STOPPED, session ), sessionListener1.events.get( 2 ) );

	assertEquals( "sessionlistener2 eventcount", 2, sessionListener2.events.size() );
	assertEquals( new SessionEvent( SessionEvent.SESSION_STARTED, session ), sessionListener2.events.get( 0 ) );
	assertEquals( new SessionEvent( SessionEvent.SESSION_CHANGED, session ), sessionListener2.events.get( 1 ) );
}

///////////////////////////////////////////////////////////////////////////////////////
// Running / stopping

public void testShouldThrowExceptionWhenRunInStoppedState()
{
	try
	{
		session.setStopped();
		session.run();
		fail( "Should have thrown an Exception for running in stopped state" );
	}
	catch ( IllegalStateException good )
	{
	}
}

public void testRunSmoothPath()
{
	// Wat is essentieel bij de run()?
	// - Dat de fireSessionStarted en fireSessionStopped() aangeroepen worden op vaste punten
	//   (Wat er ook gebeurt!)
	// - Dat de abstracte methodes in een bepaalde volgorde aangeroepen worden
	//   (Wat er ook gebeurt!)
	// - Dat de socket gesloten wordt
	//   (Wat er ook gebeurt!)
	// - Dat er geen synchronisatieproblemen zijn
	mockSocket.expects( once() )
	.method( "close" )
	;
	session.run();
	checkMethodsCalled( session, new String []
			{
		BasicSession.METHOD_VERWERK_INIT,
		BasicSession.METHOD_FIRE_SESSION_STARTED,
		BasicSession.METHOD_VERWERK_IMPL,
		BasicSession.METHOD_VERWERK_CLEANUP,
		BasicSession.METHOD_STOP,
		BasicSession.METHOD_FIRE_SESSION_STOPPED,
			});
	assertTrue( "session should be stopped", session.isStopped() );
	assertNull( "stopReason should be null", session.getStopReason() );
}

public void testNoVerwerkImplWhenVerwerkInitThrowsException()
{
	mockSocket.expects( once() )
	.method( "close" )
	;
	session.verwerkInitThrowsException = true;
	session.run();
	checkMethodsCalled( session, new String []
			{
		// Je zou nog kunnen twisten dat fireSessionStopped onlogisch is als de session nooit gestart is
		// (en er ook nooit een fireSessionStarted() geweest is)
		BasicSession.METHOD_VERWERK_INIT,
		//BasicSession.METHOD_FIRE_SESSION_STARTED,
		//BasicSession.METHOD_VERWERK_IMPL,
		BasicSession.METHOD_VERWERK_CLEANUP,
		BasicSession.METHOD_STOP,
		BasicSession.METHOD_FIRE_SESSION_STOPPED,
			});
	assertTrue( "session should be stopped", session.isStopped() );
	assertEquals( "stopReason", session.ERROR_VERWERK_INIT, session.getStopReason() );
}

public void testNothingBadHappensWhenVerwerkImplThrowsException()
{
	mockSocket.expects( once() )
	.method( "close" )
	;
	session.verwerkImplThrowsException = true;
	session.run();
	checkMethodsCalled( session, new String []
			{
		BasicSession.METHOD_VERWERK_INIT,
		BasicSession.METHOD_FIRE_SESSION_STARTED,
		BasicSession.METHOD_VERWERK_IMPL,
		BasicSession.METHOD_VERWERK_CLEANUP,
		BasicSession.METHOD_STOP,
		BasicSession.METHOD_FIRE_SESSION_STOPPED,
			});
	assertTrue( "session should be stopped", session.isStopped() );
	assertEquals( "stopReason", session.ERROR_VERWERK_IMPL, session.getStopReason() );
}

public void testNothingBadHappensWhenVerwerkCleanupThrowsException()
{
	mockSocket.expects( once() )
	.method( "close" )
	;
	session.verwerkCleanupThrowsException = true;
	session.run();
	checkMethodsCalled( session, new String []
			{
		BasicSession.METHOD_VERWERK_INIT,
		BasicSession.METHOD_FIRE_SESSION_STARTED,
		BasicSession.METHOD_VERWERK_IMPL,
		BasicSession.METHOD_VERWERK_CLEANUP,
		BasicSession.METHOD_STOP,
		BasicSession.METHOD_FIRE_SESSION_STOPPED,
			});
	assertTrue( "session should be stopped", session.isStopped() );
	// Errors tijdens cleanup worden NIET in stopreason gezet!
	assertNull( "stopReason should be null", session.getStopReason() );
}

public void testRightErrorReportedWhenMultipleExceptionsAreThrown()
{
	mockSocket.expects( once() )
	.method( "close" )
	;
	session.verwerkImplThrowsException = true;
	session.verwerkCleanupThrowsException = true;
	session.run();
	checkMethodsCalled( session, new String []
			{
		BasicSession.METHOD_VERWERK_INIT,
		BasicSession.METHOD_FIRE_SESSION_STARTED,
		BasicSession.METHOD_VERWERK_IMPL,
		BasicSession.METHOD_VERWERK_CLEANUP,
		BasicSession.METHOD_STOP,
		BasicSession.METHOD_FIRE_SESSION_STOPPED,
			});
	assertTrue( "session should be stopped", session.isStopped() );
	assertEquals( "stopReason", session.ERROR_VERWERK_IMPL, session.getStopReason() );
}
// Ideen voor verdere tests:
// - test met SocketException
// - test met stop() aangeroepen van de buitenkant

}
