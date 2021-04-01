/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaoDienApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import gmailApi.MessageObject;
import java.awt.Font;
import javax.swing.JSeparator;

/**
 *
 * @author NTA
 */
public class mailListRender extends JPanel implements ListCellRenderer<MessageObject> {

    JLabel lb_list_mailfrom = new JLabel();
    JLabel lb_list_maildate = new JLabel();
    JLabel lb_list_mailTo = new JLabel();
    JSeparator separator = new JSeparator();

//    public mailListRender() {
//        setLayout(new GridLayout(2, 1));
//        add(lb_list_mailfrom);
//        add(lb_list_maildate);
//
//    }
    public mailListRender(String label) {
	setLayout(new GridLayout(3, 1));
	if (label.equals("SENT")) {
	    add(lb_list_mailTo);
	} else {
	    add(lb_list_mailfrom);
	}
	add(lb_list_maildate);
	add(separator);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends MessageObject> list, MessageObject msgOb, int index, boolean isSelected, boolean cellHasFocus) {
	if (msgOb.unread == false) {
	    lb_list_mailfrom.setFont(new Font("Consolas", Font.PLAIN, 16));
	    lb_list_maildate.setFont(new Font("Consolas", Font.PLAIN, 16));
	    lb_list_mailTo.setFont(new Font("Consolas", Font.PLAIN, 16));
	}else{
	    lb_list_mailfrom.setFont(new Font("Consolas", Font.BOLD, 16));
	    lb_list_maildate.setFont(new Font("Consolas", Font.BOLD, 16));
	    lb_list_mailTo.setFont(new Font("Consolas", Font.BOLD, 16));
	}
	lb_list_mailfrom.setAlignmentX(5);
	lb_list_mailfrom.setForeground(Color.BLACK);
	lb_list_mailfrom.setText(msgOb.from);

	lb_list_maildate.setAlignmentX(5);
	lb_list_maildate.setForeground(Color.BLACK);
	lb_list_maildate.setText(msgOb.date);

	lb_list_mailTo.setAlignmentX(5);
	lb_list_mailTo.setForeground(Color.BLACK);
	lb_list_mailTo.setText(msgOb.to);

	lb_list_maildate.setOpaque(true);
	lb_list_mailfrom.setOpaque(true);
	lb_list_mailTo.setOpaque(true);
	// when select item
	if (isSelected) {
	    lb_list_mailfrom.setFont(new Font("Consolas", Font.PLAIN, 16));
	    lb_list_maildate.setFont(new Font("Consolas", Font.PLAIN, 16));
	    lb_list_mailTo.setFont(new Font("Consolas", Font.PLAIN, 16));
	    lb_list_mailTo.setBackground(new java.awt.Color(161, 233, 237));
	    lb_list_maildate.setBackground(new java.awt.Color(161, 233, 237));
	    lb_list_mailfrom.setBackground(new java.awt.Color(161, 233, 237));
	    setBackground(new java.awt.Color(161, 233, 237));
	} else { // when don't select
	    lb_list_mailTo.setBackground(list.getSelectionBackground());
	    lb_list_maildate.setBackground(list.getSelectionBackground());
	    lb_list_mailfrom.setBackground(list.getSelectionBackground());
	    setBackground(list.getSelectionBackground());
	}
	return this;
    }

}
