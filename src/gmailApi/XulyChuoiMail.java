/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

/**
 *
 * @author Admin
 */
public class XulyChuoiMail {

    /**
     * phân tích mail để lấy phần username
     * @param mailAddress
     * @return
     */
    public static String parseMail(String mailAddress){
        String result;
        result = mailAddress.split("@")[0];
        return result;
    }
}
