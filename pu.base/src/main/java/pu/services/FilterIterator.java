package pu.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * Insert the type's description here.
 * Creation date: (6-4-2003 19:12:46)
 * @author: Peter Urbanus
 */
public class FilterIterator<T> implements Iterator<T>
{
	private final Iterator<T> ourIterator;
	
/**
 * FilterIterator constructor comment.
 */
public FilterIterator( Iterator<T> aParentIterator, Filter<T> aFilter )
{
	super();

	// Effe simpel nu
	List<T> acceptList = new ArrayList<>();
	while ( aParentIterator.hasNext() )
	{
		T obj = aParentIterator.next();
		if ( aFilter.accept( obj ) )
		{
			acceptList.add( obj );
		}
	}
	ourIterator = acceptList.iterator();
}
@Override
public boolean hasNext()
{
	return ourIterator.hasNext();
}
@Override
public T next()
{
	return ourIterator.next();
}
@Override
public void remove()
{
	throw new UnsupportedOperationException( "Cannot remove from FilterIterator (yet)" );
}
}
