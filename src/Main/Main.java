package Main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application{
    Stage window;
    Button button;

    public static void main(String[] args) throws Exception  {
        launch(args);
        int mennyiUtasTEszt = 85;
        DataBase dataBase = new DataBase();
        dataBase.createUtasokTable();
        //dataBase.postUtasNumberOfTimes(mennyiUtasTEszt);
        //dataBase.setAnything("int", 2, "utasKor", "77");
        //System.out.println(dataBase.getAnything("int", 2, "utasKor"));
        //System.out.println(dataBase.getAnything("boolean", 4, "utasUtazikE"));

        Busz busz = new Busz("34", 100);
        //busz.buszFelszallUtas(mennyiUtasTEszt);
        //busz.buszEllenorzes(mennyiUtasTEszt);
        //busz.buszLeszallUtas(mennyiUtasTEszt);
        //dataBase.createUtvonalakTable();
        //dataBase.postUtvonal34("134");
        System.out.println(dataBase.getAllomasokLista("134"));
        //busz.buszKozlekedik();

    }

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setTitle("Transport Simulation");
        button = new Button("Katt ide");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AlertBox.display("Aleert", "Ez az üzenet");
                boolean result = SettingsBox.display("Options","Igen/Nem");
                System.out.println(result);
            }
        });
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);

        window.setScene(scene);
        window.show();
    }


}
