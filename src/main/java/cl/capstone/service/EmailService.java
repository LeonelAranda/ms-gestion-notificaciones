package cl.capstone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import cl.capstone.model.Email;
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

}
