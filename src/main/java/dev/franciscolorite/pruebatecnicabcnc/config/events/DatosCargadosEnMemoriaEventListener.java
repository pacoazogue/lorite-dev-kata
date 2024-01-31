package dev.franciscolorite.pruebatecnicabcnc.config.events;

import dev.franciscolorite.pruebatecnicabcnc.config.RepositoriesSelector;
import dev.franciscolorite.pruebatecnicabcnc.service.AlbumServiceImpl;
import dev.franciscolorite.pruebatecnicabcnc.service.PhotoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DatosCargadosEnMemoriaEventListener implements ApplicationListener<DatosCargadosEnMemoriaInternaEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DatosCargadosEnMemoriaEventListener.class);

    @Autowired
    private RepositoriesSelector repositoriesSelector;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(DatosCargadosEnMemoriaInternaEvent event) {

        logger.info("Recibido evento de carga de datos en memoria interna - " + event.getMessage());
        logger.info("Se establece estrategia de consulta de los repositorios donde se ha almacenado dicha información");

        AlbumServiceImpl albumServiceImpl = applicationContext.getBean(AlbumServiceImpl.class);
        PhotoServiceImpl photoServiceImpl = applicationContext.getBean(PhotoServiceImpl.class);

        albumServiceImpl.setAlbumRepository(repositoriesSelector.getAlbumRepository());
        albumServiceImpl.setPhotoRepository(repositoriesSelector.getPhotoRepository());

        photoServiceImpl.setPhotoRepository(repositoriesSelector.getPhotoRepository());
    }
}