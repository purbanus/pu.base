/**
 * 
 */
package pu.services.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pu.services.StringHelper;


/**
 * @author Peter Urbanus
 *
 */
public class SpringHelper
{
	/** De standaard application context naam */
	public static final String DEFAULT_APPLICATION_CONTEXT_NAME = "applicationContext.xml";
	
/**
 * 
 */
private SpringHelper()
{
	super();
}

// Werkt, maar je moet src/ voor het pad zetten, ook in de applicationContext.xml bij de properties
// applicationContext = new FileSystemXmlApplicationContext( "src/pu/services/seq/db/test/ApplicationContext.xml" );

// Werkt ook, maar je moet op beide plaatsen src/ weghalen
// Ik denk dat dit beter werkt omdat het ook in andere projecten gebruikt kan worden
// applicationContext = new ClassPathXmlApplicationContext( "pu/services/seq/db/test/ApplicationContext.xml" );

// En dit is natuurlijk nog beter!

public static ApplicationContext setupApplicationContext( Class<?> aClass )
{
	return setupApplicationContext( aClass, DEFAULT_APPLICATION_CONTEXT_NAME );
}

public static ApplicationContext setupApplicationContext( Class<?> aClass, String aXmlName )
{
    return new ClassPathXmlApplicationContext( getClassRelativeApplicationContextLocation( aClass, aXmlName ) );
}

public static String getClassRelativeApplicationContextLocation( Class<?> aClass )
{
	return getClassRelativeApplicationContextLocation(  aClass, DEFAULT_APPLICATION_CONTEXT_NAME );
}
public static String getClassRelativeApplicationContextLocation( Class<?> aClass, String aXmlName )
{
	return StringHelper.classToPath( aClass ) + "/" + aXmlName;
}
}
