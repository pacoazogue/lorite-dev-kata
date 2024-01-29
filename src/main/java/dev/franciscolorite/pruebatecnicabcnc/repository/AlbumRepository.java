package dev.franciscolorite.pruebatecnicabcnc.repository;

import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AlbumRepository extends ListCrudRepository<Album, Long>, PagingAndSortingRepository<Album, Long>{
}
