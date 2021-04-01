/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.google.common.io.BaseEncoding;
import customException.FailToLoadInitInboxException;
import static gmailApi.SendMailProcess.createMessageWithEmail;
import static gmailApi.SendMailProcess.sendMessage;
import static gmailApi.XuLyFile.checkExistFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Admin
 */
public class MessageProcess {

    /**
     * lấy danh sách mail tu label da chon
     *
     * @param service
     * @param num số lượng mail muốn load trong 1 lần
     * @param userId
     * @param loadFromLabel : nhung label dc su dung de load message
     * @return tra ve danh sach message
     * @throws IOException
     * @throws MessagingException
     */
    public static List<Message> getListMail(List<String> loadFromLabel, int num) throws IOException, MessagingException {
	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	//list response cua mail list
	ListMessagesResponse response;
	response = service.users().messages().list(userId).setLabelIds(loadFromLabel).setMaxResults(Long.valueOf(num)).execute();
	//doc tung mail
	List<Message> messages = new ArrayList<>();
	messages.addAll(response.getMessages());
//	while (response.getMessages() != null) {
//	    messages.addAll(response.getMessages());
//	    if (response.getNextPageToken() != null) {
//		String pageToken = response.getNextPageToken();
//		response = service.users().messages().list(userId).setLabelIds(loadFromLabel).setPageToken(pageToken).execute();
//	    } else {
//		break;
//	    }
//	}

//	for (Message message : messages) {
//	    System.out.println(message.toPrettyString());
//	    
//	    getMessageById(service, userId, message.getId());
//	}
	return messages;
    }

    public Map<String, String> headerMessageParser(List<MessagePartHeader> headers) {
	Map<String, String> myMap = new HashMap<>();
	for (Object i : headers.toArray()) {
	    String data = i.toString().replace("\\\"", "");
	    String[] parts = data.split("\"");
	    myMap.put(parts[3], parts[7]);
	}
	return myMap;
    }

