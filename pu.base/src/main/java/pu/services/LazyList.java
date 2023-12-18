package pu.services;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class LazyList<T> implements List<T>
{
private List<T> delegate = null;

//////////////////////////////////////////////////////////////////////////////
// Delegate business

private List<T> getDelegate()
{
	if ( delegate == null )
	{
		delegate = obtainList();
	}
	return delegate;
}

public abstract List<T> obtainList();

//////////////////////////////////////////////////////////////////////////////
// Methodes van  List

@Override
public void add( int aIndex, T aElement )
{
	getDelegate().add( aIndex, aElement );
}

@Override
public boolean add( T aO )
{
	return getDelegate().add( aO );
}

@Override
public boolean addAll( Collection<? extends T> aC )
{
	return getDelegate().addAll( aC );
}

@Override
public boolean addAll( int aIndex, Collection<? extends T> aC )
{
	return getDelegate().addAll( aIndex, aC );
}

@Override
public void clear()
{
	getDelegate().clear();
}

@Override
public boolean contains( Object aO )
{
	return getDelegate().contains( aO );
}

@Override
public boolean containsAll( Collection<?> aC )
{
	return getDelegate().containsAll( aC );
}

@Override
public boolean equals( Object aO )
{
	return getDelegate().equals( aO );
}

@Override
public T get( int aIndex )
{
	return getDelegate().get( aIndex );
}

@Override
public int hashCode()
{
	return getDelegate().hashCode();
}

@Override
public int indexOf( Object aO )
{
	return getDelegate().indexOf( aO );
}

@Override
public boolean isEmpty()
{
	return getDelegate().isEmpty();
}

@Override
public Iterator<T> iterator()
{
	return getDelegate().iterator();
}

@Override
public int lastIndexOf( Object aO )
{
	return getDelegate().lastIndexOf( aO );
}

@Override
public ListIterator<T> listIterator()
{
	return getDelegate().listIterator();
}

@Override
public ListIterator<T> listIterator( int aIndex )
{
	return getDelegate().listIterator( aIndex );
}

@Override
public T remove( int aIndex )
{
	return getDelegate().remove( aIndex );
}

@Override
public boolean remove( Object aO )
{
	return getDelegate().remove( aO );
}

@Override
public boolean removeAll( Collection<?> aC )
{
	return getDelegate().removeAll( aC );
}

@Override
public boolean retainAll( Collection<?> aC )
{
	return getDelegate().retainAll( aC );
}

@Override
public T set( int aIndex, T aElement )
{
	return getDelegate().set( aIndex, aElement );
}

@Override
public int size()
{
	return getDelegate().size();
}

@Override
public List<T> subList( int aFromIndex, int aToIndex )
{
	return getDelegate().subList( aFromIndex, aToIndex );
}

@Override
public Object [] toArray()
{
	return getDelegate().toArray();
}

@SuppressWarnings( "hiding" )
@Override
public <T> T [] toArray( T [] a )
{
	return getDelegate().toArray( a );
}

}
