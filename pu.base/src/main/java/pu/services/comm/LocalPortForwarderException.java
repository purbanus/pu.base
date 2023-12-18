package pu.services.comm;

public class LocalPortForwarderException extends RuntimeException
{

public LocalPortForwarderException()
{
}

public LocalPortForwarderException( String aMessage )
{
	super( aMessage );
}

public LocalPortForwarderException( Throwable aCause )
{
	super( aCause );
}

public LocalPortForwarderException( String aMessage, Throwable aCause )
{
	super( aMessage, aCause );
}

}
