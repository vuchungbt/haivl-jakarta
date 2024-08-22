package net.blwsmartware.service;

import net.blwsmartware.model.TagModel;

import java.util.List;

public interface ITagService {
    void update(TagModel tagModel);

    void delete(long id);

    TagModel save(TagModel tagModel);

    int count();

    List<TagModel> findAll();

    TagModel findByTagName(String tagName);
    boolean checkTagName(String tagName);
}
