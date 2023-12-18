/**
 * 
 */
package pu.services.comm;

import java.io.IOException;
import java.io.InputStream;

/**
 * Wrapper om java.net.Socket omdat die heel lastig te mocken is.
 * Het probleem is ( o.a. ?) dat je geen InetAddress kunt maken omdat
 * die een package-private constructor heeft, dus op je mock kun je niet
 * eens getInterAddress() doen.
 */
public interface TcpSocket
{
public abstract String getIpAddress();
public abstract int getLocalPort();
public abstract void close() throws IOException;
public abstract InputStream getInputStream() throws IOException;
}
