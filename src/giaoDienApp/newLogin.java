/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package giaoDienApp;

import customException.WrongLoginInfoException;
import gmailApi.CheckInternetConnection;
import gmailApi.GlobalVariable;
import gmailApi.Init;
import gmailApi.LoginProcess;
import static gmailApi.LoginProcess.checkMail;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class newLogin extends javax.swing.JFrame {

    /**
     * Creates new form newLogin
     */
    public newLogin() {
	initComponents();
	this.failFormatMail_Lb.setVisible(false);
	this.overlap_Pn.setBackground(new Color(0, 0, 0, 200));
	this.initJCBoxLoadSaveToken();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main_Pn = new javax.swing.JPanel();
        login_Pn = new javax.swing.JPanel();
        username_Lb = new javax.swing.JLabel();
        userNameInput_Tf = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        login_Bt = new javax.swing.JButton();
        savedToken_Jcb = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        save_Chb = new javax.swing.JCheckBox();
        failFormatMail_Lb = new javax.swing.JLabel();
        overlap_Pn = new javax.swing.JPanel();
        logo_Lb = new javax.swing.JLabel();
        quotes_Lb = new javax.swing.JLabel();
        madeBy_Lb = new javax.swing.JLabel();
        backgroundLeft_Lb = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(0, 0));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        main_Pn.setBackground(new java.awt.Color(255, 255, 255));
        main_Pn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        login_Pn.setBackground(new java.awt.Color(161, 233, 237));
        login_Pn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        username_Lb.setFont(new java.awt.Font("Consolas", 1, 18)); // NOI18N
        username_Lb.setText("Username");
        login_Pn.add(username_Lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 40));

        userNameInput_Tf.setBackground(new java.awt.Color(161, 233, 237));
        userNameInput_Tf.setFont(new java.awt.Font("Consolas", 1, 16)); // NOI18N
        userNameInput_Tf.setBorder(null);
        userNameInput_Tf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userNameInput_TfMouseClicked(evt);
            }
        });
        userNameInput_Tf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userNameInput_TfActionPerformed(evt);
            }
        });
        login_Pn.add(userNameInput_Tf, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 310, 50));

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        login_Pn.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 330, 10));

        login_Bt.setBackground(new java.awt.Color(161, 233, 237));
        login_Bt.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        login_Bt.setText("login");
        login_Bt.setToolTipText("");
        login_Bt.setActionCommand("");
        login_Bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_BtActionPerformed(evt);
            }
        });
        login_Pn.add(login_Bt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 450, 170, 60));

        savedToken_Jcb.setBackground(new java.awt.Color(161, 233, 237));
        savedToken_Jcb.setFont(new java.awt.Font("Consolas", 1, 16)); // NOI18N
        savedToken_Jcb.setBorder(null);
        savedToken_Jcb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                savedToken_JcbMouseClicked(evt);
            }
        });
        savedToken_Jcb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savedToken_JcbActionPerformed(evt);
            }
        });
        savedToken_Jcb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                savedToken_JcbKeyPressed(evt);
            }
        });
        login_Pn.add(savedToken_Jcb, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 330, 40));

        jLabel2.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("X");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        login_Pn.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 60, 40));

        save_Chb.setBackground(new java.awt.Color(161, 233, 237));
        save_Chb.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        save_Chb.setText("L??u l???i cho l???n sau");
        save_Chb.setBorder(null);
        save_Chb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_ChbActionPerformed(evt);
            }
        });
        login_Pn.add(save_Chb, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 210, -1));

        failFormatMail_Lb.setFont(new java.awt.Font("Consolas", 1, 13)); // NOI18N
        failFormatMail_Lb.setForeground(new java.awt.Color(255, 51, 51));
        failFormatMail_Lb.setText("Wrong mail format !");
        login_Pn.add(failFormatMail_Lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 330, 20));

        main_Pn.add(login_Pn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 370, 600));

        getContentPane().add(main_Pn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 0, 370, 600));

        overlap_Pn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo_Lb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo2.png"))); // NOI18N
        overlap_Pn.add(logo_Lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, -1, 160));

        quotes_Lb.setFont(new java.awt.Font("Castellar", 1, 18)); // NOI18N
        quotes_Lb.setForeground(new java.awt.Color(204, 204, 204));
        quotes_Lb.setText("Free, fast and furious");
        overlap_Pn.add(quotes_Lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 280, 50));

        madeBy_Lb.setFont(new java.awt.Font("Castellar", 1, 13)); // NOI18N
        madeBy_Lb.setForeground(new java.awt.Color(204, 204, 204));
        madeBy_Lb.setText("Made by TuThAn Company");
        madeBy_Lb.setToolTipText("");
        overlap_Pn.add(madeBy_Lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 540, -1, -1));

        getContentPane().add(overlap_Pn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 600));

        backgroundLeft_Lb.setIcon(new javax.swing.ImageIcon("D:\\School\\CNPM\\GmailAppAPI\\GmailApp\\src\\img\\left4.jpg")); // NOI18N
        backgroundLeft_Lb.setText("jLabel3");
        getContentPane().add(backgroundLeft_Lb, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 410, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void save_ChbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_ChbActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_save_ChbActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
	// TODO add your handling code here:
	System.exit(0);
    }//GEN-LAST:event_jLabel2MouseClicked

    private void login_BtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_BtActionPerformed
	// TODO add your handling code here:
	GlobalVariable.userId = this.userNameInput_Tf.getText();
	GlobalVariable.save = this.save_Chb.isSelected() ? 1 : 0; // ki???m tra xem cb_remember c?? ???????c tick v??o hay kh??ng
	if (this.savedToken_Jcb.getSelectedItem().toString().equals(GlobalVariable.userId)) {
	    GlobalVariable.save = 1;							 //n???u c?? tr??? v??? 1, kh??ng tr??? v??? 0
	}
	if (checkMail(this.userNameInput_Tf.getText()) == false) {
	    failFormatMail_Lb.setVisible(true);
	} else {
	    try {
		CheckInternetConnection.checkInternetConnect();
		GlobalVariable.internetOn = true;
	    } catch (IOException ex) {
		GlobalVariable.internetOn = false;
		if (this.savedToken_Jcb.getSelectedItem().toString().equals(GlobalVariable.userId) == false) {
		    JOptionPane.showMessageDialog(this, "Kh??ng th??? truy c???p m???ng, b???n ch??? c?? th??? ????ng nh???p v???i nh???ng t??i kho???n ???? l??u!");
		}
	    } finally {
		if (GlobalVariable.internetOn) {
		    try {
			// g???i h??m login() 
			LoginProcess.login();
			//th??nh c??ng th?? t???t giao di???n login
			this.setVisible(false);
			// m??? main panel
			newMainPage mainPage = new newMainPage(this);
			// set userlogin
			newMainPage.loginingUser_Lb.setText(GlobalVariable.userId);
			// 
			mainPage.setVisible(true);
		    } catch (WrongLoginInfoException ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage());
		    }
		} else {
//		    JOptionPane.showMessageDialog(this, "Kh??ng th??? truy c???p m???ng, b???n ch??? c?? th??? ????ng nh???p v???i nh???ng t??i kho???n ???? l??u!");
		    if (this.savedToken_Jcb.getSelectedItem().toString().equals(GlobalVariable.userId)) {
			this.setVisible(false);
			newMainPage mainPage = new newMainPage(this);
			newMainPage.loginingUser_Lb.setText(GlobalVariable.userId);
			mainPage.setVisible(true);
		    }
		}
	    }
	}
    }//GEN-LAST:event_login_BtActionPerformed

    private void savedToken_JcbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savedToken_JcbActionPerformed
	// TODO add your handling code here:
	this.userNameInput_Tf.setText(this.savedToken_Jcb.getSelectedItem().toString());
    }//GEN-LAST:event_savedToken_JcbActionPerformed

    private void userNameInput_TfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userNameInput_TfActionPerformed
	// TODO add your handling code here:
	this.failFormatMail_Lb.setVisible(false);
    }//GEN-LAST:event_userNameInput_TfActionPerformed

    private void userNameInput_TfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userNameInput_TfMouseClicked
	// TODO add your handling code here:
	this.failFormatMail_Lb.setVisible(false);
    }//GEN-LAST:event_userNameInput_TfMouseClicked

    private void savedToken_JcbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_savedToken_JcbKeyPressed
	// TODO add your handling code here:
	if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
	    int index = this.savedToken_Jcb.getSelectedIndex();
	    System.out.println(index);
	    if (index > 0) {
		String userName = (String) this.savedToken_Jcb.getSelectedItem();
		int response = JOptionPane.showConfirmDialog(this, "B???n c?? ch???c mu???n xo?? " + userName + " kh??ng?");
		if (response == JOptionPane.YES_OPTION) {
		    this.savedToken_Jcb.removeItemAt(index);
		    this.userNameInput_Tf.setText("");
		    LoginProcess.deleteLoginToken(userName);
		    JOptionPane.showMessageDialog(this, "B???n ???? xo?? th??nh c??ng!");
		}
	    }
	}
    }//GEN-LAST:event_savedToken_JcbKeyPressed

    private void savedToken_JcbMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_savedToken_JcbMouseClicked
        // TODO add your handling code here:
	if(evt.getButton()== MouseEvent.BUTTON3){
	    System.out.println("right click here");
	}
    }//GEN-LAST:event_savedToken_JcbMouseClicked

    private void initJCBoxLoadSaveToken() {
	List<String> listSaveToken = Init.initSaveTokens();
	this.savedToken_Jcb.addItem("");
	for (String token : listSaveToken) {
	    this.savedToken_Jcb.addItem(token);
	}
    }

    /**
     * @param args the command line arguments
     */
    public static void lookAndFeel() {
	/* Set the Nimbus look and feel */
	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
	/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
	 */
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("windows".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(newLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(newLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(newLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(newLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>
	//</editor-fold>

	/* Create and display the form */
//	java.awt.EventQueue.invokeLater(new Runnable() {
//	    public void run() {
//		new newLogin().setVisible(true);
//	    }
//	});
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel backgroundLeft_Lb;
    private javax.swing.JLabel failFormatMail_Lb;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton login_Bt;
    private javax.swing.JPanel login_Pn;
    private javax.swing.JLabel logo_Lb;
    private javax.swing.JLabel madeBy_Lb;
    private javax.swing.JPanel main_Pn;
    private javax.swing.JPanel overlap_Pn;
    private javax.swing.JLabel quotes_Lb;
    private javax.swing.JCheckBox save_Chb;
    private javax.swing.JComboBox<String> savedToken_Jcb;
    private javax.swing.JTextField userNameInput_Tf;
    private javax.swing.JLabel username_Lb;
    // End of variables declaration//GEN-END:variables
}
