package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.api.AlbumMapper;
import dev.franciscolorite.pruebatecnicabcnc.exception.GlobalExceptionHandler;
import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class DataCollectorServiceImpl implements DataCollectorService {

    private static final Logger logger = LoggerFactory.getLogger(DataCollectorServiceImpl.class);

    private PhotoRepository photoRepository;
    private AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;


    public DataCollectorServiceImpl(PhotoRepository photoRepository, AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;
    }

    @Override
    public ResponseEntity<?> loadDataFromJsonPlaceHolderServerAndSaveIntoH2Memory() {

        JsonPlaceHolderService jsonPlaceHolderService = buildJsonPlaceHolderService();

        logger.info("Obtención de albums consumiendo el end-point https://jsonplaceholder.typicode.com/albums ");

        logger.info("Almacenamiento de datos obtenidos en memoria H2");
        List<Album> albumsList = jsonPlaceHolderService.loadAlbums();
        List<Photo> photoList = jsonPlaceHolderService.loadPhotos();

        logger.info("Almacenamiento de albums");
        Instant start = Instant.now();
        albumRepository.saveAll(albumsList);
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        logger.info("Tiempo consumido: " + timeElapsed);

        logger.info("Almacenamiento de fotos");
        start = Instant.now();
        photoRepository.saveAll(photoList);
        finish = Instant.now();
        timeElapsed = Duration.between(start, finish).toMillis();

        logger.info("Tiempo consumido: " + timeElapsed);

        return ResponseEntity.ok(LOAD_DATA_FROM_JSONPLACEHOLDER_SERVER_SUCCESS_MESSAGE);
    }


    @Override
    public List<AlbumDto> loadDataFromJsonPlaceHolderServerAndSaveIntoMemory() {

        JsonPlaceHolderService jsonPlaceHolderService = buildJsonPlaceHolderService();

        List<Photo> photoList = jsonPlaceHolderService.loadPhotos();
        List<Album> albumsList = jsonPlaceHolderService.loadAlbums();

        photoRepository = new PhotoInMemoryRepository();
        albumRepository = new AlbumInMemoryRepository();

        photoRepository.saveAll(photoList);

        List<Album> albumList = albumRepository.saveAll(albumsList);

        return albumList.stream().map(this::buildAlbumDtoCompleteInformation).toList();
    }

    /*
     * Los albums (Entidades) son mapeados a su correspondiente DTO y se relacionan las fotos con los álbums a los
     * que pertenecen
     * @param album
     * @return Catálogo completo representado a través de la clase AlbumDto
     */
    private AlbumDto buildAlbumDtoCompleteInformation(Album album) {

        AlbumDto albumDto = albumMapper.entityToDto(album);

        albumDto.setPhotoList(photoRepository.findByAlbumId(album.getId()));

        return albumDto;
    }

    /*
     * Construcción de la interfaz HTTP Client
     * @return JsonPlaceHolderService
     */
    private JsonPlaceHolderService buildJsonPlaceHolderService() {

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

        return httpServiceProxyFactory.createClient(JsonPlaceHolderService.class);
    }
}
