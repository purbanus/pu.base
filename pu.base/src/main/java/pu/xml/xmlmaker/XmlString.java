/*
 * Created on 4-jan-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.xml.xmlmaker;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XmlString implements XmlBase
{
private final String string;
/**
 *
 */
public XmlString( String aString )
{
	super();
	string = aString;
}
@Override
public void getOutput( StringBuffer sb )
{
	sb.append( string );
}

}
