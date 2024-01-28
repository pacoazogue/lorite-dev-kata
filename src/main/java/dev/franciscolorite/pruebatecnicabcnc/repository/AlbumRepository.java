package dev.franciscolorite.pruebatecnicabcnc.repository;

import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import org.springframework.data.repository.ListCrudRepository;

public interface AlbumRepository extends ListCrudRepository<Album, Long> {
}
