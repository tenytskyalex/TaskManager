package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Parent addDialog;
    private static Stage stage;

    public static Parent getAddDialog() {
        return addDialog;
    }
    public static Stage getStage() {
        return stage;
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("ODZ Java. Oleksandr Tenytskyi. Task Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        addDialog = FXMLLoader.load(getClass().getResource("addDialog.fxml"));
        stage = new Stage();
        stage.setTitle("Додати інформацію");
        stage.setScene(new Scene(Main.getAddDialog()));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
