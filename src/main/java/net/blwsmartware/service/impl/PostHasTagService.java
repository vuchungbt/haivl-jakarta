package net.blwsmartware.service.impl;

import jakarta.inject.Inject;
import net.blwsmartware.dao.IPostHasTagDAO;
import net.blwsmartware.model.PostHasTagModel;
import net.blwsmartware.service.IPostHasTagService;

import java.util.List;

public class PostHasTagService implements IPostHasTagService {
    @Inject
    private IPostHasTagDAO postHasTagDAO;
    @Override
    public void update(PostHasTagModel postHasTagModel) {
        postHasTagDAO.update(postHasTagModel);
    }

    @Override
    public void delete(long id) {
        postHasTagDAO.delete(id);
    }

    @Override
    public void save(PostHasTagModel postHasTagModel) {
        postHasTagDAO.save(postHasTagModel);
    }


    @Override
    public List<PostHasTagModel> findAllByPostId(long id) {
        return postHasTagDAO.findAllByPostId(id);
    }
}
