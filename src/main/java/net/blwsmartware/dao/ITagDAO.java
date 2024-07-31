package net.blwsmartware.dao;

import net.blwsmartware.model.TagModel;

public interface ITagDAO extends GenericDAO {
    void update(TagModel tagModel);

    void delete(long id);

    Long save(TagModel tagModel);

    int count();
}
