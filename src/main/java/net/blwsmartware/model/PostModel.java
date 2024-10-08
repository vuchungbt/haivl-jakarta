package net.blwsmartware.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostModel extends AbstractModel {
    private List<String> tab = new ArrayList<>();
    private String source, refer, shortdescription, content, sourceName;
    private Timestamp publishDate, verifiedDate;
    private Long authId;
    private double avgVote;
    private int voteCount;
    private int currentVote;

    public int getCurrentVote() {
        return currentVote;
    }

    public void setCurrentVote(int currentVote) {
        this.currentVote = currentVote;
    }

    private UserModel created;
    private Set<TagModel> tags;
    private List<CommentModel> comments;

    private String imagePath;
    private String statusName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public UserModel getCreated() {
        return created;
    }

    public void setCreated(UserModel created) {
        this.created = created;
    }

    public double getAvgVote() {
        return avgVote;
    }

    public void setAvgVote(double avgVote) {
        this.avgVote = avgVote;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public Long getAuthId() {
        return authId;
    }

    public void setAuthId(Long authId) {
        this.authId = authId;
    }


    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Timestamp getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Timestamp verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getShortDescription() {
        return shortdescription;
    }

    public void setShortDescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTab() {
        return tab;
    }

    public void setTab(List<String> tab) {
        this.tab = tab;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }
}
