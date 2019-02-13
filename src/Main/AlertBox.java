package Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {
    public static void display(String title, String message) {
        final Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(1);
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
                window.close();
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
        Scene scene = new Scene(gridPane, 260, 40);

        window.setScene(scene);
        window.show();
    }
}
