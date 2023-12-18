package pu.log.log2.console;

import java.io.PrintStream;

import pu.log.log2.console.comm.MulticastOutputStream;

/**
 * ConsoleRedirector redirects console output, the output to System.out and System.err,
 * to any number of oputput streams. You can redirect it to a file, a Swing screen, a
 * socket all at the same time, by adding the appropriate output streams.
 * <p>
 * Both System.out and System.err are redirected to our list of output streams. One of the
 * stream that we always redirect to is the original System.out. This leaves the original
 * System.err free to report internal errors. For suppose something is wrong internally and
 * we want to show that on the console, it would be dangerous to simply use the current
 * (redirected) System.out or System.err since there is a chance that this output would
 * run into the same problem that we are trying to report! The original System.err can be
 * retrieved with <code>getPanicPrintStream</code>. IMPORTANT: users of this class
 * should take care that errors in the output streams that they redirect to, are not
 * blindly printed to System.out or System.err. In particular, Log.logError( e ); should
 * print to <code>getPanicPrintStream</code>.
 *
 */
public class ConsoleRedirector extends MulticastOutputStream
{
	/**
	 * The singleton instance
	 */
	private static final ConsoleRedirector instance = new ConsoleRedirector();

	/**
	 * The original System.out
	 */
	private final PrintStream oldOut;

	/**
	 * The original System.err
	 */
	private final PrintStream oldErr;

	/**
	 * The stream where errors are reported (stacktraces etc.)
	 */
	private final PrintStream panicPrintStream;
/**
 * Redirector constructor comment.
 */
private ConsoleRedirector()
{
	super();
	oldOut = System.out;
	oldErr = System.err;

	addStream( oldOut );

	PrintStream ps = new PrintStream( this );
	System.setOut( ps );
	System.setErr( ps );

	panicPrintStream = oldErr;
}
/**
 * Insert the method's description here.
 * Creation date: (27-10-2002 2:43:36)
 * @param s java.io.OutputStream
 */
public static ConsoleRedirector getInstance()
{
	return instance;
}
/**
 * Returns the PrintStream that is used to report errors. Since one of the outputstreams
 * that we multicast to may be the console, it could get hairy if we also print to the console.
 * Therefore we never print to the console, and derived classes should observe this restriction.
 * If you are interested in output (of stacktraces etc.), simply provide a PrintStream for the
 * output
 * Creation date: (10-11-2002 19:05:27)
 * @return java.io.OutputStream
 */
public PrintStream getPanicPrintStream()
{
	return panicPrintStream;
}
}
