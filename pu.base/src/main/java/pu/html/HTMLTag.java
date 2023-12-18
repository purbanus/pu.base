package pu.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pu.services.StringHelper;

/**
 * Dit is de abstracte basis voor alls classes die een HTML-tag zijn. De voornaamste klus van HTMLTag is het beheer van de attributes
 * <code>setAttribute() / getAttribute()</code>, omdat praktisch elke tag wel attributes heeft.
 * Over de volgende dingen wordt nog nagedacht:
 * <ul>
 * <li> Je zou de <code>Map</code> lazy kunnen maken, want zeker als je cascading style sheets veel gebruikt hebben de meeste
 * classes een stuk minder attributes. Moet onderzocht worden.
 * </ul>
 * Creation date: (24-2-2001 2:18:03)
 * @author: Administrator
 */
public abstract class HTMLTag extends HTMLElement
{
	/**
	 * The tag
	 */
	private final String tag;

	/**
	 * Whether the start- and endtag should be displayed, e.g &lt;tag> and &lt;/tag>.
	 */
	private boolean displayTag = true;
	
	/**
	 * Bewaart de verzameling attributes van een tag.
	 */
	private List<HTMLAttribute> attributes = null;

	/**
	 * Bewaart de verzameling attributes van een tag in key / value vorm.
	 */
	private Map<String, HTMLAttribute> attributeMap = null;
	
	/**
	 * Whether the tag has content.
	 */
	private boolean hContent = true;
	
	/**
	 * Hoeveel spaties we moeten inspringen
	 */
	private int indent = 0;

	/**
	 * Of de endtag ook ge-indent moet worden
	 */
	private boolean indentEndTag = false;

	/**
	 * Hoeveel enters we na de start tag moeten geven
	 */
	private int entersAfterStartTag = 0;

