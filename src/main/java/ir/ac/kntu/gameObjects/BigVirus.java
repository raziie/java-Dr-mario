package ir.ac.kntu.gameObjects;

import ir.ac.kntu.GameLoop;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class BigVirus {

    private Color color;

    private Image[] images;

    private ImageView imageView;

    private Timeline timeline;

    private GameLoop gameLoop;

    private PathTransition pathTransition;

    public BigVirus(Color color, GameLoop gameLoop) {
        this.color = color;
        images = new Image[6];
        switch (color) {
            case BLUE -> setBlueImages();
            case RED -> setRedImages();
            case YELLOW -> setYellowImages();
            default -> {
            }
        }
        imageView = new ImageView(images[0]);
        this.gameLoop = gameLoop;
        setTimeLine();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setBlueImages() {
        images[0] = new Image("file:src/main/resources/images/blue big virus1.png", 80, 80, false, false);
        images[1] = new Image("file:src/main/resources/images/blue big virus2.png", 80, 80, false, false);
        images[2] = new Image("file:src/main/resources/images/blue big virus3.png", 80, 80, false, false);
        images[3] = new Image("file:src/main/resources/images/blue big virus4.png", 80, 80, false, false);
        images[4] = new Image("file:src/main/resources/images/blue big virus5.png", 80, 80, false, false);
        images[5] = new Image("file:src/main/resources/images/blue big virus6.png", 80, 80, false, false);
    }

    public void setRedImages() {
        images[0] = new Image("file:src/main/resources/images/red big virus1.png", 80, 80, false, false);
        images[1] = new Image("file:src/main/resources/images/red big virus2.png", 80, 80, false, false);
        images[2] = new Image("file:src/main/resources/images/red big virus3.png", 80, 80, false, false);
        images[3] = new Image("file:src/main/resources/images/red big virus4.png", 80, 80, false, false);
        images[4] = new Image("file:src/main/resources/images/red big virus5.png", 80, 80, false, false);
        images[5] = new Image("file:src/main/resources/images/red big virus6.png", 80, 80, false, false);
    }

    public void setYellowImages() {
        images[0] = new Image("file:src/main/resources/images/yellow big virus1.png", 80, 80, false, false);
        images[1] = new Image("file:src/main/resources/images/yellow big virus2.png", 80, 80, false, false);
        images[2] = new Image("file:src/main/resources/images/yellow big virus3.png", 80, 80, false, false);
        images[3] = new Image("file:src/main/resources/images/yellow big virus4.png", 80, 80, false, false);
        images[4] = new Image("file:src/main/resources/images/yellow big virus5.png", 80, 80, false, false);
        images[5] = new Image("file:src/main/resources/images/yellow big virus6.png", 80, 80, false, false);
    }

    public void setTimeLine() {
        final int[] index = {2};
        timeline = new Timeline(new KeyFrame(Duration.millis(200), event -> {
            imageView.setImage(images[index[0]]);
            if (index[0] == 4) {
                index[0] = 0;
            } else {
                index[0] += 2;
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
    }

    public void setPath(int number) {
        Circle path = new Circle(60);
        path.setLayoutX(150);
        path.setLayoutY(500);
        pathTransition = new PathTransition();
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setCycleCount(Animation.INDEFINITE);
        pathTransition.setAutoReverse(false);
        pathTransition.setDuration(Duration.seconds(9));
        switch (number) {
            case 1 -> pathTransition.setDelay(Duration.seconds(0.1));
            case 2 -> pathTransition.setDelay(Duration.seconds(3));
            case 3 -> pathTransition.setDelay(Duration.seconds(6));
            default -> {
            }
        }
        setAddTimeline();
        pathTransition.play();
    }

    public void setAddTimeline() {
        Timeline addViruses = new Timeline(new KeyFrame(pathTransition.getDelay(), event -> {
            gameLoop.getRoot().getChildren().add(imageView);
        }));
        addViruses.setCycleCount(1);
        addViruses.setAutoReverse(false);
        addViruses.play();
    }

    public void setGameOverTimeLine() {
        pathTransition.stop();
        timeline.stop();
        imageView.setImage(images[2]);
        final int[] index = {2};
        timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            imageView.setImage(images[index[0]]);
            if (index[0] == 2) {
                index[0] = 1;
            } else {
                index[0] = 2;
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
    }
}