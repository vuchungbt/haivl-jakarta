package net.blwsmartware.controller.home.post;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;

@WebServlet(urlPatterns = {"/create-post"})
public class CreatePostController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = JWTUtil.getToken(request);
        if(token ==null){
            response.sendRedirect(request.getContextPath() + "/login?send-direction=create-post");
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/create-post.jsp");
        rd.forward(request, response);
    }

}
