package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface JsonPlaceHolderService {

    String BASE_URL = "https://jsonplaceholder.typicode.com";

    @GetExchange("/photos")
    List<Photo> loadPhotos();

    @GetExchange("/albums")
    List<Album> loadAlbums();
}
