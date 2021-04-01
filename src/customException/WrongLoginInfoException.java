/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customException;

/**
 *
 * @author Admin
 */
public class WrongLoginInfoException extends Exception{


        public WrongLoginInfoException() {
            super();
        }

        @Override
        public String getMessage() {
            return "Lỗi xảy ra do Mail nhập vào và Mail xác thực không giống nhau ";
        }
}
