package pu.log.log2.console.rlog;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import pu.log.log2.Log;
import pu.log.log2.console.RemoteLogStarter;
import pu.services.Util;

/**
 * RemoteLogOutputStream manages an output stream to a socket. The main reason for its existence
 * is error handling and recovery, which would otherwise be the responsibility of clients.
 * The main responsibilities are:
 * <ul>
 * <li>Passing bytes to the socket as quickly as possible. The client that writes to this
 * stream should percieve delays as small as possible.
 * <li>Catching errors and recovering from them. Again, the client should not be aware that
 * anything is amiss, ans should not see any delays.
 * </ul>
 */
public class RLogOutputStream extends OutputStream
{
	/**
	 * Whether tracing is enabled
	 */
	public static final boolean TRACE = false;

	/**
	 * The name of the remote log server
	 */
	private final String serverName;
	
	/**
	 * The port on which the server listens.
	 */
	private final int serverPort;

	/**
	 * How long to wait before attempting to reconnect when a connection fails
	 */
	private final int reconnectDelay;
	 
	/**
	 * The socket where we send our log entries
	 */
	private Socket socket;

	/**
	 * Input from the socket. Must be a BufferedReader since we want to use readLine()
	 */
	// @@ Not used, maybe future.
	//private BufferedReader socketIn;

	/**
	 * Output to the socket.
	 */
	private OutputStream socketOut;

