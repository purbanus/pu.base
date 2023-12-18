package pu.html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Insert the type's description here.
 * Creation date: (27-2-2001 1:04:05)
 * @author: Administrator
 */
public class HTMLTableHandmatig extends HTMLTable
{
	private final ArrayList<String> kolommen = new ArrayList<>();
	private final Collection<HTMLTableRow> rijen = new ArrayList<>();
	
/**
 * HTMLList constructor comment.
 */
public HTMLTableHandmatig()
{
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 2:14:24)
 * @param s java.lang.String
 */
public void addKolom( String aElement )
{
	kolommen.add( aElement );
}
/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 2:14:24)
 * @param s java.lang.String
 */
// Dit kan niet werken
//public void addKolom( HTMLBodyElement aElement )
//{
//	kolommen.add( aElement );
//}
/**
 * Insert the method's description here.
 * Creation date: (27-2-2001 1:07:09)
 * @param s java.lang.String
 */
public void addRij( HTMLTableRow aRij )
{
	rijen.add( aRij );
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 4:22:36)
 * @return java.lang.String
 */
@Override
public String getBody() 
{
	//HIGH Dit moet (deels) naar boven
	StringBuffer sb = new StringBuffer();
	Iterator<HTMLTableRow> it = rijen.iterator();
	while ( it.hasNext() )
	{
		sb.append( " <tr>\n" );
		sb.append( it.next() );
		sb.append( " </tr>\n" );
	}
	return sb.toString();
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:57:44)
 * @return int
 */
@Override
public int getColumnCount()
{
	return kolommen.size();
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:22:30)
 * @return int
 */
@Override
public String getColumnName( int kol )
{
	return kolommen.get( kol );
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:18:32)
 * @return int
 */
@Override
public int getRowCount()
{
	return rijen.size();
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:08:52)
 * @return java.lang.String
 */
@Override
protected String XXXgetTableTag()
{
	StringBuffer sb = new StringBuffer( "\n<table" );
	sb.append( " border=" );
	sb.append( getBorderWidth() );
	sb.append( " cellspacing=" );
	sb.append( getCellSpacing() );
	sb.append( " cellpadding=" );
	sb.append( getCellPadding() );
//	sb.append( " RULES=" );
//	sb.append( getRules() );
	sb.append( ">\n" );
	return sb.toString();
}
}
