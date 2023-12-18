/*
 * Created on 26-feb-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pu.services.comm;


/**
 * Stands for the server side of a single server-client conversation.
 * The conversation is started in the <code>run()</code> method and may be stopped in
 * two ways: the client may disconnect (or there is some other disturbance that
 * prevents the client from talking to us), or the conversation may be stopped
 * from the server side using <code>stop()</code>. In either case, this session
 * cannot be restarted; a new one will have to be created.
 * <p>
 * Normally, sessions will be created by <code>ConnectionListener</code> classes, but that
 * is not a reauirement.
 * <p>
 * A <code>Session</code> has a sessionId, and it maintains the time when it started
 * and the last time when there was any client activity. Since the <code>Session</code> is
 * very general, updating the last-activity time is the responsibility of derived classes.
 * <p>
 * <code>Session</code> objects usually live in a multi-thread environment: on the one hand
 * a conversation with the client is unfolding, with client activity coming in at unpredictable
 * moments, and on the other hand sessions may be queried from the server side, e.g. for the
 * last-activity time, or even stopped, possibly in the middle of a conversation. Implementing
 * classes should take good care to avoid any multithreading problems.
 */
public interface Session extends CommRunnable
{
/**
 * Adds a <code>SessionListener</code> that will be notified of <code>SessionEvent</code> events.
 * @param s The new <code>SessionListener</code>
 */
public abstract void addSessionListener( SessionListener s );

/**
 * Removes a <code>SessionListener</code> so that it will no longer be notified of <code>SessionEvent</code> events.
 * @param s The <code>SessionListener</code> to remove
 */
public abstract void removeSessionListener( SessionListener s );

/**
 * @return The session ID of this client session
 */
public abstract Integer getSessionId();

/**
 * @return The ip address of the socket
 */
public abstract String getIpAddress();

/**
 * @return The last time when activity occurred.
 */
public abstract long getTimeLastActivity();

/**
 * @return The time the session was started
 */
public abstract long getTimeStarted();

/**
 * Stops this session. This method can be called from the outside, which causes
 * the session to stop normally, or from inside the <code>Session</code>.
 * In the last case this could be a normal or abnormal stop. In any case, after
 * this method has been called the session has stopped, and the reason for stopping
 * the sessioin has been recorded in the <code> stopReason</code>
 */
public abstract void stop();

/**
 * @return The stop reason, a simple string describing the reason this session was stopped.
 * This may be the message of an exception that caused us to stop, or somthing like "normal"
 * if the client simply disconnected. When the session isn't stopped this method will return <code>null</code>
 */
public abstract String getStopReason();

/**
 * Returns the thread that this session runs on
 * @param aThread
 */
// HIGH Is alleen maar nodig opdat we kunnen joinen met die thread om te wachten dattie beeindigd is
public abstract Thread getThread();

/**
 * Sets the thread that this session runs on
 * @param aThread
 */
// HIGH Is alleen maar nodig opdat we kunnen joinen met die thread om te wachten dattie beeindigd is
public abstract void setThread( Thread aThread );
}
