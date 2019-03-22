package Main;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller extends Application {
    private Busz busz;

    public Controller() throws ClassNotFoundException, SQLException, ParseException, IOException {
        this.busz = new Busz("34", 100);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void buszKozlekedik() throws SQLException, ClassNotFoundException {
        int mennyiUtasTEszt = 85;
//        DataBase dataBase = new DataBase();

        //dataBase.createUtasokTable();
        //dataBase.postUtasNumberOfTimes(mennyiUtasTEszt);

        //dataBase.createUtvonalakTable();
        //dataBase.postUtvonal34("134");
//        System.out.println(dataBase.getAllomasokLista("134"));
//        busz.buszKozlekedik();
    }

    @Override
    public void start(Stage stage) {

        int maxHeight = 800;
        int maxWidth = 800;

        Image imageBus = new Image("bus2.png");
        Image imageMap = new Image("map.jpg");

        ImageView imageView = new ImageView();
        ImageView imageViewMap = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setImage(imageBus);

        imageViewMap.setFitHeight(maxHeight);
        imageViewMap.setFitWidth(maxWidth);
        imageViewMap.setImage(imageMap);

        Path path = new Path();
        PathTransition pathTransition = new PathTransition();

        List<Allomas> allomasok = busz.getAllomasok();

        Allomas elso_allomas = allomasok.get(0);
        path.getElements().add(new MoveTo(elso_allomas.getX(), elso_allomas.getY()));

        for (Allomas allomas : allomasok) {
            path.getElements().add(new LineTo(allomas.getX(), allomas.getY()));
        }

        pathTransition.setDuration(Duration.millis(5000));
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        //pathTransition.setAutoReverse(true);
        pathTransition.setPath(path);

        pathTransition.play();
        Group group = new Group(imageViewMap, imageView);
        Scene scene = new Scene(group, maxWidth, maxHeight);
        stage.setScene(scene);

        new Thread() {

            @Override
            public void run() {
                try {
                    buszKozlekedik();
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        stage.show();


    }


}