	/**
	 * Hoeveel enters we na de end tag moeten geven
	 */
	private int entersAfterEndTag = 0;
	
/**
 * Creates a new HTMLElement.
 */
// HIGH ipv in de ctor kun je ook abstract getTagString doen. Maakt de ctors wat simpeler
public HTMLTag( String aTag )
{
	super();
	tag = aTag;
}

/**
 * Returns a number of enters
 * @param aNumber The number of enters
 * @return A String with the desired number of enters
 */
private String enters( int aNumber )
{
	return StringHelper.repString( HTMLElement.NEWLINE, aNumber );
}

/**
 * Retourneert de waarde van een attribute, of <code>null</code> als het attribute niet opgegeven is
 * of geen waarde heeft.
 * @param key String met de naam van het attribute
 * @return Object De waarde van het attribute
 */
protected Object getAttribute( String key )
{
	return getAttributeMap().get( key ) == null ? null : getAttributeMap().get( key ).getValue();
}

private Map<String, HTMLAttribute> getAttributeMap()
{
	if ( attributeMap == null )
	{
		attributeMap = new HashMap<>();
	}
	return attributeMap;
}

private List<HTMLAttribute> getAttributes()
{
	if ( attributes == null )
	{
		attributes = new ArrayList<>();
	}
	return attributes;
}

/**
 * Retourneert een String representatie van de inhoud. Dat is de lege string als de verzameling attributes
 * leeg is, en een lijst in de vorm van " ATTR1=value1 ATTR2=value2...". De lijst begint altijd met een spatie.
 * @return java.lang.String
 */
protected String getAttributeString()
{
	if ( attributes == null || attributes.size() == 0 )
	{
		return "";
	}
	
	StringBuffer sb = new StringBuffer();

	for ( HTMLAttribute attr : getAttributes() )
	{
		sb.append( attr.getContent() );
	}
	return sb.toString();
}

/**
 * @return The String representation of this HTMLElement
 */
@Override
public final String getContent()
{
	StringBuffer sb = new StringBuffer();
	sb.append( indent() );
	
	// Start tag + attributes
	if ( isDisplayTag() )
	{
		sb.append( "<" + getTag() + getAttributeString() );
		if ( hasContent() )
		{
			sb.append( ">" );
			sb.append( enters( getEntersAfterStartTag() ) );
		}
	}
	
	// Content
	if ( hasContent() )
	{
		sb.append( getTagContent() );
	}
	
	// End tag
	if ( isDisplayTag() )
	{
		if ( isIndentEndTag() )
		{
			sb.append( indent() );
		}
		if ( hasContent() )
		{
			sb.append( getEndTag() );
		}
		else
		{
			sb.append( " />");
		}
		sb.append( enters( getEntersAfterEndTag() ) );
	}
	return sb.toString();
}

/**
 * Returns the end tag for this HTMLTag, e.g. <code>&lt;/p></code> for a paragraph.
 * This is ususally the start tag with the / inserted, but there are e few exceptions:
 * <code>hr/code> and <code>br</code> have no start- or endtags, but just tags: 
 * <code>&lt;hr ></code> or the newer xhtml version <code>&lt;hr /></code>.
 * getStartTag and getEndTag are overridden in such cases.
 *
 * @return the end tag for this HTMLTag
 */
// HIGH Dit is volgens mij niet ok
protected String getEndTag()
{
	return "</" + getTag() + ">";
}

/**
 * @return Hoeveel enters we na de end tag moeten geven
 */
protected int getEntersAfterEndTag()
{
	return entersAfterEndTag;
}

/**
 * @return Hoeveel enters we na de start tag moeten geven
 */
protected int getEntersAfterStartTag()
{
	return entersAfterStartTag;
}

/**
 * @return Hoeveel spaties we moeten inspringen
 */
public int getIndent()
{
	return indent;
}

/**
 * Retourneert de waarde van een int-attribute, of 0 als het attribute niet opgegeven is
 * of geen integer is.
 * <p>
 * N.B. Deze methode wordt niet gebruikt bij het genereren van HTML-code. Als daar blijkt dat
 * het attribute <code>null</code> is, wordt er geen tekst voor het attribute gegenereerd, en niet
 * "ATTR=0" of zoiets.
 * Creation date: (28-3-2001 1:29:03)
 * @param key String met de naam van het attribute
 * @return int De waarde van het attribute
 */
protected int getIntAttribute( String key )
{
	HTMLAttribute attribute = getAttributeMap().get( key );
	if ( attribute == null )
	{
		return 0;
	}
	return attribute.getValue() instanceof Integer ? ((Integer) attribute.getValue() ).intValue() : 0;
}

/**
 * Returns the start tag for this HTMLTag, e.g. <code>&lt;p></code> for a paragraph.
 * This is ususally just the start tag with the attributes inserted, but there are e few exceptions:
 * <code>hr</code> and <code>br</code> have no start- or endtags, but just tags: 
 * <code>&lt;hr ></code> or the newer xhtml version <code>&lt;hr /></code>.
 * getStartTag and getEndTag are overridden in such cases.
 *
 * @return the start tag for this HTMLTag
 */
protected String XXXgetStartTag()
{
	return "<" + getTag() + getAttributeString() + ">";
}

/**
 * @return The tag of this element
 */
public String getTag()
{
	return tag;
}

/**
 * Hier moeten afgeleide classes vertellen wat de inhoud van de tag is,
 * d.w.z. wat er tussen de begin- en eindetag staat.
 * In een HTMLEmphasis tag bijvoorbeeld: <code>&lt;em>nadruk&lt;/em></code>
 * is <code>nadruk</code> de tag content.
 * @return De tag content
 */
protected abstract String getTagContent();

/**
 * @return Whether the tag has content. If it does, the content and a full end tag
 * is rendered (&lt;/em>; if it doesn't, the tag is rendered like &lt;hr /> or 
 * &lt;base href="url" />.
 */
public boolean hasContent()
{
	return hContent;
}

private String indent()
{
	return StringHelper.spaties( getIndent() * HTMLConstants.INDENT_UNITS );
}

/**
 * @return	Whether the start- and endtag should be displayed, e.g &lt;tag> and &lt;/tag>.
 */
public boolean isDisplayTag()
{
	return displayTag;
}

/**
 * @return Whether the endtag is indented as well as the start tag
 */
protected boolean isIndentEndTag()
{
	return indentEndTag;
}

/**
 * Geeft een attribute een waarde, bijvoorbeeld <code>setAttribute( "name", "myframe" )</code> geeft een frame een naam.
 * @param key String met de naam van het attribute
 * @param value String met de nieuwe waarde
 * @return this HTMLTag
 */
protected HTMLTag setAttribute( HTMLAttribute aAttribute )
{
	getAttributes().add( aAttribute );
	getAttributeMap().put( aAttribute.getKey(), aAttribute );
	return this;
}

/**
 * Geeft een attribute een waarde, bijvoorbeeld <code>setAttribute( "name", "myframe" )</code> geeft een frame een naam.
 * @param key String met de naam van het attribute
 * @param value String met de nieuwe waarde
 * @return this HTMLTag
 */
protected HTMLTag setAttribute( String key, Object value )
{
	return setAttribute( new HTMLAttribute( key, value ) );
}

/**
 * @param b Whether the start- and endtag should be displayed, e.g &lt;tag> and &lt;/tag>.
 */
public void setDisplayTag(boolean b )
{
	displayTag = b;
}

/**
 * @return Hoeveel enters we na de end tag moeten geven
 */
protected HTMLTag setEntersAfterEndTag( int aEnters )
{
	entersAfterEndTag = aEnters;
	return this;
}

/**
 * @return Hoeveel enters we na de start tag moeten geven
 */
protected HTMLTag setEntersAfterStartTag( int aEnters )
{
	entersAfterStartTag = aEnters;
	return this;
}

/**
 * Sets whether the tag has content. If it does, the content and a full end tag
 * is rendered (&lt;/em>; if it doesn't, the tag is rendered like &lt;hr /> or 
 * &lt;base href="url" />.
 */
public void setHasContent( boolean b )
{
	hContent = b;
}

/**
 * Zet hoeveel spaties we moeten inspringen
 * @param hoeveel spaties we moeten inspringen
 * @return this HTMLTag
 */
protected HTMLTag setIndent( int aIndent )
{
	indent = aIndent;
	return this;
}

/**
 * sets whether the endtag is indented as well as the start tag
 * @param whether the endtag is indented as well as the start tag
 */
protected HTMLTag setIndentEndTag( boolean b )
{
	indentEndTag = b;
	return this;
}

/**
 * Geeft een int-attribute een waarde. Een voorbeeld is bij tabellen:
 * <code>
 * setCellSpacing( int cellSpacing )
 * {
 *	setIntAttribute( ATTR_CELLSPACING, 1 );
 * }
 * </code> zet de cellspacing van een tabel op 1.
 * @param key String met de naam van het attribute
 * @param value int met de nieuwe waarde
 * @return this HTMLTag
 */
protected HTMLTag setIntAttribute( String key, int value )
{
	return setAttribute( key, Integer.valueOf( value ) );
}

/**
 * Retourneert een String representatie van de inhoud. Dat is de lege string als de verzameling attributes
 * leeg is, en een lijst in de vorm van " ATTR1=value1 ATTR2=value2...". De lijst begint altijd met een spatie.
 * @return java.lang.String
 */
protected String XXXgetAttributeString()
{
	if ( attributes == null || attributes.size() == 0 )
	{
		return "";
	}
	
	StringBuffer sb = new StringBuffer();

	/*********
	Iterator keys = attributes.keySet().iterator();
	Iterator vals = attributes.values().iterator();
	Object value;
	while ( keys.hasNext() )
	{
		value = vals.next();
		sb.append( value == null ? "" : " " + keys.next() + "=\"" + value + '"' );
	}
	**********/
	return sb.toString();
}


}
