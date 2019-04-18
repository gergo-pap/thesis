package Main;

import UI.AlertBox;
import UI.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;


public class Main extends Application {

    public static void main(String[] args) {

        Application.launch(args);

    }

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        Beallitasok beallitasok = new Beallitasok();

        DataBase dataBase = new DataBase();
        dataBase.loadSettings(beallitasok);

        dataBase.createUtasokTable();
        if (dataBase.countTableSize() < 100) {
            dataBase.postUtasNumberOfTimes(100);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.initializeData(beallitasok, dataBase);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                AlertBox.display("Kilépés", "Biztosan ki akarsz lépni?");
                windowEvent.consume();
            }
        });

        stage.setTitle("Transport Simulation");
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }
}
