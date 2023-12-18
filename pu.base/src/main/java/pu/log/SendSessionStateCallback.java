/*
 * Created on 10-jun-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.LoggingEvent;

// Verboden want dit zijn servergegevens; staat nu in ClientProperties
//import pu.log.log4j.Config;
//import pu.log.log4j.RemoteClientData;

/**
 * SendSessionStateCallback
 * TODO Class-commentaar maken, Administrator
 */
public class SendSessionStateCallback implements SocketAppender.ConnectionCallback
{
private static final String FQCN_LOGGER = SendSessionStateCallback.class.getName();

private static Logger logger = Logger.getLogger( SendSessionStateCallback.class );

/**
 * Creates a new SendSessionStateCallback
 *
 */
public SendSessionStateCallback()
{
	super();
}


/* (non-Javadoc)
 * @see pu.log4j.net.SocketAppender.ConnectionCallback#connected(pu.log4j.net.SocketAppender)
 */
@Override
public void connected(SocketAppender appender)
{
	// Create session state map and add it to the MDC
	//Map state = new HashMap();
	//state.put( "user", "purbanus" );
	//state.put( "os.name", System.getProperty( "os.name" ) );
	// ...etc

	// MDC blijkt toch niet bruikbaar, dus we stoppen de map in de message
	//modifyMDC( state, true );


	// Stuur een speciaal LoggingEvent
	//   Dit werkt niet want de appender zit nog niet in de hierarchie
	//    logger.setLevel( Level.INFO );
	//    logger.info( "Eh, dit is de tekst van het session event" );
	// Je moet dus zelf het event versturen
	//appender.append( new LoggingEvent( Category.class.getName(), logger, Level.INFO, state, null ) );

	// Remove the state since we don't want to send it everytime
	//modifyMDC( state, false );

	// Normaliter hebben we nu logger niet meer nodig, want we willen de boel maar 1 keer verzenden
	// Echter:
	// - Je weet niet of het aangekomen is
	// - De ontvanger kan tegen meerdere boodschappen van dit type
	// - Als de server uit/aan is geweest is het nuttig om opnieuw te sturen
	// Met andere woorden, elke keer als we aangeroepen worden sturen we de boel opnieuw.

	// Later: we sturen gewoon een RemoteClientData object

	// En dit werkt ook niet: message is een transient veld. Na serializatie is er alleen een
	// String van over: renderedMessage.
	//RemoteClientData data = new RemoteClientData( Config.APP );
	//LoggingEvent event = new LoggingEvent( Category.class.getName(), logger, Level.INFO, data, null );
	//appender.append( event );

	// Terug naar het eerste idee
	modifyMDC( true );
	LoggingEvent event = new LoggingEvent( FQCN_LOGGER, logger, Level.INFO, "Remote Client Session State", null );
	appender.append( event );
	modifyMDC( false );
}
private void modifyMDC( boolean add )
{
	if ( add )
	{
		MDC.put( ClientProps.CLIENT_DATA_MDC_KEY, new ClientProps() );
	}
	else
	{
		MDC.remove( ClientProps.CLIENT_DATA_MDC_KEY );
	}
}
}
