package net.blwsmartware.dao.impl;

import net.blwsmartware.constant.IConstant;
import net.blwsmartware.constant.PostStatus;
import net.blwsmartware.dao.IPostDAO;
import net.blwsmartware.mapper.PostMapper;
import net.blwsmartware.model.PostModel;

import java.util.List;

public class PostDAO extends AbstractDAO implements IPostDAO {

    @Override
    public PostModel findByID(Long id) {
        StringBuilder sql = new StringBuilder("SELECT p.*, COUNT(pv.user_id) AS vote_count, ROUND(AVG(pv.vote), 2) AS avg_vote ");
        sql.append(" FROM posts p");
        sql.append(" LEFT JOIN post_has_votes pv ON p.id = pv.post_id");
        sql.append(" WHERE p.id = ?");
        sql.append(" GROUP BY p.id");
        return findOne(sql.toString(), new PostMapper(), id);
    }

    @Override
    public void update(PostModel newPost) {
        StringBuilder sql = new StringBuilder("UPDATE posts SET name=?,");
        sql.append(" description=?,");
        sql.append(" short_description=?,");
        sql.append(" content=?,");
        sql.append(" refer=?,");
        sql.append(" source=?,");
        sql.append(" source_name=?,");
        sql.append(" updated_by=?,");
        sql.append(" status=?,");
        sql.append(" thumbnail=?,");
        sql.append(" title=?,");
        sql.append(" publish_date=?,");
        sql.append(" verified_date=?,");
        sql.append(" type=?, ");
        sql.append(" image_path=?");
        sql.append(" WHERE id = ?");
        update(sql.toString(), newPost.getName(), newPost.getDescription(), newPost.getShortDescription(), newPost.getContent(),
                newPost.getRefer(), newPost.getSource(), newPost.getSourceName(), newPost.getModifiedBy(), newPost.getStatus(), newPost.getThumbnail(), newPost.getTitle(),
                newPost.getPublishDate(), newPost.getVerifiedDate(), newPost.getType(), newPost.getImagePath(),
                newPost.getId());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM posts WHERE id = ?";
        delete(sql, id);
    }

    @Override
    public Long save(PostModel postModel) {
        StringBuilder sql = new StringBuilder("INSERT INTO posts ");
        sql.append(" (name,publish_date,verified_date,status ,created_by,type,title,thumbnail");
        sql.append(",description,short_description,source,source_name,refer,content,auth_id,image_path)");
        sql.append(" VALUES(?,?,?,?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?, ?)");
        return insert(sql.toString(), postModel.getName(), postModel.getPublishDate(), postModel.getVerifiedDate(),
                postModel.getStatus(), postModel.getCreatedBy(), postModel.getType(), postModel.getTitle(),
                postModel.getThumbnail(), postModel.getDescription(), postModel.getShortDescription(), postModel.getSource(),
                postModel.getSourceName(), postModel.getRefer(), postModel.getContent(), postModel.getAuthId(), postModel.getImagePath()
        );
    }

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM posts";
        return count(sql);
    }

    @Override
    public List<PostModel> findAll() {
        String sql = "SELECT * FROM posts";
        return query(sql, new PostMapper());
    }

    @Override
    public List<PostModel> findAll(int page) {
        if (page == 0) page = 1;
        String limit = " LIMIT " + IConstant.RECORD_LIMIT_POST + " OFFSET " + (page - 1) * IConstant.RECORD_LIMIT_POST;
        StringBuilder sql = new StringBuilder("SELECT p.*, COUNT(pv.user_id) AS vote_count, ROUND(AVG(pv.vote), 2) AS avg_vote ");
        sql.append(" FROM posts p");
        sql.append(" LEFT JOIN post_has_votes pv ON p.id = pv.post_id");
        sql.append(" WHERE p.status = " + PostStatus.PUBLISHED.getCode());
        sql.append(" GROUP BY p.id");
        sql.append(" ORDER BY p.created_date DESC");
        sql.append(limit);
        return query(sql.toString(), new PostMapper());
    }

    @Override
    public List<PostModel> findTop(int page) {
        if (page == 0) page = 1;
        String limit = " LIMIT " + IConstant.RECORD_LIMIT_POST + " OFFSET " + (page - 1) * IConstant.RECORD_LIMIT_POST;
        StringBuilder sql = new StringBuilder("SELECT p.*, COUNT(pv.user_id) AS vote_count, ROUND(AVG(pv.vote), 2) AS avg_vote ");
        sql.append(" FROM posts p");
        sql.append(" LEFT JOIN post_has_votes pv ON p.id = pv.post_id");
        sql.append(" WHERE p.status = " + PostStatus.PUBLISHED.getCode());
        sql.append(" GROUP BY p.id");
        sql.append(" ORDER BY vote_count  DESC, p.created_date DESC ");
        sql.append(limit);
        return query(sql.toString(), new PostMapper());
    }

    @Override
    public List<PostModel> findTrending(int page) {
        return findTop(page);
    }

    @Override
    public List<PostModel> findWithStatus(int page, PostStatus status) {
        if (page == 0) page = 1;
        String limit = " LIMIT " + IConstant.RECORD_LIMIT_POST + " OFFSET " + (page - 1) * IConstant.RECORD_LIMIT_POST;
        StringBuilder sql = new StringBuilder("SELECT p.*, COUNT(pv.user_id) AS vote_count, ROUND(AVG(pv.vote), 2) AS avg_vote ");
        sql.append(" FROM posts p");
        sql.append(" LEFT JOIN post_has_votes pv ON p.id = pv.post_id");
        sql.append(" WHERE p.status = " + status.getCode());
        sql.append(" GROUP BY p.id");
        sql.append(" ORDER BY p.created_date DESC, vote_count DESC ");
        sql.append(limit);
        return query(sql.toString(), new PostMapper());
    }

    @Override
    public List<PostModel> findAllByIdUser(Long idUser) {
        StringBuilder sql = new StringBuilder("SELECT p.*, COUNT(pv.user_id) AS vote_count, ROUND(AVG(pv.vote), 2) AS avg_vote ");
        sql.append(" FROM posts p");
        sql.append(" LEFT JOIN post_has_votes pv ON p.id = pv.post_id");
        sql.append(" WHERE p.auth_id = " + idUser);
        sql.append(" GROUP BY p.id");
        sql.append(" ORDER BY p.created_date DESC");
        return query(sql.toString(), new PostMapper());
    }

}
