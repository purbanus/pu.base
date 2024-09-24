package pu.services;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pu.log.console.ConsoleRedirector;

/**
 * @@NOG Veel meer testen!
 * Creation date: (5-7-01 16:00:16)
 * @author: Peter Urbanus
 */
public class TestUtil
{
private void checkStackTrace( Throwable t, String [] aExpected )
{
	List<String> expected = Arrays.asList( aExpected );
	
	List<String> actual = Util.getStackTraceAsList( t );
//	for ( String string : actual )
//	{
//		System.out.println( string );
//	}
	// Dit kan niet want er zitten stukjes in de stacktrace die JUnit eruit filtert, dus
	// krijg je de hele regel niet te zien.
	// assertEquals( testName + " deleted: " + aLinesToDelete, expected, actual );

	//for ( int x = 0; x < actual.size(); x++ )
	//{
	//	System.out.println( actual.get( x ) );
	//}
	
	assertEquals( expected.size(), actual.size() );
	// Ik wil die regels stuk voor stuk vergelijken want dat is veeeeeel overzichtelijker dan allemaal tegelijk,
	// dat is assertEquals( expected, actual );. Je zoekt je een breuk!
	for ( int x = 0; x < expected.size(); x++ )
	{
		assertEquals( "#" + x, expected.get( x ), actual.get( x ) );
	}
	assertEquals( expected, actual );
}

//@Test
public void testListThreads()
{
	final String NL = System.getProperty( "line.separator" );
	String expected1 =	
		"------------------ Overzicht threads -----------------" + NL +
		"Huidige thread: Thread[main,5,main],Alive" + NL +
		"java.lang.ThreadGroup[name=system,maxpri=10]" + NL +
		"    Thread[Reference Handler,10,system],Daemon,Alive" + NL +
		"    Thread[Finalizer,8,system],Daemon,Alive" + NL +
		"    Thread[Signal Dispatcher,9,system],Daemon,Alive" + NL +
		"    Thread[Notification Thread,9,system],Daemon,Alive" + NL +
		"    java.lang.ThreadGroup[name=main,maxpri=10]" + NL +
		"        Thread[main,5,main],Alive" + NL +
		"        Thread[ReaderThread,5,main],Alive" + NL +
		"    java.lang.ThreadGroup[name=InnocuousThreadGroup,maxpri=10]" + NL +
		"        Thread[Common-Cleaner,8,InnocuousThreadGroup],Daemon,Alive" + NL
	;
	String expected2 = 
		"------------------ Overzicht threads -----------------" + NL +
		"Huidige thread: Thread[main,5,main],Alive" + NL +
		"java.lang.ThreadGroup[name=system,maxpri=10]" + NL +
		"    Thread[Reference Handler,10,system],Daemon,Alive" + NL +
		"    Thread Detail ---------------------------------------" + NL +
		"    Naam               : Reference Handler" + NL +
		"    Prioriteit         : 10" + NL +
		"    ThreadGroup        : java.lang.ThreadGroup[name=system,maxpri=10]" + NL +
		"    Deamon thread      : true" + NL +
		"    Levend             : true" + NL +
		"    Interrupted        : false" + NL +
//		"    Ik heb toegang     : true" + NL +
		"    -----------------------------------------------------" + NL +
		"    Thread[Finalizer,8,system],Daemon,Alive" + NL +
		"    Thread Detail ---------------------------------------" + NL +
		"    Naam               : Finalizer" + NL +
		"    Prioriteit         : 8" + NL +
		"    ThreadGroup        : java.lang.ThreadGroup[name=system,maxpri=10]" + NL +
		"    Deamon thread      : true" + NL +
		"    Levend             : true" + NL +
		"    Interrupted        : false" + NL +
//		"    Ik heb toegang     : true" + NL +
		"    -----------------------------------------------------" + NL +
		"    Thread[Signal Dispatcher,9,system],Daemon,Alive" + NL +
		"    Thread Detail ---------------------------------------" + NL +
		"    Naam               : Signal Dispatcher" + NL +
		"    Prioriteit         : 9" + NL +
		"    ThreadGroup        : java.lang.ThreadGroup[name=system,maxpri=10]" + NL +
		"    Deamon thread      : true" + NL +
		"    Levend             : true" + NL +
		"    Interrupted        : false" + NL +
//		"    Ik heb toegang     : true" + NL +
		"    -----------------------------------------------------" + NL +
		"    Thread[Notification Thread,9,system],Daemon,Alive" + NL +
		"    Thread Detail ---------------------------------------" + NL +
		"    Naam               : Notification Thread" + NL +
		"    Prioriteit         : 9" + NL +
		"    ThreadGroup        : java.lang.ThreadGroup[name=system,maxpri=10]" + NL +
		"    Deamon thread      : true" + NL +
		"    Levend             : true" + NL +
		"    Interrupted        : false" + NL +
//		"    Ik heb toegang     : true" + NL +
		"    -----------------------------------------------------" + NL +
		"    java.lang.ThreadGroup[name=main,maxpri=10]" + NL +
		"        Thread[main,5,main],Alive" + NL +
		"        Thread Detail ---------------------------------------" + NL +
		"        Naam               : main" + NL +
		"        Prioriteit         : 5" + NL +
		"        ThreadGroup        : java.lang.ThreadGroup[name=main,maxpri=10]" + NL +
		"        Deamon thread      : false" + NL +
		"        Levend             : true" + NL +
		"        Interrupted        : false" + NL +
//		"        Ik heb toegang     : true" + NL +
		"        -----------------------------------------------------" + NL +
		"        Thread[ReaderThread,5,main],Alive" + NL +
		"        Thread Detail ---------------------------------------" + NL +
		"        Naam               : ReaderThread" + NL +
		"        Prioriteit         : 5" + NL +
		"        ThreadGroup        : java.lang.ThreadGroup[name=main,maxpri=10]" + NL +
		"        Deamon thread      : false" + NL +
		"        Levend             : true" + NL +
		"        Interrupted        : false" + NL +
//		"        Ik heb toegang     : true" + NL +
		"        -----------------------------------------------------" + NL +
		"    java.lang.ThreadGroup[name=InnocuousThreadGroup,maxpri=10]" + NL +
		"        Thread[Common-Cleaner,8,InnocuousThreadGroup],Daemon,Alive" + NL +
		"        Thread Detail ---------------------------------------" + NL +
		"        Naam               : Common-Cleaner" + NL +
		"        Prioriteit         : 8" + NL +
		"        ThreadGroup        : java.lang.ThreadGroup[name=InnocuousThreadGroup,maxpri=10]" + NL +
		"        Deamon thread      : true" + NL +
		"        Levend             : true" + NL +
		"        Interrupted        : false" + NL +
//		"        Ik heb toegang     : true" + NL +
		"        -----------------------------------------------------" + NL
		;
	
	ByteArrayOutputStream out = new ByteArrayOutputStream();
	ConsoleRedirector.getInstance().addStream( out );

	try
	{
		Util.listThreads( false );
		/*************
		String actual = out.toString();
		for ( int x = 0; x < Math.min( expected1.length(), actual.length() ); x++ )
		{
			char c1 = expected1.charAt( x );
			char c2 = actual.charAt( x );
			if ( c1 != c2 )
				System.out.println( x + "\t" + (int) c1 + "\t" + (int) c2 + "\t" + new String( new char [] {c1} ) + "\t" + new String( new char [] {c2} ) );
		}
		****************/
		String actual1 = out.toString();
		assertEquals( expected1.length(), actual1.length() );
		for ( int x = 0; x < expected1.length(); x++ )
		{
			if ( expected1.charAt( x ) != actual1.charAt( x ) )
			{
				System.out.println( "expected char on " + x + " is " + expected1.charAt( x ) + " actual is " + actual1.charAt( x ) );
				System.out.println( "expected numeric = " + (int) expected1.charAt( x ) );
				System.out.println( "actual   numeric = " + (int) actual1.charAt( x ) );
			}
		}
		assertEquals( expected1, actual1 );

		out.reset();
		Util.listThreads( true );
		String actual2 = out.toString();
		assertEquals( expected2.length(), actual2.length() );
		assertEquals( expected2, actual2 );
	}
	finally
	{
		ConsoleRedirector.getInstance().removeStream( out );
	}
}

// @@NOG Test herschrijven. Dit is veel te gevoegg voor wijzigingen in de toolset
//@Test
public void testGetStackTrace()
{
	String [] expected = new String []
	{
		// Java 1
		/********
		"java.lang.Throwable: Poe wat is dit fout",
		"java.lang.Throwable(java.lang.String)",
		"void nl.mediacenter.services.test.TestUtil.testGetStackTrace()",
		"java.lang.Object java.lang.reflect.Method.invoke(java.lang.Object, java.lang.Object [])",
		"void junit.framework.TestCase.runTest()",
		"void junit.framework.TestCase.runBare()",
		"void junit.framework.TestResult$1.protect()",
		"void junit.framework.TestResult.runProtected(junit.framework.Test, junit.framework.Protectable)",
		"void junit.framework.TestResult.run(junit.framework.TestCase)",
		"void junit.framework.TestCase.run(junit.framework.TestResult)",
		"void junit.framework.TestSuite.runTest(junit.framework.Test, junit.framework.TestResult)",
		"void junit.framework.TestSuite.run(junit.framework.TestResult)",
		"junit.framework.TestResult junit.textui.TestRunner.doRun(junit.framework.Test, boolean)",
		"void junit.textui.TestRunner.run(junit.framework.Test)",
		"void nl.mediacenter.services.test.TestUtil.main(java.lang.String [])",
		*************/
		// Java 2 rond het jaar 2000
//		"java.lang.Throwable: Poe wat is dit fout",
//		"\tat nl.mediacenter.services.test.TestUtil.testGetStackTrace(TestUtil.java:172)",
//		"\tat java.lang.reflect.Method.invoke(Native Method)",
//		"\tat junit.framework.TestCase.runTest(TestCase.java:154)",
//		"\tat junit.framework.TestCase.runBare(TestCase.java:127)",
//		"\tat junit.framework.TestResult$1.protect(TestResult.java:106)",
//		"\tat junit.framework.TestResult.runProtected(TestResult.java:124)",
//		"\tat junit.framework.TestResult.run(TestResult.java:109)",
//		"\tat junit.framework.TestCase.run(TestCase.java:118)",
//		"\tat junit.framework.TestSuite.runTest(TestSuite.java:208)",
//		"\tat junit.framework.TestSuite.run(TestSuite.java:203)",
//		"\tat junit.textui.TestRunner.doRun(TestRunner.java:116)",
//		"\tat junit.textui.TestRunner.doRun(TestRunner.java:109)", // Deze is erbij gekomen, dus het verschill zit in JUnit
//		"\tat junit.textui.TestRunner.run(TestRunner.java:72)",
//		"\tat nl.mediacenter.services.test.TestUtil.main(TestUtil.java:27)",
		
		// java 18
		"java.lang.Throwable: Poe wat is dit fout",
		"\tat pu.services.TestUtil.testGetStackTrace(TestUtil.java:254)",
		"\tat java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:104)",
		"\tat java.base/java.lang.reflect.Method.invoke(Method.java:577)",
		"\tat org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)",
		"\tat org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)",
		"\tat org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)",
		"\tat org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)",
		"\tat org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)",
		"\tat org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)",
		"\tat org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)",
		"\tat org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)",
		"\tat org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)",
		"\tat org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)",
		"\tat org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)",
		"\tat org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)",
		"\tat org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)",
		"\tat org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)",
		"\tat org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)",
		"\tat org.junit.runners.ParentRunner.run(ParentRunner.java:413)",
		"\tat org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:93)",
		"\tat org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:40)",
		"\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:529)",
		"\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:756)",
		"\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:452)",
		"\tat org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:210)",
	};

	Throwable t = new Throwable( "Poe wat is dit fout" );
	
	// LinesToDelete doen we niet meer aan
	//for ( int x = 0; x < data.length + 12; x++ )
	//	checkStackTrace( testName, t, x, data );
	checkStackTrace( t, expected );
}
// Staat al hierboven toch?
//@Test
//public void testListThreads()
//{
//	Util.listThreads( false );
//}
@Test
public void testOperatingSystem()
{
	// Ik ga er hier van uit dat we dit niet op een Mac draaien
	// We draaien op Linux
	assertFalse( Util.isWindows() );
	assertTrue( Util.isLinux() );
}
// @@NOG Hier een echte test voor verzinnen
//@Test
public void testPrintClassPath()
{
	Util.printClassPath();
}
}
