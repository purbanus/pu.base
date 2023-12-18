package pu.log.log2;

/**
 * Class voor ht loggen van Exceptions. Analoog aan Debug, Info en Warning zou dit Error
 * moeten heten, maar dat conflicteert met java.lang.Error. Is waarschijnlijk niet zo erg,
 * mar toch maar effe niet doen. Dus maar LogError.
 */
public class LogError extends LogCategorie
{
/**
 * Debug constructor comment.
 * @param aPrefix java.lang.String
 */
public LogError()
{
	super( "Error: " );
}
}
