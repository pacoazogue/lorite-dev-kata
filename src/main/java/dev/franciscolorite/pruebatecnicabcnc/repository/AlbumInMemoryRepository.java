package dev.franciscolorite.pruebatecnicabcnc.repository;

import dev.franciscolorite.pruebatecnicabcnc.model.Album;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * CustomPhotoRepository - Clase repositorio de Photos que son almacenados en memoria
 */
public class AlbumInMemoryRepository implements AlbumRepository {

    private final HashMap<Long, Album> albumsMap;

    public AlbumInMemoryRepository() {
        albumsMap = new HashMap<>();
    }

    @Override
    public <S extends Album> S save(S entity) {

        albumsMap.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public <S extends Album> List<S> saveAll(Iterable<S> entities) {

        List<Album> albums = (List<Album>) entities;

        albums.forEach(this::save);

        return (List<S>) albums;
    }

    @Override
    public Optional<Album> findById(Long id) {
        return Optional.ofNullable(albumsMap.get(id));
    }

    @Override
    public boolean existsById(Long id) {
        return albumsMap.containsKey(id);
    }

    @Override
    public List<Album> findAll() {
        return albumsMap.values().stream().toList();
    }

    @Override
    public List<Album> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return albumsMap.size();
    }

    @Override
    public void deleteById(Long id) {
        albumsMap.remove(id);
    }

    @Override
    public void delete(Album entity) {
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> entities) {

    }

    @Override
    public void deleteAll(Iterable<? extends Album> entities) {

    }

    @Override
    public void deleteAll() {
        albumsMap.clear();;
    }
}
