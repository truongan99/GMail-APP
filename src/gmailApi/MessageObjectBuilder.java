/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import java.util.HashMap;
import java.util.Map;

/**
 *không có chức năng gì
 * @author Admin
 */
public class MessageObjectBuilder {
    public String from;
    public String cc;
    public String bcc;
    public String subject;
    public String id;
    public String date;
    public String time;
    public String avtName;
    public String mainText;
    public String to;
    public Map<String,String> listFile = new HashMap<>();

    public MessageObjectBuilder setFrom(String from) {
	this.from = from;
	return this;
    }

    public MessageObjectBuilder setCc(String cc) {
	this.cc = cc;
	return this;
    }

    public MessageObjectBuilder setBcc(String bcc) {
	this.bcc = bcc;
	return this;
    }

    public MessageObjectBuilder setSubject(String subject) {
	this.subject = subject;
	return this;
    }

    public MessageObjectBuilder setId(String id) {
	this.id = id;
	return this;
    }

    public MessageObjectBuilder setDate(String date) {
	this.date = date;
	return this;
    }

    public MessageObjectBuilder setTime(String time) {
	this.time = time;
	return this;
    }

    public MessageObjectBuilder setAvtName(String avtName) {
	this.avtName = avtName;
	return this;
    }

    public MessageObjectBuilder setMainText(String mainText) {
	this.mainText = mainText;
	return this;
    }

    public MessageObjectBuilder setTo(String to) {
	this.to = to;
	return this;
    }

    public MessageObjectBuilder setListFile(Map<String, String> listFile) {
	this.listFile = listFile;
	return this;
    }
    
    public MessageObject getMessageObject(){
	return new MessageObject(from,to, bcc, cc, subject, date, id, time,  avtName,mainText);
    }
}
