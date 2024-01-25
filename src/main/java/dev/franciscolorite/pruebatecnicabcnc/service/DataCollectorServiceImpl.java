package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.repository.AlbumRepository;
import dev.franciscolorite.pruebatecnicabcnc.repository.PhotoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.List;
@Service
public class DataCollectorServiceImpl implements DataCollectorService {

    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;

    public DataCollectorServiceImpl(PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public ResponseEntity<?> loadDataFromJsonPlaceHolderServer() {

        WebClient webClient = WebClient
                .builder()
                .baseUrl(JsonPlaceHolderService.BASE_URL)
                .exchangeStrategies(ExchangeStrategies
                        .builder()
                        .codecs(codecs -> codecs
                                .defaultCodecs()
                                .maxInMemorySize(50 * 1024 * 1024))
                        .build())
                .build();


        HttpServiceProxyFactory httpServiceProxyFactory = HttpServiceProxyFactory
                .builderFor(WebClientAdapter.create(webClient)).build();

        JsonPlaceHolderService jsonPlaceHolderService =
                httpServiceProxyFactory.createClient(JsonPlaceHolderService.class);

        List<Photo> photoList = jsonPlaceHolderService.loadPhotos();
        photoRepository.saveAll(photoList);

        List<Album> albums = jsonPlaceHolderService.loadAlbums();
        albumRepository.saveAll(albums);


        return ResponseEntity.ok(LOAD_DATA_FROM_JSONPLACEHOLDER_SERVER_SUCCESS_MESSAGE);
    }
}
