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
public class FailToLoadInitInboxException extends Exception{


        public FailToLoadInitInboxException() {
            super();
        }

        @Override
        public String getMessage() {
            return "Lỗi xảy ra trong qua trinh khoi tao inbox bat dau chuong trinh !";
        }
}
