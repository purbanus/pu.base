package pu.services.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * Helper class to log some basic information about the {@link ApplicationContext}
 * 
 * @author Marten Deinum
 * @author Koen Serneels
 *
 */
public class ApplicationContextLogger {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationContextLogger.class);

public static void log(ApplicationContext context)
{
    LOG.info("Context: {},{}", context.getClass(), context.getDisplayName());
    LOG.info("Beans: {}", context.getBeanDefinitionCount());
    LOG.info("Active profiles: {}", (Object[]) context.getEnvironment().getActiveProfiles());
    for (String name : context.getBeanDefinitionNames())
    {
        LOG.info("Bean: {}", name);
    }
    logProperties( context );
}

private static void logProperties( ApplicationContext aContext )
{
	//if ( aContext instanceof Config)
    Environment env = aContext.getEnvironment();
    if ( env instanceof ConfigurableEnvironment )
    {
    	ConfigurableEnvironment cenv = (ConfigurableEnvironment) env;
    	MutablePropertySources sources = cenv.getPropertySources();
        for ( PropertySource<?> propertySource : sources )
        {
	        LOG.info( propertySource.toString() );
        }
    }
}

public static void log(BeanFactory factory)
{
    if (factory instanceof ListableBeanFactory)
    {
        ListableBeanFactory lbf = (ListableBeanFactory) factory;
        LOG.info("Beans: {}", lbf.getBeanDefinitionCount());
        for (String name : lbf.getBeanDefinitionNames()) {
            LOG.info("Bean: {}", name);
        }
    }
    else
    {
        LOG.info("Not a ListableBeanFactory {}", factory);
    }

}

}
