package pu.log.log2.console;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.OutputStream;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import pu.log.log2.console.comm.JTextAreaOutputStream;

/**
 * SwingConsole is a console that is made of a JFrame with a JTextArea inside it.
 */
public class SwingConsole extends JFrame implements Runnable
{
	/**
	 * The textArea where the output goes
	 */
	private final JTextArea textArea;

	/**
	 * The outputstream that accepts bytes and sends them to the text area
	 */
	private final OutputStream outputStream;

	/**
	 * The initial text for the text area. The output of the outputstream is appended
	 * to the initial text. But see setInitialText for more information.
	 */
	private String initialText = null;
/**
 * Creates a new SwingConsole
 */
public SwingConsole()
{
	super( "Swing Console" );

	//textArea = new JTextArea( 30, 55 );
	textArea = new JTextArea();
	textArea.setEditable(false);
	//textArea.setFont( PasswordDialoog.LABEL_FONT );
	//textArea.setOpaque( false );
	//NIET textArea.setLineWrap( true );
	//NIET textArea.setWrapStyleWord( true );

	outputStream = new JTextAreaOutputStream( textArea );
}
/**
 * Fixes a Swing layout problem. After pack() you should call this method
 * and do another pack(). It is quite complicated, but if you don't do this
 * then onder some circumstances the panel containing the textarea will be
 * way too large.
 */
private void fixLayoutBug()
{
	Rectangle b = textArea.getBounds();
	if ( b != null )
		setBounds( b );
}
/**
 * Returns the outputstream that will deliver its bytes to the text area.
 */
public final OutputStream getOutputStream()
{
	return outputStream;
}
static public void main(String[] args)
{
	SwingConsole console = new SwingConsole();
	console.addWindowListener( new WindowAdapter()
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			Window w = e.getWindow();
			w.setVisible(false);
			w.dispose();
			System.exit(0);
		}

	});

	console.setInitialText( "Poepie\n" );

	//console.setInitialText(testBigText());

	console.run();

	//for ( int x = 0; x < 100; x++ )
	//	console.getTextArea().append( "Poepie " + x + "\n" );

	//console.getTextArea().append( testBigText() );

}
/**
 * Runs the screen.
 */
@Override
public void run()
{
	Dimension heleScherm = Toolkit.getDefaultToolkit().getScreenSize();

	// @@NOG Als je dit doet verdwijnen je scrollbars!!!!!!!!!!!!
	//textArea.setPreferredSize( new Dimension( heleScherm.width / 2 - 50, heleScherm.height - 100 ) );

	// @@NOG Scrollbars terug, maar hij luistert niet naar de bounds
	//textArea.setBounds( 100, 100, 100, 600 );

	JScrollPane scrollPane = new JScrollPane( textArea );
	
	getContentPane().add( scrollPane, BorderLayout.CENTER );
	addWindowListener( new WindowAdapter()
	{
		@Override
		public void windowClosing( WindowEvent e )
		{
			setVisible( false );
			dispose();
		}
	});

	if ( initialText != null )
		textArea.setText( initialText );
	textArea.setRows( 50 );
	textArea.setColumns( 55 );

	// Fixes a Swing bug. Calling pack() twice is part of the fix.
	pack();
	fixLayoutBug();
	pack();

	int width   = getSize().width;
	int height  = getSize().height;
	//int width   = heleScherm.width / 2;
	//int height  = heleScherm.height / 2;
	int x = ( heleScherm.width  - width  ) / 2;
	int y = ( heleScherm.height - height ) / 2;
	setBounds( new Rectangle( x, y, width, height ) );

	show();
}
/**
 * Sets the initial text for the text area. The output of the outputstream is appended
 * to the initial text.
 * <p>
 * This can be very hard to get right if text is already arriving on the outputstream. If we presume
 * that the initial text is a snapshot of the text in the outputstream, how can we assure that no bytes
 * will be lost between the snapshot and the moment that the outputstream is attached to whatever input?
 * Bytes may arrive on a different stream than this, and it may be dangerous (or present an unacceptable 
 * delay) to synchronize while contructing the console. The solutions I can think of are simply too heavy
 * for this problem.
 * <p>
 * So the intended use is to construct the console, get the outputstream, make a snapshot and pass it into
 * this method, attach the outputstream to the producer, and run the console.
 * <p>
 * See ServerMonitor.dubbelklik() and RemoteLogServer.attachToClient() for practical examples.
 */
public void setInitialText( String s )
{
	initialText = s;
}
/**
 * Insert the method's description here.
 * Creation date: (6-11-2002 1:04:42)
 * @return java.lang.String
 */
