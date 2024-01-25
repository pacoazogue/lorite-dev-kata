package dev.franciscolorite.pruebatecnicabcnc.repository;

import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

public interface PhotoRepository extends ListCrudRepository<Photo, Integer> {
}
