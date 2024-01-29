package dev.franciscolorite.pruebatecnicabcnc.repository;

import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;

/**
 * CustomPhotoRepository - Clase repositorio de Photos que son almacenados en memoria
 */
public class PhotoInMemoryRepository implements PhotoRepository {

    private final Map<Long, Photo> photosMap;
    private final Map<Long, List<Photo>> photosByAlbumIdMap;

    public PhotoInMemoryRepository() {
        photosMap = new HashMap<>();
        photosByAlbumIdMap = new HashMap<>();
    }

    @Override
    public <S extends Photo> S save(S entity) {

        photosMap.put(entity.getId(), entity);

        List<Photo> photosByAlbumIdList = photosByAlbumIdMap.get(entity.getAlbumId());

        if (photosByAlbumIdList == null) {
            photosByAlbumIdList = new ArrayList<>();
            photosByAlbumIdList.add(entity);
            photosByAlbumIdMap.put(entity.getAlbumId(), photosByAlbumIdList);
        } else {
            if (!photosByAlbumIdList.contains(entity)) {
                photosByAlbumIdList.add(entity);
            }
        }
        return null;
    }

    @Override
    public <S extends Photo> List<S> saveAll(Iterable<S> entities) {

        List<Photo> photoList = (List<Photo>) entities;

        photoList.forEach(this::save);

        return (List<S>) photoList;
    }

    @Override
    public Optional<Photo> findById(Long id) {
        return Optional.ofNullable(photosMap.get(id));
    }

    @Override
    public List<Photo> findByAlbumId(Long albumId) {
        return photosByAlbumIdMap.get(albumId);
    }

    @Override
    public boolean existsById(Long id) {
        return photosMap.containsKey(id);
    }

    @Override
    public List<Photo> findAll() {
        return (List<Photo>) photosMap.values();
    }

    @Override
    public List<Photo> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return photosMap.size();
    }

    @Override
    public void deleteById(Long id) {
        photosMap.remove(id);
    }

    @Override
    public void delete(Photo entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Photo> entities) {

    }

    @Override
    public void deleteAll() {
        photosMap.clear();
    }

    @Override
    public Iterable<Photo> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Photo> findAll(Pageable pageable) {
        return null;
    }
}
