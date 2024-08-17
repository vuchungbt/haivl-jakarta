package net.blwsmartware.controller.home.post.api;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.service.impl.PostService;
import net.blwsmartware.util.JWTUtil;

import java.io.IOException;

@WebServlet(urlPatterns = {"/vote-post"})
public class VoteForPostApi extends HttpServlet {
    @Inject
    private PostService postService;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int vote =Integer.parseInt(req.getParameter("_vote"));
        Long postID =Long.parseLong(req.getParameter("_post"));
        Long idUser = JWTUtil.getIdUser(req);
        if(vote>0 && vote<=5) {
            postService.setUserID(idUser);
            postService.vote(postID,vote);
        }
    }

}
