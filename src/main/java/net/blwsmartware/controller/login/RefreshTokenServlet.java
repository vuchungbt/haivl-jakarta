package net.blwsmartware.controller.login;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/refreshAuthToken"})
public class RefreshTokenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private IUserService userService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String oldToken = request.getHeader("Authorization").replace("Bearer ", "");
        DecodedJWT decodedJWT = JWTUtil.verifyToken(oldToken);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        if (decodedJWT != null) {
            Long id = decodedJWT.getClaim("id").asLong();
            UserModel userModel = userService.findByID(id);
            String token = JWTUtil.generateToken(userModel);
            Cookie tokenCookie = new Cookie("token", token);
//            tokenCookie.setPath("/");
//            tokenCookie.setHttpOnly(true);
            response.addCookie(tokenCookie);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("valid", true);
            responseData.put("token", token);
            response.getWriter().write(new Gson().toJson(responseData));
        } else {
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("valid", false);
            response.getWriter().write(new Gson().toJson(responseData));
        }
    }

}
