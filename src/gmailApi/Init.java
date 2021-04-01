/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import com.google.api.services.gmail.model.Message;
import customException.FailToLoadInitInboxException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 * load các dữ liệu khởi đầu cho chương trình
 *
 * @author Admin
 */
public class Init {

    /**
     * Hàm khởi tạo những userId đã lưu để load vào combo box
     *
     * @return List<string> chứa userId của những tokens đã lưu lại
     */
    public static List<String> initSaveTokens() {
	List<String> savedTokens = new ArrayList<>();
	File rootDirectory = new File(GlobalVariable.rootDirectorySaveTokens);
	File[] tokens = rootDirectory.listFiles();
	if (tokens != null) {
	    for (File eachToken : tokens) {
		savedTokens.add(eachToken.getName() + "@gmail.com");
	    }
	}
	return savedTokens;
    }

    /**
     * khởi tạo 1 list các message trong inbox khi chương trình vừa mở lên
     *
     * @return
     * @throws FailToLoadInitInboxException
     */
    public static List<MessageObject> initInboxList(String label) throws FailToLoadInitInboxException {
	List<String> loadFromLabel = new ArrayList<>();
	loadFromLabel.add(label);
	List<MessageObject> listMessages = new ArrayList<>();
	try {
	    List<Message> initListMessages = MessageProcess.getListMail(loadFromLabel,13);
	    for (Message msg : initListMessages) {
		MessageObject newMessOb = new MessageObject();
		newMessOb.id = msg.getId();
		newMessOb.from = MessageProcess.getFrom(MessageProcess.getMessageById(GlobalVariable.getService(), GlobalVariable.userId, newMessOb.id).getPayload().getHeaders());
		listMessages.add(newMessOb); //MessageProcess.parseHeaderMail(msg.getId())
		System.out.println(newMessOb.toString());
	    }
	} catch (IOException | MessagingException ex) {
//	    Logger.getLogger(Init.class.getName()).log(Level.SEVERE, null, ex);
	    throw new FailToLoadInitInboxException();
	}
	return listMessages;
    }
}
