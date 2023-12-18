/*
 * Created on 4-jan-05
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.xml.xmlmaker;


public interface XmlBase
{
public static final String SEP = System.getProperty( "line.separator" );

public abstract void getOutput( StringBuffer sb );
}