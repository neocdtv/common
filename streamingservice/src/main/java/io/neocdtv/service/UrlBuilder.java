/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.service;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author xix
 */
public class UrlBuilder {

  private final static Logger LOGGER = Logger.getLogger(UrlBuilder.class.getName());
  private static boolean ENCODE_URL_PARAM = true;

  public static String build(final String url, final InetAddress address) {
    if (url.startsWith("http")) {
      return url;
    } else {
      String preparedParam = null;
      try {
        if (ENCODE_URL_PARAM) {
          preparedParam = URLEncoder.encode(url, StandardCharsets.UTF_8.name());
          LOGGER.info("Encoded param: " + preparedParam);
        } else {
          preparedParam = url;
        }
      } catch (UnsupportedEncodingException ex) {
        LOGGER.log(Level.SEVERE, null, ex);
      }
      String preparedUrl = "http://" + address.getHostAddress() + ":"
          + getPortForAddress(address)
          + StreamingServiceConstants.SERVLET_PATH + "?"
          + StreamingServiceConstants.PARAM_NAME_RESOURCE + "=" + preparedParam;
      LOGGER.info("Prepared Url: " + preparedUrl);
      return preparedUrl;
    }
  }

  private static Integer getPortForAddress(final InetAddress address) {
    return StreamingService.addressToPort.get(address.getHostAddress());
  }
}