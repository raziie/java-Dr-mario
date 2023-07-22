package ir.ac.kntu.gameObjects;

import ir.ac.kntu.GameLoop;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HalfPill extends GameObject{

    private Color color;

    private ImageView imageView;

    public HalfPill(int x, int y, Color color) {
        super(x, y);
        this.color = color;
        imageView = new ImageView(getImages()[0]);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Image[] getImages() {
        Image[] images = new Image[2];
        switch (color) {
            case BLUE -> {
                images[0] = new Image("file:src/main/resources/images/blue half pill.png", 50, 50, false, false);
                images[1] = new Image("file:src/main/resources/images/destruction blue.png", 50, 50, false, false);
            }
            case RED -> {
                images[0] = new Image("file:src/main/resources/images/red half pill.png", 50, 50, false, false);
                images[1] = new Image("file:src/main/resources/images/destruction red.png", 50, 50, false, false);
            }
            case YELLOW -> {
                images[0] = new Image("file:src/main/resources/images/yellow half pill.png", 50, 50, false, false);
                images[1] = new Image("file:src/main/resources/images/destruction yellow.png", 50, 50, false, false);
            }
            default -> {}
        }
        return images;
    }

    @Override
    public void die(GameLoop gameLoop) {
        imageView.setImage(getImages()[1]);
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(250), event -> {
            gameLoop.getStomach().getChildren().remove(imageView);
        }));
        timeline.setAutoReverse(false);
        timeline.play();
    }
}