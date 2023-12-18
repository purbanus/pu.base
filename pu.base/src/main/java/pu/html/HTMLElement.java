package pu.html;

/**
 * Dit is de abstracte root van de HTML-classes. Regelt een paar dingen die voor elk HTML-element gelden:
 * <ul>
 * <li> Het feit dat elk HTML-element een <code>getContent()</code> methode moet hebben die de 
 * HTML-inhoud moet retourneren met tags en attributes etc.
 * <li> Een <code>toString()</code> methode die standaard <code>getContent()</code> doet.
 * </ul>
 * Over de volgende dingen wordt nog nagedacht:
 * <ul>
 * <li> Of de <code>getContent()</code> methode wel bestaansrecht heeft, of dat je alles moet verhuizen naar de <code>toString()</code>.
 * De gedachte was dat het onmogelijk is om de signatuur van de <code>toString()</code> methode te wijzigen, dus als ik nog 
 * flexibel wil inspringen bijvoorbeeld, dan kan ik <code>getContent( int indentLevel )</code> doen, en dan laat ik <code>toString()</code>
 * gewoon <code>getContent( 0 )</code> doen bijvoorbeeld. Deze mogelijkheid wil ik er nog even inhouden, dus zolang bestaat dat nog.
 * </ul>
 */
public abstract class HTMLElement
{
	public static final String NEWLINE = System.getProperty( "line.separator" );
	
/**
 * Creates a new HTMLElement.
 */
public HTMLElement()
{
	super();
}

/**
 * @return The content of this HTMLElement
 */
public abstract String getContent();

/**
 * @return The String representation of this HTMLElement
 */
@Override
public String toString()
{
	return getContent();
}
}
