/**
 *
 */
package pu.services;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Peter Urbanus
 *
 */
public class SpringHelper
{

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

public static ApplicationContext setupApplicationContext( Class<?> claxx )
{
	return setupApplicationContext( claxx, "applicationContext.xml" );
}

public static ApplicationContext setupApplicationContext( Class<?> claxx, String aXmlName )
{
	String path = StringHelper.classToPath( claxx );
	path = path + "/" + aXmlName;
	return new ClassPathXmlApplicationContext( path );
}

}
