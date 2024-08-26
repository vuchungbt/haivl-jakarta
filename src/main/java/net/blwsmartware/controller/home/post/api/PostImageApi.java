package net.blwsmartware.controller.home.post.api;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.constant.IConstant;
import net.blwsmartware.service.ImageService;
import net.blwsmartware.service.impl.ImageServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(urlPatterns = {"/post-image-api"})
public class PostImageApi extends HttpServlet {
//<<<<<<< HEAD
    private static final long serialVersionUID  = 1L;
    @Inject
    private ImageServiceImpl imageService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        String pathName = request.getParameter("path");

//        File image = new File(imageService.getFolderUpload()+ File.separator
//                                + IConstant.UPLOAD_DIR_RESIZE + File.separator +  pathName);
        File image = imageService.getFile(pathName);

        if(image.exists()){
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
