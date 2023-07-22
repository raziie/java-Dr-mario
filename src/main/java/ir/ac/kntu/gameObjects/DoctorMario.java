package ir.ac.kntu.gameObjects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class DoctorMario {

    private Image holdPill;

    private Image throwPill;

    private Image loosingPose;

    private ImageView imageView;

    private Timeline timeline;

    public DoctorMario() {
        holdPill = new Image("file:src/main/resources/images/dr3.png", 140, 140, false, false);
        throwPill = new Image("file:src/main/resources/images/dr4.png", 140, 140, false, false);
        loosingPose = new Image("file:src/main/resources/images/dr5.png", 140, 140, false, false);
        imageView = new ImageView(holdPill);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Image getHoldPill() {
        return holdPill;
    }

    public Image getThrowPill() {
        return throwPill;
    }

    public Image getLoosingPose() {
        return loosingPose;
    }

    public void throwPill() {
        timeline = new Timeline(new KeyFrame(Duration.millis(300), event -> {
            imageView.setImage(throwPill);
        }));
        imageView.setImage(holdPill);
        timeline.setAutoReverse(false);
        timeline.play();
    }
}
