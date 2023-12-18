package pu.log.log2.console;

public class Cfg
{
private static DbsCfg dbsCfg = new DbsCfg();
public static DbsCfg getDbsCfg()
{
	return dbsCfg;
}
public static class DbsCfg
{
private boolean rlogActive = false;
public String getRlogServer()
{
	return "localhost";
}
public int getRlogServerPort()
{
	return 44444;
}
public int getRlogReconnectDelay()
{
	return 60000;
}
public boolean isRlogActive()
{
	return rlogActive;
}
public void setRLogActive( boolean aActive )
{
	rlogActive = aActive;
}
public boolean isRlogRedirectConsole()
{
	// @@NOG Voorlopig altijd false
	return false;
}
}
}
