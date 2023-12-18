/*
 * Created on 5-mrt-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pu.log.tryout;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.chainsaw.Main;

/**
 * @author Peter Urbanus
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TryChainSaw
{

/**
 *
 */
public TryChainSaw()
{
	super();
}

/** initialize log4j **/
@SuppressWarnings( "unused" )
private static void initLog4J() {
	final Properties props = new Properties();
	props.setProperty("log4j.rootCategory"      , "DEBUG, A1");
	props.setProperty("log4j.appender.A1"       , "org.apache.log4j.ConsoleAppender");
	props.setProperty("log4j.appender.A1.layout", "org.apache.log4j.TTCCLayout");
	PropertyConfigurator.configure(props);
}

/**
 * The main method.
 *
 * @param aArgs ignored
 */
public static void main(String[] aArgs)
{
	System.setProperty( Main.PORT_PROP_NAME, String.valueOf( 44443 ) );
	Main.main( new String [0] );
}
}
