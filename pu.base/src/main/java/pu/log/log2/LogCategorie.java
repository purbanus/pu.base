package pu.log.log2;

import java.io.Serializable;

/**
 * Dit is nou grappig: alle afstammelingen van LogCategorie moeten singletons zijn
 * en ik zie geen mogelijkheid om dat hier aan te geven.
 */
public abstract class LogCategorie implements Serializable
{
	private final String logType;
/**
 * LogCategorie constructor comment.
 */
public LogCategorie( String aLogType )
{
	super();
	logType = aLogType;
}
public String getLogType()
{
	return logType;
}
}
