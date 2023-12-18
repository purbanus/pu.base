package pu.services.buffers;

import javax.swing.SwingUtilities;

/**
 * Implements a DelayedStringBuffer that always sends its output through the AWT event queue
 */
public abstract class VisualDelayedStringBuffer extends DelayedStringBuffer
{
	/**
	 * The default delay
	 */
	private static final int DEFAULT_DELAY = 1000;
/**
 * JTextAreaBuffer constructor comment.
 */
public VisualDelayedStringBuffer()
{
	super( DEFAULT_DELAY );
}
/**
 * Called when the timer goes off. Only called with
 * non-empty strings. Calls <code>newDataImpl</code>. Care is
 * taken that if this method runs on the event thread, it is appended
 * immediately, otherwise the append is submitted to the event queue.
 */
@Override
protected final void newData( final String s )
{
	if ( SwingUtilities.isEventDispatchThread() )
	{
		newDataImpl( s );
	}
	else
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			@Override
			public synchronized void run()
			{
				newDataImpl( s );
			}
		});
	}
}
/**
 * Called by newData when the buffer content needs to be delivered.
 * This method is guaranteed to run on the AWT event thread.
 */
protected abstract void newDataImpl( String s );
}
