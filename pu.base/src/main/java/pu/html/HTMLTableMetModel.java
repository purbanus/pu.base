package pu.html;

public abstract class HTMLTableMetModel extends HTMLTable {
/**
 * HTMLTableMetModel constructor comment.
 */
public HTMLTableMetModel() {
	super();
}
@Override
protected String getBody()
{
	StringBuffer sb = new StringBuffer();
	
	sb.append( "<TBODY>\n" ); // Start een bodysectie
	int atlBackgroundColors = getBodyBackground() == null ? 0 : getBodyBackground().length;
	for ( int r = 0; r < getRowCount(); r++ )
	{
		HTMLTableRow rij = new HTMLTableRow( this );
		if ( atlBackgroundColors > 0 )
		{
			rij.setBackground( getBodyBackground()[r % atlBackgroundColors] );
		}
		for ( int k = 0; k < getColumnCount(); k++ )
		{
			rij.add( getValueAt( r, k ) );
		}
		sb.append( rij.toString() );
	}
	sb.append( "</TBODY>\n" ); // Einde bodysectie
	return sb.toString();
}
protected abstract Object getValueAt(int rij, int kol);
}
