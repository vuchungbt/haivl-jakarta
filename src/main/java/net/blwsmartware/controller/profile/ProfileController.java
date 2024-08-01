package net.blwsmartware.controller.profile;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    System.out.println(token);
                }
            }
        }
        if (token != null) {
            RequestDispatcher rd = request.getRequestDispatcher("/views/web/profile.jsp");
            rd.forward(request, response);

        } else {
            System.out.println("Dont have token");
//            path = "/views/login.jsp";
            response.sendRedirect(request.getContextPath() + "/login");
        }


    }
}
