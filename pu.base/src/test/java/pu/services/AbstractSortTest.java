package pu.services;

import org.junit.Before;

/**
 * @@NOG Veel meer testen!
 * Creation date: (5-7-01 16:00:16)
 * @author: Peter Urbanus
 */
public class AbstractSortTest
{
	Integer [] testData;
	NumberName [] numberName;

	public static final class NumberName
	{
		public final Integer number;
		public final String name;
		public NumberName( Integer aNumber, String aName )
		{
			number = aNumber;
			name   = aName;
		}
		@Override
		public String toString()
		{
			return name;
		}
	}

public static Integer [] createTestData( int [] aTestData )
{
	Integer [] result = new Integer[aTestData.length];
	for ( int x = 0; x < aTestData.length; x++ )
	{
		result[x] = Integer.valueOf( aTestData[x] );
	}
	return result;
}
public static NumberName [] createTestNumberName( String [] aTestData )
{
	NumberName [] result = new NumberName[aTestData.length];
	for ( int x = 0; x < aTestData.length; x++ )
	{
		result[x] = new NumberName( Integer.valueOf( 1 ), aTestData[x] );
	}
	return result;
}
@Before
public void setUp() throws Exception
{
	testData = null;
	numberName = null;
}
}
