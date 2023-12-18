package pu.html;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Insert the type's description here.
 * Creation date: (26-2-2001 1:43:50)
 * @author: Administrator
 */
// HIGH Kan deze niet vlak onder HTMLTag zodat Row, Doc etc ook containers kunnen zijn?
public class HTMLContainer extends HTMLBodyElement
{
	private final Collection<HTMLElement> elements = new ArrayList<>();

public HTMLContainer( String aTag )
{
	super( aTag );
}

public HTMLContainer( String aTag, String aElement )
{
	super( aTag );
	add( aElement );
}

public HTMLContainer( String aTagString, HTMLBodyElement aElement )
{
	super( aTagString );
	add( aElement );
}

public Collection<HTMLElement> getElements()
{
	return elements;
}

public HTMLContainer add( String aElement )
{
	getElements().add( new HTMLText( aElement ) );
	return this;
}

public HTMLContainer add( HTMLBodyElement aElement )
{
	getElements() .add( aElement );
	return this;
}

@Override
public String getTagContent()
{
	StringBuffer sb = new StringBuffer();
	for ( HTMLElement el : getElements() )
	{
		sb.append( el.getContent() );
	}
	return sb.toString();
}
}
	