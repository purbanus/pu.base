package pu.db.seq;

import javax.sql.DataSource;

import org.springframework.beans.factory.FactoryBean;

/**
 * Factory bean waarmee Spring nieuwe SequenceHelpers maakt.
 * <p>
 * Dit was nodig omdat we de SequenceHelper in een simpele propertyfile
 * wilden opgeven en niet steeds in de AppplicationContext een paar regels wilden afsterren etc.
 * <p> Spring roept deze factory maar een keer aan omdat het een singleton is, maar voor de ze-
 * kerheid maakt deze factory de SequenceHelper ook maar 1 keer aan.
 */
public class SequenceHelperFactoryBean implements FactoryBean<SequenceHelper>
{
private DataSource dataSource = null;
private Class<SequenceHelper> sequenceHelperClass = null;
private SequenceHelper sequenceHelper= null;

public void setDataSource( DataSource aDataSource )
{
	dataSource = aDataSource;
}
public void setSequenceHelperClass( Class<SequenceHelper> aClass )
{
	sequenceHelperClass = aClass;
}

@Override
public SequenceHelper getObject() throws Exception
{
	if ( sequenceHelper == null )
	{
		sequenceHelper = sequenceHelperClass.newInstance();
		sequenceHelper.setDataSource( dataSource );
	}
	return sequenceHelper;
}

@Override
public Class<SequenceHelper> getObjectType()
{
	return SequenceHelper.class;
}

@Override
public boolean isSingleton()
{
	return true;
}

}
