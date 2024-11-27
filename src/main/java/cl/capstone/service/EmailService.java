package cl.capstone.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import cl.capstone.model.Email;
import cl.capstone.model.Email2;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService implements IEmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendMail(Email email) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

            String[] destinatarios = email.getDestinatarios().toArray(new String[0]);
            helper.setTo(destinatarios);
            helper.setSubject(email.getAsunto());

            Context context = new Context();
            context.setVariable("message", email.getMensaje());
            String contentHTML = templateEngine.process("email", context);

            helper.setText(contentHTML, true);
            javaMailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Error al enviar el correo electronico: " + e.getCause(), e);
        }
    }

    @Override
    public List<Email2> sendMail2(List<Email2> email2List) {
        for (Email2 email : email2List) { // Usamos `email` como variable en el bucle
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

                // Asignar destinatario y asunto
                helper.setTo(email.getEmail());
                helper.setSubject("Confirmación de asistencia a la faena");

                // Crear contexto para la plantilla
                Context context = new Context();
                context.setVariable("run", email.getRun()); // Usar la propiedad `run` de `Email2`

                // Generar contenido dinámico con Thymeleaf
                String contentHTML = templateEngine.process("email", context);

                // Establecer el contenido del correo
                helper.setText(contentHTML, true);
                javaMailSender.send(message);

            } catch (Exception e) {
                throw new RuntimeException("Error al enviar el correo electrónico: " + e.getCause(), e);
            }
        }
        return email2List; // Retornar la lista procesada si es necesario
    }

}
