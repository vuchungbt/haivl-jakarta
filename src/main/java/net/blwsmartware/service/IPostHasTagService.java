package net.blwsmartware.service;

import net.blwsmartware.model.PostHasTagModel;

import java.util.List;

public interface IPostHasTagService {
    void update(PostHasTagModel postHasTagModel);

    void delete(long id);

    void save(PostHasTagModel postHasTagModel);


    List<PostHasTagModel> findAllByPostId(long id);
}
