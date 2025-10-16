package pu.db.scriptrunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Deze class is helaas nodig om de mem-database op tijd te initialiseren.
 * Spring gaat er denk ik van uit dat je pas naar de database gaat als de ApplicationContext geheel
 * geladen is. Dat is bij ons niet zo:
 * <ul>
 * <li>Al van oudsher wordt de SecurityContext geladen bij de <code>afterPropertiesSet</code>
 *     dus vlak nadat de ApplicationContext geladen is.
 * <li>SecurityConstants loopt meteen want het is een FactoryBean en getObject() gaat meteen
 *     naar de database om de constantes op te halen. ZOu gemakkelijk vermeden kunnen worden maar
 *     is op dit moment gewoon zo.
 * </ul>
 * Dus op welk moment moeten dan onze database-reation scripts lopen? Na wat geexperimenteer
 * bleek de getConfigLocations() wel een goede plek. Het wordt als een van de eerste dingen
 * aangeroepen in een methode die vanuit de setup() wordt aangeroepen.
 * <p>
 * Roep bij deze class zo vaak als je wilt ensureLoaded() aan; de database-scripts zullen
 * maar eenmaal lopen.
 */
public abstract class MemDatabaseScript
{
	private static final Logger LOG = LoggerFactory.getLogger( MemDatabaseScript.class );
	
	private static boolean loaded = false;
	private ScriptRunner scriptRunner = null;

/**
 * Zorgt ervoor dat de databasescripts eenmalig gaan lopen.
 */
public void ensureLoaded()
{
	if ( ! loaded )
	{
		try
		{
			runDatabaseCreationScripts();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.exit( 0 );
		}
		loaded = true;
	}
}

protected abstract String [] getScriptLocations();

public void runDatabaseCreationScripts() throws FileNotFoundException, IOException, SQLException
{
	//StringHelper.printClassPath();

	String [] scriptLocations = getScriptLocations();
	for ( int x = 0; x < scriptLocations.length; x++ )
	{
		runScript( scriptLocations[x] );
	}
}

private void runScript( String aPath ) throws IOException, SQLException
{
	LOG.info( "Running databasescript " + aPath );
	ClassPathResource classPathResource = new ClassPathResource( aPath );
	Reader reader = new FileReader( classPathResource.getURL().getPath() );
	getScriptRunner().runScript( reader );
}
private ScriptRunner getScriptRunner()
{
	if ( scriptRunner == null )
	{
		// Wel zo veilig trouwens om dit ALTIJD over de memdatabase te runnen!!!!!!!!!!!!
		String driver   = getJdbcDriver();
		String url      = getJdbcUrl(); //"jdbc:hsqldb:hsql://localhost:63333/achfac";
		String user     = getJdbcUser();
		String password = getJdbcPassword();
	
		boolean autoCommit = true;
		boolean stopOnError = true;
		scriptRunner = new ScriptRunner( driver, url, user, password, autoCommit, stopOnError );
		scriptRunner.setLogWriter( null );
	}
	return scriptRunner;
}

public String getJdbcDriver()
{
	return "org.hsqldb.jdbcDriver";
}
public String getJdbcUrl()
{
	return "jdbc:hsqldb:mem:memdb";
}
public String getJdbcUser()
{
	return "sa";
}
public String getJdbcPassword()
{
	return "";
}

}
