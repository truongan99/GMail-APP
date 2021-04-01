/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trashCode;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import customException.WrongLoginInfoException;
import gmailApi.GlobalVariable;
import gmailApi.LoginProcess;
import gmailApi.MessageObject;
import gmailApi.MessageProcess;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.DefaultListModel;

/**
 *
 * @author Admin
 */
public class loadDataThread extends Thread {

    DefaultListModel model;
    Gmail service;
    String userId;
    List<String> loadFromLabel = new ArrayList<>();

    public loadDataThread(DefaultListModel model) {
	this.model = model;
    }

    synchronized public void getData(Gmail service, ListMessagesResponse response) {

	//doc tung mail
	List<Message> messages = new ArrayList<>();
	try {
	    while (response.getMessages() != null) {
		messages.addAll(response.getMessages());
		for (Message msg : messages) {
		    MessageObject newMessOb = new MessageObject();
		    newMessOb.id = msg.getId();
		    newMessOb.from = MessageProcess.getFrom(MessageProcess.getMessageById(GlobalVariable.getService(), GlobalVariable.userId, newMessOb.id).getPayload().getHeaders());
		    model.addElement(msg);//add(newMessOb); //MessageProcess.parseHeaderMail(msg.getId())
		}
		Thread.sleep(5000);
		if (response.getNextPageToken() != null) {
		    String pageToken = response.getNextPageToken();
		    response = service.users().messages().list(userId).setLabelIds(loadFromLabel).setPageToken(pageToken).execute();
		} else {
		    break;
		}

	    }

	} catch (IOException | MessagingException ex) {
	} catch (InterruptedException ex) {
	    Logger.getLogger(loadDataThread.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void run() {
	System.out.println("Load Data thread start !!! ");
	loadFromLabel.add("INBOX");
//	List<MessageObject>listMessages = new ArrayList<>();

	//list response cua mail list
	ListMessagesResponse response;

	try {
	    userId = "testdoan123456@gmail.com";
	    GlobalVariable.userId = userId;
	    LoginProcess.login();
	    service = GlobalVariable.getService();
	    
	    response = service.users().messages().list(userId).setLabelIds(loadFromLabel).setMaxResults(Long.valueOf(5)).execute();
	    getData(service, response);
	} catch (WrongLoginInfoException | IOException ex) {
	    Logger.getLogger(loadDataThread.class.getName()).log(Level.SEVERE, null, ex);
	}

	System.out.println("Load Data thread stop !!! ");
    }
}
