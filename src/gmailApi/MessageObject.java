/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author NTA
 */
public class MessageObject implements Serializable {
    private static final long serialVersionUID = 1L;
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
    // hai trường đặc biệt sử dụng khi reply mail
    public String messageID;
    public String references;
    public boolean unread; 
    public MessageObject(){
    }
    
    public MessageObject(String from,String to, String bcc, String cc, String subject, String date, String id, String time, String avtName,String mainText) {
        this.from = from;
	this.to = to;
        this.cc = cc;
	this.bcc = bcc;
        this.subject = subject;
        this.date = date;
	this.time = time;
	this.id = id;
        this.avtName = avtName;
	this.mainText = mainText;
    }

    public MessageObject(String from, String id, String subject) {
        this.from = from;
        this.id = id;
        this.subject = subject;
    }
    public void setId(String id) {
        this.id = id;
    }
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
//
//    public String getAvtName() {
//        return avtName;
//    }
//
//    public void setAvtName(String avtName) {
//        this.avtName = avtName;
//    }
//
//    public String getFrom() {
//        return from;
//    }
//
//    public String getCc() {
//        return cc;
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public String getDate() {
//        return date;
//    }

    @Override
    public String toString() {
        return from + " " + to + " " + date; //To change body of generated methods, choose Tools | Templates.
    }

}
