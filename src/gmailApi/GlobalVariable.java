/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import com.google.api.services.gmail.Gmail;

/**
 *
 * @author Admin
 */
public class GlobalVariable {

    /**
     * lưu trạng thái của lựa chọn có lưu lại token không
     */
    public static int save;

    /**
     * lưu userId
     */
    public static String userId;
    public static boolean checkLogin;

    /**
     * đường dẫn root lưu tokens
     */
    public static String rootDirectorySaveTokens = "src/userInfo/tokens/"; // thư mục gốc để lưu Tokens cho user
    public static String rootDirectorySaveMail = "src/userInfo/mails/"; // thư mục lưu mail cho user
    private static Gmail service;
    public static Gmail getService(){
        return GlobalVariable.service;
    }
    public static void setService(Gmail service){
        GlobalVariable.service = service;
    }

    /**
     * lưu trạng thái internet
     */
    public static boolean internetOn;
}
