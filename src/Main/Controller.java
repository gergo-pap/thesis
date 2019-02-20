package Main;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {
    public Path pathvalamii;

    @Override
    public void start(Stage stage) {

        Button btn = new Button("Genuine Coder");
        int maxHeight = 800;
        int maxWidht = 800;

        //Create new path transition

        //Set node to be animated
        Image imageBus = new Image("bus.png");
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



        //path.getElements().add(new CubicCurveTo(0, 900, 380, 120, 200, 120));
        //path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        path.getElements().add (new MoveTo (475, 165)); // x(vízszint),y(függő)

        path.getElements().add (new LineTo (475, 188));
        path.getElements().add (new LineTo (460, 215));
        path.getElements().add (new LineTo (435, 225));
        path.getElements().add (new LineTo (410, 225));
        path.getElements().add (new LineTo (415, 235));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(4000));
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
