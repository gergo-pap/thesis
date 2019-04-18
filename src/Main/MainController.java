package Main;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public class MainController {


    public Label labelAllomas;
    public Label labelSzabadhelyekSzama;
    public Label labelEsemenyek;
    public Label labelBuntetesek;
    public Label labelLeszallutasok;
    public Label labelFelszallutasok;
    public ImageView imageViewBusz;
    public ImageView imageViewMap;
    private Busz busz;
    private PathTransition pathTransition;
    private boolean autoPlay;

    public MainController() throws ClassNotFoundException, SQLException, ParseException, IOException {

        autoPlay = false;
        busz = new Busz(this,"134", 100);

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initialize() {
        pathTransition.setNode(imageViewBusz);
    }

    private void nyomasAKovetkezoMegalloba() throws Exception {
        Allomas elozoAllomas = busz.getAktualisAllomas();
        boolean utonVan = busz.kovetkezoMegallo();
        if (!utonVan) {
            return;
        }

        Path path = new Path();
        path.getElements().add(new MoveTo(elozoAllomas.getX(), elozoAllomas.getY()));
        path.getElements().add(new LineTo(busz.getAktualisAllomas().getX(), busz.getAktualisAllomas().getY()));

        double distance = Math.hypot(busz.getAktualisAllomas().getX() - elozoAllomas.getX(), busz.getAktualisAllomas().getY() - elozoAllomas.getY() * 10);
        pathTransition.setDuration(Duration.millis(distance));
        pathTransition.setPath(path);
        labelAllomas.setVisible(true);
        labelAllomas.setText("" + busz.getAktualisAllomas().getName());
        pathTransition.play();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void OnStepByStep(MouseEvent mouseEvent) throws Exception {
        imageViewBusz.setVisible(true);
        autoPlay = false;
        nyomasAKovetkezoMegalloba();
    }


    public void beallitasMenuClicked(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, ParseException {
        Beallitasok b = new Beallitasok();
        /*b.showBeallitasok();
        b.utaskorMaxTF.setText("sdaasd");*/
    }

    public void kilepesMenuClicked(ActionEvent actionEvent) {
        Platform.exit();

    }

    public void restartMenuButtonClicked(ActionEvent actionEvent) throws Exception {
        busz.buszAStartPoziciora();
        Allomas kezdoAllomas = busz.getAktualisAllomas();
        imageViewBusz.setX(kezdoAllomas.getX());
        imageViewBusz.setY(kezdoAllomas.getY());
        imageViewBusz.setVisible(true);

        autoPlay = true;
        nyomasAKovetkezoMegalloba();
    }

    public void utasUjrageneralasMenuClicked(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        DataBase dataBase = new DataBase();
        dataBase.refreshAllRow(200, 200, 10, 100, 0);
    }

    public Label getLabelAllomas() {
        return labelAllomas;
    }

    public void setLabelAllomas(Label labelAllomas) {
        this.labelAllomas = labelAllomas;
    }

    public Label getLabelSzabadhelyekSzama() {
        return labelSzabadhelyekSzama;
    }

    public void setLabelSzabadhelyekSzama(Label labelSzabadhelyekSzama) {
        this.labelSzabadhelyekSzama = labelSzabadhelyekSzama;
    }

    public Label getLabelEsemenyek() {
        return labelEsemenyek;
    }

    public void setLabelEsemenyek(Label labelEsemenyek) {
        this.labelEsemenyek = labelEsemenyek;
    }

    public Label getLabelBuntetesek() {
        return labelBuntetesek;
    }

    public void setLabelBuntetesek(Label labelBuntetesek) {
        this.labelBuntetesek = labelBuntetesek;
    }

    public Label getLabelLeszallutasok() {
        return labelLeszallutasok;
    }

    public void setLabelLeszallutasok(Label labelLeszallutasok) {
        this.labelLeszallutasok = labelLeszallutasok;
    }

    public Label getLabelFelszallutasok() {
        return labelFelszallutasok;
    }

    public void setLabelFelszallutasok(Label labelFelszallutasok) {
        this.labelFelszallutasok = labelFelszallutasok;
    }



}
