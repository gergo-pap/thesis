package UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertBox {
    public static void display(String title, String message) {
        final Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setMinWidth(250);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                windowEvent.consume();
            }
        });
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Igen");
        Button goOnButton = new Button("Nem");

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(1);
            }
        });

        goOnButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(goOnButton, 1, 0);
        GridPane.setConstraints(closeButton, 2, 0);
        gridPane.getChildren().addAll(closeButton, goOnButton, label);
        Scene scene = new Scene(gridPane, 300, 40);

        stage.setScene(scene);
        stage.show();
    }
}
