package net.blwsmartware.dao;

import net.blwsmartware.model.TagModel;

import java.util.List;

public interface ITagDAO extends GenericDAO {
    void update(TagModel tagModel);

    void delete(long id);

    Long save(TagModel tagModel);

    int count();

    List<TagModel> findAll();
    TagModel findByTagName(String tagName);
    TagModel findById(Long id);

    int checkTagName(String tagName);
}
