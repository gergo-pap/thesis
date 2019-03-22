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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Controller extends Application {


    public static void main(String[] args) {
        Application.launch(args);
    }

    public void buszKozlekedik() throws SQLException, ClassNotFoundException, InterruptedException {
        int mennyiUtasTEszt = 85;
        DataBase dataBase = new DataBase();

        //dataBase.createUtasokTable();
        //dataBase.postUtasNumberOfTimes(mennyiUtasTEszt);

        Busz busz = new Busz("34", 100);
        //dataBase.createUtvonalakTable();
        //dataBase.postUtvonal34("134");
        System.out.println(dataBase.getAllomasokLista("134"));
        busz.buszKozlekedik();
    }

    @Override
    public void start(Stage stage) throws IOException, ParseException {

        int maxHeight = 800;
        int maxWidth = 800;

        JSONParser parser = new JSONParser();
        JSONObject jaratok = (JSONObject) parser.parse(new FileReader("jaratok.json"));
        JSONArray jarat34 = (JSONArray) jaratok.get("34");

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


        JSONObject megallo = (JSONObject) jarat34.get(0);
        double x = ((Long) megallo.get("x")).doubleValue();
        double y = ((Long) megallo.get("y")).doubleValue();

        path.getElements().add(new MoveTo(x, y));

        for (Object megallo_obj : jarat34) {
            megallo = (JSONObject) megallo_obj;
            x = ((Long) megallo.get("x")).doubleValue();
            y = ((Long) megallo.get("y")).doubleValue();

            path.getElements().add(new LineTo(x, y));
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
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


        stage.show();


    }


}
