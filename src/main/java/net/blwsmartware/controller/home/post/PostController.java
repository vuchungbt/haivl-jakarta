package net.blwsmartware.controller.home.post;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
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
import net.blwsmartware.service.impl.ImageServiceImpl;
import net.blwsmartware.util.FormUtil;
import net.blwsmartware.util.HttpUtil;
import net.blwsmartware.util.JWTUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/create-post", "/edit-post"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 15    // 15 MB
)
    public class PostController extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Inject
        private IPostService postService;
        @Inject
        private IUserService userService;

        @Inject
        private ImageServiceImpl imageService;

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String token = JWTUtil.getToken(request);
            PostModel model = FormUtil.toModel(PostModel.class, request);
            if (token == null) {
                response.sendRedirect(request.getContextPath() + "/login?send-direction=create-post");
                return;
            }
            if(model.getId()!=null){
                model = postService.findByID(model.getId());
            }
            request.setAttribute("postModel", model);
            RequestDispatcher rd = request.getRequestDispatcher("/views/web/create-post.jsp");
            rd.forward(request, response);
        }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String method = request.getParameter("_method");
            if(method.equalsIgnoreCase("put")){
                doPut(request, response);
                return;
            }
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<String, Object> formData ;
        try {
            Long idUser = JWTUtil.getIdUser(request);
            UserModel userModel = userService.findByID(idUser);
            formData = new HashMap<>();
            File rez = null;
            for (Part part : request.getParts()) {
                if (part.getContentType() == null) {
                    String fieldName = part.getName();
                    String fieldValue = new BufferedReader(new InputStreamReader(part.getInputStream()))
                            .lines().collect(Collectors.joining("\n"));
                    formData.put(fieldName, fieldValue);
                } else {
                    if (part.getName().equals("image")) {
                        try {
                            imageService.makeDir();
                            imageService.setPath(part);
                            imageService.saveImageToDisk();
                            imageService.createThumbnail();
                            rez = imageService.resize();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }


            PostModel postModel = new ObjectMapper().convertValue(formData, PostModel.class);


            String tagList = request.getParameter("tag-collect");
            Pattern pattern = Pattern.compile("#\\w+");
            Matcher matcher = pattern.matcher(tagList);
            List<String> tags = new ArrayList<>();
            while (matcher.find()){
                tags.add(matcher.group());
            }

            if(!tags.isEmpty()){
                postModel.setTab(tags);
            }

            if (rez != null) {
                postModel.setImagePath(rez.getPath());
                postModel.setThumbnail(imageService.getThumbnailName());
            }
            postModel.setCreatedBy(userModel.getName());
            postModel.setAuthId(userModel.getId());
            postModel = postService.save(postModel);
            System.out.println(postModel);

            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (Exception e) {
            request.setAttribute("error-post", "error");
            request.getRequestDispatcher("/views/web/create-post.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Map<String, Object> formData;
        try {
            Long idUser = JWTUtil.getIdUser(request);
            UserModel userModel = userService.findByID(idUser);
            formData = new HashMap<>();
            File rez = null;


            for (Part part : request.getParts()) {
                if (part.getContentType() == null) {
                    String fieldName = part.getName();
                    String fieldValue = new BufferedReader(new InputStreamReader(part.getInputStream()))
                            .lines().collect(Collectors.joining("\n"));
                    formData.put(fieldName, fieldValue);
                } else {
                    if (part.getName().equals("image")) {
                        try {
                            imageService.makeDir();
                            imageService.setPath(part);
                            imageService.saveImageToDisk();
                            imageService.createThumbnail();
                            rez = imageService.resize();
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }

            PostModel postModel = new ObjectMapper().convertValue(formData, PostModel.class);
            postModel = postService.findByID(postModel.getId());
            if(!imageService.delete(postModel.getImagePath())){
                System.out.println("Delete image unsuccessfully");
            }

            String tagList = request.getParameter("tag-collect");
            Pattern pattern = Pattern.compile("#\\w+");
            Matcher matcher = pattern.matcher(tagList);
            List<String> tags = new ArrayList<>();
            while (matcher.find()) {
                tags.add(matcher.group());
            }
            //Chưa xóa tag đã đăng trước đó nếu có thay đổi
            if (!tags.isEmpty()) {
                postModel.setTab(tags);
            }

            if (rez != null) {
                postModel.setImagePath(rez.getPath());
                postModel.setThumbnail(imageService.getThumbnailName());
            }
            postModel.setModifiedBy(userModel.getName());
            postModel = postService.update(postModel);
            System.out.println(postModel);
            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (Exception e) {
            request.setAttribute("error-post", "error");
            request.getRequestDispatcher("/views/web/create-post.jsp").forward(request, response);
        }

    }


}
