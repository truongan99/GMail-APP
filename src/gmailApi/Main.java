/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import giaoDienApp.newLogin;

import static gmailApi.LoginProcess.deleteLoginToken;
import java.io.File;

/**
 * bắt đầu chạy 
 * @author Admin
 */
public class Main {

    public static void main(String[] args) {
	newLogin newLogin;

	newLogin = new newLogin();
	newLogin.lookAndFeel();
	java.awt.EventQueue.invokeLater(new Runnable() {
	    public void run() {
		newLogin.setVisible(true);
	    }
	});
	Runtime.getRuntime().addShutdownHook(new Thread() {

	    @Override
	    public void run() {
		if (GlobalVariable.save == 0) {
		    deleteLoginToken(XulyChuoiMail.parseMail(GlobalVariable.userId));
		}
	    }
	});
    }

}
