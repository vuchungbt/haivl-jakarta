package net.blwsmartware.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import net.blwsmartware.constant.PostStatus;
import net.blwsmartware.dao.IPostDAO;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.service.ICommentService;
import net.blwsmartware.service.IPostService;
import net.blwsmartware.service.IUserService;
import jakarta.inject.Inject;
import net.blwsmartware.service.ImageService;

import java.util.List;


public class PostService implements IPostService {

    @Inject
    private IPostDAO postDAO;

    @Inject
    private IUserService userService;

    @Inject
    private ICommentService commentService;

    @Inject
    private ImageService imageService;

    @Override
    public PostModel findByID(Long id) {
        return postDAO.findByID(id);
    }

    @Override
    public PostModel save(PostModel postModel) {
        if (postModel.getSource() != null && !postModel.getSource().isEmpty()) {
            String src = postModel.getSource();
            src = src.toUpperCase();
            if (!src.startsWith("HTTP://") && !src.startsWith("HTTPS://")) {
                postModel.setSource("https://" + postModel.getSource());
            }
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
        PostModel postModel = postDAO.findByID(id);
        if (postModel.getImagePath() != null && !postModel.getImagePath().isEmpty()) {
            imageService.delete(postModel.getImagePath());
        }
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

    @Override
    public List<PostModel> findPostPending(int page) {
        List<PostModel> list = postDAO.findWithStatus(page, PostStatus.PENDING);
        createdEntity(list);
        commentsEntity(list);
        return list;
    }

    @Override
    public List<PostModel> findPostPublished(int page) {
        List<PostModel> list = postDAO.findWithStatus(page, PostStatus.PUBLISHED);
        createdEntity(list);
        commentsEntity(list);
        return list;
    }

    private void createdEntity(List<PostModel> list) {
        if (list == null || list.isEmpty()) return;
        list.forEach(postModel -> {
            postModel.setCreated(userService.findByID(postModel.getAuthId()));
        });
    }

    private void commentsEntity(List<PostModel> list) {
        if (list == null || list.isEmpty()) return;
        list.forEach(postModel -> {
            postModel.setComments(commentService.findByPostIDAndFirstLevel(postModel.getId(), 1));
        });
    }

    private void currentVoted(List<PostModel> list) {
        if (list == null || list.isEmpty()) return;
        list.forEach(postModel -> {
            postModel.setComments(commentService.findByPostIDAndFirstLevel(postModel.getId(), 1));
        });
    }

    public List<PostModel> findAllByIdUser(Long idUser) {
        List<PostModel> list = postDAO.findAllByIdUser(idUser);
        createdEntity(list);
        commentsEntity(list);
        return list;
    }


}
