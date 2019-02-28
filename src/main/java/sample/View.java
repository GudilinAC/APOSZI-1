package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class View {
    private Controller controller = new Controller(this);

    @FXML private TextField from;
    @FXML private TextField password;
    @FXML private TextField to;
    @FXML private TextField topic;
    @FXML private TextArea text;
    @FXML private Button sendBtn;
    @FXML private TextArea logArea;

    @FXML void send() {
        sendBtn.setDisable(true);
        controller.send(new Mail(to.getText(), password.getText(), from.getText(), topic.getText(), text.getText(), null));
    }

    void endSending(boolean success) {
        if (success) log("------ Message successfully sended ------");
        else log("------ During sending an error has occurred ------");
        sendBtn.setDisable(false);
    }

    void log(String string) {
        logArea.appendText(string + "\r\n");
    }
}
