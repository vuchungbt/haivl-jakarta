package net.blwsmartware.model;

public class PostHasTagModel extends AbstractModel {

    private Long postId;
    private Long tagId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
