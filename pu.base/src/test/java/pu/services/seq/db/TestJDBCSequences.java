/*
 * Created on 18-apr-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.services.seq.db;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pu.services.seq.SequenceManager;

public class TestJDBCSequences extends AbstractDbTest
{
SequenceManager sequenceManager;
HsqldbSequenceHelper helper;
JdbcLimitHandler limitHandler;

@Before
@Override
public void setUp() throws Exception
{
	super.setUp(); // Maakt applicationContext

	helper = (HsqldbSequenceHelper) applicationContext.getBean( "hsqldbSequenceHelper" );
	helper.createTable();

	sequenceManager = (SequenceManager) applicationContext.getBean( "sequenceManager" );

	limitHandler = (JdbcLimitHandler) applicationContext.getBean( "limitHandler" );
}

@After
public void tearDown() throws Exception
{
	helper.dropTableIfExists();
}

private void check( String aTestName, int aTestNo, String aTable, int aExpected, List<Map<String, Object>> aExpectedTableState )
{
	// aTestNo = 0 is initial setup
	if ( aTestNo > 0 )
	{
		assertTrue  ( aTestName + " hasNext#" + aTestNo, sequenceManager.hasNext( aTable ) );
		assertEquals( aTestName + " next#"    + aTestNo, aExpected, sequenceManager.next( aTable ) );
	}
	checkTableState( aTestName + " table state", aExpectedTableState );
}

private void checkTableState( String aTestName, List<Map<String, Object>> aExpectedState )
{
	Collection<Map<String,Object>> actualColl = limitHandler.getAllRows();

	assertEquals( aTestName + " size", aExpectedState.size(), actualColl.size() );

	Iterator<Map<String, Object>> expected = aExpectedState.iterator();
	Iterator<Map<String, Object>> actual   = actualColl.iterator();
	int index = 0;
	while ( expected.hasNext() )
	{
		Map<String, Object> exp = expected.next();
		Map<String, Object> act = actual.next();

		assertEquals( aTestName + "row size#" + index, exp.size(), act.size() );
		assertEquals( aTestName + "#" + index++, exp, act );
	}
}
@Test
public void testBasics()
{
	String testName = "basics";

	List<Map<String, Object>> expectedTabelState = new ArrayList<>();
	checkTableState( testName + " start", expectedTabelState );

	Map<String, Object> albRow = new HashMap<>();
	albRow.put( JdbcLimitHandler.COL_TABLE, "ALB" );
	albRow.put( JdbcLimitHandler.COL_NUMBER, Integer.valueOf( 4 ) );
	expectedTabelState.add( albRow );
	check( testName, 2, "ALB", 1, expectedTabelState );

	Map<String, Object> blaRow = new HashMap<>();
	blaRow.put( JdbcLimitHandler.COL_TABLE, "BLA" );
	blaRow.put( JdbcLimitHandler.COL_NUMBER, 4 );
	expectedTabelState.add( blaRow );
	check( testName, 1, "BLA", 1, expectedTabelState );

	check( testName, 3, "BLA", 2, expectedTabelState );
	check( testName, 4, "ALB", 2, expectedTabelState );
	check( testName, 3, "BLA", 3, expectedTabelState );
	check( testName, 4, "ALB", 3, expectedTabelState );

	// Nu deelt hij een nieuw blok uit
	blaRow.put( JdbcLimitHandler.COL_NUMBER, 7 );
	check( testName, 3, "BLA", 4, expectedTabelState );
	albRow.put( JdbcLimitHandler.COL_NUMBER, 7 );
	check( testName, 4, "ALB", 4, expectedTabelState );
}

@SuppressWarnings( "unused" )
private void printTableState()
{
	Collection<Map<String, Object>> coll = limitHandler.getAllRows();
	for ( Map<String, Object> row : coll )
	{
		System.out.println( row );
	}
}
}
