import javax.swing.*;
import java.io.File;

public class Select {

    public static File[] selectMultiFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(true);
        int result = fileChooser.showOpenDialog(fileChooser);
        if (result == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFiles();
        } else {
            return null;
        }
    }

}
