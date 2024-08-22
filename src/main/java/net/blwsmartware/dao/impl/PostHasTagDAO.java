package net.blwsmartware.dao.impl;

import net.blwsmartware.dao.IPostHasTagDAO;
import net.blwsmartware.mapper.PostHasTagMapper;
import net.blwsmartware.model.PostHasTagModel;
import net.blwsmartware.model.TagModel;

import java.util.List;

public class PostHasTagDAO extends AbstractDAO implements IPostHasTagDAO {
    @Override
    public void update(PostHasTagModel postHasTagModel) {
        StringBuilder sql = new StringBuilder("UPDATE post_has_tab SET status = ? ");
        sql.append("WHERE post_id = ? and tab_id = ?");
        update(sql.toString(), postHasTagModel.getStatus(), postHasTagModel.getPostId(), postHasTagModel.getTagId());
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM post_has_tab where post_id = ? ";
        delete(sql, id);
    }

    @Override
    public void save(PostHasTagModel postHasTagModel) {
        StringBuilder sql = new StringBuilder(" INSERT INTO post_has_tab ");
        sql.append("( post_id, tab_id ) VALUES (?, ?)");
        insertNotReturn(sql.toString(),postHasTagModel.getPostId(), postHasTagModel.getTagId());
    }


    @Override
    public List<PostHasTagModel> findAllByPostId(long id) {
        StringBuilder sql = new StringBuilder( "SELECT post_has_tab.* , tabs.name ");
        sql.append("FROM post_has_tab JOIN tabs ON post_has_tab.tab_id = tabs.id where post_has_tab.post_id = ?");

        return query(sql.toString(), new PostHasTagMapper(),id);
    }
}
