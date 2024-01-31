package dev.franciscolorite.pruebatecnicabcnc.config.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class DatosCargadosEnMemoriaInternaEvent extends ApplicationEvent {

    private final String message;

    public DatosCargadosEnMemoriaInternaEvent(String message) {
        super(message);
        this.message = message;
    }
}