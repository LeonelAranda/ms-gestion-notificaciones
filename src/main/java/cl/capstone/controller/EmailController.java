package cl.capstone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.capstone.model.Email;
import cl.capstone.service.IEmailService;
import jakarta.mail.MessagingException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // CORS para todos los endpoints en esta clase
public class EmailController {

    @Autowired
    IEmailService emailService;

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody Email email) throws MessagingException {

        if (email.getDestinatarios() == null || email.getDestinatarios().isEmpty()) {
            return new ResponseEntity<>("El destinatario no puede ser nulo o vacío.", HttpStatus.BAD_REQUEST);
        }
        emailService.sendMail(email);
        return new ResponseEntity<>("Correo enviado exitosamente.", HttpStatus.OK);
    }

}
