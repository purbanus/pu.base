package pu.db.ibatis;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class JNBooleanTypeHandler extends BaseTypeHandler<Boolean>
{
	private static final String J = "J";
	private static final String N = "N";

	public JNBooleanTypeHandler()
	{
		super();
	}

	@Override
	public void setNonNullParameter( PreparedStatement aPreparedStatement, int aColumnIndex, Boolean aParameter, JdbcType aJdbcType ) throws SQLException
	{
		// @@NOG Werkt dit?
		aPreparedStatement.setString( aColumnIndex, aParameter ? J : N  );
	}

	@Override
	public Boolean getNullableResult( ResultSet aResultSet, String aColumnName ) throws SQLException
	{
		return valueOf( aResultSet.getString( aColumnName ) );
	}

	@Override
	public Boolean getNullableResult( ResultSet aResultSet, int aColumnIndex ) throws SQLException
	{
		return valueOf( aResultSet.getString( aColumnIndex ) );
	}

	@Override
	public Boolean getNullableResult( CallableStatement aCallableStatement, int aColumnIndex ) throws SQLException
	{
		return valueOf( aCallableStatement.getString( aColumnIndex ) );
	}

	public Boolean valueOf( String s )
	{
		if ( s == null )
		{
			return null;
		}
		else if ( J.equals( s ) )
		{
			return Boolean.TRUE;
		}
		else if ( N.equals( s ) )
		{
			return Boolean.FALSE;
		}
		throw new RuntimeException( "Unexpected value " + s + " found where " + J + " or " + N + " was expected." );
	}

//	@Override
//	public Object getResult( ResultGetter aGetter ) throws SQLException
//	{
//		String s = aGetter.getString();
//		if ( aGetter.wasNull() )
//		{
//			return null;
//		}
//		if ( J.equals( s ) )
//		{
//			return Boolean.TRUE;
//		}
//		if ( N.equals( s ) )
//		{
//			return Boolean.FALSE;
//		}
//		throw new RuntimeException( "Unexpected value " + s + " found where " + J + " or " + N + " was expected." );
//	}
//
//	@Override
//	public void setParameter( ParameterSetter aSetter, Object aParameter ) throws SQLException
//	{
//		if ( aParameter == null )
//		{
//			aSetter.setNull( Types.CHAR );
//		}
//		else
//		{
//			boolean b = ( (Boolean) aParameter ).booleanValue();
//			aSetter.setString( b ? J : N );
//		}
//	}


}
