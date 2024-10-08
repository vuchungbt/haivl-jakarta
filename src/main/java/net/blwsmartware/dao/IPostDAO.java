package net.blwsmartware.dao;

import net.blwsmartware.constant.PostStatus;
import net.blwsmartware.model.PostModel;

import java.util.List;

public interface IPostDAO extends GenericDAO {
    PostModel findByID(Long id);

    void update(PostModel newPost);

    void delete(long id);

    Long save(PostModel postModel);

    int count();
    void vote(Long postID,int vote, Long userID);
    void updateVote(Long postID,int vote, Long userID);
    List<PostModel> findAll();

    List<PostModel> findAll(int page);

    List<PostModel> findTop(int page);

    List<PostModel> findTrending(int page);

    List<PostModel> findWithStatus(int page, PostStatus status);
    List<PostModel> findAllByIdUser(Long idUser, int page);
    int countByIdUser(Long idUser);

    int checkVote(Long postID,Long userID );

    List<PostModel> findPostStatusByIdUser(Long idUser, int page, int status);

}
