package dev.franciscolorite.pruebatecnicabcnc.estudioeficiencia;

import dev.franciscolorite.pruebatecnicabcnc.model.Photo;
import dev.franciscolorite.pruebatecnicabcnc.service.JsonPlaceHolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class EstudioEficienciaTest {

    private static final Logger logger = LoggerFactory.getLogger(EstudioEficienciaTest.class);

    JsonPlaceHolderService jsonPlaceHolderService;

    @BeforeEach
    void init() {
        jsonPlaceHolderService = buildJsonPlaceHolderService();
    }


    @Test
    public void estudioEficienciaDeEstructuras() {
        testIterableCollections();
        testMap();
    }


    private void testIterableCollections() {
        logger.info("---- Estudio de eficiencia de estructuras que implementan interfaz Collection ----");
        testList();
        testSet();
    }

    private void testList() {

        logger.info("--- Estudio de eficiencia de List ---");

        List<Photo> photoList = jsonPlaceHolderService.loadPhotos();

        logger.info("-- Estudio de ArrayList --");
        testArrayListSolution(photoList);
        logger.info("-- Estudio de LinkedList --");
        testLinkedListSolution(photoList);
    }


    private void testArrayListSolution(List<Photo> photoList) {

        Photo photoToCreate = new Photo(6000L, 1L, "a", "a", "a");

        photoList = new ArrayList<>(photoList);

        eliminarElemento(photoList, 4000);
        contieneElemento(photoList, 4555);
        insertarElemento(photoList, photoToCreate);
    }


    private void testLinkedListSolution(List<Photo> photoList) {

        Photo photoToCreate = new Photo(6000L, 1L, "a", "a", "a");
        photoList = new LinkedList<>(photoList);

        eliminarElemento(photoList, 4000);
        contieneElemento(photoList, 4555);
        insertarElemento(photoList, photoToCreate);
    }

    private void testMap() {
        logger.info("---- Estudio de eficiencia de estructuras que implementan interfaz Map ----");

        List<Photo> photoList = jsonPlaceHolderService.loadPhotos();

        logger.info("-- Estudio de HashMap --");
        testHashMap(photoList);
        logger.info("-- Estudio de TreeMap --");
        testTreeMap(photoList);
    }

    private void testHashMap(List<Photo> photoList) {

        Photo photoToCreate = new Photo(6000L, 1L, "a", "a", "a");

        HashMap<Long, Photo> photosMap = new HashMap<>();

        photoList.forEach(photo -> {photosMap.put(photo.getId(), photo);});

        contieneElemento(photosMap, 4555L);
        eliminarElemento(photosMap, 4555L);
        insertarElemento(photosMap, photoToCreate);
    }

    private void testTreeMap(List<Photo> photoList) {

        Photo photoToCreate = new Photo(6000L, 1L, "a", "a", "a");

        TreeMap<Long, Photo> photosTreeMap = new TreeMap<>();

        photoList.forEach(photo -> {photosTreeMap.put(photo.getId(), photo);});

        contieneElemento(photosTreeMap, 4555L);
        eliminarElemento(photosTreeMap, 4555L);
        insertarElemento(photosTreeMap, photoToCreate);
    }

    private void contieneElemento(Map<Long, Photo> photoMap, Long index){


        Instant start = Instant.now();

        photoMap.get(index);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por la busqueda de un elemento según index %d: %d nanoseconds", index, timeElapsed);

        logger.info(message);
    }

    private void contieneElemento(List<Photo> photoList, int index){

        Photo photo = photoList.get(index);

        Instant start = Instant.now();

        photoList.contains(photo);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por la busqueda de un elemento según posición %d: %d nanoseconds", index, timeElapsed);

        logger.info(message);
    }


    private void contieneElemento(Set<Photo> photoSet, Photo photo){

        Instant start = Instant.now();

        photoSet.contains(photo);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por la busqueda de un elemento según objeto: %d nanoseconds", timeElapsed);

        logger.info(message);
    }

    private void eliminarElemento(List<Photo> photoList, int index){

        Instant start = Instant.now();

        photoList.remove(index);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por el borrado de un elemento según posición: %d nanoseconds", timeElapsed);

        logger.info(message);
    }


    private void eliminarElemento(Set<Photo> photoHashSet, Photo photo){

        Instant start = Instant.now();

        photoHashSet.remove(photo);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por el borrado de un elemento según objeto: %d nanoseconds", timeElapsed);

        logger.info(message);
    }

    private void eliminarElemento(Map<Long, Photo> photoMap, Long index){

        Instant start = Instant.now();

        photoMap.remove(index);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por el borrado de un elemento según hash index: %d nanoseconds", timeElapsed);

        logger.info(message);
    }

    private void insertarElemento(Set<Photo> photoSet, Photo photo){

        Instant start = Instant.now();

        photoSet.add(photo);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por la inserción de un elemento: %d nanoseconds", timeElapsed);

        logger.info(message);
    }

    private void insertarElemento(List<Photo> photoList, Photo photo){

        Instant start = Instant.now();

        photoList.add(photo);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por la inserción de un elemento: %d nanoseconds", timeElapsed);

        logger.info(message);
    }

    private void insertarElemento(Map<Long, Photo> photoMap, Photo photo){

        Instant start = Instant.now();

       photoMap.put(photo.getId(), photo);

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toNanos();

        String message = String.format("Tiempo consumido por la inserción de un elemento: %d nanoseconds", timeElapsed);

        logger.info(message);
    }

    private void testSet() {

        logger.info("--- Estudio de eficiencia de Set ---");

        List<Photo> photoList = jsonPlaceHolderService.loadPhotos();

        Photo photoRandom = photoList.get(3456);

        logger.info("-- Estudio de HashSet --");
        HashSet<Photo> photoHashSet = new HashSet<>(photoList);
        testSetChild(photoHashSet, photoRandom);
        logger.info("-- Estudio de LinkedHashSet --");
        LinkedHashSet<Photo> linkedHashSet = new LinkedHashSet<>(photoList);
        testSetChild(linkedHashSet, photoRandom);
        logger.info("-- Estudio de TreeSet --");
        // Incoveniente a tener en cuenta: Se exige implementar Comparable para uso de TreeSet
        TreeSet<Photo> treeSet = new TreeSet<>(photoList);
        testSetChild(treeSet, photoRandom);
    }

    private void testSetChild(Set<Photo> photoSet, Photo photo) {
        contieneElemento(photoSet, photo);
        eliminarElemento(photoSet, photo);
        Photo photoToCreate = new Photo(6000L, 1L, "a", "a", "a");
        insertarElemento(photoSet, photoToCreate);
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