@SuppressWarnings( "unused" )
private static String testBigText()
{
	return
	"02:45:54 359 Debug: nl.mediacenter.application.cfg.BasicCfgStrategy: constructor met Application: nl.mediacenter.adhoc.Customer_2_Screen\n" +
	"02:45:54 437 Debug: nl.mediacenter.application.cfg.Login: login properties komen van DEV_LOGIN_PATH: C:\\IrisDev\\login.properties\n" +
	"02:45:54 484 Debug: nl.mediacenter.application.cfg.BasicCfgStrategy: Login properties: bootPath=http://www.iris/iris/boot.properties user=db2admin\n" +
	"02:45:54 484 Debug: nl.mediacenter.application.cfg.BasicCfgStrategy: boot properties komen van http://www.iris/iris/boot.properties\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: null\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: null\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: null\n" +
	"02:45:55 390 Info : nl.mediacenter.application.cfg.BasicCall400: instance gemaakt\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: null\n" +
	"02:45:55 765 Debug: Selectie: 0\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: 0\n" +
	"02:45:55 921 Debug: Klaar met haalData voor DatabaseModelAdhoc for Customer_2_Selector(4 recs, 109ms)\n" +
	"02:45:55 937 Debug: nl.mediacenter.table.dbm.DataReadyEvent source=DatabaseModelAdhoc for Customer_2_Selector numrecords=4 from DatabaseModelAdhoc for Customer_2_Selector\n" +
	"02:45:55 937 Debug: Selectie: geen\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: null\n" +
	"02:45:55 953 Debug: Selectie: 0\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: 1\n" +
	"02:45:55 984 Debug: Klaar met haalData voor DatabaseModelAdhoc for Customer_2_SalesSelector(3 recs, 31ms)\n" +
	"02:45:55 984 Debug: nl.mediacenter.table.dbm.DataReadyEvent source=DatabaseModelAdhoc for Customer_2_SalesSelector numrecords=3 from DatabaseModelAdhoc for Customer_2_SalesSelector\n" +
	"02:45:56 109 Debug: Selectie: 0\n" +
	"02:45:56 734 Debug: run() beeindigd in nl.mediacenter.application.VisualApplicationDelegate\n" +
	"02:46:36 203 Debug: Selectie: 1\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: 2\n" +
	"02:46:36 218 Debug: Klaar met haalData voor DatabaseModelAdhoc for Customer_2_SalesSelector(3 recs, 0ms)\n" +
	"02:46:36 218 Debug: nl.mediacenter.table.dbm.DataReadyEvent source=DatabaseModelAdhoc for Customer_2_SalesSelector numrecords=3 from DatabaseModelAdhoc for Customer_2_SalesSelector\n" +
	"02:46:36 218 Debug: Selectie: geen\n" +
	"02:46:36 234 Debug: Selectie: 0\n" +
	"02:46:38 656 Debug: Selectie: 2\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: 3\n" +
	"02:46:38 671 Debug: Klaar met haalData voor DatabaseModelAdhoc for Customer_2_SalesSelector(3 recs, 0ms)\n" +
	"02:46:38 671 Debug: nl.mediacenter.table.dbm.DataReadyEvent source=DatabaseModelAdhoc for Customer_2_SalesSelector numrecords=3 from DatabaseModelAdhoc for Customer_2_SalesSelector\n" +
	"02:46:38 671 Debug: Selectie: geen\n" +
	"02:46:38 671 Debug: Selectie: 0\n" +
	"02:48:13 125 Debug: Selectie: 0\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: 1\n" +
	"02:48:13 140 Debug: Klaar met haalData voor DatabaseModelAdhoc for Customer_2_SalesSelector(3 recs, 15ms)\n" +
	"02:48:13 140 Debug: nl.mediacenter.table.dbm.DataReadyEvent source=DatabaseModelAdhoc for Customer_2_SalesSelector numrecords=3 from DatabaseModelAdhoc for Customer_2_SalesSelector\n" +
	"02:48:13 156 Debug: Selectie: geen\n" +
	"02:48:13 156 Debug: Selectie: 0\n" +
	"02:48:13 281 Debug: Selectie: 0\n" +
	"02:48:14 656 Debug: Selectie: 2\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: 3\n" +
	"02:48:14 687 Debug: Klaar met haalData voor DatabaseModelAdhoc for Customer_2_SalesSelector(3 recs, 16ms)\n" +
	"02:48:14 687 Debug: nl.mediacenter.table.dbm.DataReadyEvent source=DatabaseModelAdhoc for Customer_2_SalesSelector numrecords=3 from DatabaseModelAdhoc for Customer_2_SalesSelector\n" +
	"02:48:14 687 Debug: Selectie: geen\n" +
	"02:48:14 687 Debug: Selectie: 0\n" +
	"02:48:15 328 Debug: Selectie: 1\n" +
	"nl.mediacenter.adhoc.Customer_2_SalesSelector.setParentKey: 2\n" +
	"02:48:15 343 Debug: Klaar met haalData voor DatabaseModelAdhoc for Customer_2_SalesSelector(3 recs, 15ms)"
	;

}
}
