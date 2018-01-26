package com.example.square.data.models.commitmodel;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class RepoData {
    private String name;
    private Integer forks;
    private Integer stars;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getForks() {
        return forks;
    }

    public void setForks(Integer forks) {
        this.forks = forks;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
