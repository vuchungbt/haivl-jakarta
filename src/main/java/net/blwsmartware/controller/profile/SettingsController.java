package net.blwsmartware.controller.profile;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;
import java.util.Objects;

@WebServlet(urlPatterns = {"/settings"})
public class SettingsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Inject
    private IUserService userService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UserModel userModel = userService.findByID(JWTUtil.getIdUser(request));
        request.setAttribute("userModel", userModel);
        RequestDispatcher rd = request.getRequestDispatcher("/views/web/settings.jsp");
        rd.forward(request, response);
    }
}
