/**
 *
 */
package pu.db;

public interface DataResource
{

/**
 * @return Returns the driver.
 */
public abstract String getDriver();

/**
 * @return Returns the name.
 */
public abstract String getName();

/**
 * @return Returns the password.
 */
public abstract String getPassword();

/**
 * @return Returns the url.
 */
public abstract String getUrl();

/**
 * @return Returns the user.
 */
// Spelling komt overeen met Spring
public abstract String getUsername();

/**
 * @param aDriver The driver to set.
 */
public abstract void setDriver( String aDriver );

/**
 * @param aName The name to set.
 */
public abstract void setName( String aName );

/**
 * @param aPassword The password to set.
 */
public abstract void setPassword( String aPassword );

/**
 * @param aUrl The url to set.
 */
public abstract void setUrl( String aUrl );

/**
 * @param aUser The user to set.
 */
//Spelling komt overeen met Spring
public abstract void setUsername( String aUser );

}