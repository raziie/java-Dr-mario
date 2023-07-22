package ir.ac.kntu.gameObjects;

import ir.ac.kntu.GameLoop;
import ir.ac.kntu.constants.GlobalConstants;
import ir.ac.kntu.map.Map;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.Random;

public class Pill extends GameObject{

    public enum Type {BLUE_RED, BLUE_YELLOW, BLUE, RED_BLUE, RED_YELLOW, RED, YELLOW_BLUE, YELLOW_RED, YELLOW}

    private Type type;

    private ImageView imageView;

    private GameLoop gameLoop;

    private Pane root;

    private Timeline timeline;

    private boolean isVertical = false;

    private boolean isAllowedToMove = false;

    public Pill(Type type, GameLoop gameLoop, Pane root) {
        super(84, 0);
        this.type = type;
        imageView = new ImageView(getImages()[1]);
        this.gameLoop = gameLoop;
        this.root = root;
        setThrowingAnimation();
    }

    public ImageView getImageView() {
        return imageView;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public Type getType() {
        return type;
    }

    public Image[] getImages() {
        Image[] images = new Image[2];
        if(type == Type.BLUE_RED) {
            images = getBlueRedImages();
        }else if(type == Type.BLUE_YELLOW) {
            images = getBlueYellowImages();
        }else if(type == Type.BLUE) {
            images = getBlueImages();
        }else if(type == Type.RED_BLUE) {
            images = getRedBlueImages();
        }else if(type == Type.RED_YELLOW) {
            images = getRedYellowImages();
        }else if(type == Type.RED) {
            images = getRedImages();
        }else if(type == Type.YELLOW_BLUE) {
            images = getYellowBlueImages();
        }else if(type == Type.YELLOW_RED) {
            images = getYellowRedImages();
        }else if(type == Type.YELLOW) {
            images = getYellowImages();
        }
        return images;
    }

    public Image[] getBlueRedImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/blue_red pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/blue_red pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getBlueYellowImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/blue_yellow pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/blue_yellow pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getBlueImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/blue pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/blue pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getRedBlueImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/red_blue pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/red_blue pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getRedYellowImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/red_yellow pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/red_yellow pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getRedImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/red pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/red pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getYellowBlueImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/yellow_blue pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/yellow_blue pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getYellowRedImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/yellow_red pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/yellow_red pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public Image[] getYellowImages() {
        Image[] result = new Image[2];
        result[0] = new Image("file:src/main/resources/images/yellow pill vertical.png",
                GlobalConstants.PILL_WIDTH, GlobalConstants.PILL_HEIGHT, false, false);
        result[1] = new Image("file:src/main/resources/images/yellow pill horizontal.png",
                GlobalConstants.PILL_HEIGHT, GlobalConstants.PILL_WIDTH, false, false);
        return result;
    }

    public  static Pill generateRandomPill(GameLoop gameLoop, Pane root) {
        int random = new Random().nextInt(9);
        if(random == 0) {
            return new Pill(Type.BLUE_RED, gameLoop, root);
        }else if(random == 1) {
            return new Pill(Type.BLUE_YELLOW, gameLoop, root);
        }else if(random == 2) {
            return new Pill(Type.BLUE, gameLoop, root);
        }else if(random == 3) {
            return new Pill(Type.RED_BLUE, gameLoop, root);
        }else if(random == 4) {
            return new Pill(Type.RED_YELLOW, gameLoop, root);
        }else if(random == 5) {
            return new Pill(Type.RED, gameLoop, root);
        }else if(random == 6) {
            return new Pill(Type.YELLOW_BLUE, gameLoop, root);
        }else if(random == 7) {
            return new Pill(Type.YELLOW_RED, gameLoop, root);
        }else {
            return new Pill(Type.YELLOW, gameLoop, root);
        }
    }

    public void moveDown() {
        if(canMoveDown()) {
            setY(getY() + GlobalConstants.PILL_STEP);
            imageView.setLayoutY(getY());
        }else if(isAllowedToMove) {
            Map.addToMap(gameLoop.getCurrentPill());
            Map.destroyIfNeeded(gameLoop);
            if(getY() == 0) {
                gameLoop.gameOver();
            }else {
                gameLoop.generatePill();
            }
            timeline.stop();
        }
    }

    public boolean canMoveDown() {
        if(!isAllowedToMove) {
            return false;
        }else if(isVertical) {
            if(getY() + (GlobalConstants.PILL_STEP) <= 385) {
                try {
                    return !Map.isCollided(this, Direction.DOWN);
                }catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }else {
            if(getY() + GlobalConstants.PILL_STEP <= 420) {
                try {
                    return !Map.isCollided(this, Direction.DOWN);
                }catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return false;
    }

    public void moveLeft() {
        if(canMoveLeft()) {
            setX(getX() - GlobalConstants.PILL_STEP);
            imageView.setLayoutX(getX());
        }
    }

    public boolean canMoveLeft() {
        if(!isAllowedToMove) {
            return false;
        }else if(getX() - GlobalConstants.PILL_STEP >= 0) {
            try {
                return !Map.isCollided(this, Direction.LEFT);
            }catch (IndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public void moveRight() {
        if(canMoveRight()) {
            setX(getX() + GlobalConstants.PILL_STEP);
            imageView.setLayoutX(getX());
        }
    }

    public boolean canMoveRight() {
        if(!isAllowedToMove) {
            return false;
        }else if(isVertical) {
            if(getX() + GlobalConstants.PILL_STEP <= 310) {
                try {
                    return !Map.isCollided(this, Direction.RIGHT);
                }catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }else {
            if(getX() + GlobalConstants.PILL_STEP <= 280) {
                try {
                    return !Map.isCollided(this, Direction.RIGHT);
                }catch (IndexOutOfBoundsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return false;
    }

    public void rotate() {
        if(canRotate()) {
            if(isVertical) {
                imageView.setImage(getImages()[1]);
                isVertical = false;
            }else {
                imageView.setImage(getImages()[0]);
                isVertical = true;
            }
        }
    }

    public boolean canRotate() {
        //TODO
        return true;
    }

    @Override
    public void die(GameLoop gameLoop) {
        split();
    }

    public void split() {
        HalfPill firstHalf = createGFirstHalf();
        HalfPill secondHalf = createSecondHalf();
        gameLoop.addGameObject(firstHalf);
        gameLoop.getStomach().getChildren().add(firstHalf.getImageView());
        gameLoop.addGameObject(secondHalf);
        gameLoop.getStomach().getChildren().add(secondHalf.getImageView());
    }

    public HalfPill createGFirstHalf() {
        if(Map.firstIsBlue(this.getType())) {
            return new HalfPill(getX(), getY(), Color.BLUE);
        }else if(Map.firstIsRed(this.getType())) {
            return new HalfPill(getX(), getY(), Color.RED);
        }else {
            return new HalfPill(getX(),getY(), Color.YELLOW);
        }
    }

    public HalfPill createSecondHalf() {
        if(isVertical) {
            if (Map.secondIsBlue(this.getType())) {
                return new HalfPill(getX(), getY() + 25, Color.BLUE);
            } else if (Map.secondIsRed(this.getType())) {
                return new HalfPill(getX(), getY() + 25, Color.RED);
            } else {
                return new HalfPill(getX(), getY() + 25, Color.YELLOW);
            }
        }else {
            if (Map.secondIsBlue(this.getType())) {
                return new HalfPill(getX() + 25, getY(), Color.BLUE);
            } else if (Map.secondIsRed(this.getType())) {
                return new HalfPill(getX() + 25, getY(), Color.RED);
            } else {
                return new HalfPill(getX() + 25, getY(), Color.YELLOW);
            }
        }
    }

    public void setThrowingAnimation() {
        Path path = new Path();
        path.getElements().add(new MoveTo(270, -25));
        ArcTo arcTo = new ArcTo();
        arcTo.setX(20);
        arcTo.setY(0);
        arcTo.setRadiusX(60);
        arcTo.setRadiusY(60);
        path.getElements().add(arcTo);
        path.getElements().add(new LineTo(30, 0));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(1.5));
        pathTransition.setPath(path);
        pathTransition.setNode(imageView);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
        pathTransition.setOnFinished(e -> {
            setTimeLine();
            isAllowedToMove = true;
        });
    }

    public void setTimeLine() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            moveDown();
        }));
        this.timeline = timeline;
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.play();
    }
}