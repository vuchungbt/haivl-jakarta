package net.blwsmartware.controller.home.post;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/create-post", "/edit-post","/up-post"})
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
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");

        Map<String, Object> formData ;
        try {
            Long idUser = (Long) Objects.requireNonNull(JWTUtil.getClaimsFromToken(JWTUtil.getToken(request))).get("id");
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
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/views/web/create-post.jsp").forward(request, response);
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
            Long idUser = (Long) Objects.requireNonNull(JWTUtil.getClaimsFromToken(JWTUtil.getToken(request))).get("id");
            UserModel userModel = userService.findByID(idUser);

            PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
            postModel.setModifiedBy(userModel.getName());
            postModel = postService.update(postModel);
            System.out.println(postModel);
            response.sendRedirect(request.getContextPath() + "/profile");
        } catch (Exception e) {
            request.setAttribute("status", "error");
            request.getRequestDispatcher("/views/web/create-post.jsp").forward(request, response);
        }
    }

    }
