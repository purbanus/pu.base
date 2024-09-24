package pu.services;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;

///import nl.mediacenter.table.dm.dec.Expandable;

/**
 * This type was created in VisualAge.
 */
public class Util
{
	/*************** Misschien is dit nog wat
public static void safeExit()
{
	// Haal lijst van alle threads in de huidige groep
	Thread mijnThread = Thread.currentThread();
	ThreadGroup groep = mijnThread.getThreadGroup();
	int atl = groep.activeCount();
	Thread [] threads = new Thread [atl + 100];
	groep.enumerate( threads );

	// Interrupt ze allemaal, behalve onszelf natuurlijk
	for ( int x = 0; x < threads.length; x++ )
		if ( threads [x] != null && threads [x] != mijnThread )
			threads[x].interrupt();

	// Wacht tot ze allemaal klaar zijn
	for ( int x = 0; x < threads.length; x++ )
	{
		if ( threads [x] != null && threads [x] != mijnThread )
		{
			String name = threads[x].toString(); // de threadnaam is soms ineens null
			boolean threadGestopt = false;
			while ( !threadGestopt )
			{
				try
				{
					final int timeOut = 1000;
					System.out.println( "Wacht " + timeOut + "ms op " + name );
					threads[x].join( timeOut );
					if ( threads[x].isAlive() )
					{
						System.out.println( "Thread wordt gestopt: " + name );
						threads[x].stop();
					}
					else
					{
						threadGestopt = true;
					}
				}
				catch( InterruptedException e )
				{
					// Kill, kill, kill!
					System.out.println( "Thread wordt gestopt: " + name );
					threads[x].stop();
				}
			}
		}
	}
}
*****************************/

private Util()
{
	super();
}
/**
 * @deprecated The method Thread.checkaccess is deprecated and marked for removal
 */
@Deprecated
public static boolean checkAccess( Thread t )
{
	try
	{
		t.checkAccess();
		return true;
	}
	catch( SecurityException e )
	{
		return false;
	}
}
/**
 * Creates a clone of any object, and fires an Assert if that's not possible.
 * Creating a clone of the basic value classes such as Number, String, etc. is not supported
 * by Java (and impossible to add since these classes are <code>final</code>), officially
 * because these objects are immutable so you don't need to clone them. Right, I'm writing
 * this method because I like typing so much, or what?
 * <p>
 * <i>WARNING: For an ImageIcon, no clone is produced, the object itself is returned. For the
 * rationale behind this see the source.</i>
 * @param Obj The Object to be cloned
 * @return The clone of the object passed in
 */
public static Object cloneObject( Object obj )
{
	// null is the easy case
	if ( obj == null )
	{
		return null;
	}
		
	// Since all Number-descendants have a handy xxxValue, treat the Numbers separately
	if ( obj instanceof Number )
	{
		Number num = (Number)obj;
		if ( obj instanceof Byte)
		{
			return Byte.valueOf( num.byteValue() );
		}
		if ( obj instanceof Double)
		{
			return Double.valueOf( num.doubleValue() );
		}
		if ( obj instanceof Float)
		{
			return Float.valueOf( num.floatValue() );
		}
		if ( obj instanceof Integer )
		{
			return Integer.valueOf( num.intValue() );
		}
		if ( obj instanceof Long)
		{
			return Long.valueOf( num.longValue() );
		}
		if ( obj instanceof Short )
		{
			return Short.valueOf( num.shortValue() );
		}
		if ( obj instanceof BigDecimal )
		{
			BigDecimal big = (BigDecimal)num;
			return new BigDecimal( big.toString() );
		}
		if ( obj instanceof BigInteger )
		{
			BigInteger big = (BigInteger)num;
			return new BigInteger( big.toByteArray() );
		}
	}

	// Basic classes Boolean and String
	if ( obj instanceof Boolean )
	{
		return Boolean.valueOf( ( (Boolean) obj ).booleanValue() );
	}
	if ( obj instanceof String )
	{
		return new String( (String) obj );
	}

	// All java.util.Date descendants have a getTime() that we can use
	if ( obj instanceof java.util.Date )
	{
		java.util.Date date = (java.util.Date) obj;
		// Datum wil ik niet in pu.base hebben
//		if ( obj instanceof Datum )
//		{
//			return new Datum( date );
//		}
		if ( obj instanceof java.sql.Date )
		{
			return new java.sql.Date( date.getTime() );
		}
		if ( obj instanceof java.util.Date )
		{
			return new java.util.Date( date.getTime() );
		}
	}
	if ( obj instanceof ImageIcon )
	{
		//ImageIcon icon = (ImageIcon) obj;
		//return new ImageIcon( icon.getImage(), icon.getDescription() );

		// The above commented code doesn't buy you anything, you get a new ImageIcon
		// with the SAME Image and description as the original one.
		// Since Image also doesn't have a clone() method, and doesn't provide methods
		// with which you may do a do-it-yourself clone, and because until now we don't
		// need true clones of ImageIcons anyway, we simply return the object itself.
		return obj;
	}

	// Now try a clone on the object using reflection
	Class<?> objClass = obj.getClass();
	//if ( obj instanceof Expandable )
	//if ( StringHelper.getShortClassName( objClass ).equals( "ExpandableCategoryAccumulator" ) )
	//	System.out.println( "Util.cloneObject: tis een " + objClass.getName() );
	try 
	{
		Method methodClone = objClass.getMethod( "clone", new Class[0] );
		return methodClone.invoke( obj, new Object[0] );
	}
	catch ( NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e ) 
	{
		throw new RuntimeException( e );
	}	

}
/**
 * Compares two Integers numerically.
 * In java 1.1, there is no Integer.compareTo method, so we define one here.
 *
 * @param   a One <code>Integer</code> to be compared.
 * @param   a The other <code>Integer</code> to be compared.
 * @return  the value <code>0</code> if both Integers are equal to each other;
 *          a value less than <code>0</code> if <code>a</code>
 *          is numerically less <code>b</code>; and a
 *          value greater than <code>0</code> if <code>a</code> is
 *          numerically greater than <code>b</code>.
 *		    (signed comparison).
 */
public static int compare( Integer a, Integer b )
{
	return a.intValue() - b.intValue();
}

/**
 * Returns the stackTrace of a Throwable as a single string.
 * @param e The Throwable
 */
public static String getStackTraceAsString(Throwable e)
{
	// Get the stack trace in a String
	ByteArrayOutputStream os = new ByteArrayOutputStream();

	PrintWriter ps = new PrintWriter(os);
	e.printStackTrace( ps );
	ps.flush();
	
	return os.toString();
}

/**
 * Returns the stackTrace of a Throwable as a List of strings.
 * @param e The Throwable
 */
public static List<String> getStackTraceAsList( Throwable aThrowable )
{
	ListWriter vw = new ListWriter();
	aThrowable.printStackTrace( vw );
	return vw.getList();
}
/**
 * This method was created in VisualAge.
 * @return java.lang.ThreadGroup
 */
public static ThreadGroup getSystemThreadGroup()
{
	ThreadGroup systemGroup = Thread.currentThread().getThreadGroup();
	while ( systemGroup.getParent() != null )
	{
		systemGroup = systemGroup.getParent();
	}
	return systemGroup;
}
/**
 * This method was created in VisualAge.
 */
public static void interruptAllThreads()
{
	PrintWriter pw = new PrintWriter( System.out );
	
	// Print huidige thread
	pw.println( "------------------ Alle threads worde ge-interrupt -----------------" );
	listThread( pw, Thread.currentThread(), "Huidige thread: " );
	pw.println();

	// Bepaal hoogste threadgroup
	ThreadGroup systemGroup = getSystemThreadGroup();

	// Druk all threads af
	interruptThreadGroup( pw, systemGroup, "" );


}
/**
 * This method was created in VisualAge.
 * @param tg java.lang.ThreadGroup
 * @param level int
 */
private static void interruptThreadGroup( PrintWriter aWriter, ThreadGroup tg, String prefix )
{
	// print huidige threadgroup
	aWriter.println( prefix + tg );

	// rest een nivo dieper
	prefix += "  ";
	
	// Interrupt alle threads onder de groep
	int activeCount = tg.activeCount();
	Thread [] threads = new Thread[activeCount + 100];
	int atl = tg.enumerate( threads, false );
	for ( int x = 0; x < atl; x++  )
	{
		aWriter.println( "Interrupting thread " + threads[x] );
		sleep( 200 );
		threads[x].interrupt();
		sleep( 200 );
	}

	// Interrupt alle threadgroups onder de groep
	ThreadGroup [] threadGroups = new ThreadGroup[tg.activeGroupCount() + 100];
	atl = tg.enumerate( threadGroups, false );
	for ( int x = 0; x < atl; x++ )
	{
		interruptThreadGroup( aWriter, threadGroups[x], prefix );
	}
	
}
public static boolean isLinux()
{
	// Tot nu toe werkt dit betrouwbaar
	String osName = System.getProperty( "os.name" );
	return osName.indexOf( "Linux" ) >= 0;
}
public static boolean isWindows()
{
	// Tot nu toe werkt dit betrouwbaar
	String osName = System.getProperty( "os.name" );
	return osName.indexOf( "Windows" ) >= 0;
}
private static void listThread( PrintWriter aWriter, Thread t, String prefix )
{
	try
	{
		aWriter.println( prefix + t + ( t.isDaemon() ? ",Daemon" : "" ) + ( t.isAlive() ? ",Alive" : "Dead" ) );
	}
	catch ( Exception e )
	{
		e.printStackTrace();
	}
}
@SuppressWarnings( "unused" )
private static void listThread2( PrintWriter aWriter, Thread t, String prefix )
{
	aWriter.println( prefix + t + "threadgroup=" + t.getThreadGroup() +  ( t.isDaemon() ? ", Daemon" : "" ) + ( t.isAlive() ? ", Alive" : "Dead" ) );
}
private static void listThreadDetail( PrintWriter aWriter, Thread t, String prefix )
{
	aWriter.println( prefix + "Thread Detail ---------------------------------------" );
	aWriter.println( prefix + "Naam               : " + t.getName() );
	aWriter.println( prefix + "Prioriteit         : " + t.getPriority() );
	aWriter.println( prefix + "ThreadGroup        : " + t.getThreadGroup() );
	aWriter.println( prefix + "Deamon thread      : " + t.isDaemon() );
	aWriter.println( prefix + "Levend             : " + t.isAlive() );
	aWriter.println( prefix + "Interrupted        : " + t.isInterrupted() );
	// checkAccess is deprecated
	//aWriter.println( prefix + "Ik heb toegang     : " + checkAccess( t ) );
	aWriter.println( prefix + "-----------------------------------------------------" );
}
/**
 * This method was created in VisualAge.
 * @param tg java.lang.ThreadGroup
 * @param level int
 */
private static void listThreadGroup( PrintWriter aWriter, ThreadGroup tg, String prefix, boolean detail )
{
	// print huidige threadgroup
	aWriter.println( prefix + tg );

	// rest een nivo dieper
	prefix += "    ";
	
	// Print alle threads onder de groep
	int activeCount = tg.activeCount();
	Thread [] threads = new Thread[activeCount + 100];
	int atl = tg.enumerate( threads, false );
	for ( int x = 0; x < atl; x++  )
	{
		listThread( aWriter, threads[x], prefix );
		if ( detail )
		{
			listThreadDetail( aWriter, threads[x], prefix );
		}
	}

	// Print alle threadgroups onder de groep
	ThreadGroup [] threadGroups = new ThreadGroup[tg.activeGroupCount() + 100];
	atl = tg.enumerate( threadGroups, false );
	for ( int x = 0; x < atl; x++ )
	{
		listThreadGroup( aWriter, threadGroups[x], prefix, detail );
	}
}

public static void listThreads( boolean detail )
{
	listThreads( (Class<?>) null, detail );
}
public static void listThreads( Class<?> fromClass, boolean detail )
{
	ListWriter pw = new ListWriter();
	listThreads( pw, fromClass, detail );
	pw.flush();
	for ( String element : pw.getList() )
	{
		System.out.println( element );
	}
}
/**
 * This method was created in VisualAge.
 */
public static void listThreads( PrintWriter aWriter, Class<?> fromClass, boolean detail )
{
	// Print huidige thread
	aWriter.println( "------------------ Overzicht threads " + ( fromClass == null ? "" : fromClass.getName() ) + "-----------------" );
	listThread( aWriter, Thread.currentThread(), "Huidige thread: " );
	aWriter.println();

	// Bepaal hoogste threadgroup
	ThreadGroup systemGroup = getSystemThreadGroup();
	
	// Druk alle threads af
	listThreadGroup( aWriter, systemGroup, "", detail );
}
public static void printClassPath()
{
	String classPathString = System.getProperty( "java.class.path" );
	String [] classPathSplit = classPathString.split( ":" );
	List<String> classPathList = Arrays.asList( classPathSplit );
	classPathList.sort(  Comparator.comparing( String::toString ) );
	for ( String entry : classPathList )
	{
		System.out.println( entry );
	}
	
}
@Deprecated
public static void safeExit()
{
	// Haal lijst van alle threads in de huidige groep
	Thread mijnThread = Thread.currentThread();
	ThreadGroup groep = mijnThread.getThreadGroup();
	int atl = groep.activeCount();
	Thread [] threads = new Thread [atl + 100];
	groep.enumerate( threads );

	// Interrupt ze allemaal, behalve onszelf natuurlijk
	for ( int x = 0; x < threads.length; x++ )
	{
		if ( threads [x] != null && threads [x] != mijnThread )
		{
			threads[x].interrupt();
		}
	}

	// Wacht tot ze allemaal klaar zijn
	for ( int x = 0; x < threads.length; x++ )
	{
		if ( threads [x] != null && threads [x] != mijnThread )
		{
			if ( threads[x].isDaemon() )
			{
				// Daemon threads gewoon ombrengen
				try
				{
					System.out.println( "Daemon wordt gestopt: " + threads[x] );
					threads[x].stop();
				}
				catch( Exception e )
				{
					System.out.println( e );
				}
			}
			else
			{
				try
				{
					System.out.println( "Wachten op " + threads[x] );
					threads[x].join();
				}
				catch( InterruptedException e )
				{
					// De thread is een slapertje. De bijl erin!
					System.out.println( "Slapertje wordt gestopt: " + threads[x] );
					threads[x].stop();
				}
			}
		}
	}
}
/**
 * Insert the method's description here.
 * Creation date: (12-10-01 14:11:56)
 * @param ms long
 */
public static void sleep( long ms )
{
	try
	{
		Thread.sleep( ms );
	}
	catch( InterruptedException e )
	{
	}
}
}