	private boolean connected = false;
	Thread recoveryThread = null;

/**
 * Creates a new RLogOutputStream, with the output going to the specified port.
 */
public RLogOutputStream( String aServerName, int aServerPort, int aReconnectDelay )
{
	super();
	serverName = aServerName;
	serverPort = aServerPort;
	reconnectDelay = aReconnectDelay;
}
/**
 * Closes this output stream and releases any system resources 
 * associated with the stream. 
 * <p>
 * The <code>close</code> method of <code>FilterOutputStream</code> 
 * calls its <code>flush</code> method, and then calls the 
 * <code>close</code> method of its underlying output stream. 
 *
 * @exception  IOException  if an I/O error occurs.
 * @see        java.io.FilterOutputStream#flush()
 * @see        java.io.FilterOutputStream#out
 */
@Override
public void close() throws IOException
{
	// @@NOG Is dit ok?
	if ( !isConnected() )
		return;

	// Flush the socket. If that doesn't work, it isn't worth the trouble
	// to attempt recovery, as we are closing anyway. This is also the reason
	// that we use socketOut.flush instead of flush here.
	try
	{
		socketOut.flush();
	}
	catch (IOException ignored)
	{
	}

	// @@NOG Ignore exceptions here?
	socketOut.close();
}

public void connect() //throws /*UnknownHostException,*/ IOException
{
	// @@NOG Voorlopig lijkt me dit een programmeerfout: je moet maar eenmaal connect doen
	if ( isConnected() )
	{
		throw new RuntimeException( "Already connected" );
	}

	try
	{
		doConnect();
	}
	catch ( IOException e )
	{
		startRecovery( e );
		return;
	}
}

public void disconnect() throws IOException
{
	// If recovery is running, it must be stopped
	synchronized( this )
	{
		if ( recoveryThread != null )
		{
			if ( TRACE )
			{
				Log.debug( this, "INTERRUPTING RECOVERY THREAD" );
			}
			recoveryThread.interrupt();
		}
		// @@NOG Is dit niet te vroeg gejuicht?
		setConnected( false );
	}
	doDisconnect();
}

private void doConnect() throws IOException
{
	
	// @@NOG Exception handling:
	// SocketExceptions: BindException, ConnectException en NoRouteToHostException
	// Zie verder java.net, bijvoorbeeld ProtocolException
	// Verder nog UnknownHostException
	//       - error recovery
	//       - uitzoeken wat er precies gegooid kan worden
	//       - etc
	socket = new Socket( serverName, serverPort );

	// Buffering is no help for the "delay" problems with SwingConsole
	//socketOut = new BufferedOutputStream( socket.getOutputStream(), 1000 );

	socketOut = socket.getOutputStream();

	// Socket input is not used (yet)
	//socketIn = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

	setConnected( true );
}

private void doDisconnect() throws IOException
{
	if ( TRACE )
	{
		Log.debug( this, "ENTER DISCONNECT" );
	}
	if ( socket != null )
	{
		if ( TRACE )
		{
			Log.debug( this, "START DISCONNECT" );
		}
		try
		{
			try
			{
				if ( TRACE )
				{
					Log.debug( this, "CLOSING SOCKETOUT" );
				}
				// @@NOG Is dit niet deadlock-prone? Bij RLogSingleClient hadden we net zo iets. Als ventje
				//       aan het writen is naar de socket zit hij in een blocked write (@@NOG IS dat zo? waar vind
				//       ik dat?) en dan heeft de output stream locks (misschien dus). Close probeert ook het lock
				//       te krijgen en voor je het weet heb je een deadlock.
				//       Uitzoeken of de close van het socket tevens de streams closet.
				socketOut.close(); // Also flushes
			}
			catch ( IOException ignore )
			{
			}
			//socketIn.close();
			
			try
			{
				if ( TRACE ) Log.debug( this, "CLOSING SOCKET" );
				socket.close();
			}
			catch ( IOException ignore )
			{
			}
		}
		finally
		{
			// Om te voorkomen dat we meerdere keren iets proberen te sluiten dat niet
			// wil sluiten
			socketOut = null;
			socket = null;
		}
		if ( TRACE )
		{
			Log.debug( this, "END DISCONNECT" );
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (11-11-2002 1:52:47)
 */
private void doRecovery( IOException aInitialException )
{
	if ( TRACE )
	{
		Log.debug( this, "RECOVERY: ENTER" );
	}
	doRecovery_ReportProgress( aInitialException );

	// Immediately check if the current thread is interrupted. Between starting this thread	and
	// it actually running, a lot may have happened. One example is testing: when an erroneous
	// address was give and recovery was started, the test was long finished by the time the recovery
	// thread finally started running. So it never noticed it was interrupted, and it didn't quit,
	// wrecking up subsequent tests.
	// @@NOG This might be a java bug. If you are interrupted and start a sleep, when do you get the
	//       InterruptedException? Apparently after sleep finishes. This sounds illogical.
	//       However this may be, checking the interrupt status now sloves the problem.
	// @@NOG Or is it better to check interrupt status just before the sleep?
	if ( recoveryThread.isInterrupted() )
	{
		if ( TRACE )
		{
			Log.debug( this, "RECOVERY: IMMEDIATE EXIT: INTERRUPTED" );
		}
	}
	else
	{
		for ( ;; )
		{
			// Immediately try a new connection
			try
			{
				doDisconnect();
			}
			catch ( IOException ignore )
			{
			}
			try
			{
				doConnect();
				doRecovery_ReportProgress( null );
				RemoteLogStarter.logProperties();
				break;
			}
			catch ( IOException e )
			{
				// Maybe do something specific here. Not known yet.
				// See doConnect for some info.
				// Maybe UnknownHostException is unrecoverable and should be reported
			
				// Dit krijg je als je een werkelijk niet bestaande server opgeeft:
				// - java.net.UnknownHostException: pipo	
				// Dit krijg je als de server niet aan staat:
				// - java.net.ConnectException: Connection refused: no further information
				// Deze als je de server uitzet (onbekend wat het verschil is):
				// - java.net.SocketException: socket write error (code=10053)
				// - java.net.SocketException: Connection reset by peer: socket write error
				// Dit gebeurde thuis toen ik de netwerkstekker eruit trok:
				// - 02:02:54 101 Info : RemoteLog: java.net.SocketException: Connection reset by peer: socket write error
				// - 02:02:54 161 Info : RemoteLog: java.net.NoRouteToHostException: Host unreachable: connect
				// - ... Dit ging een tijdje door...
				// - 02:06:25 145 Info : RemoteLog: java.net.ConnectException: Connection refused: connect
				// - Daarna was het weer ok

				doRecovery_ReportProgress( e );
			}
			
			try
			{
				if ( TRACE )
				{
					Log.debug( this, "RECOVERY Start Sleep" );
				}
				Thread.sleep( reconnectDelay );
			}
			catch ( InterruptedException e )
			{
				break;
			}
			if ( TRACE )
			{
				Log.debug( this, "RECOVERY Not interrupted" );
			}
		}
	}
	synchronized( this )
	{
		recoveryThread = null;
	}
}

private void doRecovery_ReportProgress( IOException e )
{
	if ( e == null )
	{
		Log.info( this, "RemoteLog: connection re-established" );
	}
	else
	{
		Log.info( this, "RemoteLog: " + e.toString() );
	}
}

@Override
protected void finalize()
{
	// We don't want ANY exception here!
	try
	{
		disconnect();
	}
	catch ( Throwable e )
	{
		try
		{
			System.err.println( "ERROR IN FINALIZE" );
			Log.error( this, e );
		}
		catch ( Throwable tooBad )
		{
		}
	}
}
/**
 * Flushes this output stream and forces any buffered output bytes 
 * to be written out to the stream. 
 * <p>
 * The <code>flush</code> method of <code>FilterOutputStream</code> 
 * calls the <code>flush</code> method of its underlying output stream. 
 *
 * @exception  IOException  if an I/O error occurs.
 * @see        java.io.FilterOutputStream#out
 */
@Override
public void flush()
{
	if ( isConnected() )
	{
		try
		{
			socketOut.flush();
		}
		catch ( IOException e )
		{
			startRecovery( e );
		}
	}
}

@SuppressWarnings( "unused" )
private OutputStream getSocketOut()
{
	return socketOut;
}

public synchronized boolean isConnected()
{
	return connected;
}
private synchronized void setConnected( boolean b )
{
	connected = b;
}

private synchronized void startRecovery( final IOException e )
{
	// @@NOG Gevaar: Dit log kan naar RLogOutputStream gaan! Dat zijn wij!
	//       PanicOutputStream? Of de boel safe maken, b.v. eerst connected = false doen?
	if ( TRACE ) Log.debug( this, "START RECOVERY" );
	setConnected( false );
	Runnable r = new Runnable()
	{
		@Override
		public void run()
		{
			doRecovery( e );
		}
	};
	// Thread moet in SystemThreadGroup lopen want als je in Notes zit worden normale threads
	// gekilled.
	recoveryThread = new Thread( Util.getSystemThreadGroup(), r, "RecoveryThread" );
	recoveryThread.setPriority( Thread.MIN_PRIORITY );
	recoveryThread.start();
}
/**
 * Writes <code>b.length</code> bytes to this output stream. 
 * <p>
 * The <code>write</code> method of <code>FilterOutputStream</code> 
 * calls its <code>write</code> method of three arguments with the 
 * arguments <code>b</code>, <code>0</code>, and 
 * <code>b.length</code>. 
 * <p>
 * Note that this method does not call the one-argument 
 * <code>write</code> method of its underlying stream with the single 
 * argument <code>b</code>. 
 *
 * @param      b   the data to be written.
 * @exception  IOException  if an I/O error occurs.
 * @see        java.io.FilterOutputStream#write(byte[], int, int)
 */
@Override
public void write( byte b[] )
{
	write( b, 0, b.length );
}
/**
 * Writes <code>len</code> bytes from the specified 
 * <code>byte</code> array starting at offset <code>off</code> to 
 * this output stream. 
 * <p>
 * The <code>write</code> method of <code>FilterOutputStream</code> 
 * calls the <code>write</code> method of one argument on each 
 * <code>byte</code> to output. 
 * <p>
 * Note that this method does not call the <code>write</code> method 
 * of its underlying input stream with the same arguments. Subclasses 
 * of <code>FilterOutputStream</code> should provide a more efficient 
 * implementation of this method. 
 *
 * @param      b     the data.
 * @param      off   the start offset in the data.
 * @param      len   the number of bytes to write.
 * @exception  IOException  if an I/O error occurs.
 * @see        java.io.FilterOutputStream#write(int)
 */
@Override
public void write( byte b[], int off, int len )
{
	if ( isConnected() )
	{
		try
		{
			socketOut.write( b, off, len );
		}
		catch ( IOException e )
		{
			startRecovery( e );
		}
	}
	// @@NOG Opsparen indien niet connected? Ingewikkeld, en als er dan weer geconnect
	//       wordt zijn er ik weet niet hoeveel clients die allemaal hun achterstallige
	//       100K opsturen, waardoor de server het wat drukjes krijgt. Voorlopig niet.
}
/**
 * Writes the specified <code>byte</code> to this output stream. 
 * <p>
 * The <code>write</code> method of <code>FilterOutputStream</code> 
 * calls the <code>write</code> method of its underlying output stream, 
 * that is, it performs <tt>out.write(b)</tt>.
 * <p>
 * Implements the abstract <tt>write</tt> method of <tt>OutputStream</tt>. 
 *
 * @param      b   the <code>byte</code>.
 * @exception  IOException  if an I/O error occurs.
 */
@Override
public void write( int b )
{
	write( new byte [] { (byte) b }, 0, 1 );
}
}
