package com.example.square.data.models.commitmodel;

/**
 * Created by l1maginaire on 1/25/18.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commit {

    @SerializedName("sha")
    @Expose
    private String sha;
    @SerializedName("commit")
    @Expose
    private CommitDetails commit;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("htmlDetailsurl")
    @Expose
    private String htmlUrl;
    @SerializedName("commentsDetailsurl")
    @Expose
    private String commentsUrl;
    @SerializedName("author")
    @Expose
    private AuthorDetails author;
    @SerializedName("committer")
    @Expose
    private CommitterDetails committer;
    @SerializedName("parents")
    @Expose
    private List<Parent> parents = null;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public CommitDetails getCommit() {
        return commit;
    }

    public void setCommit(CommitDetails commit) {
        this.commit = commit;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getCommentsUrl() {
        return commentsUrl;
    }

    public void setCommentsUrl(String commentsUrl) {
        this.commentsUrl = commentsUrl;
    }

    public AuthorDetails getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDetails author) {
        this.author = author;
    }

    public CommitterDetails getCommitter() {
        return committer;
    }

    public void setCommitter(CommitterDetails committer) {
        this.committer = committer;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        this.parents = parents;
    }

}