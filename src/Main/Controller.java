package Main;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {


    @Override
    public void start(Stage stage) {

        int maxHeight = 800;
        int maxWidht = 800;


        Image imageBus = new Image("bus2.png");
        Image imageMap = new Image("map.jpg");

        ImageView imageView = new ImageView();
        ImageView imageViewMap = new ImageView();
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setImage(imageBus);

        imageViewMap.setFitHeight(maxHeight);
        imageViewMap.setFitWidth(maxWidht);
        imageViewMap.setImage(imageMap);

        Path path = new Path();


        path.getElements().add (new MoveTo (453, 161)); // x(vízszint),y(függő)

        path.getElements().add (new LineTo (453, 161));
        path.getElements().add (new LineTo (438, 202));
        path.getElements().add (new LineTo (424, 200));
        path.getElements().add (new LineTo (406, 199));
        path.getElements().add (new LineTo (400, 215));
        path.getElements().add (new LineTo (400, 215));
        path.getElements().add (new LineTo (399, 214));
        path.getElements().add (new LineTo (393, 230));
        path.getElements().add (new LineTo (389, 275));
        path.getElements().add (new LineTo (410, 279));
        path.getElements().add (new LineTo (402, 301));
        path.getElements().add (new LineTo (396, 315));
        path.getElements().add (new LineTo (386, 318));
        path.getElements().add (new LineTo (380, 339));
        path.getElements().add (new LineTo (378, 375));
        path.getElements().add (new LineTo (382, 392));
        path.getElements().add (new LineTo (376, 424));
        path.getElements().add (new LineTo (367, 456));
        path.getElements().add (new LineTo (359, 500));
        path.getElements().add (new LineTo (353, 532));
        path.getElements().add (new LineTo (351, 546));
        path.getElements().add (new LineTo (366, 566));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(5000));
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.setPath(path);

        pathTransition.play();
        Group group = new Group(imageViewMap, imageView);
        Scene scene = new Scene(group, maxWidht, maxHeight);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);

    }


}
