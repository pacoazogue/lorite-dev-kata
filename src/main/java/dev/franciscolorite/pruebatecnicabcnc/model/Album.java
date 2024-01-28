package dev.franciscolorite.pruebatecnicabcnc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @ToString
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer userId;
    private String title;

    public Album(){}

    public Album(Long id, Integer userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }
}
