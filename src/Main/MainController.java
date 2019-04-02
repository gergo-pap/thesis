package Main;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    public ImageView imageViewBusz;
    public ImageView imageViewMap;
    private Busz busz;
    private PathTransition pathTransition;

    public MainController() throws ClassNotFoundException, SQLException, ParseException, IOException {
        this.busz = new Busz("34", 100);
        this.pathTransition = new PathTransition();
    }

    public void initialize() {
        Path path = new Path();
        Allomas kezdoAllomas = busz.getAktualisAllomas();
        path.getElements().add(new MoveTo(kezdoAllomas.getX(), kezdoAllomas.getY()));

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
