package net.blwsmartware.mapper;

import net.blwsmartware.model.CommentModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<CommentModel> {
    @Override
    public CommentModel mapRow(ResultSet rs) {
        CommentModel commentModel = new CommentModel();
        try {
            commentModel.setId(rs.getLong("id"));
            commentModel.setCreatedBy(rs.getString("created_by"));
            commentModel.setCreatedDate(rs.getTimestamp("created_date"));
            commentModel.setDescription(rs.getString("description"));
            commentModel.setStatus(rs.getInt("status"));
            commentModel.setName(rs.getString("name"));
            commentModel.setThumbnail(rs.getString("thumbnail"));
            commentModel.setTitle(rs.getString("title"));
            commentModel.setType(rs.getString("type"));
            commentModel.setModifiedBy(rs.getString("updated_by"));
            commentModel.setModifiedDate(rs.getTimestamp("updated_date"));
            commentModel.setLevel(rs.getInt("level"));
            commentModel.setContent(rs.getString("content"));
            commentModel.setForPost(rs.getLong("for_post"));
            commentModel.setAuthId(rs.getLong("auth_id"));
            commentModel.setVoteCount(rs.getInt("vote_count"));
        } catch (SQLException e) {
            System.out.println("CommentMapper: " + e.getMessage());
            return null;
        }
        return commentModel;
    }
}
