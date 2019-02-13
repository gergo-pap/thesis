package Main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.sql.SQLException;

public class SettingsBox {


    public static void display(String title, String message) throws SQLException, ClassNotFoundException {
        final Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                AlertBox.display("Exit", "Biztosan ki akarsz lépni?");
                windowEvent.consume();
            }
        });
        final Label label = new Label();
        label.setText(message);
        //Create two buttons
        final Button yesButton = new Button("Mehet a program");
        final Button noButton = new Button("Kilépés");

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            Busz busz = new Busz("34", 100);
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    busz.buszKozlekedik();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                AlertBox.display("Exit", "Biztosan ki akarsz lépni?");
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(yesButton, 1, 0);
        GridPane.setConstraints(noButton, 2, 0);
        gridPane.getChildren().addAll(noButton, yesButton, label);
        Scene scene = new Scene(gridPane, 270, 40);
        window.setScene(scene);
        window.show();
    }
}
