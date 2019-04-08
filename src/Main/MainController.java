package Main;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {
    public ImageView imageViewBusz;
    public ImageView imageViewMap;
    public static Label labelAllomas;
    public static Label labelSzabadhelyekSzama;
    public static Label labelEsemenyek;
    public static Label labelBuntetesek;
    public static Label labelLeszallutasok;
    public static Label labelFelszallutasok;
    private Busz busz;
    private PathTransition pathTransition;
    private boolean autoPlay;

    public MainController() throws ClassNotFoundException, SQLException, ParseException, IOException {
        busz = new Busz("134", 100);
        autoPlay = false;

        pathTransition = new PathTransition();
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (autoPlay) {
                        nyomasAKovetkezoMegalloba();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initialize() {
        pathTransition.setNode(imageViewBusz);
    }

    private void nyomasAKovetkezoMegalloba() throws SQLException {
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
        labelAllomas.setVisible(true);
        labelAllomas.setText("" + busz.getAktualisAllomas().getName());
        pathTransition.play();
    }

    public void OnRestart(MouseEvent mouseEvent) throws SQLException {

        busz.buszAStartPoziciora();
        Allomas kezdoAllomas = busz.getAktualisAllomas();
        imageViewBusz.setX(kezdoAllomas.getX());
        imageViewBusz.setY(kezdoAllomas.getY());
        imageViewBusz.setVisible(true);

        autoPlay = true;
        nyomasAKovetkezoMegalloba();
    }

    public void OnStepByStep(MouseEvent mouseEvent) throws SQLException {
        imageViewBusz.setVisible(true);
        autoPlay = false;
        nyomasAKovetkezoMegalloba();
    }
}
