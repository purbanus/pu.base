package pu.html;

public class Test
{
public Test()
{
	super();
}
public static void main( String[] args )
{
	new Test().verwerk();
}
public void test1()
{
	HTMLHeader header = new HTMLHeader( new HTMLTitle( "titeltje" ) );
//	HTMLBody body = new HTMLBody();

	// Body maken we in een StringBuffer
	StringBuffer sb = new StringBuffer();
	sb.append( new HTMLHeading.h1( "Koppie koppie" ) );
	sb.append( "En dit is dan de body\n" );
	HTMLParagraph p = new HTMLParagraph();
	p.add( "Bladiebla" );
	p.add( new HTMLHorizontalRule() );
	p.add( "Blo, o blo die bla, maar " );
	p.add( new HTMLItalic( "zap" ) );
	p.add( " de minxome foe" );
	p.add( new HTMLHorizontalRule() );
	sb.append( p );
	sb.append( new HTMLAnchor( "de oude.html", "Dit is de oude html" ) );
	sb.append( new HTMLHorizontalRule() );

	HTMLList li = new HTMLUnorderedList();
	li.add( "Ik wil gaan slapen" );
	li.add( "Maar ik wil ook wakkerblijven" );
	li.add( "Zou gewoon dromen wat zijn?" );
	sb.append( li );

	HTMLList ol = new HTMLOrderedList();
	ol.add( "Kijk op de klok" );
	ol.add( "Is het voor 03:00, dan blijven we wakker" );
	ol.add( "Daarna dromen we even" );
	ol.add( "Dan gaan we slapen" );
	sb.append( ol );
		
	sb.append( new HTMLHorizontalRule() );
	
	HTMLTableHandmatig tabel = new HTMLTableHandmatig();
	tabel.addKolom( "Kolom 1" );
	tabel.addKolom( "Kolom 2" );
	tabel.addKolom( "Kolom 3" );
	HTMLTableRow rij;
	rij = new HTMLTableRow( tabel );
	rij.add( "1" );
	rij.add( "Peter" );
	rij.add( "Urbanus" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "2" );
	rij.add( "Marjo" );
	rij.add( "van Erp" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "13" );
	rij.add( "Steven" );
	rij.add( "van Erp" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "97" );
	rij.add( "Jantje" );
	rij.add( "van Loenen" );
	tabel.addRij( rij );

	sb.append( tabel );
	
	HTMLBody body = new HTMLBody( sb.toString() );
	System.out.println( new HTMLDoc( header, body ) );
}
public void test2()
{
	HTMLHeader header = new HTMLHeader( new HTMLTitle( "titeltje" ) );
	HTMLBody body = new HTMLBody();

	// Body maken we in een StringBuffer
	body.add( new HTMLHeading.h1( "Koppie koppie" ) );
	body.add( "En dit is dan de body\n" );
	HTMLParagraph p = new HTMLParagraph();
	p.add( "Bladiebla" );
	p.add( new HTMLHorizontalRule() );
	p.add( "Blo, o blo die bla, maar " );
	p.add( new HTMLItalic( "zap" ) );
	p.add( " de minxome foe" );
	p.add( new HTMLHorizontalRule() );
	body.add( p );
	
	body.add( new HTMLAnchor( "de oude.html", "Dit is de oude html" ) );
	body.add( new HTMLHorizontalRule() );

	HTMLList li = new HTMLUnorderedList();
	li.add( "Ik wil gaan slapen" );
	li.add( "Maar ik wil ook wakkerblijven" );
	li.add( "Zou gewoon dromen wat zijn?" );
	body.add( li );

	HTMLList ol = new HTMLOrderedList();
	ol.add( "Kijk op de klok" );
	ol.add( "Is het voor 03:00, dan blijven we wakker" );
	ol.add( "Daarna dromen we even" );
	ol.add( "Dan gaan we slapen" );
	body.add( ol );
		
	body.add( new HTMLHorizontalRule() );
	
	HTMLTableHandmatig tabel = new HTMLTableHandmatig();
	tabel.addKolom( "Kolom 1" );
	tabel.addKolom( "Kolom 2" );
	tabel.addKolom( "Kolom 3" );
	HTMLTableRow rij;
	rij = new HTMLTableRow( tabel );
	rij.add( "1" );
	rij.add( "Peter" );
	rij.add( "Urbanus" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "2" );
	rij.add( "Marjo" );
	rij.add( "van Erp" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "13" );
	rij.add( "Steven" );
	rij.add( "van Erp" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "97" );
	rij.add( "Jantje" );
	rij.add( "van Loenen" );
	tabel.addRij( rij );

	body.add( tabel );
	
	System.out.println( new HTMLDoc( header, body ) );
}
public void testAbbr()
{
	HTMLHeader head = new HTMLHeader( new HTMLTitle( "Test abbr en acronym" ) );
	HTMLBody body = new HTMLBody();
	HTMLDoc doc = new HTMLDoc( head, body );
	HTMLParagraph para = new HTMLParagraph();
	body.add( para );

	para.add( "Dit is een <ABBR>afko</ABBR>, eens kijken wat ze ermee doen" );
	para.add( new HTMLLineBreak() );
	para.add( "Dit is een <ACRONYM>IBM</ACRONYM>, eens kijken wat ze ermee doen" );

	System.out.println( doc );
}
public void testColor()
{
	java.awt.Color c = new java.awt.Color( 0xcc, 0xcc, 0xff );
	HTMLColor hc = new HTMLColor( c );
	System.out.println( hc );
}
public void testFrames()
{
	HTMLHeader head = new HTMLHeader( new HTMLTitle( "Test Frames" ) );
	HTMLFrameSetAttribute attr = new HTMLFrameSetAttribute( HTMLFrameSetAttribute.ROWS, "60,*" );
	HTMLFrameSet main = new HTMLFrameSet( attr);
	HTMLFrameDoc doc = new HTMLFrameDoc( head, main );

	main.addFrame( new HTMLFrame( "Frame1.html", "Frame1" ) );
	main.addFrame( new HTMLFrame( "Frame2.html", "Frame2" ) );

	System.out.println( doc );
	
}
public void testHomePage()
{
	HTMLHeader header = new HTMLHeader( new HTMLTitle( "PU's homepage" ) );
	HTMLBody body = new HTMLBody();

	body.add( new HTMLHeading.h1( "PU's Homepage" ) );
	body.add( new HTMLHorizontalRule() );

	//HTMLParagraph par = new HTMLParagraph();

	HTMLList li = new HTMLUnorderedList();
	li.add( new HTMLAnchor( "admin/index.html", "Administratie van de IBM HTTP Server" ) );
	li.add( new HTMLAnchor( "doc/index.html", "Java documentatie" ) );
	body.add( li );

/***********	
	HTMLTableHandmatig tabel = new HTMLTableHandmatig();
	tabel.addKolom( "Kolom 1" );
	tabel.addKolom( "Kolom 2" );
	tabel.addKolom( "Kolom 3" );
	HTMLTableRow rij;
	rij = new HTMLTableRow( tabel );
	rij.add( "1" );
	rij.add( "Peter" );
	rij.add( "Urbanus" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "2" );
	rij.add( "Marjo" );
	rij.add( "van Erp" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "13" );
	rij.add( "Steven" );
	rij.add( "van Erp" );
	tabel.addRij( rij );
	rij = new HTMLTableRow( tabel );
	rij.add( "97" );
	rij.add( "Jantje" );
	rij.add( "van Loenen" );
	tabel.addRij( rij );
****************/
	System.out.println( new HTMLDoc( header, body ) );
}
public void verwerk()
{
	test1();
	// test2();
	// testColor();
	// testFrames();
	// testAbbr();
	// testHomePage();
	
	
}
}
