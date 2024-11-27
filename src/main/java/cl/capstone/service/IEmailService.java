package cl.capstone.service;

import java.util.List;

import cl.capstone.model.Email;
import cl.capstone.model.Email2;

public interface IEmailService {

    public void sendMail(Email email);

    public List<Email2> sendMail2(List<Email2> email2);

}
