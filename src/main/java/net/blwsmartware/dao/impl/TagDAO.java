package net.blwsmartware.dao.impl;

import net.blwsmartware.dao.ITagDAO;
import net.blwsmartware.mapper.TagMapper;
import net.blwsmartware.model.TagModel;

import java.util.List;

public class TagDAO extends AbstractDAO implements ITagDAO {

    @Override
    public void update(TagModel tagModel) {
        StringBuilder sql = new StringBuilder("UPDATE tabs SET name = ? ");
        sql.append(", description = ? ");
        sql.append(", updated_by = ? ");
        sql.append(", type = ? ");
        sql.append(", WHERE id = ? ");
        update(sql.toString(), tagModel.getDescription(), tagModel.getModifiedBy(), tagModel.getType(), tagModel.getId());
    }

    @Override
    public void delete(long id) {
        String sql = " DELETE FROM tabs WHERE id = ?";
        delete(sql, id);
    }

    @Override
    public Long save(TagModel tagModel) {
        StringBuilder sql = new StringBuilder("INSERT INTO tabs ");
        sql.append("(name, description, created_by, type) ");
        sql.append("VALUES (?, ?, ?, ?)");
        return insert(sql.toString(), tagModel.getName(), tagModel.getDescription(), tagModel.getCreatedBy(), tagModel.getType());
    }

    @Override
    public int count() {
        String sql = "SELECT count(*) FROM tabs";
        return count(sql);
    }

    @Override
    public List<TagModel> findAll(){
        String sql = "SELECT * FROM tabs";
        return query(sql, new TagMapper());
    }
    @Override
    public TagModel findByTagName(String tagName){
        String sql = "SELECT * FROM tabs WHERE name = ? ";
        return findOne(sql, new TagMapper(), tagName);
    }
    @Override
    public TagModel findById(Long id){
        String sql = "SELECT * FROM tabs WHERE id = ? ";
        return findOne(sql, new TagMapper(), id);
    }

    @Override
    public int checkTagName(String tagName){
        String sql = "SELECT count(*) FROM tabs where name = ?";
        return count(sql, tagName);
    }
}
