package pu.services;

/**
 * Insert the type's description here.
 * Creation date: (2-6-02 20:51:26)
 * @author: Peter Urbanus
 */
public class JavaVersion
{
	public static final JavaVersion JAVAVERSION1_1 = new JavaVersion( "Java 1.1 (en lager)" );
	public static final JavaVersion JAVAVERSION1_2 = new JavaVersion( "Java 1.2 (en hoger)" );
	// public static final JavaVersion CURRENTVERSION = JAVAVERSION1_1; // Waar was dat in vredesnaam voor?
	private static JavaVersion javaVersion = null;
	@SuppressWarnings( "unused" )
	private final String description;
	public static class VersionTriplet implements Comparable<VersionTriplet>
	{
		public final int major;
		public final int minor;
		public final int point;
		public VersionTriplet( int aMajor, int aMinor, int aPoint )
		{
			super();
			major = aMajor;
			minor = aMinor;
			point = aPoint;
		}
		
		@Override
		public int compareTo( VersionTriplet other )
		{
			int diff = major - other.major;
			if ( diff != 0 )
			{
				return diff;
			}
			diff = minor - other.minor;
			if ( diff != 0 )
			{
				return diff;
			}
			return point - other.point;
		}
		@Override
		public String toString()
		{
			return major + "." + minor + "." + point;
		}
	}
	public static final VersionTriplet V122 = new VersionTriplet( 1, 2, 2 );
	public static final VersionTriplet V131 = new VersionTriplet( 1, 3, 1 );
	public static final VersionTriplet V142 = new VersionTriplet( 1, 4, 2 );
	public static final VersionTriplet V150 = new VersionTriplet( 1, 5, 0 );
	
/**
 * JavaVersion constructor comment.
 */
public JavaVersion( String aDescription )
{
	super();
	description = aDescription;
}
/**
 * Insert the method's description here.
 * Creation date: (2-6-02 21:02:15)
 * @return nl.mediacenter.services.JavaVersion
 */
public static JavaVersion getJavaVersion()
{
	if ( javaVersion == null )
	{
		try
		{
			// AWTEventlistener was introduced in JAVA2
			Class.forName( "java.awt.event.AWTEventListener" );
			javaVersion = JAVAVERSION1_2;
		}
		catch ( ClassNotFoundException e )
		{
			javaVersion = JAVAVERSION1_1;
		}
		catch ( NoClassDefFoundError e )
		{
			javaVersion = JAVAVERSION1_1;
		}
	}
	return javaVersion;
}

/**
 * Bepaal de javaversie als een triplet van drie getallen: de major version, de minor version
 * en de point version. Bijvoorbeeld in 1.4.2 is a de major, 4 de minor en 2 de point version
 * <p>
 * Dit stikje is aangepast van Bruce Eckel. Sun is niet erg duidelijk over de versiestring in
 * "java.version", zie http://java.sun.com/j2se/versioning_naming.html
 * Daaruit kan ik echt niet opmaken wat er gebeurt als de versies groter dan 10 worden b.v.
 * Bruce gaat er van uit dat ze altijd maar 1 cijfer groot zijn. 
 * @return De javaversie als triplet
 */
public static VersionTriplet getJavaVersionTriplet()
{
    String version = System.getProperty( "java.version" );
    int major = version.charAt( 0 ) - '0';
	int minor = version.charAt( 2 ) - '0';
	int point = version.charAt( 4 ) - '0';
	return new VersionTriplet( major, minor, point );
}

public static boolean is122OrHigher()
{
	return getJavaVersionTriplet().compareTo( V122 ) >= 0;
}
public static boolean is131OrHigher()
{
	return getJavaVersionTriplet().compareTo( V131 ) >= 0;
}
public static boolean is142OrHigher()
{
	return getJavaVersionTriplet().compareTo( V142 ) >= 0;
}
public static boolean is150OrHigher()
{
	return getJavaVersionTriplet().compareTo( V150 ) >= 0;
}
}
