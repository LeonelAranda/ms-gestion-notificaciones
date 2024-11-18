package cl.capstone.model;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Email {

    private List<String> destinatarios; // Cambiado a lista
    private String asunto;
    private String mensaje;

}
