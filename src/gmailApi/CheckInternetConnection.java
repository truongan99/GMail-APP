/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author Admin
 */
public class CheckInternetConnection {
    public static boolean checkInternetConnect() throws UnknownHostException, IOException{
        String address = "www.google.com";
        InetAddress inetAddress = InetAddress.getByName(address);
        return inetAddress.isReachable(100);
    }
}
