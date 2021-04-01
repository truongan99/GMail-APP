/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import customException.WrongLoginInfoException;
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
import trashCode.index;
import java.io.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginProcess {

    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String tokenRootPath = GlobalVariable.rootDirectorySaveTokens;
    private static String TOKENS_DIRECTORY_PATH;

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
	InputStream in = LoginProcess.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
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

    /**
     * login và kiểm tra user có xác thực đúng với mail đã nhập không
     *
     * @throws IOException
     * @throws GeneralSecurityException
     * @throws WrongLoginInfoException
     */
    public static void login() throws WrongLoginInfoException {
	TOKENS_DIRECTORY_PATH = tokenRootPath + XulyChuoiMail.parseMail(GlobalVariable.userId);
	try {
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

	    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
		    .setApplicationName(APPLICATION_NAME)
		    .build();

	    // kiểm tra xác thực mail nhập vào với mail xác thực có đúng không
	    service.users().getProfile(GlobalVariable.userId).execute();
	    // set service khi đã kiểm tra hoàn thành
	    GlobalVariable.setService(service);
	    System.out.println("Login for userId: " + GlobalVariable.userId);
	    System.out.println("Save: " + GlobalVariable.save);
	    // nếu không muốn lưu lại thì xoá file token sau khi dừng chương trình

	} catch (IOException |GeneralSecurityException e) {
	    //nếu login sai thì xoá đi file token vừa tạo
	    deleteLoginToken(XulyChuoiMail.parseMail(GlobalVariable.userId));
	    throw new WrongLoginInfoException();
	}
    }

    /**
     * xoá token của user
     *
     * @param user : tên của user sau khi parse ( bỏ đằng sau @)
     * @return :1 nếu thành công, 0 nếu fail
     */
    public static int deleteLoginToken(String user) {
	String tokenfolderPath = tokenRootPath + user + "/";
	String tokenFilePath = tokenfolderPath + "StoredCredential";
	//delete file tokens and folder chứa nó
	File deleteFile = new File(tokenFilePath);
	File deleteFolder = new File(tokenfolderPath);
	if (deleteFile.delete() && deleteFolder.delete()) {
	    System.out.println("Da delete file roi !");
	    return 1;
	}
	return 0;
    }

    /**
     * Gọi ngay khi xác nhận là không lưu cho lần kế tiếp ở welcome panel
     *
     */
    public static void deleteLoginTokenAtEnd() {
	File deleteFile = new File(TOKENS_DIRECTORY_PATH + "/StoredCredential");
	File deleteFolder = new File(TOKENS_DIRECTORY_PATH);
	deleteFolder.deleteOnExit();
	deleteFile.deleteOnExit();
    }
    
    /**
     * kiểm tra email có được nhập đúng format không
     * @param email
     * @return
     */
    public static boolean checkMail(String email) {
	try {
	    String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//	    String email = this.userNameInput_Tf.getText();
	    if (email.matches(pattern)) {
		return true;
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return false;
    }
}
