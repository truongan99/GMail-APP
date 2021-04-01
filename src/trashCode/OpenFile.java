package trashCode;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class OpenFile extends JFrame {

    private JButton buttonBrowse;

    public OpenFile() {
        super("Open File");
        setLayout(new FlowLayout());
        buttonBrowse = new JButton("Open");
        buttonBrowse.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                showOpenFileDialog();
            }
        });
        getContentPane().add(buttonBrowse);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                new OpenFile();
            }
        });
    }

    private void showOpenFileDialog() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn file");
        String linkFile;
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            linkFile = fileToOpen.getAbsolutePath();
            System.out.println("Link: " + linkFile);
        }
    }
}
