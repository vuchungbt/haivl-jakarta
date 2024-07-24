package net.blwsmartware.dao;


import net.blwsmartware.model.CommentModel;

import java.util.List;

public interface ICommentDAO extends GenericDAO {
    Long save(CommentModel commentModel);

    void update(CommentModel commentModel);

    void delete(Long id);

    CommentModel findByID(Long id);

    List<CommentModel> findByPostID(Long idPost);

    List<CommentModel> findByPostID(Long idPost, int page);

    List<CommentModel> findByPostIDAndFirstLevel(Long idPost);

    List<CommentModel> findByPostIDAndFirstLevel(Long idPost, int page);

    List<CommentModel> findByParentID(Long idParent);

    List<CommentModel> findByParentID(Long idParent, int page);

}
