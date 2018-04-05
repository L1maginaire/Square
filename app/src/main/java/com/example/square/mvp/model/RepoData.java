package com.example.square.mvp.model;

import java.io.Serializable;

/**
 * Created by l1maginaire on 1/26/18.
 */

public class RepoData{
    private String name;
    private String description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
