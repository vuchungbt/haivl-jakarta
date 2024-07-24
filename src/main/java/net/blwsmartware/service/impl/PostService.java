package net.blwsmartware.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import net.blwsmartware.dao.IPostDAO;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.service.ICommentService;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.service.IUserService;
import jakarta.inject.Inject;

import java.util.List;


public class PostService implements IPostService {

    @Inject
    private IPostDAO postDAO;

    @Inject
    private IUserService userService;

    @Inject
    private ICommentService commentService;

    @Override
    public PostModel findByID(Long id) {
        return postDAO.findByID(id);
    }

    @Override
    public PostModel save(PostModel postModel) {
        String src = postModel.getSource();
        src = src.toUpperCase();
        if (!src.startsWith("HTTP://") && !src.startsWith("HTTPS://")) {
            postModel.setSource("https://" + postModel.getSource());
        }
        Long id = postDAO.save(postModel);
        return postDAO.findByID(id);
    }

    @Override
    public PostModel update(PostModel postModel) {
        postDAO.update(postModel);
        return postDAO.findByID(postModel.getId());
    }

    @Override
    public void delete(long id) {
        postDAO.delete(id);
    }

    @Override
    public int count() {
        return postDAO.count();
    }

    @Override
    public List<PostModel> findAll() {
        List<PostModel> list = postDAO.findAll();
        createdEntity(list);
        commentsEntity(list);
        return list;
    }

    @Override
    public List<PostModel> findAll(int page) {
        List<PostModel> list = postDAO.findAll(page);
        createdEntity(list);
        commentsEntity(list);
        return list;
    }

    @Override
    public List<PostModel> findTop(int page) {
        List<PostModel> list = postDAO.findTop(page);
        createdEntity(list);
        commentsEntity(list);
        return list;
    }

    @Override
    public List<PostModel> findTrending(int page) {
        List<PostModel> list = postDAO.findTrending(page);
        createdEntity(list);
        commentsEntity(list);
        return list;

    }

    private void createdEntity(List<PostModel> list) {
        if (list.isEmpty()) return;
        list.forEach(postModel -> {
            postModel.setCreated(userService.findByID(postModel.getAuthId()));
        });
    }

    ;

    private void commentsEntity(List<PostModel> list) {
        if (list.isEmpty()) return;
        list.forEach(postModel -> {
            postModel.setComments(commentService.findByPostIDAndFirstLevel(postModel.getId(), 1));
        });
    }

    ;
}
