package pu.services;

import java.io.Serializable;

/*
 * An implementation of MergeSort that sorts indices instead of the objects themselves.
 * Sometimes you don't want to rearrange the data itself, but rather create an index array
 * that represents the sorted data. For example, if you want to sort some integers ascending and they are
 * <p>{ Carla, Dirk, Bert, Ed, Abe }<p>
 * then the computed index-array would be 
 * <p>{ 4, 2, 0, 1, 3 }<p>
 * for if people would ask what the 0th element would be in sorted order, then it's on index 4 in the
 * original data (Abe). The 1st element in sorted order would be index 2 (Bert), etcetera.
 * <p>
 * Here's an example that prints out the names above in the correct order.
 * <pre>
 * public class IndexSortExample
 * {
 *	public static void main( String[] args )
 *	{
 *		final String [] originalData = { "Carla", "Dirk", "Bert", "Ed", "Abe" };
 *		IndexSort indexSort = new IndexSort()
 *		{
 *			public int compareElements( int a, int b )
 *			{
 *				return originalData[a].compareTo( originalData[b] );
 *			}
 *		};
 *		int [] indices = indexSort.sort( originalData );
 *		for ( int x = 0; x < originalData.length; x++ )
 *			System.out.println( originalData[indices[x]] );
 *			
 *	}
 * }
 * </pre>
 * During sorting, you can also refer to the original data simply as 'data' so
 * the compare could also have been written as
 * <pre>
 *				return data[a].compareTo( data[b] );
 * </pre>
 */
public abstract class IndexSort implements Serializable
{
	protected Object [] data;
	protected int toSort[];
	protected int swapSpace[];
/*
 * Vergelijk twee elementen. Retourneer een getal kleiner dan 0 als links < rechts is,
 * nul als ze aan elkaar gelijk zijn, en een getal groter dan nul als links > rechts is.
 * @param links int
 * @param rechts int
 * @return int
 */
public abstract int compareElements( int links, int rechts );
protected void merge(int begin, int middle, int end)
{
	int firstHalf, secondHalf, count;
	firstHalf = count = begin;
	secondHalf = middle + 1;
	while ((firstHalf <= middle) && (secondHalf <= end))
	{
		if (this.compareElements( toSort[secondHalf], toSort[firstHalf] ) < 0)
			swapSpace[count++] = toSort[secondHalf++];
		else
			swapSpace[count++] = toSort[firstHalf++];
	}
	if (firstHalf <= middle)
	{
		while (firstHalf <= middle)
			swapSpace[count++] = toSort[firstHalf++];
	}
	else
	{
		while (secondHalf <= end)
			swapSpace[count++] = toSort[secondHalf++];
	}
	for (count = begin; count <= end; count++)
		toSort[count] = swapSpace[count];
}
protected void mergeSort( int begin, int end )
{
	if (begin != end)
	{
		int mid;
		mid = ( begin + end ) / 2;
		mergeSort( begin, mid );
		mergeSort( mid + 1, end );
		merge( begin, mid, end );
	}
}
public int [] sort( Object [] aData )
{
	if ( aData == null )
		return new int[0];
		
	toSort = new int[aData.length];
	for ( int x = 0; x < aData.length; x++ )
		toSort[x] = x;
		
	if ( toSort.length > 1)
	{
		data = aData;
		swapSpace = new int[toSort.length];
		mergeSort( 0, toSort.length - 1);
		data = null;
		swapSpace = null;
	}
	return toSort;
}
}
