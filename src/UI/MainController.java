package UI;

import Main.Allomas;
import Main.Beallitasok;
import Main.Busz;
import Main.Database;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MainController {

    private final List<String> jaratok = Arrays.asList("160", "134");

    @FXML private ImageView busz1Img;
    @FXML private Label busz1Jaratszam;
    @FXML private Label busz1Allomas;
    @FXML private TextArea busz1Info;

    @FXML private ImageView busz2Img;
    @FXML private Label busz2Jaratszam;
    @FXML private Label busz2Allomas;
    @FXML private TextArea busz2Info;


    private Beallitasok beallitasok;
    private Database database;

    private Busz busz1;
    private PathTransition busz1PathTransition;
    private boolean busz1AutoPlay;

    private Busz busz2;
    private PathTransition busz2PathTransition;
    private boolean busz2AutoPlay;


    public MainController() {
        busz1AutoPlay = false;
        busz2AutoPlay = false;

        busz1PathTransition = new PathTransition();
        busz1PathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        busz1PathTransition.setCycleCount(1);
        busz1PathTransition.setOnFinished(actionEvent -> {
            try {
                if (busz1AutoPlay) {
                    busz1NyomasAKovetkezoMegalloba();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        busz2PathTransition = new PathTransition();
        busz2PathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        busz2PathTransition.setCycleCount(1);
        busz2PathTransition.setOnFinished(actionEvent -> {
            try {
                if (busz2AutoPlay) {
                    busz2NyomasAKovetkezoMegalloba();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void initializeData(Beallitasok beallitasok, Database database) throws ParseException, IOException {
        String jaratszam;

        this.beallitasok = beallitasok;
        this.database = database;

        jaratszam = this.jaratok.get(0);
        this.busz1Jaratszam.setText(jaratszam);
        this.busz1 = new Busz(jaratszam, this.database);

        jaratszam = this.jaratok.get(1);
        this.busz2Jaratszam.setText(jaratszam);
        this.busz2 = new Busz(jaratszam, this.database);
    }

    public void initialize() {
        busz1PathTransition.setNode(busz1Img);
        busz2PathTransition.setNode(busz2Img);
    }

    private void busz1NyomasAKovetkezoMegalloba() throws Exception {
        if (nyomasAKovetkezoMegalloba(busz1, busz1PathTransition)) return;

        this.busz1Allomas.setText(busz1.getAktualisAllomas().getName());
        this.busz1Info.setText(busz1.getBuszInfo());

        busz1PathTransition.play();
    }
    
    private void busz2NyomasAKovetkezoMegalloba() throws Exception {
        if (nyomasAKovetkezoMegalloba(busz2, busz2PathTransition)) return;

        this.busz2Allomas.setText(busz2.getAktualisAllomas().getName());
        this.busz2Info.setText(busz2.getBuszInfo());

        busz2PathTransition.play();
    }

    private boolean nyomasAKovetkezoMegalloba(Busz busz, PathTransition pathTransition) throws Exception {
        Allomas elozoAllomas = busz.getAktualisAllomas();
        boolean utonVan = busz.kovetkezoMegallo();
        if (!utonVan) {
            return true;
        }

        Path path = new Path();
        path.getElements().add(new MoveTo(elozoAllomas.getX(), elozoAllomas.getY()));
        path.getElements().add(new LineTo(busz.getAktualisAllomas().getX(), busz.getAktualisAllomas().getY()));

        double distance = Math.hypot(busz.getAktualisAllomas().getX() - elozoAllomas.getX(), busz.getAktualisAllomas().getY() - elozoAllomas.getY());
        pathTransition.setDuration(Duration.millis(distance * 30));
        pathTransition.setPath(path);

        return false;
    }

    public void beallitasMenuClicked(ActionEvent actionEvent) throws IOException {
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
        Allomas kezdoAllomas;

        busz1.buszAStartPoziciora();
        kezdoAllomas = busz1.getAktualisAllomas();
        busz1Img.setX(kezdoAllomas.getX());
        busz1Img.setY(kezdoAllomas.getY());
        busz1Img.setVisible(true);
        busz1AutoPlay = true;
        busz1NyomasAKovetkezoMegalloba();
        
        
        busz2.buszAStartPoziciora();
        kezdoAllomas = busz2.getAktualisAllomas();
        busz2Img.setX(kezdoAllomas.getX());
        busz2Img.setY(kezdoAllomas.getY());
        busz2Img.setVisible(true);
        busz2AutoPlay = true;
        busz2NyomasAKovetkezoMegalloba();
    }

    public void utasUjrageneralasMenuClicked(ActionEvent actionEvent) throws SQLException {
        this.database.refreshAllRow();
    }

    @FXML
    private void busz1StepByStep() throws Exception {
        busz1Img.setVisible(true);
        busz1AutoPlay = false;
        busz1NyomasAKovetkezoMegalloba();
    }

    @FXML
    private void busz2StepByStep() throws Exception {
        busz2Img.setVisible(true);
        busz2AutoPlay = false;
        busz2NyomasAKovetkezoMegalloba();
    }
}
