package net.blwsmartware.dao;

import net.blwsmartware.model.PostHasTagModel;
import net.blwsmartware.model.TagModel;

import java.util.List;

public interface IPostHasTagDAO extends GenericDAO{
    void update(PostHasTagModel postHasTagModel);

    void delete(long id);

    void save(PostHasTagModel postHasTagModel);


    List<PostHasTagModel> findAllByPostId(long id);
}
