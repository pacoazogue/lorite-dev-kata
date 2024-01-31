package dev.franciscolorite.pruebatecnicabcnc.config;

import dev.franciscolorite.pruebatecnicabcnc.repository.AlbumRepository;
import dev.franciscolorite.pruebatecnicabcnc.repository.PhotoRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * RepositoriesSelector - En el contexto de aplicación desarrollada, se puede cargan los datos en memoria interna H2 o en
 * memoria interna, cada opción está representada en un repositorio.
 * Cuando se hace la carga vía memoria, nos enfrentamos al problema que por defecto sigue atacando al repositorio H2
 * por lo que no se van a obtener datos en las llamadas posteriores a la carga en memoria.
 * Este componente soluciona este problema en este contexto
 *
 */
@Component
@Getter @Setter
public class RepositoriesSelector {

    private AlbumRepository albumRepository;
    private PhotoRepository photoRepository;
}
