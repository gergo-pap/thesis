package Main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.sql.SQLException;


public class Main {
    Stage window;
    Button button;

    public static void main(String[] args) throws Exception  {
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
        busz.buszKozlekedik();

    }

    /*@Override
    public void start(Stage stage) {
        int widht = 800;
        int height = 800;
        Image image = new Image("map.jpg");
        ImageView imageView = new ImageView();
        imageView.setFitHeight(height);
        imageView.setFitWidth(widht);
        imageView.setImage(image);
        window = stage;
        window.setTitle("Transport Simulation");
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                AlertBox.display("Exit", "Biztosan ki akarsz lépni?");
                //System.exit(1);
                windowEvent.consume();
            }
        });

        Label label = new Label("Budapest tömmegközlekedési térkép");
        label.setAlignment(Pos.CENTER);
        button = new Button("Kattolj");
        button.setMinWidth(widht);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //AlertBox.display("Aleert", "Ez az üzenet");
                try {SettingsBox.display("Options","Folytatod?");}
                catch (SQLException e) {e.printStackTrace();}
                catch (ClassNotFoundException e) {e.printStackTrace();}
            }
        });



        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(button, 0, 2);
        GridPane.setConstraints(imageView,0,1);
        gridPane.getChildren().addAll(button,label,imageView);
        Scene scene = new Scene(gridPane, widht+25, height+75);

        window.setScene(scene);
        window.show();
    }
*/

}
