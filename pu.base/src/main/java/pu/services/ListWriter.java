/*
 * Created on 13-jul-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.services;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


/**
  * ListWriter is a seemingly trivial implemtantion of PrintWriter.
  * The throwable instance that we are trying to represnt is asked to
  * print itself to a VectorWriter. 
  *
  * By our design choice, r string representation of the throwable
  * does not contain any line separators. It follows that println()
  * methods of VectorWriter ignore the 'ln' part.
  * */
public class ListWriter extends PrintWriter
{
	private static final Writer NULL_WRITER = new Writer()
	{    
		@Override
		public void close()
		{
			// blank
		}
		
		@Override
		public void flush()
		{
			// blank
		}
		
		@Override
		public void write(char[] cbuf, int off, int len)
		{
			// blank
		}
	};

	private final List<String> v = new ArrayList<>();

	public ListWriter()
	{
		super( NULL_WRITER );
	}

	public List<String> getList()
	{
		// Niet nodig om een kopie te maken voorlopig
		return v;
	}
	@Override
	public void print(Object o)
	{
		v.add(o.toString());
	}

	@Override
	public void print(char[] chars)
	{
		v.add(new String(chars));
	}

	@Override
	public void print(String s)
	{
		v.add(s);
	}

	@Override
	public void println(Object o)
	{
		v.add(o.toString());
	}

	// JDK 1.1.x apprenly uses this form of println while in
	// printStackTrace()
	@Override
	public void println(char[] chars)
	{
		v.add(new String(chars));
	}

	@Override
	public void println(String s)
	{
		v.add(s);
	}

	@Override
	public void write(char[] chars)
	{
		v.add(new String(chars));
	}

	@Override
	public void write(char[] chars, int off, int len)
	{
		v.add(new String(chars, off, len));
	}

	@Override
	public void write(String s, int off, int len)
	{
		v.add(s.substring(off, off + len));
	}

	@Override
	public void write(String s)
	{
		v.add(s);
	}

	public String[] toStringArray()
	{
		int len = v.size();
		String[] sa = new String[len];
		return v.toArray( sa );
	}

}
