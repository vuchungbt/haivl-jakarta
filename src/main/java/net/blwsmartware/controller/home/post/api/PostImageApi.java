package net.blwsmartware.controller.home.post.api;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(urlPatterns = {"/post-image-api"})
public class PostImageApi extends HttpServlet {
    public static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getParameter("path");

        File image = new File(path);

        if (image.exists() && !image.isDirectory()) {
            String minType = getServletContext().getMimeType(image.getName());
            if (minType == null) {
                minType = "application/octet-stream";
            }
            response.setContentType(minType);
            response.setContentLengthLong(image.length());
            Files.copy(image.toPath(), response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
