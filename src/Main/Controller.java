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
            Allomas elozoAllomas = busz.getAktualisAllomas();
            boolean vegallomasraErt = busz.kovetkezoMegallo();

            path.getElements().add(new MoveTo(elozoAllomas.getX(), elozoAllomas.getY()));
            path.getElements().add(new LineTo(busz.getAktualisAllomas().getX(), busz.getAktualisAllomas().getY()));
            pathTransition.play();
            pathTransition.wait();

            if (vegallomasraErt) {
                break;
            }
        }
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
        Allomas kezdoAllomas = busz.getAktualisAllomas();
        path.getElements().add(new MoveTo(kezdoAllomas.getX(), kezdoAllomas.getY()));

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
        Allomas elozoAllomas = busz.getAktualisAllomas();
        boolean utonVan = busz.kovetkezoMegallo();
        if (!utonVan) {
            return;
        }

        Path path = new Path();
        path.getElements().add(new MoveTo(elozoAllomas.getX(), elozoAllomas.getY()));
        path.getElements().add(new LineTo(busz.getAktualisAllomas().getX(), busz.getAktualisAllomas().getY()));

        pathTransition.setDuration(Duration.millis(500));
        pathTransition.setPath(path);
        pathTransition.play();
    }
}
