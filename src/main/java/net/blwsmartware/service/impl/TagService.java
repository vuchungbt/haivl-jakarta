package net.blwsmartware.service.impl;

import jakarta.inject.Inject;
import net.blwsmartware.dao.ITagDAO;
import net.blwsmartware.model.TagModel;
import net.blwsmartware.service.ITagService;

import java.util.List;

public class TagService implements ITagService {

    @Inject
    private ITagDAO tagDAO;
    @Override
    public void update(TagModel tagModel) {
        tagDAO.update(tagModel);
    }

    @Override
    public void delete(long id) {
        tagDAO.delete(id);
    }

    @Override
    public TagModel save(TagModel tagModel) {

        Long id =  tagDAO.save(tagModel);
        return tagDAO.findById(id);
    }

    @Override
    public int count() {
        return tagDAO.count();
    }

    @Override
    public List<TagModel> findAll() {
        return tagDAO.findAll();
    }

    @Override
    public TagModel findByTagName(String tagName){
        return tagDAO.findByTagName(tagName);
    }
    @Override
    public boolean checkTagName(String tagName){
        int count = tagDAO.checkTagName(tagName);
        return count > 0;
    }

}
