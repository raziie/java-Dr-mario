package ir.ac.kntu;

import ir.ac.kntu.constants.GlobalConstants;
import ir.ac.kntu.gameObjects.*;
import ir.ac.kntu.map.Map;
import ir.ac.kntu.menu.Menu;
import ir.ac.kntu.menu.PlayersMenu;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GameLoop implements Serializable {

    public static ArrayList<Player> players;

    private Stage stage;

    private DoctorMario doctor;

    private ArrayList<Virus> viruses;

    private ArrayList<GameObject> gameObjects;

    private Pane stomach;

    private boolean isGameOver = false;

    public static Player currentPlayer;

    private Pill currentPill;

    private Pane root= new Pane();

    private Map map;

    private BigVirus blueVirus;

    private BigVirus redVirus;

    private BigVirus yellowVirus;

    public GameLoop(Stage stage)  {
        this.stage = stage;
        players = new ArrayList<>();
        doctor = new DoctorMario();
        viruses = new ArrayList<>();
        gameObjects = new ArrayList<>();
        stomach = new Pane();
        map = new Map();
    }

    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
    }

    public void removeGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    public Pill getCurrentPill() {
        return currentPill;
    }

    public Pane getStomach() {
        return stomach;
    }

    public Pane getRoot() {
        return root;
    }

    public Scene getGameScene() throws FileNotFoundException {
        adjustRoot(root);
        root.getStyleClass().add("root");
        root.setBackground(Menu.getBackground(new File("src/main/resources/images/MainScene2.png")));
        root.getChildren().add(stomach);
        stomach.setLayoutX(310);
        stomach.setLayoutY(210);
        setTexts();
        setDoctor();
        placeViruses();
        setBigViruses();
        generatePill();
        doctor.throwPill();
        Scene scene = new Scene(root, 870, 695);
        setMoveOnKeyPressed(scene);
        return scene;
    }

    public void adjustRoot(Pane root) {
        root.getStyleClass().add("root");
        try {
            root.getStylesheets().add(
                    this.getClass()
                            .getResource("/style.css")
                            .toString());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void setTexts() {
        Text text1 = new Text("Top\n"/* + currentPlayer.getHighScore()*/);
        text1.setStyle(getTextStyle());
        Text text2 = new Text("Score");
        text2.setStyle(getTextStyle());
        Text text3 = new Text("Level\n" + Menu.level);
        text3.setStyle(getTextStyle());
        Text text4 = new Text("Speed\n" + Menu.speed);
        text4.setStyle(getTextStyle());
        Text text5 = new Text("Virus\n" + viruses.size());
        text5.setStyle(getTextStyle());
        root.getChildren().addAll(text1, text2, text3, text4, text5);
        text1.setLayoutX(60);
        text1.setLayoutY(150);
        text2.setLayoutX(60);
        text2.setLayoutY(230);
        text3.setLayoutX(630);
        text3.setLayoutY(430);
        text4.setLayoutX(630);
        text4.setLayoutY(510);
        text5.setLayoutX(630);
        text5.setLayoutY(590);
    }

    public String getTextStyle() {
        return """
                -fx-font: 40px "Copperplate";
                -fx-fill: black;
                -fx-font-weight: bold;
                -fx-font-size: 25px;""".indent(4);
    }

    public void placeViruses() throws FileNotFoundException {
        for(int row=0; row<Map.map.length; row++) {
            for(int column=0; column<Map.map[row].length; column++) {
                switch (Map.map[row][column]) {
                    case "b" -> createVirus(row, column, Color.BLUE);
                    case "y" -> createVirus(row, column, Color.YELLOW);
                    case "r" -> createVirus(row, column, Color.RED);
                    default -> {}
                }
            }
        }
    }

    public void createVirus(int x, int y, Color color) {
        Virus virus = new Virus(x * 28, y * 25, color);
        viruses.add(virus);
        gameObjects.add(virus);
        ImageView image = virus.getImageView();
        stomach.getChildren().add(image);
        image.setLayoutX(virus.getX());
        image.setLayoutY(virus.getY());
    }

    public void setDoctor() {
        ImageView image = doctor.getImageView();
        root.getChildren().add(image);
        image.setLayoutX(630);
        image.setLayoutY(195);
    }

    public void generatePill() {
        currentPill = Pill.generateRandomPill(this, root);
        gameObjects.add(currentPill);
        ImageView image = currentPill.getImageView();
        stomach.getChildren().add(image);
        image.setLayoutX(currentPill.getX());
        image.setLayoutY(currentPill.getY());
        doctor.throwPill();
    }

    public void setMoveOnKeyPressed(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch(event.getCode()) {
                case RIGHT -> currentPill.moveRight();
                case LEFT -> currentPill.moveLeft();
                case DOWN -> currentPill.moveDown();
                case X -> currentPill.rotate();
                default -> {}
            }
        });
    }

    public void gameOver() {
        isGameOver = true;
        doctor.getImageView().setImage(doctor.getLoosingPose());
        ImageView gameOver = new ImageView(new Image("file:src/main/resources/images/GameOver.png", 140, 100, false, false));
        stomach.getChildren().clear();
        stomach.getChildren().add(gameOver);
        gameOver.setLayoutX(55);
        gameOver.setLayoutY(140);
        setRestartButton();
        blueVirus.setGameOverTimeLine();
        redVirus.setGameOverTimeLine();
        yellowVirus.setGameOverTimeLine();
    }

    public void setRestartButton() {
        Button restart = new Button("< restart >");
        restart.getStyleClass().add("restart");
        restart.setStyle("-fx-border-color: #000000;\n" +
                "    -fx-padding: 5px 15px;\n" +
                "    -fx-font-size: 20px;\n" +
                "    -fx-font-family: \"papyrus\";");
        restart.setOnAction(event -> {
            stomach.getChildren().clear();
            map = new Map();
            try {
                placeViruses();
                blueVirus.getTimeline().stop();
                redVirus.getTimeline().stop();
                yellowVirus.getTimeline().stop();
                blueVirus.setTimeLine();
                redVirus.setTimeLine();
                yellowVirus.setTimeLine();
                blueVirus.setPath(1);
                redVirus.setPath(2);
                yellowVirus.setPath(3);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            generatePill();
        });
        stomach.getChildren().add(restart);
        restart.setLayoutX(60);
        restart.setLayoutY(340);
    }

    public void setBigViruses() {
        blueVirus = new BigVirus(Color.BLUE, this);
        redVirus = new BigVirus(Color.RED, this);
        yellowVirus = new BigVirus(Color.YELLOW, this);
        ImageView blueVirusImage = blueVirus.getImageView();
        ImageView redVirusImage = redVirus.getImageView();
        ImageView yellowVirusImage = yellowVirus.getImageView();
        //root.getChildren().addAll(blueVirusImage, redVirusImage, yellowVirusImage);
        /*blueVirusImage.setLayoutX(152);
        blueVirusImage.setLayoutY(420);
        redVirusImage.setLayoutX(125);
        redVirusImage.setLayoutY(520);
        yellowVirusImage.setLayoutX(58);
        yellowVirusImage.setLayoutY(448);*/
        blueVirus.setPath(1);
        redVirus.setPath(2);
        yellowVirus.setPath(3);
    }

    public void removeImages(ArrayList<int[]> toRemove) {
        for(int[] coordinate : toRemove) {
            for (GameObject gameObject : gameObjects) {
                if (matchesTheCoordinate(coordinate, gameObject) || isVerticalSecondHalf(coordinate, gameObject)
                        || isHorizontalSecondHalf(coordinate, gameObject)) {
                    gameObject.die(this);
                    if(gameObject.getClass().equals(Pill.class)) {
                        stomach.getChildren().remove(((Pill) gameObject).getImageView());
                        removeGameObject(gameObject);
                        removeImages(toRemove);
                        return;
                    }
                }
            }
        }
    }

    public boolean matchesTheCoordinate(int[] coordinate, GameObject gameObject) {
        return (coordinate[0] * 25 == gameObject.getX() && coordinate[1] * 25 == gameObject.getY());
    }

    public boolean isVerticalSecondHalf(int[] coordinate, GameObject gameObject) {
        return (gameObject.getClass().equals(Pill.class) && ((Pill) gameObject).isVertical() &&
                coordinate[0] * 25 == gameObject.getX() && (coordinate[1] - 1) * 25 == gameObject.getY());
    }

    public boolean isHorizontalSecondHalf(int[] coordinate, GameObject gameObject) {
        return (gameObject.getClass().equals(Pill.class) && !((Pill) gameObject).isVertical() &&
                (coordinate[0] - 1) * 25 == gameObject.getX() && coordinate[1] * 25 == gameObject.getY());
    }
}