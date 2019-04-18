package UI;

import Main.Allomas;
import Main.Beallitasok;
import Main.Busz;
import Main.Database;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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



    @FXML public Label labelAllomas134;
    @FXML public Label labelSzabadhelyekSzama134;
    @FXML public Label labelEsemenyek134;
    @FXML public Label labelBuntetesek134;
    @FXML public Label labelLeszallutasok134;
    @FXML public Label labelFelszallutasok134;

    @FXML public Label labelFelSzallUtas160;
    @FXML public Label labelLeSzallUtas160;
    @FXML public Label labelBuntetesek160;
    @FXML public Label labelEsemenyek160;
    @FXML public Label labelBuntetesekSzam134;
    @FXML public Label labelAllomas160;

    @FXML private ImageView imageViewBusz;

    private Beallitasok beallitasok;
    private Database database;

    private Busz busz;
    private PathTransition pathTransition;
    private boolean autoPlay;

    public MainController() {

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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void initializeData(Beallitasok beallitasok, Database database) throws ClassNotFoundException, SQLException, ParseException, IOException {
        this.beallitasok = beallitasok;
        this.database = database;

        busz = new Busz(this.database, this, "160", 100);
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
        labelAllomas134.setVisible(true);
        labelAllomas134.setText("" + busz.getAktualisAllomas().getName());
        pathTransition.play();
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void OnStepByStep() throws Exception {
        imageViewBusz.setVisible(true);
        autoPlay = false;
        nyomasAKovetkezoMegalloba();
    }


    public void beallitasMenuClicked(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException, ParseException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI/beallitasok.fxml"));
        Parent root = (Parent) loader.load();

        BeallitasokController controller = loader.getController();
        controller.initializeData(this.beallitasok, this.database);

        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Beállítások");
        stage.setScene(scene);
        stage.show();
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

    public void utasUjrageneralasMenuClicked(ActionEvent actionEvent) throws SQLException {
        this.database.refreshAllRow();
    }

    public void OnStepByStep134KeyPressed(KeyEvent keyEvent) throws Exception {
        imageViewBusz.setVisible(true);
        autoPlay = false;
        nyomasAKovetkezoMegalloba();
    }

    public void OnStepByStep160KeyPressed(MouseEvent mouseEvent) {

    }

    public Label getLabelAllomas134() {
        return labelAllomas134;
    }

    public void setLabelAllomas134(Label labelAllomas134) {
        this.labelAllomas134 = labelAllomas134;
    }

    public Label getLabelSzabadhelyekSzama134() {
        return labelSzabadhelyekSzama134;
    }

    public void setLabelSzabadhelyekSzama134(Label labelSzabadhelyekSzama134) {
        this.labelSzabadhelyekSzama134 = labelSzabadhelyekSzama134;
    }

    public Label getLabelEsemenyek134() {
        return labelEsemenyek134;
    }

    public void setLabelEsemenyek134(Label labelEsemenyek134) {
        this.labelEsemenyek134 = labelEsemenyek134;
    }

    public Label getLabelBuntetesek134() {
        return labelBuntetesek134;
    }

    public void setLabelBuntetesek134(Label labelBuntetesek134) {
        this.labelBuntetesek134 = labelBuntetesek134;
    }

    public Label getLabelLeszallutasok134() {
        return labelLeszallutasok134;
    }

    public void setLabelLeszallutasok134(Label labelLeszallutasok134) {
        this.labelLeszallutasok134 = labelLeszallutasok134;
    }

    public Label getLabelFelszallutasok134() {
        return labelFelszallutasok134;
    }

    public void setLabelFelszallutasok134(Label labelFelszallutasok134) {
        this.labelFelszallutasok134 = labelFelszallutasok134;
    }

    public Label getLabelFelSzallUtas160() {
        return labelFelSzallUtas160;
    }

    public void setLabelFelSzallUtas160(Label labelFelSzallUtas160) {
        this.labelFelSzallUtas160 = labelFelSzallUtas160;
    }

    public Label getLabelLeSzallUtas160() {
        return labelLeSzallUtas160;
    }

    public void setLabelLeSzallUtas160(Label labelLeSzallUtas160) {
        this.labelLeSzallUtas160 = labelLeSzallUtas160;
    }

    public Label getLabelBuntetesek160() {
        return labelBuntetesek160;
    }

    public void setLabelBuntetesek160(Label labelBuntetesek160) {
        this.labelBuntetesek160 = labelBuntetesek160;
    }

    public Label getLabelEsemenyek160() {
        return labelEsemenyek160;
    }

    public void setLabelEsemenyek160(Label labelEsemenyek160) {
        this.labelEsemenyek160 = labelEsemenyek160;
    }

    public Label getLabelBuntetesekSzam134() {
        return labelBuntetesekSzam134;
    }

    public void setLabelBuntetesekSzam134(Label labelBuntetesekSzam134) {
        this.labelBuntetesekSzam134 = labelBuntetesekSzam134;
    }

    public Label getLabelAllomas160() {
        return labelAllomas160;
    }

    public void setLabelAllomas160(Label labelAllomas160) {
        this.labelAllomas160 = labelAllomas160;
    }


}
