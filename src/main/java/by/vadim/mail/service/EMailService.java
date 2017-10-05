package by.vadim.mail.service;

import by.vadim.mail.model.Mail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EMailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Configuration freemarkerConfig;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public void sendSimpleMessage(Mail mail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getTo());
        message.setSubject(mail.getSubject());
        message.setText(mail.getContent());
        mailSender.send(message);
    }

    public void sendMimeMessage(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);

        helper.setTo(mail.getTo());
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getContent());

        mail.getAttachments().forEach((s, o) -> {
            try {
                helper.addAttachment(s, (ClassPathResource) o);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        mailSender.send(message);
    }

    public void sendHTMLFreemarkerTemplateMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        mail.getAttachments().forEach((s, o) -> {
            try {
                helper.addAttachment(s, (ClassPathResource) o);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        Template template = freemarkerConfig.getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mail.getModel());

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());

        mailSender.send(message);
    }

    public void sendHTMLThymeleafTemplateMessage(Mail mail) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        mail.getAttachments().forEach((s, o) -> {
            try {
                helper.addAttachment(s, (ClassPathResource) o);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

        Context context = new Context();
        context.setVariables(mail.getModel());
        String html = templateEngine.process("email-template",context);

        helper.setTo(mail.getTo());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());

        mailSender.send(message);
    }
}
