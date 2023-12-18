/*
 * Created on 3-jan-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.xml.xmlmaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XmlElement implements XmlBase
{
private final String tag;
// Bij grote XML-bestanden kom je in de geheugenproblemen als je hier alvast een
// HashMap en een ArrayList maakt:
// - een HashMap is iets van 280 bytes + de object-overhead
// - een ArrayList is plm. 48 bytes + de object-overhead
// Samen alweer 328 bytes!
private Map<String, String> attributes = null;
private Collection<XmlBase> children = null;

/**
 *
 */
public XmlElement( String aTag )
{
	super();
	tag = aTag;
}

/**
 * @return Returns the attributes.
 */
private Map<String, String> getAttributes()
{
	if ( attributes == null )
	{
		// Eventueel ook capaciteit kleiner maken, zeg 5 of zo (standaard is 16)
		attributes = new HashMap<>();
	}
	return attributes;
}

/**
 * @return Returns the children.
 */
private Collection<XmlBase> getChildren()
{
	if ( children == null )
	{
		children = new ArrayList<>( 2 );
	}
	return children;
}

public boolean hasAttributes()
{
	return attributes != null;
}

public boolean hasChildren()
{
	return children != null;
}

public XmlElement add( XmlElement aElement )
{
	getChildren().add( aElement );
	return this;
}

public XmlElement add( String s )
{
	getChildren().add( new XmlString( s ) );
	return this;
}

public XmlElement addAttribute( String aKey, Object aValue )
{
	getAttributes().put( aKey, String.valueOf( aValue ) );
	return this;
}

public XmlElement addAttribute( String aKey, int aValue )
{
	getAttributes().put( aKey, String.valueOf( aValue ) );
	return this;
}

@Override
public void getOutput( StringBuffer sb )
{
	sb.append( "<" );
	sb.append( tag );
	if ( hasAttributes() )
	{
		for ( String key : getAttributes().keySet() )
		{
			sb.append( " " )
			.append( key )
			.append( "=" )
			.append( "\"" )
			.append( getAttributes().get( key ) )
			.append( "\"" );
		}
	}
	if ( ! hasChildren() )
	{
		sb.append( " />");
		sb.append( SEP );
	}
	else
	{
		sb.append( ">");
		// Enter als er meer dan 1 child is
		if ( getChildren().size() > 1)
		{
			sb.append( SEP );
		}
		else
		{
			// Er is 1 kind. Alleen enter geven als die kinderen heeft
			XmlBase onlyChild = getChildren().iterator().next();
			if ( onlyChild instanceof XmlElement && ( (XmlElement) onlyChild ).hasChildren() )
			{
				sb.append( SEP );
			}
		}
		for ( XmlBase element : getChildren() )
		{
			element.getOutput( sb );
		}
		sb.append( "</");
		sb.append( tag );
		sb.append( ">");
		sb.append( SEP );
	}
}
@Override
public String toString()
{
	StringBuffer sb = new StringBuffer();
	getOutput( sb );
	return sb.toString();
}
}
