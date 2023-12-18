/*
 * Created on 23-apr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.services;

/**
 * @author purbanus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface Filter<T>
{
	@SuppressWarnings( "rawtypes" )
	public static final Filter TRUE = new Filter()
	{
		@Override
		public boolean accept( Object obj )
		{
			return true;
		}
	};
	@SuppressWarnings( "rawtypes" )
	public static final Filter FALSE = new Filter()
	{
		@Override
		public boolean accept( Object obj )
		{
			return false;
		}
	};
public abstract boolean accept( T obj );
}
