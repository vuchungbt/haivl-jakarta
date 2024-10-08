package net.blwsmartware.controller.home;

import jakarta.inject.Inject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.util.JWTUtil;
import net.blwsmartware.util.RouterUtil;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home", "/trending", "/top", "/ask"}, name = "home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Inject
    private IPostService postService;

    @Override
    public void init() throws ServletException {
        super.init();
        if (postService == null) {
            System.out.println("postService is null");
        } else {
            System.out.println("postService is injected successfully: " + postService);

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pth_request = (String) request.getAttribute("router");
        System.out.println("link : " + pth_request);
        String page = RouterUtil.getRouter(2, request);
        int pageNumber = 1;
        try {
            pageNumber = Integer.parseInt(page);
            if (pageNumber == 0) pageNumber = 1;
        } catch (Exception e) {
            //Log 404
        }
        Long idUser = JWTUtil.getIdUser(request);
        System.out.println("50======================idUser:"+idUser);
        postService.setUserID(idUser);
        List<PostModel> list = switch (pth_request) {
            case "", "home" -> postService.findAll(pageNumber);
            case "top" -> postService.findTop(pageNumber);
            case "ask" -> postService.findPostPending(pageNumber);
            case "trending" -> postService.findTrending(pageNumber);
            default -> null;
        };

        request.setAttribute("posts", list);

        RequestDispatcher rd = request.getRequestDispatcher("/views/home.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher rd = request.getRequestDispatcher("/views/home.jsp");
        rd.forward(request, response);
    }

}

