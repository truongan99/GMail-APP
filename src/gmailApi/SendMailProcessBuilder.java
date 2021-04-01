/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import com.google.api.services.gmail.Gmail;
import java.util.List;
import javax.mail.internet.MimeMessage;

/**
 * builder cho sendMailProcess 
 * @author Admin
 */
public class SendMailProcessBuilder {
    String[] toMail;
    String[] cc;
    String[] bcc;
    String subject;
    String body;
    List<String> fileName;

    public SendMailProcessBuilder setToMail(String[] toMail) {
	this.toMail = toMail;
	return this;
    }

    public SendMailProcessBuilder setCc(String[] cc) {
	this.cc = cc;
	return this;
    }

    public SendMailProcessBuilder setBcc(String[] bcc) {
	this.bcc = bcc;
	return this;
    }

    public SendMailProcessBuilder setSubject(String subject) {
	this.subject = subject;
	return this;
    }

    public SendMailProcessBuilder setBody(String body) {
	this.body = body;
	return this;
    }

    public SendMailProcessBuilder setFileName(List<String> fileName) {
	this.fileName = fileName;
	return this;
    }
    
    public SendMailProcess getSendMailProcess(){
	return new SendMailProcess(toMail, cc, bcc, subject, body,fileName);
    }
}
