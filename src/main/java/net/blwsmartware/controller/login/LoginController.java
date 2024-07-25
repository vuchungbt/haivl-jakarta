package net.blwsmartware.controller.login;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.constant.IConstant;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;
import java.net.URLDecoder;

@WebServlet(urlPatterns = {"/login", "/logout"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Inject
    private IUserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String error_code = request.getParameter("error_code");
        String error_message = request.getParameter("error_message");
        if(error_code!=null || error_message!=null){
            response.sendRedirect(request.getContextPath()+ "/login");
            return;
        }
        String path;
        if (code != null) {
            if (state != null) {

                String decodedState = URLDecoder.decode(state, "UTF-8");
                JsonObject stateJson = JsonParser.parseString(decodedState).getAsJsonObject();
                String sendDirection = stateJson.has("send-direction") || !stateJson.get("send-direction").getAsString().isEmpty()? stateJson.get("send-direction").getAsString() : "home";
                String provider = stateJson.has("provider") ?stateJson.get("provider").getAsString():"unknown";

                UserModel acc= null;
                if (provider.equals("google")) {
                    String accessToken = GoogleLogin.getToken(code);
                    acc = GoogleLogin.getUserInfo(accessToken);
                } else if (provider.equals("facebook")) {
                    String accessToken = FacebookLogin.getToken(code);
                    acc = FacebookLogin.getUserInfo(accessToken);
                } else {
                    System.out.println("Unknown provider: " + provider);
                }
                if(acc != null){
                    handleUserLogin(acc,provider,sendDirection,request,response);
                    System.out.println(acc);
                    return;

                }else{
                    request.setAttribute("status", 401);
                    request.setAttribute("msg", "User does not exists");
                    path = "/views/login.jsp";
                }
            } else {
                System.out.println("Sate is null");
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
        } else {
            path = "/views/login.jsp";
        }
        String action = request.getParameter("action");
        if(action!=null &&action.equals("logout")){
            handleUserLogout(request,response);
            return;
        }
        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request,response);

    }
    public void handleUserLogin(UserModel userModel, String provider, String sendDirection, HttpServletRequest request, HttpServletResponse response)
            throws IOException{
        UserModel existingUser;
        if(provider.equals("google")){
            existingUser = userService.findByGgID(userModel.getGgID());
        }else{
            existingUser = userService.findByFbID(userModel.getFbID());
        }

        if (existingUser == null) {
            userModel.setRoleId(userService.getRoleIDByRoleCode(IConstant.USER));
            userModel = userService.save(userModel);
        }else{
            userModel.setId(existingUser.getId());
            userModel.setRoleId(existingUser.getRoleId());
            userModel = userService.update(userModel);
        }
        response.setContentType("application/json");
        String jwtToken = JWTUtil.generateToken(userModel);

        System.out.println("Generated JWT Token: " + jwtToken);

        Cookie cookie = new Cookie("token",jwtToken);
//        cookie.setPath("/");
//        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath() + "/" + sendDirection);

    }
    public void handleUserLogout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        JWTUtil.destroyToken(request, response);
        response.sendRedirect(request.getContextPath() + "/login");
    }
}
