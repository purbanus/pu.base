package pu.log.log2;

public interface Logger
{
public abstract void log( Object aCaller, String s, Throwable aThrowable, LogCategorie aLogCategorie );
public abstract boolean isEnabled();
public abstract void setEnabled( boolean b );
public abstract boolean isZelfLogger();

}
