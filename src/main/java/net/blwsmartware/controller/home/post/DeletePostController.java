package net.blwsmartware.controller.home.post;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.blwsmartware.model.CommentModel;
import net.blwsmartware.service.ICommentService;
import net.blwsmartware.service.IPostService;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/delete-post"})
public class DeletePostController extends HttpServlet {

    @Inject
    private IPostService postService;

    @Inject
    private ICommentService commentService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long idPost = Long.parseLong(request.getParameter("id"));
        String message;
        String alert;
        try {
            List<CommentModel> commentModelList = commentService.findByPostID(idPost);
            for (CommentModel commentModel : commentModelList) {
                commentService.delete(commentModel.getId());
            }
            postService.delete(idPost);

            System.out.println("Đã xóa");
            message = "delete-success";
            alert = "success";
        } catch (Exception e) {
            message = "delete-error";
            alert = "error";
            System.out.println(e.getMessage());
        }
        response.sendRedirect(request.getContextPath() + "/profile?message=" + message + "&alert=" + alert);

    }
}
