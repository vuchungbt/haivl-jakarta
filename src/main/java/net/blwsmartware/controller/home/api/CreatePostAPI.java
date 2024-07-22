package net.blwsmartware.controller.home.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.util.HttpUtil;

import java.io.IOException;
import java.rmi.ServerException;

@WebServlet(urlPatterns = "/api-create-post")
public class CreatePostAPI {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServerException, IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json");
        PostModel postModel = HttpUtil.of(request.getReader()).toModel(PostModel.class);
        Cookie[]cookies = request.getCookies();
        Cookie tokenCookie = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    tokenCookie = cookie;
                    break;
                }
            }
        }



    }
}
