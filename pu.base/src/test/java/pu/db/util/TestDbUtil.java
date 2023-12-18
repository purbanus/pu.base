package pu.db.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 */
public class TestDbUtil
{

@Test
public void testQuestionMarkList()
{
	assertEquals( "", DbUtil.questionMarkList( 0 ) );
	assertEquals( "?", DbUtil.questionMarkList( 1 ) );
	assertEquals( "?, ?", DbUtil.questionMarkList( 2 ) );
}

}