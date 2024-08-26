package net.blwsmartware.controller.profile;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.FormUtil;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Inject
    private IPostService postService;
    @Inject
    private IUserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String status = request.getParameter("status");
        List<PostModel> list;
        int page = 1;
        UserModel userModel = userService.findByID(JWTUtil.getIdUser(request));
        if(status ==null){
            list = postService.findAllByIdUser(userModel.getId(), page);
        }else{
            list =postService.findPostStatusByIdUser(userModel.getId(),page, Integer.parseInt(status));
        }
        request.setAttribute("posts",list);
        request.setAttribute("userModel", userModel);
        request.setAttribute("count",postService.countByIdUser(userModel.getId()));
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/profile.jsp");
        rd.forward(request, response);


    }
}
