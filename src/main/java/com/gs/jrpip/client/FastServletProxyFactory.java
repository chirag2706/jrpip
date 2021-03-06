/*
  Copyright 2017 Goldman Sachs.
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing,
  software distributed under the License is distributed on an
  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  KIND, either express or implied.  See the License for the
  specific language governing permissions and limitations
  under the License.
 */

package com.gs.jrpip.client;

import java.io.IOException;
import java.net.MalformedURLException;

public class FastServletProxyFactory implements ServletProxyFactory
{
    public static final String MAX_CONNECTIONS_PER_HOST = HttpMessageTransport.MAX_CONNECTIONS_PER_HOST;
    public static final String MAX_TOTAL_CONNECTION = HttpMessageTransport.MAX_TOTAL_CONNECTION;

    private HttpMessageTransport transport;
    private MtProxyFactory proxyFactory;
    /**
     * Creates the new proxy factory.
     */
    public FastServletProxyFactory()
    {
        this.transport = new HttpMessageTransport();
        this.proxyFactory = new MtProxyFactory(this.transport);
    }

    public FastServletProxyFactory(String user, String password)
    {
        this.transport = new HttpMessageTransport(user, password);
        this.proxyFactory = new MtProxyFactory(this.transport);
    }

    // Initialise FastServletProxyFactory with array of tokens, path and domain
    public FastServletProxyFactory(String[] tokenArr, String path, String domain)
    {
        this.transport = new HttpMessageTransport(tokenArr, path, domain);
        this.proxyFactory = new MtProxyFactory(this.transport);
    }

    protected void setTransport(HttpMessageTransport transport)
    {
        this.transport = transport;
        this.proxyFactory = new MtProxyFactory(this.transport);
    }

    public static void setMaxConnectionsPerHost(int maxConnections)
    {
        HttpMessageTransport.setMaxConnectionsPerHost(maxConnections);
    }

    public static void setMaxTotalConnections(int maxTotalConnections)
    {
        HttpMessageTransport.setMaxTotalConnections(maxTotalConnections);
    }

    public static int getMaxTotalConnection()
    {
        return HttpMessageTransport.getMaxTotalConnection();
    }

    public static int getMaxConnectionsPerHost()
    {
        return HttpMessageTransport.getMaxConnectionsPerHost();
    }

    public void setUseLocalService(boolean useLocalService)
    {
        this.proxyFactory.setUseLocalService(useLocalService);
    }

    public static void clearServerChunkSupportAndIds()
    {
        HttpMessageTransport.clearServerChunkSupportAndIds();
    }

    /**
     * Creates a new proxy with the specified URL.  The returned object
     * is a proxy with the interface specified by api.
     * <p/>
     * <pre>
     * String url = "http://localhost:7001/objectmanager/JrpipServlet");
     * RemoteObjectManager rom = (RemoteObjectManager) factory.create(RemoteObjectManager.class, url);
     * </pre>
     *
     * @param api the interface the proxy class needs to implement
     * @param url the URL where the client object is located.
     * @return a proxy to the object with the specified interface.
     */
    @Override
    public <T> T create(Class<T> api, String url) throws MalformedURLException
    {
        return this.proxyFactory.create(api, url);
    }

    /**
     * Creates a new proxy with the specified URL.  The returned object
     * is a proxy with the interface specified by api.
     * <p/>
     * <pre>
     * String url = "http://localhost:7001/objectmanager/JrpipServlet");
     * RemoteObjectManager rom = (RemoteObjectManager) factory.create(RemoteObjectManager.class, url);
     * </pre>
     *
     * @param api           the interface the proxy class needs to implement
     * @param url           the URL where the client object is located.
     * @param timeoutMillis maximum timeoutMillis for remote method call to run, zero for no timeoutMillis
     * @return a proxy to the object with the specified interface.
     */
    @Override
    public <T> T create(Class<T> api, String url, int timeoutMillis) throws MalformedURLException
    {
        return this.proxyFactory.create(api, url, timeoutMillis);
    }

    /**
     * Creates a new proxy with the specified URL.  The returned object
     * is a proxy with the interface specified by api.
     * <p/>
     * <pre>
     * String url = "http://localhost:7001/objectmanager/JrpipServlet");
     * RemoteObjectManager rom = (RemoteObjectManager) factory.create(RemoteObjectManager.class, url);
     * </pre>
     *
     * @param api           the interface the proxy class needs to implement
     * @param url           the URL where the client object is located.
     * @param timeoutMillis maximum timeoutMillis for remote method call to run, zero for no timeoutMillis
     * @return a proxy to the object with the specified interface.
     */
    public <T> T create(Class<T> api, String url, int timeoutMillis, boolean disconnectedMode) throws MalformedURLException
    {
        return this.proxyFactory.create(api, url, timeoutMillis, disconnectedMode);
    }

    /**
     * @return the http response code returned from the server. Response code 200 means success.
     */
    public static int fastFailPing(AuthenticatedUrl url) throws IOException
    {
        return HttpMessageTransport.fastFailPing(url, MessageTransport.PING_TIMEOUT);
    }

    @Override
    public boolean isServiceAvailable(String url)
    {
        return this.proxyFactory.isServiceAvailable(url);
    }

    public static long getProxyId(AuthenticatedUrl url)
    {
        return HttpMessageTransport.getProxyId(url);
    }

    public static boolean serverSupportsChunking(AuthenticatedUrl url)
    {
        return HttpMessageTransport.serverSupportsChunking(url);
    }

    protected void setInvocationHandlerFunction(HttpInvocationHandlerFunction invocationHandlerFunction)
    {
        this.transport.setHttpInvocationHandlerFunction(invocationHandlerFunction);
    }
}

