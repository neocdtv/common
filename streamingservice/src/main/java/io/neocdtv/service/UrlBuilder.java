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
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author xix
 */
public class UrlBuilder {

    private final static Logger LOGGER = Logger.getLogger(UrlBuilder.class.getName());
    private static final String IPV4_REGEX = "\\A(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}\\z";
    private static String ipV4;
    private static final String UTF8 = "UTF-8";

    public static String build(final String url) {
        if (url.startsWith("http")) {
            return url;
        } else {
            String encodedUrl = null;
            try {
                encodedUrl = URLEncoder.encode(url, UTF8);
            } catch (UnsupportedEncodingException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            return "http://" + getIpV4Address() + ":"
                    + StreamingServiceConstants.DEFAULT_SERVICE_PORT
                    + StreamingServiceConstants.SERVLET_PATH + "?"
                    + StreamingServiceConstants.PARAM_NAME_RESOURCE + "=" + encodedUrl;
        }
    }

    public static String getIpV4Address() {
        if (ipV4 == null) {
            try {
                Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                while (interfaces.hasMoreElements()) {
                    NetworkInterface iface = interfaces.nextElement();
                    // filters out 127.0.0.1 and inactive interfaces
                    if (iface.isLoopback() || !iface.isUp()) {
                        continue;
                    }

                    Enumeration<InetAddress> addresses = iface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        InetAddress addr = addresses.nextElement();
                        String currnetAddress = addr.getHostAddress();
                        if (currnetAddress.matches(IPV4_REGEX)) {
                            ipV4 = currnetAddress;
                        }
                    }
                }
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }
        return ipV4;
    }
}
