package pu.services.buffers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

/**
 * DelayedList maintains a List that you can append to with
 * <code>append</code>. This starts a timer, and when the timer goes off, the
 * objects in the list, including objects that arrived after the timer started,
 * is made available to users by calling the abstract method <code>newData</code>.
 * Note that after calling newData, the list is cleared.
 * <p>
 * The motivation for this class was the scrolling behaviour of TextArea and JTextArea.
 * If you rapidly append strings to either of these, they scroll up and down so fast
 * you can't read the text. If text arrives in batches with a pause of at least half a
 * second between appends, the screen is nice and steady.
 * <p>
 * It turns out that a greater delay than half a second can also be beneficial, because
 * less repainting etc. has to be done. A delay of one or two seconds seems ok.
 * <p>
 * Multithread remarks
 * <p>
 * Multiple threads may write to the list at unpredictable times. Due to the unpredictability
 * of thread scheduling (not to name platform differences), the order in which the output appears
 * in the list is somewhat random. More specifically, suppose a number of threads is writing
 * timestamps into the list, some of those timestamps will appear out of order. If you need
 * stronger assurances of sequencing, you need to develop your own class. Perhaps overriding
 * <code>append</code> to generate timestamps internally would be something. Of course, when
 * only one thread writes to the listr, there is no problem.
 * <p>
 * So there may be multiple write-threads, and the timer runs on its
 * own thread. The following rules must be observed:
 * <ul>
 * <li> The <code>append</code> method(s) should be fully synchronized
 * <li> The <code>flush</code> method should lock only long enough to obtain a snapshot of
 * the list and clear it. It must also be private, as is explained further on.
 * <li> <code>newData</code> is an open method, so it may only be called when no locks are held.
 * Bear in mind that the <code>newData</code> message is probably sent to the same class that
 * is calling <code>append</code>.
 * </ul>
 * If you want to retire a DelayedList, simply stop sending it data and wait for
 * the last <code>newData</code> message. Providing a public <code>flush<code> method that
 * would cause one final <code>newData</code> message would greatly complicate things:
 * <ul>
 * <li> A race develops between the timer thread and the thread that calls <code>flush</code>
 * If the timer thread wins part of the race and obtains some list data, there is no telling
 * when it will actually deliver the data. This would violate the expected semantics of 
 * <code>flush</code>, namely that all data is delivered when <code>flush</code> finishes.
 * <li> A race develops between the thread(s) that append data, and the timer thread and
 * flush thread. It is easy to develop a scenario where <code>newData</code> delivers data
 * in the wrong order.
 * </ul>
 * A solution for these problems could be devised, but keeping <code>flush</code> private makes
 * this class very simple. And the need for a public <code>flush</code> are not very great.
 */
public abstract class DelayedList
{
	/**
	 * The delay for calling <code>newData</code>, in milliseconds
	 */
	private final int delay;

	/**
	 * The timer
	 */
	private final Timer timer;

	/**
	 * The list that accumulates text
	 */
	private List<Object> data = new ArrayList<>();
/**
 * Creates a new DelayedList with the specified delay.
 */
public DelayedList( int aDelay )
{
	super();
	delay = aDelay;
	// HIGH Moet je hier geen setRepeats( false ) doen????????
	timer = new Timer( delay, new ActionListener()
	{
		@Override
		public void actionPerformed( ActionEvent e )
		{
			flush();
		}
	});
}
/**
 * Appends the specified string to the internal list. If the timer is currently
 * not running, it is started.
 */
public synchronized void append( Object s )
{
	// HIGH Race condition? Can it happen that we deliver new data and fail to
	// restart the timer?
	// - timer fires, flush() is called, timer thread is descheduled right after sync block in flush
	// - this thread appends to the buffer. Does not restart the timer for it is still
	//   running ==> NB is dat zo?
	// - timer finishes its stuff, and is no longer running
	
	/***********************
	TIMER										CLIENT
	flush()
	{
		final List oldList;
		final List newList = new ArrayList();
		synchronized( this )
		{
			oldList = data;
			data = newList;
		}
						<<<deschedule>>>
												append()
												{
													data.add( s );
													if ( ! timer.isRunning() )
													{
														timer.restart();
													}
												}

						<<<deschedule>>>
						
		if ( oldList.size() > 0 )
		{
			newData( oldList );
		}
	}
	*******************/

	data.add( s );
	if ( ! timer.isRunning() )
	{
		timer.restart();
	}
}
/**
 * Called when the timer goes off. Retrieves the content of the list,
 * clears it and calls newData if there was anything in the list.
 */
private void flush()
{
	// We need to synchronize as briefly as possible, so we only sync
	// to get the current list and make a new one.
	final List<Object> oldList;
	final List<Object> newList = new ArrayList<>();
	synchronized( this )
	{
		oldList = data;
		data = newList;
	}
	// Call newData if there is something. Note: it is forbidden to call newData if there is no new data.
	if ( oldList.size() > 0 )
	{
		newData( oldList );
	}
}
/**
 * Called when the timer goes off. Only called with
 * a non-empty list and with synchronisatuin off.
 */
protected abstract void newData( List<Object> s );
}
