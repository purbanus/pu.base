/*
 * Created on 1-mrt-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pu.services.comm;

/**
 * @author Peter Urbanus
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SessionEvent
{
public static final int SESSION_STARTED = 1;
public static final int SESSION_STOPPED = 2;
public static final int SESSION_CHANGED = 3;

private final int id;
private final Session session;

/**
 *
 */
public SessionEvent( int aId, Session aSession )
{
	super();
	if ( aSession == null )
	{
		throw new IllegalArgumentException( "session may not be null" );
	}
	id = aId;
	session = aSession;
}

public int getId()
{
	return id;
}

public Session getSession()
{
	return session;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Equals, hashCode en toString

@Override
public boolean equals( Object aObject )
{
	if ( aObject == this )
	{
		return true;
	}
	if ( ! ( aObject instanceof SessionEvent )  )
	{
		return false;
	}
	SessionEvent sessionEvent = (SessionEvent) aObject;
	return getId() == sessionEvent.getId() && getSession().equals( sessionEvent.getSession() );
}

@Override
public int hashCode()
{
	return getId();
}

@Override
public String toString()
{
	switch ( getId() )
	{
		case SESSION_STARTED: return "Session " + getId() + " started";
		case SESSION_STOPPED: return "Session " + getId() + " stopped";
		case SESSION_CHANGED: return "Session " + getId() + " changed";
		default: return "Unknown event id!";
	}
}
}
