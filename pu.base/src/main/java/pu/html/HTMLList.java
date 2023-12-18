package pu.html;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Insert the type's description here.
 * Creation date: (27-2-2001 1:04:05)
 * @author: Administrator
 */
public abstract class HTMLList extends HTMLBodyElement
{
	/**
	 * HTMLList is een container van ListElement, niets anders!
	 */
	private final Collection<HTMLElement> elements = new ArrayList<>();
	
	protected class ListElement extends HTMLContainer
	{
		private static final String TAG = "li";
		public ListElement( HTMLBodyElement aElement )
		{
			super( TAG, aElement );
			setEntersAfterEndTag( 1 );
		}
		public ListElement( String aElement )
		{
			super( TAG, aElement );
			setEntersAfterEndTag( 1 );
		}
	}
	
/**
 * HTMLList constructor comment.
 */
protected HTMLList( String aTag )
{
	super( aTag );
	setEntersAfterStartTag( 1 );
	setEntersAfterEndTag( 1 );
}

public Collection<HTMLElement> getElements()
{
	return elements;
}

/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 1:07:09)
 * @param s java.lang.String
 */
public void add( String aElement )
{
	elements.add( new ListElement( aElement ) );
}

/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 1:07:09)
 * @param s java.lang.String
 */
public void add( HTMLBodyElement aElement )
{
	elements.add( new ListElement( aElement ) );
}

/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 1:22:49)
 * @return java.lang.String
 */
@Override
protected String getTagContent()
{
	StringBuffer sb = new StringBuffer();
	for ( HTMLElement el : getElements() )
	{
		sb.append( el.getContent() );
	}
	return sb.toString();
}
}
