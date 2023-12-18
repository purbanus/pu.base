package pu.services.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class JdbcBatcher
{
	private final JdbcTemplate jdbcTemplate;
	private final String tableName;
	private final int batchSize;
	private List<Map<String, Object>> batch = new ArrayList<>();
public JdbcBatcher( JdbcTemplate aJdbcTemplate, String aTableName, int aBatchSize )
{
    super();
    jdbcTemplate = aJdbcTemplate;
    tableName = aTableName;
    batchSize = aBatchSize;
}
public JdbcTemplate getJdbcTemplate()
{
	return jdbcTemplate;
}
public String getTableName()
{
	return tableName;
}
public int getBatchSize()
{
	return batchSize;
}
public void add( Map<String, Object> aRow )
{
	batch.add( aRow );
	if ( batch.size() >= getBatchSize() )
	{
		insertBatch();
	}
}
private void insertBatch()
{
	@SuppressWarnings("unchecked")
	Map<String, Object> [] batchArray = new Map[batch.size()]; // Je mag geen new Map<String, Object>[batch.size()] doen. Mag gewoon niet.
	batch.toArray( batchArray );
	new SimpleJdbcInsert( getJdbcTemplate() ).withTableName( getTableName() ).executeBatch( batchArray );
    batch = new ArrayList<>();
}
public void finish()
{
	if ( batch.size() > 0 )
	{
		insertBatch();
	}
}
	
}