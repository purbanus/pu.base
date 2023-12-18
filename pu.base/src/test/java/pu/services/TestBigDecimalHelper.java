package pu.services;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testing IRISDatum, BedrijfsConstanten, MedewerkerHistBo and WerkdagLookup
 * Creation date: (5-7-01 14:53:08)
 * @author: Peter Urbanus
 */
public class TestBigDecimalHelper
{
@Test
public void testBerekenPercentageBedrag()
{
	class TestData
	{
		public final BigDecimal percentage;
		public final BigDecimal bedrag;
		public final int        scale;
		public final BigDecimal expected;
		TestData( String aPercentage, String aBedrag, int aScale, String aExpected )
		{
			super();
			percentage = new BigDecimal( aPercentage );
			bedrag = new BigDecimal( aBedrag );
			scale = aScale;
			expected = new BigDecimal( aExpected );
		}
	}
		
	TestData [] test = new TestData []
	{
		new TestData( "10.00", "1000.00", 2, "100.00" ),
		new TestData( "10.50", "1000.00", 2, "105.00" ),
		new TestData( "12.34", "1000.00", 2, "123.40" ),
		new TestData( "10.00", "5555.55", 2, "555.56" ),

		// Eerst wordt het percentage gedeeld door 100 (en afgerond op n+2 decimalen, maar dat
		// is overbodig), dan vermenigvuldigd. Hier komt 1851,6648... uit
		new TestData( "33.33", "5555.55", 2, "1851.66" ),
		new TestData( "33.33", "5555.56", 2, "1851.67" ),
		new TestData( "33.34", "5555.55", 2, "1852.22" ),

		// Kleine bedragjes
		new TestData( "17.65", "0", 2, "0.00" ),
		new TestData( "17.65", "1", 2, "0.18" ),
		new TestData( "17.65", "1", 3, "0.177" ),
		new TestData( "17.65", "1", 4, "0.1765" ),
		
	};
	
	// De test
	for ( int x = 0; x < test.length; x++ )
	{
		assertEquals( test[x].expected, BigDecimalHelper.berekenPercentageBedrag( test[x].percentage, test[x].bedrag, test[x].scale ) );
	}
}
@Test
public void testConstants()
{
	// Kijken of de scale goed is
	assertEquals( BigDecimalHelper.NUL_0  .scale(), 0 );
	assertEquals( BigDecimalHelper.EEN_0  .scale(), 0 );
	assertEquals( BigDecimalHelper.TWEE_0 .scale(), 0 );
	assertEquals( BigDecimalHelper.DRIE_0 .scale(), 0 );
	assertEquals( BigDecimalHelper.VIER_0 .scale(), 0 );
	assertEquals( BigDecimalHelper.VIJF_0 .scale(), 0 );
	assertEquals( BigDecimalHelper.TIEN_0 .scale(), 0 );
	assertEquals( BigDecimalHelper.HONDERD_0 .scale(), 0 );
	
	assertEquals( BigDecimalHelper.KWART_2.scale(), 2 );
	assertEquals( BigDecimalHelper.HALF_2 .scale(), 2 );
	assertEquals( BigDecimalHelper.NUL_2  .scale(), 2 );
	assertEquals( BigDecimalHelper.EEN_2  .scale(), 2 );
	assertEquals( BigDecimalHelper.TWEE_2 .scale(), 2 );
	assertEquals( BigDecimalHelper.DRIE_2 .scale(), 2 );
	assertEquals( BigDecimalHelper.VIER_2 .scale(), 2 );
	assertEquals( BigDecimalHelper.VIJF_2 .scale(), 2 );
	assertEquals( BigDecimalHelper.TIEN_2 .scale(), 2 );
	assertEquals( BigDecimalHelper.HONDERD_2 .scale(), 2 );
	assertEquals( BigDecimalHelper.EEN_4  .scale(), 4 );

	// Test string representatie
	assertEquals( BigDecimalHelper.NUL_0  .toString(), "0" );
	assertEquals( BigDecimalHelper.EEN_0  .toString(), "1" );
	assertEquals( BigDecimalHelper.TWEE_0 .toString(), "2" );
	assertEquals( BigDecimalHelper.DRIE_0 .toString(), "3" );
	assertEquals( BigDecimalHelper.VIER_0 .toString(), "4" );
	assertEquals( BigDecimalHelper.VIJF_0 .toString(), "5" );
	assertEquals( BigDecimalHelper.TIEN_0 .toString(), "10" );
	assertEquals( BigDecimalHelper.HONDERD_0 .toString(), "100" );
	
	assertEquals( BigDecimalHelper.KWART_2.toString(), "0.25" );
	assertEquals( BigDecimalHelper.HALF_2 .toString(), "0.50" );
	assertEquals( BigDecimalHelper.NUL_2  .toString(), "0.00" );
	assertEquals( BigDecimalHelper.EEN_2  .toString(), "1.00" );
	assertEquals( BigDecimalHelper.TWEE_2 .toString(), "2.00" );
	assertEquals( BigDecimalHelper.DRIE_2 .toString(), "3.00" );
	assertEquals( BigDecimalHelper.VIER_2 .toString(), "4.00" );
	assertEquals( BigDecimalHelper.VIJF_2 .toString(), "5.00" );
	assertEquals( BigDecimalHelper.TIEN_2 .toString(), "10.00" );
	assertEquals( BigDecimalHelper.HONDERD_2 .toString(), "100.00" );

	assertEquals( BigDecimalHelper.EEN_4  .toString(), "1.0000" );
}
@Test
public void testKwartierenNaarUren()
{
	int [] test = new int []
	{
		-8, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8,
	};
	BigDecimal [] expected = new BigDecimal []
	{
		// Normal values
		new BigDecimal( "-2.00" ), 
		new BigDecimal( "-1.75" ), 
		new BigDecimal( "-1.50" ), 
		new BigDecimal( "-1.25" ),	
		new BigDecimal( "-1.00" ), 
		new BigDecimal( "-0.75" ), 
		new BigDecimal( "-0.50" ), 
		new BigDecimal( "-0.25" ),	
		new BigDecimal( "0.00" ), 
		new BigDecimal( "0.25" ), 
		new BigDecimal( "0.50" ), 
		new BigDecimal( "0.75" ), 
		new BigDecimal( "1.00" ),
		new BigDecimal( "1.25" ), 
		new BigDecimal( "1.50" ), 
		new BigDecimal( "1.75" ), 
		new BigDecimal( "2.00" ),
	};
	
	// Evenveel testwaardes als expected
	assertEquals( "aantal testwaardes en expected zijn niet gelijk", test.length, expected.length );

	// De test
	for ( int x = 0; x < test.length; x++ )
	{
		assertEquals( expected[x], BigDecimalHelper.kwartierenNaarUren( test[x] ) );
	}
}
@Test
public void testRoundDouble()
{
	double [] test = new double []
	{
		10,
		10.1,
		10.11,
		10.111,
		10.1111,
		10.11111,
		10.111111,
		10.1111111,
		
		0,
		0.1,
		0.0000005,
		0.0000006,
		-1,
		-0.1,
	};
	BigDecimal [] expected = new BigDecimal []
	{
		new BigDecimal( "10" ),
		new BigDecimal( "10.1" ),
		new BigDecimal( "10.11" ),
		new BigDecimal( "10.111" ),
		new BigDecimal( "10.1111" ),
		new BigDecimal( "10.11111" ),
		new BigDecimal( "10.111111" ),
		new BigDecimal( "10.111111" ),
		
		new BigDecimal( "0" ),
		new BigDecimal( "0.1" ),
		new BigDecimal( "0" ), // Want 0.0000005 wordt als double 0.0000004999999999999999773740559129431293428069693618454039096832275390625
		new BigDecimal( "0.000001" ), 
		new BigDecimal( "-1" ),
		new BigDecimal( "-0.1" ),
	};
	// Evenveel testwaardes als expected
	assertEquals( " aantal testwaardes en expected zijn niet gelijk", test.length, expected.length );

	// De test
	for ( int x = 0; x < test.length; x++ )
	{
		assertEquals( expected[x], BigDecimalHelper.roundDouble( test[x], 6 ) );
	}
}
@Test
public void testUrenNaarKwartieren()
{
	BigDecimal [] test = new BigDecimal []
	{
		// Normal values
		new BigDecimal( "-1.00" ), 
		new BigDecimal( "-0.75" ), 
		new BigDecimal( "-0.50" ), 
		new BigDecimal( "-0.25" ),	
		new BigDecimal( "0.00" ), 
		new BigDecimal( "0.25" ), 
		new BigDecimal( "0.50" ), 
		new BigDecimal( "0.75" ), 
		new BigDecimal( "1.00" ),

		// Different number of decimals
		new BigDecimal( "-1" ), 
		new BigDecimal( "-1.0" ), 
		new BigDecimal( "-1.00" ), 
		new BigDecimal( "-1.000" ),
		
		// Rounding
		new BigDecimal( "-0.99" ),
		new BigDecimal( "-0.75" ),
		new BigDecimal( "-0.50" ),
		new BigDecimal( "-0.499" ),
		new BigDecimal( "-0.49" ),
		new BigDecimal( "-0.26" ),
		new BigDecimal( "-0.25" ),
		new BigDecimal( "-0.24" ),
		new BigDecimal( "-0.13" ),
		new BigDecimal( "-0.12" ),
		new BigDecimal( "-0.1" ),
		new BigDecimal( "0.1" ),
		new BigDecimal( "0.12" ),
		new BigDecimal( "0.13" ),
		new BigDecimal( "0.24" ),
		new BigDecimal( "0.25" ),
		new BigDecimal( "0.26" ),
	};
	int [] expected = new int []
	{
		// Normal values
		-4, -3, -2, -1, 0, 1, 2, 3, 4,

		// Different number of decimals
		-4, -4, -4, -4,

		//Rounding
		-4,
		-3,
		-2,
		-2,
		-2,
		-1,
		-1,
		-1,
		-1,
		 0,
		 0,
		 0,
		 0,
		 1,
		 1,
		 1,
		 1,
	};
	
	// Evenveel testwaardes als expected
	assertEquals( " aantal testwaardes en expected zijn niet gelijk", test.length, expected.length );

	// De test
	for ( int x = 0; x < test.length; x++ )
	{
		assertEquals( expected[x], BigDecimalHelper.urenNaarKwartieren( test[x] ) );
	}
}
@Test
public void testBigDecimalFormatters()
{
	// Punt of komma. Punt dus, en wat er bij de komma gebeurt snap ik niet
	Format metKomma = new DecimalFormat( " #,## %" );
	Format metPunt = new DecimalFormat( " #.## %" );
	Format metSigni = new DecimalFormat( " 0.00 %" );
	//System.out.println( metKomma.format( new BigDecimal( "1.2345" ) ) );
	//System.out.println( metPunt.format( new BigDecimal( "1.2345" ) ) );
	assertEquals( " 1,23 %", metKomma.format( new BigDecimal( "1.2345" ) ) );
	assertEquals( " 123.45 %", metPunt.format( new BigDecimal( "1.2345" ) ) );

	assertEquals( " 10 %", metPunt.format( new BigDecimal( "0.1000" ) ) );
	assertEquals( " 10.00 %", metSigni.format( new BigDecimal( "0.1000" ) ) );

	assertEquals( " 0.1 %", metPunt.format( new BigDecimal( "0.0010" ) ) );
	assertEquals( " 0.10 %", metSigni.format( new BigDecimal( "0.0010" ) ) );

	// TODO De formatters in Globals testen
}
@Test
public void testIsNul()
{
	assertTrue( BigDecimalHelper.isNul( null ) );
	assertTrue( BigDecimalHelper.isNul( new BigDecimal( "0" ) ) );
	assertTrue( BigDecimalHelper.isNul( new BigDecimal( "0.00" ) ) );
	assertFalse( BigDecimalHelper.isNul( new BigDecimal( "0.01" ) ) );
}
}