    /**
     * lấy header của 1 message thông qua id
     *
     * @param messageId
     * @return 1 MessageObject
     */
    public static MessageObject getQuickHeaderInfo(String messageId) {
	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	MessageObject msg = new MessageObject();

	Message message;
	try {
	    message = service.users().messages().get(userId, messageId).setFormat("full").execute();
	    MessagePart payload = message.getPayload();
	    List<MessagePartHeader> headers = payload.getHeaders();
	    Map<String, String> myMap = new HashMap<>();
	    for (Object i : headers.toArray()) {
		String data = i.toString().replace("\\\"", "");
		String[] parts = data.split("\"");
		myMap.put(parts[3], parts[7]);
	    }
	    msg.from = myMap.get("From");
	    msg.date = myMap.get("Date");
	    msg.to = myMap.get("To");
	    msg.id = messageId;
	    msg.unread = checkUnread(message);
	} catch (IOException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
	return msg;
    }

    /**
     * đổ dữ liệu phần header cho Object
     *
     * @param msgOb
     * @param message
     */
    public static void loadHeaderForMessageOb(MessageObject msgOb, Message message) {
	MessagePart payload = message.getPayload();
	List<MessagePartHeader> headers = payload.getHeaders();
	Map<String, String> myMap = new HashMap<>();
	for (Object i : headers.toArray()) {
	    String data = i.toString().replace("\\\"", "");
	    String[] parts = data.split("\"");
	    myMap.put(parts[3], parts[7]);
	}
	msgOb.from = myMap.get("From");
	msgOb.subject = myMap.get("Subject");
	msgOb.date = myMap.get("Date");
	msgOb.cc = myMap.get("Cc");
	msgOb.to = myMap.get("To");
	msgOb.messageID = myMap.get("Message-ID");
	msgOb.references = myMap.get("References");
    }

    /**
     * đổ dữ liệu phần body cho Object
     *
     * @param msgOb
     * @param parts
     */
    public static void loadBodyForMessageOb(MessageObject msgOb, List<MessagePart> parts) {
	for (MessagePart part : parts) {
	    String mimeType = part.getMimeType();
	    if (mimeType.equals("multipart/related")) {
		loadBodyForMessageOb(msgOb, part.getParts());
	    }
	    if (mimeType.equals("application/pdf")) {
		MessagePartBody body = part.getBody();
		String attId = body.getAttachmentId();
		String filename = part.getFilename();
		msgOb.listFile.put(filename, attId);
	    }
	    if (mimeType.equals("image/png")) {
		MessagePartBody body = part.getBody();
		String attId = body.getAttachmentId();
		String filename = part.getFilename();
		msgOb.listFile.put(filename, attId);
	    }
	    if (mimeType.equals("audio/mp3")) {
		MessagePartBody body = part.getBody();
		String attId = body.getAttachmentId();
		String filename = part.getFilename();
		msgOb.listFile.put(filename, attId);
	    }
	    if (mimeType.equals("text/plain")) {
		if (!part.getFilename().isEmpty()) {
		    MessagePartBody body = part.getBody();
		    String attId = body.getAttachmentId();
		    String filename = part.getFilename();
		    msgOb.listFile.put(filename, attId);
		} else {
		    String data = part.getBody().getData();
		    Base64 base64Url = new Base64(true);
		    byte[] emailBytes = Base64.decodeBase64(data);
		    String text = new String(emailBytes);
		    msgOb.mainText = text;
		}
	    }
	    String fileName = part.getFilename();
	    if (!fileName.isEmpty()) {
		MessagePartBody body = part.getBody();
		String attId = body.getAttachmentId();
		msgOb.listFile.put(fileName, attId);
	    }
	}
    }

    /**
     * load từ Message(api.gmail) sang msgOb, nói chung là đổ dữ liều vào object
     *
     * @param msgOb
     */
    public static void loadMessage(MessageObject msgOb) {
	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	Message message;
	try {
	    message = service.users().messages().get(userId, msgOb.id).setFormat("full").execute();
	    List<MessagePart> parts = new ArrayList<MessagePart>();
	    System.out.println("parts: " + parts.isEmpty());
	    loadHeaderForMessageOb(msgOb, message);
	    parts = message.getPayload().getParts();
	    
	    if (parts != null) {
		loadBodyForMessageOb(msgOb, parts);
	    } else {
		// nếu cái parts không có, tức chỉ có text , chính cái Parts này, chính là cái text
		String data = message.getPayload().getBody().getData();
		Base64 base64Url = new Base64(true);
		byte[] emailBytes = Base64.decodeBase64(data);
		String text = new String(emailBytes);
		msgOb.mainText = text;
	    }
	} catch (IOException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    /**
     * tải xuống những file đính kèm
     *
     * @param service
     * @param userId
     * @param messageId
     * @param attId
     * @param filename
     * @throws IOException
     */
    public static void downloadAttach(String messageId, String attId, String filename) throws IOException {
	MessagePartBody attachPart = GlobalVariable.getService().users().messages().attachments().get(GlobalVariable.userId, messageId, attId).execute();

	Base64 base64Url = new Base64(true);
	byte[] fileByteArray = base64Url.decodeBase64(attachPart.getData());
	FileOutputStream fileOutFile
		= new FileOutputStream("" + filename);
	fileOutFile.write(fileByteArray);
	fileOutFile.close();
    }

    /**
     * phân tích phần body của mail, nhận diện các tệp kèm theo
     *
     * @param service
     * @param userId
     * @param messageId
     * @param msgP
     */
    public static void parseBodyParts(Gmail service, String userId, String messageId, List<MessagePart> msgP) {
	for (MessagePart m : msgP) {
	    System.out.println("Part id:" + m.getPartId());
	    String mimeType = m.getMimeType();
	    System.out.println("Mimetype: " + mimeType);
	    if (mimeType.equals("multipart/related")) {
		parseBodyParts(service, userId, messageId, m.getParts());
	    }
	    if (mimeType.equals("application/pdf")) {
		String filename = m.getFilename();
		MessagePartBody body = m.getBody();
		String attId = body.getAttachmentId();
		System.out.println("New PDF detect: " + filename);
		System.out.println("Downloading file...");
		try {
		    downloadAttach(messageId, attId, filename);
		} catch (IOException ex) {
		    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(body);
	    }
	    if (mimeType.equals("image/png")) {
		System.out.println("New Image detect: " + m.getFilename());
		MessagePartBody body = m.getBody();
		String attId = body.getAttachmentId();
		System.out.println(attId);
		System.out.println("Downloading picture...");
		try {
		    downloadAttach(messageId, attId, m.getFilename());
		} catch (IOException ex) {
		    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    if (mimeType.equals("audio/mp3")) {
		System.out.println("New audio detect: " + m.getFilename());
		MessagePartBody body = m.getBody();
		String attId = body.getAttachmentId();
		System.out.println(attId);
		System.out.println("Downloading audio...");
		try {
		    downloadAttach(messageId, attId, m.getFilename());
		} catch (IOException ex) {
		    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    if (mimeType.equals("text/plain")) {
		System.out.println("Text/plain detect !");
		if (!m.getFilename().isEmpty()) {
		    System.out.println("New file detect: " + m.getFilename());
//		    downloadAttach(service, userId, messageId, userId, mimeType);
		} else {
		    String data = m.getBody().getData();
		    Base64 base64Url = new Base64(true);
		    byte[] emailBytes = Base64.decodeBase64(data);
		    String text = new String(emailBytes);
		    System.out.println(text);
		}
	    }
	    // testing
	    String fileName = m.getFilename();
	    if (!fileName.isEmpty()) {
		System.out.println("New file: " + fileName);
	    }
//            System.out.println(m.getBody().toString());
//            List<MessagePart> parts1 = m.getParts();
//            System.out.println(parts1);
	}
    }

    /**
     * lấy ra 1 mail
     *
     * @param service
     * @param userId
     * @param messageId
     * @return Message
     * @throws IOException
     * @throws MessagingException
     */
    public static Message getMessageById(Gmail service, String userId, String messageId) throws IOException, MessagingException {
	Message message = service.users().messages().get(userId, messageId).setFormat("full").execute();
	return message;
    }

    /**
     * Thêm,xoá lables vào 1 message
     *
     * @param labelsToAdd
     * @param labelsToRemove
     * @param messageId
     * @throws IOException
     */
    public static void modifyLabelsToMessage(List<String> labelsToAdd, List<String> labelsToRemove, String messageId) throws IOException {
	Gmail service = GlobalVariable.getService();
	ModifyMessageRequest mods;
	if (labelsToAdd.isEmpty()) {
	    mods = new ModifyMessageRequest().setRemoveLabelIds(labelsToRemove);
	} else if (labelsToRemove.isEmpty()) {
	    mods = new ModifyMessageRequest().setAddLabelIds(labelsToAdd);
	} else {
	    mods = new ModifyMessageRequest().setAddLabelIds(labelsToAdd)
		    .setRemoveLabelIds(labelsToRemove);
	}

	Message message = service.users().messages().modify(GlobalVariable.userId, messageId, mods).execute();

	System.out.println("Message id: " + message.getId());
	System.out.println(message.toPrettyString());
    }

    /**
     * lấy về địa chỉ của thuộc tính To: trong headers gửi đến ai
     *
     * @param messageHeader
     * @return String
     */
    public static String getTo(List<MessagePartHeader> messageHeader) {
	String toAdd = null;
	for (MessagePartHeader e : messageHeader) {
	    if (e.getName().equals("To")) {
		toAdd = e.getValue();
	    }
	}
	return toAdd;
    }

    /**
     * lấy về thuộc tính From: trong headers gửi từ ai
     *
     * @param messageHeader
     * @return String
     */
    public static String getFrom(List<MessagePartHeader> messageHeader) {
	String fromAdd = null;
	for (MessagePartHeader e : messageHeader) {
	    if (e.getName().equals("From")) {
		fromAdd = e.getValue();
	    }
	}
	return fromAdd;
    }

    /**
     * Lấy về thuộc tính Date trong headers Nhận vào ngày nào
     *
     * @param messageHeader
     * @return String
     */
    public static String getDate(List<MessagePartHeader> messageHeader) {
	String dateTo = null;
	for (MessagePartHeader e : messageHeader) {
	    if (e.getName().equals("Date")) {
		dateTo = e.getValue();
	    }
	}
	return dateTo;
    }

    /**
     * Lấy về Message-ID:
     *
     * @param messageHeader
     * @return String
     */
    public static String getMessageId(List<MessagePartHeader> messageHeader) {
	String messageId = null;
	for (MessagePartHeader e : messageHeader) {
	    if (e.getName().equals("Message-ID")) {
		messageId = e.getValue();
	    }
	}
	return messageId;
    }

    /**
     * lấy về tiêu đề của message
     *
     * @param messageHeader
     * @return String
     */
    public static String getSubject(List<MessagePartHeader> messageHeader) {
	String subject = null;
	for (MessagePartHeader e : messageHeader) {
	    if (e.getName().equals("Subject")) {
		subject = e.getValue();
	    }
	}
	return subject;
    }

    /**
     * lấy về thuộc tính đặc biệt References chỉ dùng trong những message RE:
     *
     * @param messageHeader
     * @return String
     */
    public static String getReferences(List<MessagePartHeader> messageHeader) {
	String references = null;
	for (MessagePartHeader e : messageHeader) {
	    if (e.getName().equals("References")) {
		references = e.getValue();
	    }
	}
	return references;
    }

    /**
     * lấy về thuộc tính đặc biệt In-reply-to: chỉ có trong những message RE:
     *
     * @param messageHeader
     * @return String
     */
    public static String getInReplyTo(List<MessagePartHeader> messageHeader) {
	String inReplyTo = null;
	for (MessagePartHeader e : messageHeader) {
	    if (e.getName().equals("In-Reply-To")) {
		inReplyTo = e.getValue();
	    }
	}
	return inReplyTo;
    }

    /**
     * in ra header của messsage chỉ sử dụng cho mục đích test
     *
     * @param message
     */
    public static void printHeader(Message message) {
	MessagePart payload = message.getPayload();
	List<MessagePartHeader> headers = payload.getHeaders();
	for (MessagePartHeader messHeadPart : headers) {
	    if (messHeadPart.getName().equals("From")) {
		System.out.println(messHeadPart.getValue());
	    }
	    if (messHeadPart.getName().equals("Subject")) {
		System.out.println(messHeadPart.getValue());
	    }
	}
    }

    /**
     * xoá hoàn toàn mail này, không quay lại được
     *
     * @param messageId
     * @throws IOException
     */
    public static void permantlyDeleteMessage(String messageId) throws IOException {
	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	service.users().messages().delete(userId, messageId).execute();

    }

    /**
     * đưa vào labels rác, có thể quay lại
     *
     * @param messageId
     * @throws IOException
     */
    public static void moveToTrash(String messageId) throws IOException {
	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	service.users().messages().trash(userId, messageId).execute();
    }

    /**
     * lấy mail ra khỏi thư mục rác
     *
     * @param messageId
     * @throws IOException
     */
    public static void unTrash(String messageId) throws IOException {
	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	service.users().messages().untrash(userId, messageId).execute();
    }

    /**
     * tìm kiếm mail theo query
     *
     * @param query
     * @throws IOException
     * @throws MessagingException
     */
    public static List<MessageObject> search(String query) throws IOException, MessagingException {
	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();

	List<Message> messages;
	messages = new ArrayList<>();
	while (response.getMessages() != null) {
	    messages.addAll(response.getMessages());
	    if (response.getNextPageToken() != null) {
		String pageToken = response.getNextPageToken();
		response = service.users().messages().list(userId).setQ(query)
			.setPageToken(pageToken).execute();
	    } else {
		break;
	    }
	}
	List<MessageObject> listMessages = new ArrayList<>();
	for (Message msg : messages) {
	    MessageObject newMessOb = MessageProcess.getQuickHeaderInfo(msg.getId());
	    listMessages.add(newMessOb); //MessageProcess.parseHeaderMail(msg.getId())
	}//	    Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
	return listMessages;
    }

    /**
     * save mail đang đọc
     *
     * @param msgOb
     * @param pathDir
     */
    public static void downloadMail(MessageObject msgOb, String pathDir) {
	FileOutputStream fout = null;
	ObjectOutputStream o = null;
	try {

	    fout = new FileOutputStream(new File(pathDir + msgOb.id + ".msgOb"));

	    o = new ObjectOutputStream(fout);
	    o.writeObject(msgOb);
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		if (o != null) {
		    o.close();
		}
		if (fout != null) {
		    fout.close();
		}
	    } catch (IOException ex) {
		Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    /**
     * load lại mail từ file
     *
     * @param fileName
     * @return MessageObject
     */
    public static MessageObject readSaveMail(String fileName) {
	FileInputStream fin = null;
	ObjectInputStream in = null;
	MessageObject msgOb = null;
	try {
	    fin = new FileInputStream(fileName);
	    in = new ObjectInputStream(fin);

	    msgOb = (MessageObject) in.readObject();
	} catch (FileNotFoundException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	} catch (IOException | ClassNotFoundException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	} finally {
	    try {
		if (in != null) {
		    in.close();
		}
		if (fin != null) {
		    fin.close();
		}
	    } catch (IOException ex) {
		Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	return msgOb;
    }

    /**
     * kiểm tra cái mail này đã được đọc chưa
     *
     * @param message
     * @return true if unread, false if read
     */
    public static boolean checkUnread(Message message) {
	//	    Message message = GlobalVariable.getService().users().messages().get(GlobalVariable.userId, messageId).setFormat("full").execute();
	List<String> labelIds = message.getLabelIds();
	if (labelIds.stream().anyMatch((label) -> (label.equals("UNREAD")))) {
	    return true;
	}
	return false;
    }

    /**
     * kiểm tra cái mail này có nhãn quan trọng hay khôgn
     *
     * @param message
     * @return true if starred, false if no starred
     */
    public static boolean checkImportant(Message message) {
	//	    Message message = GlobalVariable.getService().users().messages().get(GlobalVariable.userId, messageId).setFormat("full").execute();
	List<String> labelIds = message.getLabelIds();
	if (labelIds.stream().anyMatch((label) -> (label.equals("STARRED")))) {
	    return true;
	}
	return false;
    }

    /**
     * reply a message
     *
     * @param msgOb
     * @param listFilePath
     * @param replyMessage
     * @throws IOException
     */
    public static void reply(MessageObject msgOb, String replyMessage, List<String> listFilePath) throws IOException {
	// must get from old mail
	String from;
	String subject;
	String newSubject = "Re: "; //re = reply
	String oldReferences;
	String messageID;

	Gmail service = GlobalVariable.getService();
	String userId = GlobalVariable.userId;
	// lấy thông tin của mail cũ
	from = msgOb.from;
	subject = msgOb.subject;
	oldReferences = msgOb.references;
	messageID = msgOb.messageID;
	// tạo mail mới
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	MimeMessage mimeMessage = new MimeMessage(session);
	InternetAddress[] listto = new InternetAddress[1];
	InternetAddress[] listfrom = new InternetAddress[1];
	try {
	    listto[0] = new InternetAddress(from);
	    mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, listto);
	    mimeMessage.setFrom(new InternetAddress(userId));
	    mimeMessage.setSubject(newSubject + subject, "utf-8");
	    mimeMessage.setHeader("References", oldReferences + " " + messageID);
	    mimeMessage.setHeader("In-Reply-To", messageID);
	    if (listFilePath == null || listFilePath.isEmpty()) {
		mimeMessage.setText(replyMessage);
	    } else {
//		SendMailProcess.prepareMailAttachment(mimeMessage, listFilePath, replyMessage);
		// create the multi message for attachment
		Multipart multiPart = new MimeMultipart();
		try {

		    // create the body part 
		    BodyPart msgBodyPart;
		    msgBodyPart = new MimeBodyPart();

		    // add the body message
		    msgBodyPart.setText(replyMessage);

		    // add the first multi part (text)  
		    multiPart.addBodyPart(msgBodyPart);

		    // add the second part (attachment)
		    for (int i = 0; i < listFilePath.size(); i++) {
//		addAttachment(multiPart, listFilePath.get(i));
			try {
			    BodyPart attachBodyPart = new MimeBodyPart();
			    DataSource source = new FileDataSource(listFilePath.get(i));
			    DataHandler dh = new DataHandler(source);
			    attachBodyPart.setDataHandler(dh);
			    attachBodyPart.setFileName(listFilePath.get(i));
			    multiPart.addBodyPart(attachBodyPart);
			} catch (MessagingException e) {
			    System.out.println(e.toString());
			    System.out.println("Failed to add file: " + listFilePath.get(i));
			}
		    }

		    // set msg full part(text + attachment) 
		    mimeMessage.setContent(multiPart);
		} catch (MessagingException e) {
		    System.out.println(e.toString());
		    System.out.println("There're something wrong with prepare mail with attachment !");
		    multiPart = null;
		}
	    }
	    SendMailProcess.sendMessage(service, userId, mimeMessage);
	} catch (AddressException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	} catch (MessagingException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    /**
     * Forward message
     * @param msgOb
     * @param listTo
     * @param forwardMessage
     * @param listFilePath
     * @throws IOException
     * @throws MessagingException
     */
    public static void forward2(MessageObject msgOb, String[] listTo, String forwardMessage, List<String> listFilePath) throws IOException, MessagingException {
	Message message = GlobalVariable.getService().users().messages().get(GlobalVariable.userId, msgOb.id).setFormat("raw").execute();

	Base64 base64Url = new Base64(true);
	byte[] emailBytes = base64Url.decodeBase64(message.getRaw());

	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);

	MimeMessage email = new MimeMessage(session, new ByteArrayInputStream(emailBytes));
//	for(Address a :email.getAllRecipients()){
//	    System.out.println(a);
//	}
//	Enumeration allHeaderLines = email.getAllHeaderLines();
//	while (allHeaderLines.hasMoreElements()) {
//	    System.out.println(allHeaderLines.nextElement().toString());
//	}
//	System.out.println(email.getDescription()); // ko có gì
//	System.out.println(email.getContentID()); // ko cos gif
//	System.out.println(email.getFileName());
//	System.out.println(email.getHeader("From")[0]);
	InternetAddress[] listto = new InternetAddress[listTo.length];
	for (int i = 0; i < listTo.length; i++) {
	    try {
		listto[i] = new InternetAddress(listTo[i]);
	    } catch (AddressException ex) {
		Logger.getLogger(SendMailProcess.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
//	email.setRecipients(javax.mail.Message.RecipientType.TO, listto);
	//remove header To cũ
	email.removeHeader("To");
	email.removeHeader("Cc");
	email.removeHeader("Bcc");
	// set cái header To mới
	email.setHeader("To", listTo[0]);
	String standardMessage = "------------Forwarded message-------------\n"
		+ "From: " + msgOb.from + "\n"
		+ "Date: " + msgOb.date + "\n"
		+ "Subject: " + msgOb.subject + "\n"
		+ "To: " + msgOb.to + "\n";
//	Object content = email.getContent();
//	System.out.println(content.toString());
//	Enumeration allHeaderLines2 = email.getAllHeaderLines();
//	while (allHeaderLines2.hasMoreElements()) {
//	    System.out.println(allHeaderLines2.nextElement().toString());
//	}
	SendMailProcess.sendMessage(GlobalVariable.getService(), GlobalVariable.userId, email);
    }
    
    public static void autoDownload(MessageObject msgOb){
	try {
	    String fileName = msgOb.id+".msgOb";
	    Message msg = getMessageById(GlobalVariable.getService(), GlobalVariable.userId, msgOb.id);
	    if(checkImportant(msg) && !checkExistFile(fileName)){
		downloadMail(msgOb, GlobalVariable.rootDirectorySaveMail);
		System.out.println("Success download mail !");
	    }
	} catch (IOException | MessagingException ex) {
	    Logger.getLogger(MessageProcess.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
}
