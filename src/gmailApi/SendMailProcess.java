/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class SendMailProcess {

    /**
     * doc cho ham sendMail
     *
     * @param service: mail session
     * @param toMail : String[] danh sach mail muon gui toi
     * @param cc : String[]danh sach cc
     * @param bcc: String[] danh sach bcc
     * @param subject String tieu de cua mail
     * @param body String noi dung cua mail
     * @param fileName: String[] danh sach nhung file can attach
     */
    Gmail service = GlobalVariable.getService();
    String userId = GlobalVariable.userId;
    String[] toMail;
    String[] cc;
    String[] bcc;
    String subject;
    String body;
//    String in_reply_to;
//    String reference;
    List<String> fileName;
    MimeMessage msg;

    public SendMailProcess(String[] toMail, String[] cc, String[] bcc, String subject, String body) {
	this.toMail = toMail;
	this.cc = cc;
	this.bcc = bcc;
	this.subject = subject;
	this.body = body;
    }

    public SendMailProcess(String[] toMail, String[] cc, String[] bcc, String subject, String body, List<String> fileName) {
	this.toMail = toMail;
	this.cc = cc;
	this.bcc = bcc;
	this.subject = subject;
	this.body = body;
	this.fileName = fileName;
    }
//    public SendMailProcess(String[] toMail, String[] cc, String[] bcc, String subject, String body, List<String> fileName, String in_reply_to, String reference) {
//	this.toMail = toMail;
//	this.cc = cc;
//	this.bcc = bcc;
//	this.subject = subject;
//	this.body = body;
//	this.fileName = fileName;
//	this.reference = reference;
//	this.in_reply_to = in_reply_to;
//    }

    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static com.google.api.services.gmail.model.Message createMessageWithEmail(MimeMessage emailContent)
	    throws MessagingException, IOException {
	ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	emailContent.writeTo(buffer);
	byte[] bytes = buffer.toByteArray();
	String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
	com.google.api.services.gmail.model.Message message = new com.google.api.services.gmail.model.Message();
	message.setRaw(encodedEmail);
	return message;
    }

    // chuẩn bị phần mail là dạng text
    private void prepareTextMail() {
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	msg = new MimeMessage(session);
	try {
	    // set mail header
	    msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	    msg.addHeader("format", "flowed");
	    msg.addHeader("Content-Transfer-Encoding", "8bit");

	    // set cc
	    if (cc != null) {
		InternetAddress[] listcc = new InternetAddress[cc.length];
		for (int i = 0; i < cc.length; i++) {
		    listcc[i] = new InternetAddress(cc[i]);
		}
		msg.setRecipients(Message.RecipientType.CC, listcc);
	    }
	    // set bcc
	    if (bcc != null) {

		InternetAddress[] listbcc = new InternetAddress[bcc.length];
		for (int i = 0; i < bcc.length; i++) {
		    listbcc[i] = new InternetAddress(bcc[i]);
		}
		msg.setRecipients(Message.RecipientType.BCC, listbcc);
	    }
	    // set to mail
	    if (toMail != null) {
		InternetAddress[] listto = new InternetAddress[toMail.length];
		for (int i = 0; i < toMail.length; i++) {
		    listto[i] = new InternetAddress(toMail[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, listto);
	    }

	    // set subject of mail
	    msg.setSubject(subject);

	    // body
	    msg.setText(body, "UTF-8");
	} catch (MessagingException e) {
	    System.out.println(e.toString());
	    System.out.println("There's something wrong with prepare text mail!");
	    msg = null;
	}
    }

    // thêm file attachment
    private static void addAttachment(Multipart multiPart, String file) {
	try {
	    BodyPart attachBodyPart = new MimeBodyPart();
	    DataSource source = new FileDataSource(file);
	    DataHandler dh = new DataHandler(source);
	    attachBodyPart.setDataHandler(dh);
	    attachBodyPart.setFileName(file);
	    multiPart.addBodyPart(attachBodyPart);
	} catch (MessagingException e) {
	    System.out.println(e.toString());
	    System.out.println("Failed to add file: " + file);
	}
    }

    // thêm vào message phần attachment
    private void prepareMailAttachment(List<String> fileName, String body) {
	// create the multi message for attachment
	Multipart multiPart = new MimeMultipart();
	try {

	    // create the body part 
	    BodyPart msgBodyPart;
	    msgBodyPart = new MimeBodyPart();

	    // add the body message
	    msgBodyPart.setText(body);

	    // add the first multi part (text)  
	    multiPart.addBodyPart(msgBodyPart);

	    // add the second part (attachment)
	    for (int i = 0; i < fileName.size(); i++) {
		addAttachment(multiPart, fileName.get(i));
	    }

	    // set msg full part(text + attachment) 
	    msg.setContent(multiPart);
	} catch (MessagingException e) {
	    System.out.println(e.toString());
	    System.out.println("There're something wrong with prepare mail with attachment !");
	    multiPart = null;
	}
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param service Authorized Gmail API instance.
     * @param userId User's email address. The special value "me" can be used to
     * indicate the authenticated user.
     * @param emailContent Email to be sent.
     * @throws MessagingException
     * @throws IOException
     */
    public static void sendMessage(Gmail service,
	    String userId,
	    MimeMessage emailContent)
	    throws MessagingException, IOException {
	//mimeMessage -> message (gmail api)
	com.google.api.services.gmail.model.Message message = createMessageWithEmail(emailContent);
	service.users().messages().send(userId, message).execute();
//	chỉ dành cho khi test chức năng
//	System.out.println("Message id: " + message.getId());
//	System.out.println(message.toPrettyString());
//	System.out.println("Send Succesful !");
//        return message;
    }

    /**
     * gửi mail
     *
     * @throws MessagingException
     * @throws IOException
     */
    public void setUpAndSend() throws MessagingException, IOException {
	if (fileName == null) {
	    // just Text mail
	    prepareTextMail();
	} else {
	    prepareTextMail();
	    prepareMailAttachment(fileName,body);
	}
	sendMessage(service, userId, msg);
    }
    
        
    /**
     * taọ mail như là 1 thư nháp, được tìm thấy trong hộp thư nháp
     *
     * @param service
     * @param userId
     */
    public void createDraft(Gmail service,
	    String userId) {
	if (fileName == null) {
	    // just Text mail
	    prepareTextMail();
	} else {
	    prepareTextMail();
	    prepareMailAttachment(fileName,body);
	}
	try {
	    com.google.api.services.gmail.model.Message message = createMessageWithEmail(this.msg);
	    Draft draft = new Draft();
	    draft.setMessage(message);
	    service.users().drafts().create(userId, draft).execute();
	} catch (IOException | MessagingException ex) {
	    Logger.getLogger(SendMailProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public boolean sendDraft(Draft draft) {
	if (fileName == null) {
	    // just Text mail
	    prepareTextMail();
	} else {
	    prepareTextMail();
	    prepareMailAttachment(fileName,body);
	}
	com.google.api.services.gmail.model.Message message;

	try {
	    message = createMessageWithEmail(this.msg);
	    draft.setMessage(message);
	    GlobalVariable.getService().users().drafts().send(userId, draft).execute();
	    return true;
	} catch (IOException | MessagingException ex) {
	    Logger.getLogger(SendMailProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
	return false;

    }
}
