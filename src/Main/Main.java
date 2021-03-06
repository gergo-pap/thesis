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
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.sql.SQLException;


public class Main extends Application  {

    public static void main(String[] args) {

        Application.launch(args);

    }

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException, ParseException{
        Beallitasok beallitasok = new Beallitasok();

        Database dataBase = new Database();
        dataBase.loadSettings(beallitasok);

        dataBase.createUtasokTable();
        if (dataBase.countTableSize() < 100) {
            dataBase.postUtasNumberOfTimes(100);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/main.fxml"));
        Parent root = (Parent) loader.load();

        MainController controller = loader.getController();
        controller.initializeData(beallitasok, dataBase);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {

                if (AlertBox.getAnswer("Kilépés", "Biztosan ki akarsz lépni?")) {
                    System.exit(1);
                }

                windowEvent.consume();
            }
        });


        stage.setResizable(false);
        stage.setTitle("Transport Simulation");
        Scene scene = new Scene(root, 800, 800);
        stage.setScene(scene);
        stage.show();
    }
}
