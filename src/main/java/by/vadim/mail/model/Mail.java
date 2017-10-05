package by.vadim.mail.model;

import java.util.List;
import java.util.Map;

public class Mail {

    private String to;
    private String subject;
    private String content;
    private Map<String,String> model;
    private Map<String,Object> attachments;

    public Mail(){

    }

    public Mail(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String,String> getModel() {
        return model;
    }

    public void setModel(Map<String,String> model) {
        this.model = model;
    }

    public Map<String,Object> getAttachments() {
        return attachments;
    }

    public void setAttachments(Map<String,Object> attachments) {
        this.attachments = attachments;
    }

}
