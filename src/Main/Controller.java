package Main;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class Controller extends Application {
    private Busz busz;
    private PathTransition pathTransition;

    public Controller() throws ClassNotFoundException, SQLException, ParseException, IOException {
        this.busz = new Busz("34", 100);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public void buszKozlekedik(Path path, PathTransition pathTransition) throws SQLException, InterruptedException {
        while (true) {
            Allomas elozo_allomas = busz.getAktualisAllomas();
            boolean vegallomasra_ert = busz.kovetkezoMegallo();

            path.getElements().add(new MoveTo(elozo_allomas.getX(), elozo_allomas.getY()));
            path.getElements().add(new LineTo(busz.getAktualisAllomas().getX(), busz.getAktualisAllomas().getY()));
            pathTransition.play();
            pathTransition.wait();

            if (vegallomasra_ert) {
                break;
            }
        }

//        int mennyiUtasTEszt = 85;
//        DataBase dataBase = new DataBase();

        //dataBase.createUtasokTable();
        //dataBase.postUtasNumberOfTimes(mennyiUtasTEszt);

        //dataBase.createUtvonalakTable();
        //dataBase.postUtvonal34("134");
//        System.out.println(dataBase.getAllomasokLista("134"));
//        busz.buszKozlekedik();
    }

    @Override
    public void start(Stage stage) throws InterruptedException {
        int maxHeight = 800;
        int maxWidth = 800;

        Image imageBusz = new Image("bus2.png");
        Image imageMap = new Image("map.jpg");

        ImageView imageViewBusz = new ImageView();
        imageViewBusz.setFitHeight(50);
        imageViewBusz.setFitWidth(50);
        imageViewBusz.setImage(imageBusz);

        ImageView imageViewMap = new ImageView();
        imageViewMap.setFitHeight(maxHeight);
        imageViewMap.setFitWidth(maxWidth);
        imageViewMap.setImage(imageMap);

        Path path = new Path();
        Allomas kezdo_allomas = busz.getAktualisAllomas();
        path.getElements().add(new MoveTo(kezdo_allomas.getX(), kezdo_allomas.getY()));

        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1));
        pathTransition.setNode(imageViewBusz);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        //pathTransition.setAutoReverse(true);
        pathTransition.setPath(path);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    nyomasAKovetkezoMegalloba();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        pathTransition.play();

        Group group = new Group(imageViewMap, imageViewBusz);
        Scene scene = new Scene(group, maxWidth, maxHeight);
        stage.setScene(scene);
        stage.show();
    }

    void nyomasAKovetkezoMegalloba() throws SQLException {
        Allomas elozo_allomas = busz.getAktualisAllomas();
        boolean uton_van = busz.kovetkezoMegallo();
        if (!uton_van) {
            return;
        }

        Path path = new Path();
        path.getElements().add(new MoveTo(elozo_allomas.getX(), elozo_allomas.getY()));
        path.getElements().add(new LineTo(busz.getAktualisAllomas().getX(), busz.getAktualisAllomas().getY()));

        pathTransition.setDuration(Duration.millis(500));
        pathTransition.setPath(path);
        pathTransition.play();
    }
}
