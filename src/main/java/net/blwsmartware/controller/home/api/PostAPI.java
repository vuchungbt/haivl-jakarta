package net.blwsmartware.controller.home.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.service.ImageService;
import net.blwsmartware.service.impl.ImageServiceImpl;
import net.blwsmartware.util.HttpUtil;
import net.blwsmartware.util.JWTUtil;
import org.jboss.logging.annotations.Pos;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/api-post")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
public class PostAPI extends HttpServlet {


    @Inject
    private IPostService postService;
    @Inject
    private IUserService userService;

    @Inject
    private ImageServiceImpl imageService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");

        Map<String, Object> result = new HashMap<>();
        try {
            Long idUser = JWTUtil.getIdUserFromToken(JWTUtil.getToken(request));
            UserModel userModel = userService.findByID(idUser);

            Map<String,Object> formData = new HashMap<>();
            File rez = null;
            for(Part part : request.getParts()){
                if(part.getContentType() == null){
                    String fieldName = part.getName();
                    String fieldValue = new BufferedReader(new InputStreamReader(part.getInputStream()))
                            .lines().collect(Collectors.joining("\n"));
                    formData.put(fieldName,fieldValue);
                }else{
                    if(part.getName().equals("image")){
                        try{
                            imageService.makeDir();
                            imageService.setPath(part);
                            imageService.saveImageToDisk();
                            imageService.createThumbnail();
                            rez = imageService.resize();
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            PostModel postModel = new ObjectMapper().convertValue(formData, PostModel.class);
            if(rez!=null){
                postModel.setImagePath(rez.getPath());
                postModel.setThumbnail(imageService.getThumbnailName());
            }
            postModel.setCreatedBy(userModel.getName());
            postModel.setAuthId(userModel.getId());
            postModel = postService.save(postModel);
            System.out.println(postModel);
            result.put("status", "success");
            objectMapper.writeValue(response.getOutputStream(), result);
        }
        catch (Exception e){
            result.put("status", "error");
            objectMapper.writeValue(response.getOutputStream(), result);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        Map<String, Object> result = new HashMap<>();
        try {
            Long idUser = JWTUtil.getIdUserFromToken(JWTUtil.getToken(request));
            UserModel userModel = userService.findByID(idUser);

            PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
            postModel.setModifiedBy(userModel.getName());
            postModel = postService.update(postModel);
            System.out.println(postModel);
            result.put("status","success");
            objectMapper.writeValue(response.getOutputStream(), result);
        }
        catch (Exception e){
            result.put("status", "error");
            objectMapper.writeValue(response.getOutputStream(), result);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        try {
            Long idUser = JWTUtil.getIdUserFromToken(JWTUtil.getToken(request));
            PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
            postService.delete(postModel.getId());

        }catch (Exception e){
            result.put("status", "error");
        }
        objectMapper.writeValue(response.getOutputStream(), result);
    }
}
