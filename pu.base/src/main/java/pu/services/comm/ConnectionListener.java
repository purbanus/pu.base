/**
 *
 */
package pu.services.comm;

/**
 * @author Peter Urbanus
 *
 */
public interface ConnectionListener extends CommRunnable
{

/**
 * @return Whether this ConnectionListener has been stopped
 */
public abstract boolean isStopped();

/**
 * Starts this connection listener
 */
@Override
public abstract void run();

/**
 * Closes the socket that we are listening on. As a result, this <code>Runnable</code>ends.
 * If we are the <code>Runnable</code> of a thread (most likely), the thread will terminate also.
 * <p>
 * This method is designed to be called from a different thread than the one that
 * the ConnectionListener is listening on.
 */
public abstract void stop();

}