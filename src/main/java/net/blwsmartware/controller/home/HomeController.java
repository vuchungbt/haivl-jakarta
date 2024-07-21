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
import net.blwsmartware.util.RouterUtil;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home","/trending","/top","/ask"} , name = "home")
public class HomeController extends HttpServlet {

    @Inject
    private IPostService postService;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pth_request = (String) request.getAttribute("router");
        System.out.println(pth_request);

        String page = RouterUtil.getRouter(2,request);
        int pageNumber = 1 ;
        try{
            pageNumber = Integer.parseInt(page);
            if(pageNumber==0) pageNumber=1;
        }
        catch (Exception e){
            //Log 404
        }
        List<PostModel> list = null;

        switch (pth_request) {
            case "":
            case "home":
                list = postService.findAll(pageNumber);
                break;
            case "top":
                list = postService.findTop(pageNumber);
                break;
            case "trending":
                list = postService.findTrending(pageNumber);
                break;
        }

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