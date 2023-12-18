package pu.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pu.services.ArrayHelper;
import pu.services.LogHelper;
import pu.services.StringHelper;


/**
 * ClientProps is niets anders dan een Map met waardes die we van de client aan de logserver mee
 * willen geven. Gekozen is voor een Map omdat we snel dingen willen kunnen wijzigen terwijl er
 * nog oude clients lopen. Of tijdens de run van een client nieuwe props sturen, etc.
 * <p>
 * De ClientProps worden (momenteel) via een MDC aan een event geplakt.
 */
public class ClientProps implements Serializable
{
/** Key voor ClientProps in de MDC */
public static final String CLIENT_DATA_MDC_KEY = "clientProps";

/** System properties */
public static final String PROP_OS_NAME               = "OS_NAME";
public static final String PROP_OS_VERSION            = "OS_VERSION";
public static final String PROP_JAVA_CLASS_PATH       = "JAVA_CLASS_PATH";
public static final String PROP_JAVA_EXT_DIRS         = "JAVA_EXT_DIRS";
public static final String PROP_JAVA_HOME             = "JAVA_HOME";
public static final String PROP_JAVA_LIBRARY_PATH     = "JAVA_LIBRARY_PATH";
public static final String PROP_JAVA_RUNTIME_NAME     = "JAVA_RUNTIME_NAME";
public static final String PROP_JAVA_RUNTIME_VERSION  = "JAVA_RUNTIME_VERSION";
public static final String PROP_JAVA_VERSION          = "JAVA_VERSION";
public static final String PROP_JAVA_VM_VERSION       = "JAVA_VM_VERSION";
public static final String PROP_JAVA_CLASS_VERSION    = "JAVA_CLASS_VERSION";
public static final String PROP_SUN_BOOT_CLASS_PATH   = "SUN_BOOT_CLASS_PATH";
public static final String PROP_SUN_BOOT_LIBRARY_PATH = "SUN_BOOT_LIBRARY_PATH";
public static final String PROP_USER_COUNTRY          = "USER_COUNTRY";
public static final String PROP_USER_NAME             = "USER_NAME";
public static final String PROP_USER_TIMEZONE         = "USER_TIMEZONE"	;

//  // Het schijnt dat deze gepleten zijn in Java 1.4
// public static final String JAVA_FULLVERSION = "java.fullversion";
// public static final String USER_REGION      = "user.region";

/** De system properties voor het gemak in een array */
private static final String [] SYSTEM_PROPS =
{
	// ApplicationID is geen system property maar iets van ons
	PROP_OS_NAME,
	PROP_OS_VERSION,
	PROP_JAVA_CLASS_PATH,
	PROP_JAVA_EXT_DIRS,
	PROP_JAVA_HOME,
	PROP_JAVA_LIBRARY_PATH,
	PROP_JAVA_RUNTIME_NAME,
	PROP_JAVA_RUNTIME_VERSION,
	PROP_JAVA_VERSION,
	PROP_JAVA_VM_VERSION,
	PROP_JAVA_CLASS_VERSION,
	PROP_SUN_BOOT_CLASS_PATH,
	PROP_SUN_BOOT_LIBRARY_PATH,
	PROP_USER_COUNTRY,
	PROP_USER_NAME,
	PROP_USER_TIMEZONE,
};

/** De application properties voor het gemak in een array */
private static final String [] APP_PROPS =
{
	LogHelper.PROP_APPLICATION_ID,
};

/** Alle properties */
public static final String [] ALL_PROPS = ArrayHelper.combineArrays( SYSTEM_PROPS, APP_PROPS );

public static final String NO_VALUE = "NO VALUE";
private final Map<String, Object> props = new HashMap<>();

/**
 * ClientProps constructor.
 */
public ClientProps()
{
	super();

	// TODO Dit is tijdelijk, appid moet ergens anders vandaan komen
	set( LogHelper.PROP_APPLICATION_ID, Log.getApplicationID() );

	for ( String key : SYSTEM_PROPS )
	{
		String value = System.getProperty( toSystemPropertyFormat( key ) );
		if ( value == null )
		{
			Log.error( this, "System property is null: " + key );
			value = NO_VALUE;
		}
		set( key, value );
	}
}

/**
 * Serverside constructor for when remotedata is not yet available.
 */
private ClientProps( int dummy )
{
	super();
	for ( String element : ALL_PROPS )
	{
		set( element, NO_VALUE );
	}
}

public static ClientProps createEmptyClientData()
{
	return new ClientProps( 0 );
}

/**
 * Converts an array of String from our propertyformat (JAVA_HOME) to System.properties() format (java.home)
 * @param aProp The properties to convert
 * @return The converted properties
 */
public static List<String> toSystemPropertyFormat( List<String> aProps )
{
	List<String> res = new ArrayList<>( aProps.size());
	for ( String prop : aProps )
	{
		res.add( toSystemPropertyFormat( prop ) );
	}
	return res;
}

/**
 * Converts our propertyformat (JAVA_HOME) to System.properties() format (java.home)
 * @param aProp The property to convert
 * @return The converted property
 */
public static String toSystemPropertyFormat( String aProp )
{
	return StringHelper.replaceAll( aProp, "_", ".").toLowerCase();
}

/**
 * Converts System.properties() format (java.home) to our propertyformat (JAVA_HOME)
 * @param aProp The property to convert
 * @return The converted property
 */
public static String toOurPropertyFormat( String aProp )
{
	return StringHelper.replaceAll( aProp, ".", "_").toUpperCase();
}

public Object get( String aName )
{
	return props.get( aName );
}

// Dit moet nog even 1.3-compatible blijven dus geen generis!
public Map<String, Object> getAll()
{
	// Niet nodig om een defensieve kopie te maken: als iemand ze wijzigt gebeurt er niets, zijn allemaal primitieve waarden.
	return props;
}

public void set( String aName, Object aValue )
{
	props.put( aName, aValue );
}

/**
 * Returns a String that represents the value of this object.
 * @return a string representation of the receiver
 */
@Override
public String toString()
{
	return props.toString();
}

}
