/*
 * Created on 22-mrt-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.services.seq;


import java.util.HashMap;
import java.util.Map;


/**
 * Manages a collection of Sequences, one per table.
 * <p>
 * Multithread remarks
 * <hr>
 * This class, and the Sequences it uses, should be fully threadsafe. Besides the
 * obvious, that the integrity of this class should not depend on the number of threads
 * accessing it simultaneously, but also that waits should be minimized.
 * This class maintains a table of Sequences that every now and then need to go to the
 * database to obtain a new block of numbers. This should not block other threads that
 * access the other Sequences.
 *
 */
/**********
 * TODO
 * - rename table to sequenceId
 * - nextBlock( String aSequenceId, int aBlockSize )
 * - rename RangeSequence to BlockSequence
 * - blockSize should move to BlockSequence. SequenceManager should have the default blockSize.
 * - Limithandler should receive blockSize in nextBlock(). This makes a performance improvement possible
 *   for large blocks: BlockSequence could take the requested blocksize plus an extra block minus its remaining
 *   numbers, and ask for a block that size. Then it could return the requested block size and still have a whole
 *   new block left fur future (and less thirsty) clients.
 * - Supports block size per sequence id. I think SequenceManager should receive a Map of <sequenceId, blockSize> and
 *   when a sequenceId is not in the Map, it gets the default blockSize.
 * - Support for recovery when Something Terrible Happened: someone deleted the sequence table, or tried to repair
 *   a row by "UPDATE SEQUENCE_TABLE SET NUMBER = 725000", forgetting the WHERE. Obviously, if the sequence table
 *   is corrupted, new inserts are likely to fail. Ideas:
 *   - When a row is added to the sequence table, check the max in the target table. This requires the (Jdbc)LimitHandler
 *     to know the name of the key-field in the target table
 *   - Check all entries in the sequence table on startup versus the max in the target tables. This also requires the
 *     (Jdbc)LimitHandler to know the names of the key-fields, and I think they need to be in a column of the sequence table.
 *   - Requiring the LimitHandler to know the key names worries me. Perhaps with a bit of metadata you can figure out
 *     the key names. After all, the tables we work for have only one PK column, don't they?
 * - When fetching a new block from the database, we should start a nested transaction. It would be a disaster if we
 *   committed or rollbacked a running, half finished transaction.
 * - RangeSequence is a bit complicated, can it be simplified?
 * - Shouldn't the synchronization on the RangeSequences be moved to the RangeSequences themselves, so they could be
 *   used without a SequenceManager? SequenceManager should then only take care of the Map of Sequences.
 *   On the other hand, this would require other types of Sequence to handle all the synchronization issues
 *   themselves, causing duplicate code etc.
 * - Shouldn't hasNext be eliminated from the normal sequences? It is only useful for testing and for iris-type
 *   blocks where the client is willing to call hasNext() to detect problems.
 * - A SequenceManager can have only one LimitHandler. What if one of the sequences comes from an AS/400 dataqueue?
 * - LimitHandler is now a mixture of DAO and a bit of business logic. We might separate the DAO out and move the bit of
 *   business logic into the RangeSequence.
 */
public class SequenceManager
{
/**
 * The map of Sequences, one per table.
 * Multithread remarks: every access to the tableMap must be synchronized. Most of the time,
 * no more than one Sequence will need to be updated from the database; threads using other Sequencers
 * should not have to wait for that update to complete. Every change to the Map should complete as soon
 * as possible to allow other threads to continue.
 */
private final Map<String, Sequence> tableMap = new HashMap<>();

/**
 * The Limit handler
 */
private final LimitHandler limitHandler;

/**
 * Creates a new SequenceManager with the specified LimitHandlerFactory
 * @param aLimitHandlerFactory The factory for new SequenceLimitHandlers.
 */
public SequenceManager( LimitHandler aLimitHandler )
{
	super();
	limitHandler = aLimitHandler;
}
public boolean hasNext( String aTable )
{
	// Obtain the sequence
	Sequence sequence = getSequence( aTable );

	// We must synchronize on the sequence but hasNext is a fast method (no database calls)
	synchronized( sequence )
	{
		return sequence.hasNext();
	}
}
public int next( String aTable )
{
	// Obtain the sequence
	Sequence sequence = getSequence( aTable );

	// Now we synchronize on one sequence to obtain the next number. Since this only blocks
	// threads wanting to use this particular sequence, we can relax a little. Sequence.nextNumber
	// might involve going to the database to fetch a new block of numbers.
	synchronized( sequence )
	{
		return sequence.next();
	}
}
private Sequence getSequence( String aTable )
{
	// Get the Sequence from the tableMap.
	// The synchronized block that follws blocks all threads, so it should complete as quickly
	// as possible. But creating a sequence for a table that we don't know yet takes considerable time
	// (we probably need to go to the database).
	// If we move the creation code out of the synchronized block, multiple ranges might be created. This may
	// not be so bad, because all those ranges will be valid, and a random one wins. Also, sequence numbers may
	// be created out of order. So we compromized: The RangeSequence we create has an empty range, and only
	// when next() is called on the RangeSequence, the database is touched.
	synchronized( tableMap )
	{
		Sequence sequence = tableMap.get( aTable );
		if ( sequence == null )
		{
			sequence = new RangeSequence( getLimitHandler(), aTable );
			tableMap.put( aTable, sequence );
		}
		return sequence;
	}
}

/**
 * @return the LimitHandler we use
 */
private LimitHandler getLimitHandler()
{
	return limitHandler;
}

}
