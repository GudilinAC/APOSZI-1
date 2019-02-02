package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class View {
    private Stage primaryStage;
    private Controller controller;

    public View(Stage primaryStage, Controller controller) throws Exception {
        this.primaryStage = primaryStage;
        this.controller = controller;

        controller.testSock();

        HBox root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("SMTP-client");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}
