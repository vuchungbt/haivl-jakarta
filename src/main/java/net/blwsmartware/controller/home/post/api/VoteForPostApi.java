package net.blwsmartware.controller.home.post.api;

import com.google.gson.Gson;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.service.impl.PostService;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/vote-post"})
public class VoteForPostApi extends HttpServlet {
    @Inject
    private PostService postService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        int vote =Integer.parseInt(req.getParameter("_vote"));
        Long postID =Long.parseLong(req.getParameter("_post"));

        System.out.println("==0:"+vote);
        System.out.println("==0:"+postID);

        Long idUser = JWTUtil.getIdUser(req);
        Map<String, Object> responseData = new HashMap<>();
        if(idUser==null) {
            System.out.println("==1");
            responseData.put("message", "No login");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(new Gson().toJson(responseData));
        }
        else if(vote>0 && vote<=5) {
            System.out.println("==2");
            postService.setUserID(idUser);
            postService.vote(postID,vote);
            double avg_vote = postService.findByID(postID).getAvgVote();
            responseData.put("message", "Vote successfully");
            responseData.put("avg_vote", avg_vote);
            resp.getWriter().write(new Gson().toJson(responseData));
        }
        else {
            System.out.println("==3");
            responseData.put("message", "Vote number should be 1 to 5");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(new Gson().toJson(responseData));
        }
    }

}
