/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.google.common.collect.HashBiMap;
import static gmailApi.MessageProcess.getListMail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public class GmailQuickstart {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/tokens";

    /**
     * Global instance of the scopes required by this quickstart. If modifying
     * these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
	// Load client secrets.
	InputStream in = GmailQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	if (in == null) {
	    throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	}
	GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	// Build flow and trigger user authorization request.
	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
		.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
		.setAccessType("offline")
		.build();
	LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

//        /**
//     * Create a MimeMessage using the parameters provided.
//     *
//     * @param to email address of the receiver
//     * @param from email address of the sender, the mailbox account
//     * @param subject subject of the email
//     * @param bodyText body text of the email
//     * @return the MimeMessage to be used to send email
//     * @throws MessagingException
//     */
//    public static MimeMessage createEmail(String to,
//                                          String from,
//                                          String subject,
//                                          String bodyText)
//            throws MessagingException {
//        Properties props = new Properties();
//        Session session = Session.getDefaultInstance(props, null);
//
//        MimeMessage email = new MimeMessage(session);
//
//        email.setFrom(new InternetAddress(from));
//        email.addRecipient(javax.mail.Message.RecipientType.TO,
//                new InternetAddress(to));
//        email.setSubject(subject);
//        email.setText(bodyText);
//        return email;
//    }
    public static void main(String... args) throws IOException, GeneralSecurityException {
	// Build a new authorized API client service.
	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

	Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
		.setApplicationName(APPLICATION_NAME)
		.build();
	GlobalVariable.setService(service);
	// Print the labels in the user's account.
	String user = "testdoan123456@gmail.com";
	GlobalVariable.userId = user;
	String subject = "test send mail with header, bbc, cc, atttachment after fixed code by Gmail API";
	String body = "this is the second mail sent with attachment file";
	String[] toMail = {"n17dcat061@student.ptithcm.edu.vn", "thanhtuan9906@gmail.com", "h127qw81naskdb@gmail.com"};
	String[] cc = {"n17dcat061@student.ptithcm.edu.vn", "thanhtuan9906@gmail.com"};
	String[] bcc = {"n17dcat061@student.ptithcm.edu.vn", "thanhtuan9906@gmail.com"};
	String[] fileName = {"C:\\Users\\Admin\\Documents\\flag_buf1.txt"};

	//        Gmail.Users.History.List history = service.users().history().list(user);
////        System.out.println(history);
//        ListLabelsResponse listResponse = service.users().labels().list(user).execute();
////        fetching mail list
//
//        List<Label> labels = listResponse.getLabels();
//        if (labels.isEmpty()) {
//            System.out.println("No labels found.");
//        } else {
//            System.out.println("Labels:");
//            for (Label label : labels) {
//                System.out.printf("- %s\n", label.getName());
//            }
//    }
//	try {
//	    MessageProcess.search("adrian");
//	    getListMail(service, user);
//	    Message message = MessageProcess.getMessageById(service, user, "171809dd2f97dd6a");
//	    MessageProcess.parseBodyParts(service, user, "171809dd2f97dd6a", );
//	    MessagePartBody body1 = message.getPayload().getBody();
//	    System.out.println(body1.toPrettyString());
//	    System.out.println(MessageProcess.getTo(message.getPayload().getHeaders()));
//	} catch (MessagingException ex) {
//	    Logger.getLogger(GmailQuickstart.class.getName()).log(Level.SEVERE, null, ex);
//	}
//	MessageProcess.reply("1718c276288b984e", "day la mail thu 3");
//	System.out.println(XuLyFile.listAllFileInDirectory(GlobalVariable.rootDirectorySaveTokens));
//	try {
//	    Message messageById = MessageProcess.getMessageById(service, user, "171748240b8f6f6f");
//	    System.out.println(messageById.getLabelIds());
//	    MessagePart payload = messageById.getPayload();
//	    List<MessagePartHeader> headers = payload.getHeaders();
//	    MessagePartBody body1 = payload.getBody();
////	    ObjectMapper objMapper = new ObjectMapper();
//	    Map<String, String> myMap = new HashMap<>();
//	    for (Object i : headers.toArray()) {
//		String data = i.toString().replace("\\\"", "");
////		System.out.println(i);
//		String[] parts = data.split("\"");
//		for(String m : parts){
//		    System.out.println(m);
//		}
//		System.out.println(parts[3] + ":" +parts[7]);
//		String key = data.split(":")[1].split(",")[0];
//		System.out.println("Phan tu key: "+key);
//		
//		String value = data.split(":")[2];
//		System.out.println("Phan tu value: "+value);
//		myMap.put(parts[3], parts[7]);
//	    }
//	    System.out.println(myMap.get("From"));
//	    System.out.println(myMap.get("To"));
//	    System.out.println(myMap.get("Subject"));
//	    System.out.println(myMap.get("Date"));
//	    System.out.println(payload.getBody());
//	    for (Map.Entry m : myMap.entrySet()) {
//		System.out.println(m.getKey() + " " + m.getValue());
//	    }
//	    System.out.println(myMap);
//	    Map jsonData;
//	    byte[] jsonData = payload.getHeaders().toArray()[0].toString().getBytes();
//	    System.out.println(jsonData);
//	    System.out.println(jsonData[0]);

//	    myMap = objMapper.readValue(jsonData,HashMap.class);
//	    System.out.println(myMap);
//	    System.out.println(payload.getHeaders());
//	} catch (MessagingException ex) {
//	    Logger.getLogger(GmailQuickstart.class.getName()).log(Level.SEVERE, null, ex);
//	}
//	List<String> labelAdd = new ArrayList<>();
//	labelAdd.add("TRASH");
//	List<String> labelremove = new ArrayList<>();
//	labelAdd.add("TRASH");
//	MessageProcess.modifyLabelsToMessage(labelAdd, labelremove, "171748240b8f6f6f");
    MessageObject msgOb = new MessageObject();
    msgOb.id = "171748240b8f6f6f";
    String[] listo = {"n17dcat061@student.ptithcm.edu.vn"};
//    MessageProcess.loadMessage(msgOb);
    List<String> listfile = null;
	try {
	    MessageProcess.forward2(msgOb, listo,body, listfile);
	} catch (MessagingException ex) {
	    Logger.getLogger(GmailQuickstart.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }
}
//1712445165e55410 : test RE
//171809dd2f97dd6a : re re mail
// 1718c276288b984e: test reply
// 171748240b8f6f6f: nhieu loai file
