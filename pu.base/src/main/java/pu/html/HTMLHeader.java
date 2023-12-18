package pu.html;

/**
 * Insert the type's description here.
 * Creation date: (24-2-2001 2:48:12)
 * @author: Administrator
 */
public class HTMLHeader extends HTMLTag
{
	private static final String TAG = "head";
	
	// HIGH Collection?
	private HTMLBase base = null;
	private HTMLTitle title = null;
	private HTMLLink link = null;
	
public HTMLHeader( String aTitle )
{
	this( new HTMLTitle( aTitle ) );
}
public HTMLHeader( HTMLTitle aTitle )
{
	super( TAG );
	setTitle( aTitle );
	setEntersAfterStartTag( 1 );
	setEntersAfterEndTag( 1 );
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:41:43)
 * @return PU.htmlgen.HTMLBase
 */
public HTMLBase getBase()
{
	return base;
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:39:09)
 * @return PU.htmlgen.HTMLTitle
 */
public HTMLLink getLink()
{
	return link;
}

// HIGH Check: all getTagContent should be protected
@Override
protected String getTagContent()
{
	return ( getTitle() == null ? "" : getTitle().getContent() )
		+   ( getBase()  == null ? "" : getBase().getContent() )
		+   ( getLink()  == null ? "" : getLink().getContent() )
	;
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:39:09)
 * @return PU.htmlgen.HTMLTitle
 */
public HTMLTitle getTitle()
{
	return title;
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:41:43)
 * @param newBase PU.htmlgen.HTMLBase
 */
public HTMLHeader setBase( HTMLBase newBase )
{
	base = newBase;
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:41:43)
 * @param newBase PU.htmlgen.HTMLBase
 */
public HTMLHeader setLink( HTMLLink newLink )
{
	link = newLink;
	return this;
}

/**
 * Insert the method's description here.
 * Creation date: (11-3-2001 0:39:09)
 * @param newTitle PU.htmlgen.HTMLTitle
 */
public HTMLHeader setTitle( HTMLTitle newTitle )
{
	title = newTitle;
	return this;
}
}
