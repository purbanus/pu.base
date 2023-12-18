/**
 * 
 */
package pu.services.comm;

import java.io.IOException;


/**
 * Wrapper om java.net.ServerSocket omdat die heel lastig te mocken is.
 * Het probleem is ( o.a. ?) dat je geen InetAddress kunt maken omdat
 * die een package-private constructor heeft, dus op je mock kun je niet
 * eens getInterAddress() doen.
 */
public interface TcpServerSocket
{
public abstract String getIpAddress();
public abstract int getLocalPort();
public abstract void close() throws IOException;
public abstract TcpSocket accept() throws IOException;
}
