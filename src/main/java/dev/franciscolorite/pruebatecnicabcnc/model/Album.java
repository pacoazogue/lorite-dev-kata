package dev.franciscolorite.pruebatecnicabcnc.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter @Setter @ToString
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer userId;
    private String title;

    @OneToMany (mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Photo> photoList;

    public Album(){}

    public Album(Integer id, Integer userId, String title, List<Photo> photoList) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.photoList = photoList;
    }
}
