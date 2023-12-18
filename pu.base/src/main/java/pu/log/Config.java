/*
 * Created on 23-jun-04
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package pu.log;

/**
 * Config bevat alle constantes die we een keer configureerbaar moeten maken
 */
public interface Config
{
public static final String RLOG_SERVER          = "localhost";
public static final int    RLOG_SERVER_PORT     = 44444;
public static final int    RLOG_RECONNECT_DELAY = 60000;

// Iedere app zijn eigen APP, zoals UREN, IRCL, IRSV, etc
public static final String APP = "TEST";
}
