package net.blwsmartware.controller.home.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.HttpUtil;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/api-post")
public class PostAPI extends HttpServlet {


    @Inject
    private IPostService postService;
    @Inject
    private IUserService userService;

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

            PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
            postModel.setCreatedBy(userModel.getName());
            postModel.setAuthId(userModel.getId());
            postModel = postService.save(postModel);
            System.out.println(postModel);
            result.put("status", "success");
        } catch (Exception e) {
            result.put("status", "error");
        }
        objectMapper.writeValue(response.getOutputStream(), result);

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");

        Long idUser = JWTUtil.getIdUserFromToken(JWTUtil.getToken(request));
        UserModel userModel = userService.findByID(idUser);

        PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
        postModel.setModifiedBy(userModel.getName());
        postModel = postService.update(postModel);
        System.out.println(postModel);
        objectMapper.writeValue(response.getOutputStream(), postModel);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();

        PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);

        postService.delete(postModel.getId());
        objectMapper.writeValue(response.getOutputStream(), "{}");
    }
}
