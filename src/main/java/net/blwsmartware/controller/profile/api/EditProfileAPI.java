package net.blwsmartware.controller.profile.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.UserModel;
import net.blwsmartware.service.IUserService;
import net.blwsmartware.util.HttpUtil;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;
import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/edit-profile"})
public class EditProfileAPI extends HttpServlet {
    @Inject
    private IUserService userService;

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServerException, IOException {
        Map<String, Object> result = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        UserModel userModel = HttpUtil.of(request.getReader()).toModel(UserModel.class);
        userModel.setId(JWTUtil.getIdUser(request));
        userService.update(userModel);
        result.put("message", "success");
        objectMapper.writeValue(response.getOutputStream(), result);
    }
}
