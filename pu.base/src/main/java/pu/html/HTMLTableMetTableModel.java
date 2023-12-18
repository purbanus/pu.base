package pu.html;

import javax.swing.table.TableModel;

/**
 * Insert the type's description here.
 * Creation date: (11-3-2001 3:35:46)
 * @author: Administrator
 */
public class HTMLTableMetTableModel extends HTMLTableMetModel
{
	private final TableModel model;
/**
 * TableMetTableModel constructor comment.
 * @param aModel javax.swing.table.TableModel
 */
public HTMLTableMetTableModel( TableModel aModel )
{
	super();
	model = aModel;
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:18:32)
 * @return int
 */
@Override
public int getColumnCount()
{
	return model.getColumnCount();
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:22:30)
 * @return int
 */
@Override
public String getColumnName( int kol )
{
	return model.getColumnName( kol );
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:18:32)
 * @return int
 */
@Override
public int getRowCount()
{
	return model.getRowCount();
}
/**
 * Insert the method's description here.
 * Creation date: (25-3-2001 3:40:06)
 * @return java.lang.Object
 * @param rij int
 * @param kol int
 */
@Override
protected Object getValueAt( int rij, int kol )
{
	return model.getValueAt( rij, kol );
}
}
