/**
 *
 */
package pu.db;

/**
 * @author Peter Urbanus
 *
 */
public class SimpleDataResource implements DataResource
{
private static final String DEFAULT_NAME = "adhoc dataresource";

// HIGH Ik geloof dat de url niet meer correct is, je moet een database-alias opgeven.
public static final DataResource HSQL_NET = new SimpleDataResource(
		"org.hsqldb.jdbcDriver",
		"jdbc:hsqldb:hsql://localhost",
		"sa",
		""
		);

private String driver;
private String url;
private String username;
private String password;
private String name = DEFAULT_NAME;

/**
 * Creates a new SimpleDataResource
 */
public SimpleDataResource()
{
	super();
}

/**
 * Creates a new SimpleDataResource
 * @param aDriver
 * @param aUrl
 * @param aUser
 * @param aPassword
 */
public SimpleDataResource( String aDriver, String aUrl, String aUser, String aPassword )
{
	this( aDriver, aUrl, aUser, aPassword, DEFAULT_NAME );
}

/**
 * Creates a new SimpleDataResource
 * @param aDriver
 * @param aUrl
 * @param aUser
 * @param aPassword
 * @param aName
 */
public SimpleDataResource( String aDriver, String aUrl, String aUser, String aPassword, String aName )
{
	super();
	driver = aDriver;
	url = aUrl;
	username = aUser;
	password = aPassword;
	name = aName;
}

/**
 * @return Returns the driver.
 */
@Override
public String getDriver()
{
	return driver;
}

/**
 * @param aDriver The driver to set.
 */
@Override
public void setDriver( String aDriver )
{
	driver = aDriver;
}

/**
 * @return Returns the name.
 */
@Override
public String getName()
{
	return name;
}

/**
 * @param aName The name to set.
 */
@Override
public void setName( String aName )
{
	name = aName;
}

/**
 * @return Returns the password.
 */
@Override
public String getPassword()
{
	return password;
}

/**
 * @param aPassword The password to set.
 */
@Override
public void setPassword( String aPassword )
{
	password = aPassword;
}

/**
 * @return Returns the url.
 */
@Override
public String getUrl()
{
	return url;
}

/**
 * @param aUrl The url to set.
 */
@Override
public void setUrl( String aUrl )
{
	url = aUrl;
}

/**
 * @return Returns the user.
 */
@Override
public String getUsername()
{
	return username;
}

/**
 * @param aUser The user to set.
 */
@Override
public void setUsername( String aUser )
{
	username = aUser;
}

}
