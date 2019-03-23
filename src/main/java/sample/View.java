package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class View {
    private Stage primaryStage;
    private Controller controller = new Controller(this);
    private List<File> attached = new ArrayList<>();
    private FileChooser fileChooser = new FileChooser();
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML private VBox inputArea;
    @FXML private TextField from;
    @FXML private TextField password;
    @FXML private TextField to;
    @FXML private TextField subject;
    @FXML private TextArea text;
    @FXML private VBox attachmentsArea;
    @FXML private Button chooseFileBtn;
    @FXML private Button sendBtn;
    @FXML private TextArea logArea;

    public View() {
        alert.setTitle("Error");
        alert.setHeaderText("Error during sending");
    }

    void setStage(Stage stage){ primaryStage = stage; }

    @FXML void attach() {
        List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);
        attached.addAll(files);
        files.forEach(f -> attachmentsArea.getChildren().add(createAttachFileField(f)));
    }

    private HBox createAttachFileField(File file){
        Button fileButton = new Button(file.getName());
        fileButton.setPrefWidth(800);
        Button deleteButton = new Button("X");
        HBox hBox = new HBox(fileButton, deleteButton);
        hBox.setUserData(file);
        deleteButton.setOnAction(e -> {
            attached.remove(hBox.getUserData());
            attachmentsArea.getChildren().remove(hBox);
        });
        return hBox;
    }

    @FXML void send() {
        disableFields(true);
        controller.send(new Mail(to.getText(), password.getText(), from.getText(), subject.getText(), text.getText(), attached));
    }

    void endSending(boolean success, String errorMessage) {
        if (success) log("------ Message successfully sended ------");
        else {
            log("------ During sending an error has occurred ------");
            alert.setContentText(errorMessage);
            Platform.runLater(() -> alert.showAndWait());
        }
        disableFields(false);
    }

    private void disableFields(boolean disable){
        inputArea.setDisable(disable);
    }

    void log(String string) {
        logArea.appendText(string + "\r\n");
    }
}
