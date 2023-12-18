package pu.html;

import java.awt.Color;

import pu.services.StringHelper;

/**
 * An html color class
 */
public class HTMLColor extends HTMLElement
{
	private final Color color;

/********* TODO De 16 standaard kleuren
    Black  = #000000    Green  = #008000
    Silver = #C0C0C0    Lime   = #00FF00
    Gray   = #808080    Olive  = #808000
    White  = #FFFFFF    Yellow = #FFFF00
    Maroon = #800000    Navy   = #000080
    Red    = #FF0000    Blue   = #0000FF
    Purple = #800080    Teal   = #008080
    Fuchsia= #FF00FF    Aqua   = #00FFFF
************/

/**
 * Creates a new HTMLColor
 * @param r The red component
 * @param g The green component
 * @param b The blue component
 */
public HTMLColor( int r, int g, int b )
{
	super();
	color = new Color( r, g, b );
}

/**
 * Creates a new HTMLColor from the specified java Color
 * @param Color the java Color
 */
public HTMLColor( Color c )
{
	super();
	color = c;
}

/**
 * @return the java Color
 */
public Color getColor()
{
	return color;
}
/**
 * @return The String representation of this HTMLElement
 */
@Override
public String getContent()
{
	// Je moet ook voorloopnullen hebben, ik zie nog niet hoe NumberFormat dat kan doen
	// met hex.
	String stringetje = Integer.toHexString( getColor().getRGB() & 0x00ffffff );
	int nullenToeTeVoegen = 6 - stringetje.length();
	if ( nullenToeTeVoegen < 0 )
	{
		throw new RuntimeException( "Fout in HTMLColor.getContent(): Ongeldige lengte voor HTMLColor: " + stringetje.length() );
	}
	String deNullen = StringHelper.repChar( '0', nullenToeTeVoegen );
	return "#" + deNullen + stringetje;
}
}
