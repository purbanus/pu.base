package pu.db.seq;

/**
 * SequenceHelper die werkt voor HSQLDB
 */
public class HsqldbSequenceHelper extends AbstractSequenceHelper
{

public HsqldbSequenceHelper()
{
	super();
}

@Override
protected String getNextValueSql( String aSequenceName )
{
	return "call NEXT VALUE FOR " + aSequenceName;
}

}
