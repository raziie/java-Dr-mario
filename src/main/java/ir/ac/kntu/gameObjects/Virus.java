package ir.ac.kntu.gameObjects;

import ir.ac.kntu.GameLoop;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileNotFoundException;

public class Virus extends GameObject {

    private Color color;

    private boolean isDead = false;

    private ImageView imageView;

    private boolean isFirstImage = true;

    private Timeline timeline;

    public Virus(int x, int y, Color color) {
        super(x, y);
        this.color = color;
        imageView = new ImageView(getImages()[0]);
        timeline = setTimeLine();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Image[] getImages() {
        Image[] images = new Image[3];
        if (color == Color.BLUE) {
            images[0] = new Image("file:src/main/resources/images/blue virus 1.png", 50, 50, false, false);
            images[1] = new Image("file:src/main/resources/images/blue virus 2.png", 50, 50, false, false);
            images[2] = new Image("file:src/main/resources/images/destruction blue.png", 50, 50, false, false);
        } else if (color == Color.RED) {
            images[0] = new Image("file:src/main/resources/images/red virus 1.png", 50, 50, false, false);
            images[1] = new Image("file:src/main/resources/images/red virus 2.png", 50, 50, false, false);
            images[2] = new Image("file:src/main/resources/images/destruction red.png", 50, 50, false, false);
        } else {
            images[0] = new Image("file:src/main/resources/images/yellow virus 1.png", 50, 50, false, false);
            images[1] = new Image("file:src/main/resources/images/yellow virus 2.png", 50, 35, false, false);
            images[2] = new Image("file:src/main/resources/images/destruction yellow.png", 50, 50, false, false);
        }
        return images;
    }

    public Timeline setTimeLine() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(150), event -> {
            try {
                changeImage();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
        return timeline;
    }

    public void changeImage() throws FileNotFoundException {
        if (isFirstImage) {
            imageView.setImage(getImages()[1]);
            isFirstImage = false;
        } else {
            imageView.setImage(getImages()[0]);
            isFirstImage = true;
        }
    }

    @Override
    public void die(GameLoop gameLoop) {
        timeline.stop();
        imageView.setImage(getImages()[2]);
        timeline = new Timeline(new KeyFrame(Duration.millis(250), event -> {
            gameLoop.getStomach().getChildren().remove(imageView);
        }));
        timeline.setAutoReverse(false);
        timeline.play();
    }
}