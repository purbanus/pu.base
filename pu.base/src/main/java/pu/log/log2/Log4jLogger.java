package pu.log.log2;

import org.apache.log4j.LogManager;

public class Log4jLogger implements Logger
{
private boolean enabled = true;
@Override
public boolean isEnabled()
{
	return enabled;
}
@Override
public void setEnabled(boolean b)
{
	enabled = b;
}

@Override
public boolean isZelfLogger()
{
    return true;
}

// Je hebt hier nog nodig: de logcategorie en een eventuele throwable
@Override
public void log( Object aCaller, String s, Throwable aThrowable, LogCategorie aLogCategorie )
{
	org.apache.log4j.Logger logger;
	if ( aCaller instanceof Class )
	{
		logger = LogManager.getLogger( (Class<?>) aCaller );
	}
	else
	{
		logger = LogManager.getLogger( aCaller.getClass() );
	}
	if ( aLogCategorie == Log.DEBUG )
	{
		if ( aThrowable == null )
		{
			logger.debug( s );
		}
		else
		{
			logger.debug( s, aThrowable );
		}
	}
	else if ( aLogCategorie == Log.INFO )
	{
		if ( aThrowable == null )
		{
			logger.info( s );
		}
		else
		{
			logger.info( s, aThrowable );
		}
	}
	else if ( aLogCategorie == Log.WARNING )
	{
		if ( aThrowable == null )
		{
			logger.warn( s );
		}
		else
		{
			logger.warn( s, aThrowable );
		}
	}
	else
	{
		if ( aThrowable == null )
		{
			logger.error( s );
		}
		else
		{
			logger.	error( s, aThrowable );
		}
	}
}
}
