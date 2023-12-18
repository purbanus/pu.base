package pu.services.seq.db;

import org.junit.Before;
import org.springframework.context.ApplicationContext;

import pu.services.SpringHelper;

/**
 * Abstracte testclass voor simpele sequence-tests. Als je een ruime Range neemt namelijk,
 * is een RangeSequence niet anders dan een SimpleSequence. Dus testen we de eenvoudige
 * methodes van die twee in een keer door. Het verschil geef je aan met een SequenceFactory.
 */
public abstract class AbstractDbTest
{
ApplicationContext applicationContext;

@Before
public void setUp() throws Exception
{
	applicationContext = SpringHelper.setupApplicationContext( getClass() );
}

}