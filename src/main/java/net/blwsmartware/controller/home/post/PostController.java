package net.blwsmartware.controller.home.post;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import net.blwsmartware.constant.IConstant;
import net.blwsmartware.dao.IPostHasTagDAO;
import net.blwsmartware.model.PostHasTagModel;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.model.TagModel;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IPostHasTagService;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.service.ITagService;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.service.impl.ImageServiceImpl;
import net.blwsmartware.util.FormUtil;
import net.blwsmartware.util.HttpUtil;
import net.blwsmartware.util.JWTUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.Normalizer;
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

        @Inject
        private ITagService tagService;

        @Inject
        private IPostHasTagService postHasTagService;

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
            List<TagModel> tags = tagService.findAll();
            List<String> tagNames = tags.stream()
                                    .map(TagModel::getName)
                                    .toList();
            request.setAttribute("postModel", model);
            String tagNamesJson = new Gson().toJson(tagNames);
//            System.out.println(tagNamesJson);
            request.setAttribute("tagNames",tagNamesJson );
            RequestDispatcher rd = request.getRequestDispatcher("/views/web/create-post.jsp");
            rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<String, Object> formData = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        File rez = null;
        try {
            UserModel userModel = getUserInfo(request);
            PostModel postModel = getPostInfo(request);

            postModel.setCreatedBy(userModel.getName());
            postModel.setAuthId(userModel.getId());
            postModel = postService.save(postModel);
            saveTag(request,userModel, postModel);

            System.out.println(postModel);
            result.put("status","success");
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
        }
        objectMapper.writeValue(response.getOutputStream(), result);

}

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        Map<String, Object> formData;
        Map<String, Object> result = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            UserModel userModel = getUserInfo(request);
            PostModel postModel = getPostInfo(request);

            postModel.setModifiedBy(userModel.getName());
            postModel = postService.update(postModel);
            saveTag(request,userModel, postModel);
            System.out.println(postModel);
            result.put("status", "success");

        } catch (Exception e) {
            result.put("status","error");
            result.put("message", e.getMessage());
        }
        objectMapper.writeValue(response.getOutputStream(), result);

    }
    private List<String> getTags(HttpServletRequest request){
        String tagList = request.getParameter("tag-collect");
        Pattern pattern = Pattern.compile("#(\\S+)");
        Matcher matcher = pattern.matcher(tagList);
        List<String> tags = new ArrayList<>();
        while (matcher.find()) {
            tags.add(matcher.group(1));
        }
        return tags;
    }
    private UserModel getUserInfo(HttpServletRequest request){
            Long idUser = JWTUtil.getIdUser(request);
            return userService.findByID(idUser);
    }
    private PostModel getPostInfo(HttpServletRequest request){
        Map<String, Object> formData = new HashMap<>();
        File rez = null;
        try {
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
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        PostModel postModel =  new ObjectMapper().convertValue(formData, PostModel.class);
        if(postModel.getId()!=null){
            postModel = postService.findByID(postModel.getId());
            if(rez != null){
                if(!imageService.delete(postModel.getImagePath())){
                    System.out.println("Delete image unsuccessfully");
                }
            }
            postService.deleteAllTag(postModel);

        }
        List<String> tags = getTags(request);
        if (!tags.isEmpty()) {
            postModel.setTab(tags);
        }

        if (rez != null) {
            postModel.setImagePath(rez.getName());
            postModel.setThumbnail(imageService.getThumbnailName());
        }
        return postModel;
    }
    private void saveTag(HttpServletRequest request, UserModel userModel, PostModel postModel){
            List<String> tags = getTags(request);
        if(!tags.isEmpty()){
            for(String tag : tags){
                TagModel tagModel = tagService.findByTagName(tag);
                if(tagModel == null ||tagModel.getName() == null){
                    tagModel = new TagModel();
                    tagModel.setCreatedBy(userModel.getName());
                    tagModel.setName(tag);
                    tagModel = tagService.save(tagModel);
                }
                PostHasTagModel postHasTagModel = new PostHasTagModel();
                postHasTagModel.setTagId(tagModel.getId());
                postHasTagModel.setPostId(postModel.getId());
                postHasTagService.save(postHasTagModel);
            }
        }
    }


}

