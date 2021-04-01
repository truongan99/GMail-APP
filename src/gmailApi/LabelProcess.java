/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gmailApi;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Admin
 */
public class LabelProcess {

    /**
     * tạo Lable mới
     *
     * @param labelName
     * @return new label
     * @throws java.io.IOException
     */
    public static Label addLabel(String labelName) throws IOException {
        Gmail service = GlobalVariable.getService();
        Label newLabel = new Label().setName(labelName);
        newLabel = service.users().labels().create(GlobalVariable.userId, newLabel).execute();
        System.out.println("Label id: " + newLabel.getId());
        System.out.println(newLabel.toPrettyString());
        return newLabel;
    }

    /**
     * xoá 1 Label
     *
     * @param labelName
     * @throws IOException
     */
    public static void deleteLabel(String labelName) throws IOException {
        Gmail service = GlobalVariable.getService();
        service.users().labels().delete(GlobalVariable.userId, labelName).execute();
        System.out.println("Label with id: " + labelName + " deleted successfully.");
    }

    /**
     * dùng để lấy ra tổng số mail của 1 label
     *
     * @param labelId
     * @return số lượng mail của Label
     * @throws IOException
     */
    public static int countAllMailLabel(String labelId) throws IOException {
        int count;
        Gmail service = GlobalVariable.getService();
        Label inboxLabel = service.users().labels().get(GlobalVariable.userId, labelId).execute();
        count = inboxLabel.getMessagesTotal();
        return count;
    }

    /**
     * dùng để lấy ra tổng số mail chưa đọc của 1 label
     *
     * @param labelId
     * @return tổng mail
     * @throws IOException
     */
    public static int countUnreadMailLabel(String labelId) throws IOException {
        int count;
        Gmail service = GlobalVariable.getService();
        Label inboxLabel = service.users().labels().get(GlobalVariable.userId, labelId).execute();
        count = inboxLabel.getMessagesUnread();
        return count;
    }

    /**
     * Hàm này dùng để load tất cả labels mà user có
     * @return list of label user have
     * @throws IOException
     */
    public static List<String> loadAllLabels() throws IOException {
        Gmail service = GlobalVariable.getService();
        ListLabelsResponse listResponse = service.users().labels().list(GlobalVariable.userId).execute();
        List<String> listLabel = null;
        List<Label> labels = listResponse.getLabels();
        if (labels.isEmpty()) {
            System.out.println("No labels found.");
            listLabel = null;
        } else {
            System.out.println("Labels:");
            for (Label label : labels) {
                System.out.printf("- %s\n", label.getName());
                listLabel.add(label.getName());
            }
        }
        return listLabel;
    }

}
