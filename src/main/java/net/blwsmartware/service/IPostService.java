package net.blwsmartware.service;

import net.blwsmartware.constant.PostStatus;
import net.blwsmartware.model.PostModel;

import java.util.List;

public interface IPostService {
    PostModel findByID(Long id);

    PostModel save(PostModel postModel);

    PostModel update(PostModel postModel);

    void delete(long id);

    int count();

    List<PostModel> findAll();

    List<PostModel> findAll(int page);

    List<PostModel> findTop(int page);

    List<PostModel> findTrending(int page);

    List<PostModel> findPostPending(int page);

    List<PostModel> findPostPublished(int page);

    List<PostModel> findPostWithStatus(PostStatus status, int page);

    List<PostModel> findAllByIdUser(Long idUser);
    void  setUserID(Long id);
    void vote(Long idPost,int vote);
}
