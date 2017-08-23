package com.xcz.afcs.container;

import com.xcz.afcs.util.AfbpProperties;
import com.xcz.afcs.util.IOUtil;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class JettyServer {

    private static final Logger LOG = LoggerFactory.getLogger(JettyServer.class);

    private static final String PROP_NAME__PREFIX = AfbpProperties.PROP_NAME_PREFIX + "jetty.";
    
    private static final String PROP_NAME_CONTEXT_PATH = PROP_NAME__PREFIX + "contextPath";
    
    private static final String PROP_NAME_WEBAPP_PATH = PROP_NAME__PREFIX + "webappPath";
    
    private static final String PROP_NAME_HOST = PROP_NAME__PREFIX + "host";
    
    private static final String PROP_NAME_PORT = PROP_NAME__PREFIX + "port";

    private static final String DEFAULT_CHARSET = "UTF-8";
    
    private String contextPath;
    
    private String webappPath;
    
    private InetSocketAddress address;
    
    private Server server;
    
    private WebAppContext webapp;

    private static JettyServer jetty;

    private static volatile boolean running = true;

    public static void main(String[] args) {
        try {

            String contextPath = System.getProperty(PROP_NAME_CONTEXT_PATH);
            if (StringUtils.isBlank(contextPath)) {
                contextPath = AfbpProperties.getProperty(PROP_NAME_CONTEXT_PATH);
            }

            String webappPath = System.getProperty(PROP_NAME_WEBAPP_PATH);
            if (StringUtils.isBlank(webappPath)) {
                webappPath = AfbpProperties.getProperty(PROP_NAME_WEBAPP_PATH);
            }

            String host = System.getProperty(PROP_NAME_HOST);
            if (StringUtils.isBlank(host)) {
                host = AfbpProperties.getProperty(PROP_NAME_HOST);
            }

            String portStr = System.getProperty(PROP_NAME_PORT);
            if (StringUtils.isBlank(portStr)) {
                portStr = AfbpProperties.getProperty(PROP_NAME_PORT);
            }
            if (StringUtils.isBlank(portStr)) {
                throw new IllegalArgumentException(PROP_NAME_PORT + " is required");
            }
            int port = 0;
            try {
                port = Integer.parseInt(portStr);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IllegalArgumentException(PROP_NAME_PORT + " should be an integer between 1 and 65535");
            }
            if (StringUtils.isBlank(host)) {
                jetty = new JettyServer(contextPath, webappPath, port);
            } else {
                jetty = new JettyServer(contextPath, webappPath, host, port);
            }
            jetty.start();

        }catch (Exception e) {
           e.printStackTrace();
           System.exit(1);
        }
    }
    
    public JettyServer(String contextPath, String webappPath, int port) {
        this(contextPath, webappPath, new InetSocketAddress(port));
    }
    
    public JettyServer(String contextPath, String webappPath, String hostname, int port) {
        this(contextPath, webappPath, new InetSocketAddress(hostname, port));
    }
    
    public JettyServer(String contextPath, String webappPath, InetSocketAddress address) {
        if (StringUtils.isBlank(contextPath)) {
            contextPath = "";
        }
        this.contextPath = contextPath;
        if (StringUtils.isBlank(webappPath)) {
            throw new IllegalArgumentException("webappPath is required");
        }
        this.webappPath = webappPath;
        this.address = address;
    }
    
    public void start() throws Exception {
        if (null == server || server.isStopped()) {
            doStart();
        } else {
            throw new IllegalStateException("JettyServer already started.");
        }
    }

    public void stop() throws Exception {
        if (null != server) {
            doStop();
        } else {
            throw new IllegalStateException("JettyServer already stopped.");
        }
    }


    
    private void doStart() throws Exception {
        if (!checkServerAddressAvailable()) {
            throw new IllegalStateException("Server address already in use: " + address);
        }
        
        ensureTmpDir();
        
        System.setProperty("org.eclipse.jetty.util.URI.charset", DEFAULT_CHARSET);
        System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
        System.setProperty("org.eclipse.jetty.server.Request.maxFormContentSize", "600000");
        
        webapp = new WebAppContext(webappPath, contextPath);
        server = new Server(address);
        server.setHandler(webapp);
        
        long st = System.currentTimeMillis();
        server.start();
        server.setStopAtShutdown(true);
        long sp = System.currentTimeMillis() - st;
        System.out.println("JettyServer started: " + String.format("%.2f sec", sp / 1000D));
        
        server.join();
    }

    private void doStop() throws Exception {
        server.stop();
        System.out.println("JettyServer st: ");
    }

    private void ensureTmpDir() {
        String tmpDir = System.getProperty("java.io.tmpdir");
        IOUtil.ensureDir(new File(tmpDir));
    }
    
    private boolean checkServerAddressAvailable() {
        int port = address.getPort();
        if (0 < port) {
            ServerSocket ss = null;
            try {
                ss = new ServerSocket(port, 0, address.getAddress());
            } catch (Exception e) {
                LOG.error("check serverAddress failed", e);
                return false;
            } finally {
                if (null != ss) {
                    try {
                        ss.close();
                    } catch (IOException e) {
                        LOG.error("close ServerSocket failed", e);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid port " + port);
        }
        return true;
    }
    
}
