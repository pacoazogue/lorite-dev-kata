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
    private Integer id;
    private String title;
    private String url;
    private String thumbnailUrl;

    @ManyToOne
    @JoinColumn(name="album_id")
    private Album album;

    /**
     * Constructor vacio con el propósito de JPA
     * Teniendo en cuenta que se puede trabajar con Java 17, se pueden utilizar "Records" pero JPA no soporta Records.
     * Por ello este constructor vacio. Los records son inmutables por lo cual se establece este y luego se puede ir
     * cambiando
     */
    public Photo(){}

    public Photo(Integer id, String title, String url, String thumbnailUrl, Album album) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
        this.album = album;
    }

}



