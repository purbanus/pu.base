package pu.html;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract base class for body and frameset
 */
public abstract class AbstractBody extends HTMLTag
{
private final Collection<HTMLElement> elements = new ArrayList<>();

/**
 * Creates a new AbstractBody.
 */
public AbstractBody( String aTag )
{
	super( aTag );

}

/**
 * @return The elements of this body
 */
protected Collection<HTMLElement> getElements()
{
	return elements;
}

/**
 * @return the content of this tag, that is the content between the start- and entag
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
