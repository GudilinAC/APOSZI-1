package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class View {
    private Stage primaryStage;
    private Controller controller = new Controller(this);
    private List<File> attached;
    private FileChooser fileChooser = new FileChooser();

    @FXML private TextField from;
    @FXML private TextField password;
    @FXML private TextField to;
    @FXML private TextField topic;
    @FXML private TextArea text;
    @FXML private TextArea attachments;
    @FXML private Button chooseFileBtn;
    @FXML private Button sendBtn;
    @FXML private TextArea logArea;

    void setStage(Stage stage){ primaryStage = stage; }

    @FXML void attach() {
        attached = fileChooser.showOpenMultipleDialog(primaryStage);
        //TODO show in interface
    }

    @FXML void send() {
        disableFields(true);
        controller.send(new Mail(to.getText(), password.getText(), from.getText(), topic.getText(), text.getText(), attached));
    }

    void endSending(boolean success) {
        if (success) log("------ Message successfully sended ------");
        else log("------ During sending an error has occurred ------");
        disableFields(false);
    }

    private void disableFields(boolean disable){
        from.setDisable(disable);
        password.setDisable(disable);
        to.setDisable(disable);
        topic.setDisable(disable);
        attachments.setDisable(disable);
        text.setDisable(disable);
        chooseFileBtn.setDisable(disable);
        sendBtn.setDisable(disable);
    }

    void log(String string) {
        logArea.appendText(string + "\r\n");
    }
}
