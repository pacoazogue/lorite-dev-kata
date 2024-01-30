package dev.franciscolorite.pruebatecnicabcnc.service;

import dev.franciscolorite.pruebatecnicabcnc.api.AlbumMapper;
import dev.franciscolorite.pruebatecnicabcnc.model.Album;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.AlbumDto;
import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.model.dto.DataCollectorH2Response;
import dev.franciscolorite.pruebatecnicabcnc.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataCollectorServiceImpl implements DataCollectorService {

    private static final Logger logger = LoggerFactory.getLogger(DataCollectorServiceImpl.class);

    private PhotoRepository photoRepository;
    private AlbumRepository albumRepository;

    private final AlbumMapper albumMapper;

    private List<Photo> photosFromJsonPlaceHolderServiceList;
    private List<Album> albumsFromJsonPlaceHolderServiceList;


    public DataCollectorServiceImpl(PhotoRepository photoRepository, AlbumRepository albumRepository, AlbumMapper albumMapper) {
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
        this.albumMapper = albumMapper;

        photosFromJsonPlaceHolderServiceList = new ArrayList<>();
        albumsFromJsonPlaceHolderServiceList = new ArrayList<>();
    }

    @Override
    public DataCollectorH2Response loadDataFromJsonPlaceHolderServerAndSaveIntoH2Memory() {

        getAppDataFromJsonPlaceHolderServer();

        logger.info("Almacenamiento de albums en memoria H2");
        storeAlbumsDataInMemory();

        logger.info("Almacenamiento de fotos en memoria H2");
        storePhotosDataInMemory();

        DataCollectorH2Response dataCollectorH2Response = new DataCollectorH2Response();
        dataCollectorH2Response.setResponseMessage(LOAD_DATA_FROM_JSONPLACEHOLDER_SERVER_SUCCESS_MESSAGE);
        dataCollectorH2Response.setPhotosMessage("Fotos almacenadas en memoria H2: " + photosFromJsonPlaceHolderServiceList.size());
        dataCollectorH2Response.setAlbumsMessage("Albums almacenados en memoria H2: " + albumsFromJsonPlaceHolderServiceList.size());

        return dataCollectorH2Response;
    }

    @Override
    public List<AlbumDto> loadDataFromJsonPlaceHolderServerAndSaveIntoMemory() {

        getAppDataFromJsonPlaceHolderServer();

        logger.info("Almacenamiento de fotos en memoria");
        photoRepository = new PhotoInMemoryRepository();
        storePhotosDataInMemory();

        logger.info("Almacenamiento de albums en memoria");
        albumRepository = new AlbumInMemoryRepository();
        storeAlbumsDataInMemory();

        return albumsFromJsonPlaceHolderServiceList.stream().map(this::buildAlbumDtoCompleteInformation).toList();
    }

    /*
     * Obtiene datos de "photos" y "albums" del servidor JsonPlaceHolder a través de interfaz HTTP client
     */
    private void getAppDataFromJsonPlaceHolderServer() {

        JsonPlaceHolderService jsonPlaceHolderService = buildJsonPlaceHolderService();

        logger.info("Obtención de albums consumiendo el end-point https://jsonplaceholder.typicode.com/albums");
        albumsFromJsonPlaceHolderServiceList = jsonPlaceHolderService.loadAlbums();

        logger.info("Obtención de photos consumiendo el end-point https://jsonplaceholder.typicode.com/photos");
        photosFromJsonPlaceHolderServiceList = jsonPlaceHolderService.loadPhotos();
    }

    /*
     * Almacenamiento en memoria de los albums (El repositorio a través de polimorfismo (Siguiendo Strategy pattern)
     * condiciona el tipo de memoria donde se almacena)
     */
    private void storeAlbumsDataInMemory() {

        Instant start = Instant.now();

        albumRepository.saveAll(albumsFromJsonPlaceHolderServiceList);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        logger.info("Albums almacenados: " + albumsFromJsonPlaceHolderServiceList.size());
        logger.info("Tiempo consumido: " + timeElapsed + " ms");
    }

    /*
     * Almacenamiento en memoria de las photos (El repositorio a través de polimorfismo (Siguiendo Strategy pattern)
     * condiciona el tipo de memoria donde se almacena)
     */
    private void storePhotosDataInMemory() {

        Instant start = Instant.now();

        photoRepository.saveAll(photosFromJsonPlaceHolderServiceList);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        logger.info("Fotos almacenadas: " + photosFromJsonPlaceHolderServiceList.size());
        logger.info("Tiempo consumido: " + timeElapsed + " ms");
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
