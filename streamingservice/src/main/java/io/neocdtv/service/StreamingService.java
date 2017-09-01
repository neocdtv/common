package io.neocdtv.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class StreamingService {
    private final static Logger LOGGER = Logger.getLogger(StreamingService.class.getName());

    public static void start() throws Exception {
        start(StreamingServiceConstants.DEFAULT_SERVICE_PORT);
    }
    
    public static void start(final int port) throws Exception {
        LOGGER.log(Level.INFO, "starting streaming service on port {0}", port);
        Server server = new Server(port);
        
        ServletHandler handler = new ServletHandler();
        server.setHandler(handler);
        handler.addServletWithMapping(StreamingSerlvet.class, StreamingServiceConstants.SERVLET_PATH);
        server.start();
    }
}
