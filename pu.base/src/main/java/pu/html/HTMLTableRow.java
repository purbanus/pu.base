package pu.html;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Insert the type's description here.
 * Creation date: (27-2-2001 2:18:27)
 * @author: Administrator
 */
public class HTMLTableRow extends HTMLTag 
{
	private static final String TAG = "tr";
	
	private final Collection<HTMLElement> elements = new ArrayList<>();
	
	// HIGH Is er alleen voor de alignment lookup. Beetje magere reeden voor de parent/child
	private final HTMLTable parent;

public HTMLTableRow( HTMLTable aParent )
{
	super( TAG );
	parent = aParent;
	setIndent( 1 );
	setIndentEndTag( true );
	setEntersAfterStartTag( 1 );
}

public Collection<HTMLElement> getElements()
{
	return elements;
}

/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 2:20:08)
 * @param aContent PU.htmlgen.HTMLContent
 */
public void add( Object aElement )
{
	if ( aElement == null )
	{
		add( new HTMLTableData( "" ) );
	}
	else if ( aElement instanceof HTMLTableData )
	{
		// Als ze HTMLTableData opgeven veranderen we daar niets aan
		getElements().add( (HTMLTableData) aElement );
	}
	else if ( aElement instanceof HTMLBodyElement )
	{
		// HTMLBodyElement idem (voorlopig)
		getElements().add( new HTMLTableData( (HTMLBodyElement) aElement ) );
	}
	else
	{
		// Alle andere objecten: zoek de alignment op in de tabel
		HTMLTableData data = new HTMLTableData( aElement.toString() );
		data.setAlignment( parent.lookupAlignment( aElement.getClass() ) );
		getElements().add( data );
	}
}
/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 2:20:08)
 * @param aContent PU.htmlgen.HTMLContent
 */
public void add( HTMLBodyElement aElement )
{
	getElements().add( new HTMLTableData( aElement ) );
}
/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 2:20:08)
 * @param aContent PU.htmlgen.HTMLContent
 */
public void add( HTMLTableData aElement )
{
	getElements().add( aElement );
}
/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 4:48:29)
 * @return java.awt.Color
 */
public HTMLColor getBackground()
{
	return (HTMLColor) getAttribute( HTMLConstants.ATTR_BACKGROUND );
}
/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 2:47:49)
 * @return java.lang.String
 */
@Override
protected String getTagContent()
{
	StringBuffer sb = new StringBuffer();
	for ( HTMLElement el: getElements() )
	{
		sb.append( el.getContent() );
	}
	return sb.toString();
}
/**
 * Insert the method's description here.
 * Creation date: (29-3-2001 0:16:22)
 * @return PU.htmlgen.HTMLTable
 */
protected final HTMLTable getParent()
{
	return parent;
}
/**
 * Insert the method's description here.
 * Creation date: (7-3-2001 4:48:29)
 * @param newBackground java.awt.Color
 */
public HTMLTableRow setBackground( HTMLColor newBackground )
{
	setAttribute( HTMLConstants.ATTR_BACKGROUND, newBackground );
	return this;
}
}
