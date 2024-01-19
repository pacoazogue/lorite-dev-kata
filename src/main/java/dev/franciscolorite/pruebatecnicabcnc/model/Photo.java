package dev.franciscolorite.pruebatecnicabcnc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Photo {

    @Id
    private Integer id;
    private Integer albumId;
    private String title;
    private String url;

    /**
     * Constructor vacio con el propósito de JPA
     * Teniendo en cuenta que se puede trabajar con Java 17, se pueden utilizar "Records" pero JPA no soporta Records.
     * Por ello este constructor vacio. Los records son inmutables por lo cual se establece este y luego se puede ir
     * cambiando
     */
    public Photo(){}

    public Photo(Integer id, Integer albumId, String title, String url) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}



