package dev.franciscolorite.pruebatecnicabcnc.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String thumbnailUrl;
    private Long albumId;

    /**
     * Constructor vacio con el propósito de JPA
     * Teniendo en cuenta que se puede trabajar con Java 17, se pueden utilizar "Records" pero JPA no soporta Records.
     * Por ello este constructor vacio. Los records son inmutables por lo cual se establece este y luego se puede ir
     * cambiando
     */
    public Photo(){}

    public Photo(Long id, Long albumId, String title, String url, String thumbnailUrl) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

}



