package io.neocdtv.service;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StreamingService {
  private final static Logger LOGGER = Logger.getLogger(StreamingService.class.getName());
  //TODO: SocketAddress check if this can used to contain address and port or if maybe if will occupy the port which is required for jetty server
  public static Map<String, Integer> addressToPort = new HashMap<>();

  public static void startIt(final List<InetAddress> addresses) throws Exception {
    for (InetAddress address : addresses) {
      int port = discoverFreeNetworkPort();
      Server server = new Server(port);
      ServletHandler handler = new ServletHandler();
      server.setHandler(handler);
      handler.addServletWithMapping(StreamingServlet.class, StreamingServiceConstants.SERVLET_PATH);
      server.start();
      addressToPort.put(address.getHostAddress(), port);
      LOGGER.log(Level.INFO,
          String.format("starting streaming service on address %s and port %s",
              address.getHostAddress(),
              port));
    }
  }

  private static int discoverFreeNetworkPort() throws IOException {
    final ServerSocket socket = new ServerSocket(0);
    socket.setReuseAddress(true);
    int port = socket.getLocalPort();
    socket.close();
    return port;
  }
}
