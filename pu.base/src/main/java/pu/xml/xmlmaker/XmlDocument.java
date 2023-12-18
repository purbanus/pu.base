package pu.xml.xmlmaker;


public class XmlDocument implements XmlBase
{
public static final String DEFAULT_VERSION  = "1.0";
public static final String DEFAULT_ENCODING = "UTF-8";

private final XmlElement root;
private final String encoding;
private final String version;

/**
 *
 */
public XmlDocument( XmlElement aRoot )
{
	this( aRoot, DEFAULT_ENCODING, DEFAULT_VERSION );
}
/**
 *
 */
public XmlDocument( XmlElement aRoot, String aEncoding )
{
	this( aRoot, aEncoding, DEFAULT_VERSION );
}
/**
 *
 */
public XmlDocument( XmlElement aRoot, String aEncoding, String aVersion )
{
	super();
	root = aRoot;
	encoding = aEncoding;
	version = aVersion;
}
@Override
public void getOutput( StringBuffer sb )
{
	sb.append( "<?xml version=\"" + version + "\" encoding=\"" + encoding + "\"?>" );
	sb.append( XmlBase.SEP );
	root.getOutput( sb );
}

}
