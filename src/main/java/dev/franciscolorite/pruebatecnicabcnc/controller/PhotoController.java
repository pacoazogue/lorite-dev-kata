package dev.franciscolorite.pruebatecnicabcnc.controller;

import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.repository.PhotoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bcncapp/api/photos")
public class PhotoController {

    private final PhotoRepository photoRepository;

    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @GetMapping("")
    public List<Photo> findAll() {
        return photoRepository.findAll();
    }

}
