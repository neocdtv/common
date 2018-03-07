package io.neocdtv.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamingService {
  private final static Logger LOGGER = Logger.getLogger(StreamingService.class.getName());
  static int PORT;

  public static void main(String[] args) throws Exception {
    StreamingService.startIt();
  }

  public static void startIt() throws Exception {
    PORT = discoverFreeNetworkPort();
    Server server = new Server(PORT);

    ServletHandler handler = new ServletHandler();
    server.setHandler(handler);
    handler.addServletWithMapping(StreamingServlet.class, StreamingServiceConstants.SERVLET_PATH);
    server.start();
    LOGGER.log(Level.INFO, "starting streaming service on port {0}", PORT);
  }

  private static int discoverFreeNetworkPort() throws IOException {
    final ServerSocket socket = new ServerSocket(0);
    socket.setReuseAddress(true);
    int port = socket.getLocalPort();
    socket.close();
    return port;
  }

}
