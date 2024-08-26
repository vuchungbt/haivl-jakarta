package net.blwsmartware.mapper;

import net.blwsmartware.model.TagModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagMapper implements  RowMapper<TagModel>{
    @Override
    public TagModel mapRow(ResultSet rs) {
        TagModel tagModel = new TagModel();
        try {
            tagModel.setId(rs.getLong("id"));
            tagModel.setName(rs.getString("name"));
            tagModel.setCreatedBy(rs.getString("created_by"));
            tagModel.setCreatedDate(rs.getTimestamp("created_date"));
            tagModel.setModifiedBy(rs.getString("updated_by"));
            tagModel.setModifiedDate(rs.getTimestamp("updated_date"));
            tagModel.setDescription(rs.getString("description"));
            tagModel.setTrend(rs.getBoolean("is_trend"));
            tagModel.setStatus(rs.getInt("status"));
            tagModel.setType(rs.getString("type"));

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return tagModel;
    }
}
