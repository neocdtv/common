package io.neocdtv.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jetty.server.AbstractNetworkConnector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class StreamingService {
  private final static Logger LOGGER = Logger.getLogger(StreamingService.class.getName());

  public static void main(String[] args) throws Exception {
    StreamingService.start();
  }

  public static void start() throws Exception {
    int port = discoverFreeNetworkPort();
    Server server = new Server(port);

    ServletHandler handler = new ServletHandler();
    server.setHandler(handler);
    handler.addServletWithMapping(StreamingServlet.class, StreamingServiceConstants.SERVLET_PATH);
    server.start();
    LOGGER.log(Level.INFO, "starting streaming service on port {0}", port);
  }

  private static int discoverFreeNetworkPort() throws IOException {
    final ServerSocket socket = new ServerSocket(0);
    socket.setReuseAddress(true);
    int port = socket.getLocalPort();
    socket.close();
    return port;
  }

}
