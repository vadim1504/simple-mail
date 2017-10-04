package by.vadim.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

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
        eMailService.sendMimeMessage(mail,new ClassPathResource("oleen26.jpg"));
    }
}
