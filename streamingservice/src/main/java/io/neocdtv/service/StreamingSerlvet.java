/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.neocdtv.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author xix
 */
public class StreamingSerlvet extends HttpServlet {

    private final static Logger LOGGER = Logger.getLogger(StreamingSerlvet.class.getName());
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        
        final String requestedResource = request.getParameter(StreamingServiceConstants.PARAM_NAME_RESOURCE);
        try {
            final FileInputStream fileInputStream = new FileInputStream(requestedResource);
            final BufferedOutputStream out;
            try (BufferedInputStream in = new BufferedInputStream(fileInputStream)) {
                response.setContentType(determineMimeTypeForRequestedResource(requestedResource));
                response.setStatus(HttpServletResponse.SC_OK);
                out = new BufferedOutputStream(response.getOutputStream());
                int c;
                while ((c = in.read()) != -1) {
                    out.write(c);
                }
            }
            out.close();
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String determineMimeTypeForRequestedResource(final String requestedResource) throws IOException {
        final Path path = FileSystems.getDefault().getPath(requestedResource);
        return Files.probeContentType(path);
    }
}
