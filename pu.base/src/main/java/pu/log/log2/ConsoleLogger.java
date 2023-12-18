package pu.log.log2;

public class ConsoleLogger extends AbstractLogger
{
@Override
public void logImpl( String s )
{
	System.out.println( s );
}
}
