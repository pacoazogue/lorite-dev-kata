package dev.franciscolorite.pruebatecnicabcnc.config.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DatosCargadosEnMemoriaEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final String message) {

        DatosCargadosEnMemoriaInternaEvent datosCargadosEvent = new DatosCargadosEnMemoriaInternaEvent(message);

        applicationEventPublisher.publishEvent(datosCargadosEvent);
    }
}