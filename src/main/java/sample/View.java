package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class View {
    private Controller controller = new Controller(this);

    @FXML
    private TextArea from;

    @FXML
    private TextArea to;

    @FXML
    private TextArea topic;

    @FXML
    private TextArea text;

    @FXML
    private TextArea logArea;

    void log(String string) {
        logArea.appendText(string + "\r\n");
    }

    public void test()
    {
        controller.testSock();
    }
}
