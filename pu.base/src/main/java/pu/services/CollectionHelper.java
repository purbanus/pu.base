package pu.services;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CollectionHelper
{
private CollectionHelper()
{
}
/**
 * Returns {@code true} if the {@link Collection} throws an {@link UnsupportedOperationException} when calling
 * {@link Collection#addAll} with {@link Collections#emptyList}, false otherwise.
 *
 * @param collection
 *            instance being tested for being unmodifiable
 * @return {@code true} if the {@link Collection} throws an {@link UnsupportedOperationException} when calling
 *         {@link Collection#addAll} with {@link Collections#emptyList}, false otherwise
 */
public static boolean isUnmodifiable( Collection<?> collection )
{
	try
	{
		collection.addAll( Collections.emptyList() );
		return false;
	}
	catch ( UnsupportedOperationException UnsupportedOperationException )
	{
		return true;
	}
}

/**
 * Returns {@code true} if the {@link Map} throws an {@link UnsupportedOperationException} when calling
 * {@link Map#putAll} with an empty {@link Map#of}, false otherwise.
 *
 * @param map
 *            instance being tested for being unmodifiable
 * @return {@code true} if the {@link Map} throws an {@link UnsupportedOperationException} when calling
 *         {@link Map#putAll} with an empty {@link Map#of}, false otherwise
 */
public static boolean isUnmodifiable( Map<?, ?> map )
{
	try
	{
		map.putAll( Map.of() );
		return false;
	}
	catch ( UnsupportedOperationException UnsupportedOperationException )
	{
		return true;
	}
}
}
