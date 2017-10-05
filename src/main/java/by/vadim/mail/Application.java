package by.vadim.mail;

import by.vadim.mail.model.Mail;
import by.vadim.mail.service.EMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application implements ApplicationRunner {

    @Autowired
    private EMailService eMailService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        Mail mail = new Mail("receiver@gmail.com","Sending Simple Email with JavaMailSender Example","This tutorial demonstrates how to send a simple email using Spring Framework.");

        eMailService.sendSimpleMessage(mail);

        Map<String,Object> attachments = new HashMap<>();
        attachments.put("logo.png",new ClassPathResource("logo.png"));
        mail.setAttachments(attachments);
        eMailService.sendMimeMessage(mail);

        Map<String,String> model = new HashMap<>();
        model.put("name", "Name");
        model.put("location", "Location");
        mail.setModel(model);

        eMailService.sendHTMLFreemarkerTemplateMessage(mail);
        eMailService.sendHTMLThymeleafTemplateMessage(mail);
    }
}
