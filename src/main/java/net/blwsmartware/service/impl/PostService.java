package net.blwsmartware.service.impl;

import net.blwsmartware.constant.PostStatus;
import net.blwsmartware.dao.IPostDAO;
import net.blwsmartware.dao.IPostHasTagDAO;
import net.blwsmartware.model.PostHasTagModel;
import net.blwsmartware.model.PostModel;
import net.blwsmartware.model.TagModel;
import net.blwsmartware.service.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;


public class PostService implements IPostService {

    private Long userID;

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public void vote(Long idPost, int vote) {
        postDAO.vote(idPost,vote,this.userID);
    }

    @Inject
    private IPostDAO postDAO;

    @Inject
    private IUserService userService;

    @Inject
    private ICommentService commentService;

    @Inject
    private ImageService imageService;

    @Inject
    private IPostHasTagService postHasTagService;

    @Override
    public PostModel findByID(Long id) {
        PostModel postModel = postDAO.findByID(id);
        currentTag(postModel);
        return postModel;
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
        currentTag(list);
        currentVoted(list,this.userID);
        return list;
    }

    @Override
    public List<PostModel> findAll(int page) {
        List<PostModel> list = postDAO.findAll(page);
        createdEntity(list);
        commentsEntity(list);
        currentTag(list);
        currentVoted(list,this.userID);
        return list;
    }

    @Override
    public List<PostModel> findTop(int page) {
        List<PostModel> list = postDAO.findTop(page);
        createdEntity(list);
        commentsEntity(list);
        currentTag(list);
        currentVoted(list,this.userID);
        return list;
    }

    @Override
    public List<PostModel> findTrending(int page) {
        List<PostModel> list = postDAO.findTrending(page);
        createdEntity(list);
        commentsEntity(list);
        currentTag(list);
        currentVoted(list,this.userID);
        return list;
    }

    @Override
    public List<PostModel> findPostPending(int page) {
        List<PostModel> list = postDAO.findWithStatus(page, PostStatus.PENDING);
        createdEntity(list);
        commentsEntity(list);
        currentTag(list);
        return findPostWithStatus(PostStatus.PENDING,page);
    }

    @Override
    public List<PostModel> findPostPublished(int page) {
        return findPostWithStatus(PostStatus.PUBLISHED,page);
    }

    @Override
    public List<PostModel> findPostWithStatus(PostStatus status, int page) {
        List<PostModel> list = postDAO.findWithStatus(page, status);
        createdEntity(list);
        commentsEntity(list);
        currentTag(list);
        currentVoted(list,this.userID);
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

    private void currentVoted(List<PostModel> list,Long userCurrentID) {
        if (list == null || list.isEmpty() || userCurrentID==null) return;
        list.forEach(postModel -> {
            System.out.println("Vote:"+postDAO.checkVote(postModel.getId(),userCurrentID));
            postModel.setCurrentVote( postDAO.checkVote(postModel.getId(),userCurrentID));
        });
    }

    public List<PostModel> findAllByIdUser(Long idUser, int page) {
        List<PostModel> list = postDAO.findAllByIdUser(idUser, page);
        createdEntity(list);
        commentsEntity(list);
        currentTag(list);
        return list;
    }

    @Override
    public List<PostModel> findPostStatusByIdUser(Long idUser, int page, int status) {
        List<PostModel> list = postDAO.findPostStatusByIdUser(idUser, page, status);
        createdEntity(list);
        commentsEntity(list);
        currentTag(list);
        return list;

    }

    public void currentTag(List<PostModel> list){
        if(list ==null || list.isEmpty()) return;
        list.forEach(this::currentTag);
    }
    public void currentTag(PostModel postModel){
        List<PostHasTagModel> postHasTagModels = postHasTagService.findAllByPostId(postModel.getId());
        List<String> tagNames = postHasTagModels.stream()
                .map(PostHasTagModel::getName)
                .toList();
        postModel.setTab(tagNames);
    }
    @Override
    public void deleteAllTag(PostModel postModel){
        postHasTagService.delete(postModel.getId());
    }
    @Override
    public int countByIdUser(Long idUser) {
        return postDAO.countByIdUser(idUser);
    }


}
