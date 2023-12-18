package pu.log.log2;

public abstract class AbstractLogger implements Logger
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
	return false;
}
@Override
public final void log( Object aCaller, String s, Throwable aThrowable, LogCategorie aLogCategorie )
{
	if ( isEnabled() )
	{
		logImpl( s );
	}
}
protected abstract void logImpl( String s );

}
