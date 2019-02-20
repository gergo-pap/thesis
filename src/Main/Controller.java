package Main;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller extends Application {
    public Path pathvalamii;

    @Override
    public void start(Stage stage) {

        Button btn = new Button("Genuine Coder");


        //Create new path transition

        //Set node to be animated
        Image image = new Image("bus.png");

        ImageView imageView = new ImageView();
        imageView.setFitHeight(15);
        imageView.setFitWidth(15);
        imageView.setFitHeight(300);
        imageView.setFitWidth(300);
        imageView.setImage(image);
        btn.setStyle("-fx-background-image: url('map.jpg')");
        btn.setMinHeight(1000);
        btn.setMinWidth(1000);
        Path path = new Path();

        path.getElements().add(new MoveTo(0,0));
        path.getElements().add(new CubicCurveTo(380, 900, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(10000));
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.setPath(path);
        pathTransition.play();
        Group group = new Group(btn, imageView);
        Scene scene = new Scene(group, 600, 600);
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        Application.launch(args);

    }


}
