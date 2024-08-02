package net.blwsmartware.controller.home.post;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.util.FormUtil;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;

    @WebServlet(urlPatterns = {"/create-post", "/edit-post"})
    public class PostController extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Inject
        private IPostService postService;

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

    }
