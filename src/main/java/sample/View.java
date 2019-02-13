package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class View {
    private Controller controller = new Controller(this);

    @FXML private TextArea from;
    @FXML private TextArea password;
    @FXML private TextArea to;
    @FXML private TextArea topic;
    @FXML private TextArea text;
    @FXML private Button sendBtn;
    @FXML private TextArea logArea;

    void log(String string) {
        logArea.appendText(string + "\r\n");
    }

    public void send() {
        sendBtn.setDisable(true);
        controller.send(new Mail(to.getText(), password.getText(), from.getText(), topic.getText(), text.getText(), null));
    }
}
