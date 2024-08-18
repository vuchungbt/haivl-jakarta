package net.blwsmartware.mapper;

import net.blwsmartware.model.PostHasTagModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PostHasTagMapper implements  RowMapper<PostHasTagModel>{
    @Override
    public PostHasTagModel mapRow(ResultSet rs) {
        PostHasTagModel postHasTagModel = new PostHasTagModel();
        try {
            postHasTagModel.setPostId(rs.getLong("post_id"));
            postHasTagModel.setTagId(rs.getLong("tab_id"));
            postHasTagModel.setCreatedDate(rs.getTimestamp("created_date"));
            postHasTagModel.setModifiedDate(rs.getTimestamp("updated_date"));
            postHasTagModel.setStatus(rs.getInt("status"));
            postHasTagModel.setName(rs.getString("name"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return postHasTagModel;
    }
}
