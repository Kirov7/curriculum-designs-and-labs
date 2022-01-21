package sample;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class AlertWindow_base {
    public static void display(String title, String mg){
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Label label = new Label();
        label.setText(mg);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(label);

        Scene scene = new Scene(stackPane,400,200);

        stage.setTitle(title);
        stage.setScene(scene);
        stage.showAndWait();

    }
}