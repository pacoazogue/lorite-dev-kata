package dev.franciscolorite.pruebatecnicabcnc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Album {

    @Id
    private Integer id;
    private Integer userId;
    private String title;

    public Album(){}

    public Album(Integer id, Integer userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                '}';
    }
}
