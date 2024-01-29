package dev.franciscolorite.pruebatecnicabcnc.repository;

import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface PhotoRepository extends ListCrudRepository<Photo, Long>, PagingAndSortingRepository<Photo, Long> {

    Optional<Photo> findById(Long photoId);
    List<Photo> findByAlbumId(Long albumId);
}
