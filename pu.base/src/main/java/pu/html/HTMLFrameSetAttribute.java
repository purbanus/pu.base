package pu.html;

/**
 * A HTMLAttribute for frame sets. Can be either ROWS or COLS
 */
public class HTMLFrameSetAttribute extends HTMLAttribute
{
	/**
	 * For specifying the <code>rows</code> attribute
	 */
	public static final RC ROWS = new RC( "rows" );

	/**
	 * For specifying the <code>cols</code> attribute
	 */
	public static final RC COLS = new RC( "cols" );
	
	/**
	 * Private enum class for the <code>row</code> or <code>column</code> attribute
	 */
	private static class RC extends HTMLElement
	{
		private final String rc;
		private RC( String aRC )
		{
			rc = aRC;
		}
		@Override
		public String getContent()
		{
			return rc;
		}
	}

/**
 * creates a new HTMLFrameSetAttribute with the specified RC and value.
 * @param aRC the <code>row</code> or <code>column</code> attribute
 * @param aValue the value of the <code>row</code> or <code>column</code> attribute
 */
public HTMLFrameSetAttribute( RC aRC, String aValue )
{
	super( aRC.getContent(), aValue );
}

}
