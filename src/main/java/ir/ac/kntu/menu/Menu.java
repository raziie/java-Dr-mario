package ir.ac.kntu.menu;

import ir.ac.kntu.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static ir.ac.kntu.constants.GlobalConstants.MAX_VIRUS_LEVEL;

public class Menu {

    public enum Speed{LOW, MED, HI}

    private GridPane root;

    private Scene scene;

    private Stage stage;

    public static int level = 0;

    public static Speed speed;

    public ToggleGroup toggleGroup1 = new ToggleGroup();

    public ToggleGroup toggleGroup2 = new ToggleGroup();

    public Menu(GridPane root, Scene scene, Stage stage) throws IOException {
        this.root = root;
        this.scene = scene;
        this.stage = stage;
        drawMenu();
    }

    public void drawMenu() throws IOException {
        root.setBackground(getBackground(new File("src/main/resources/images/jpg2png/menu3.png")));
        Button play = new Button("play");
        Button exit = new Button("exit");
        setHandler(play, "play");
        setHandler(exit, "exit");
        root.getStyleClass().add("root");
        play.getStyleClass().add("play");
        exit.getStyleClass().add("exit");
        root.getChildren().addAll(play, exit);
        root.setMargin(play,new Insets(100,100,150,110));
        root.setMargin(exit,new Insets(200,100,100,110));
    }

    public static Background getBackground(File file) throws FileNotFoundException {
        BackgroundImage backgroundImage = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        } catch (FileNotFoundException e) {
            System.out.println("File:" + e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new Background(backgroundImage);
    }

    public void setHandler(Button button, String label) {
        switch (label) {
            case "play" -> button.setOnAction(event -> {
                try {
                    stage.setScene(getSettingScene());
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }
                stage.show();
            });
            case "exit" -> button.setOnAction(event -> {
                stage.close();
            });
            default -> {

            }
        }
    }

    public Scene getSettingScene() throws FileNotFoundException {
        GridPane root= new GridPane();
        new Main().adjustRoot(root);
        root.setBackground(getBackground(new File("src/main/resources/images/jpg2png/background2.png")));
        root.getStyleClass().add("root");
        setTexts(root);
        setCounter(root);
        setSpeedOptions(root);
        setMusicOptions(root);
        setNextButton(root);
        return new Scene(root, 870, 695);
    }

    public void setTexts(GridPane root) {
        Text text1 = new Text("1 player game");
        text1.setStyle("    -fx-font: 40px \"Papyrus\";\n" +
                "    -fx-fill: white;");
        Text text2 = new Text("virus level");
        text2.setStyle("    -fx-font: 30px \"Papyrus\";\n" +
                "    -fx-fill: white;");
        Text text3 = new Text("speed");
        text3.setStyle("    -fx-font: 30px \"Papyrus\";\n" +
                "    -fx-fill: white;");
        Text text4 = new Text("music type");
        text4.setStyle("    -fx-font: 30px \"Papyrus\";\n" +
                "    -fx-fill: white;");
        root.getChildren().addAll(text1, text2, text3, text4);
        root.setMargin(text1,new Insets(0,100,340,310));
        root.setMargin(text2,new Insets(100,100,260,180));
        root.setMargin(text3,new Insets(320,100,250,180));
        root.setMargin(text4,new Insets(430,100, 140,180));
    }

    public void setCounter(GridPane root) throws FileNotFoundException {
        TextField textField = new TextField();
        setTextField(textField);
        Button up = new Button();
        Button down = new Button();
        setLevelButtonsHandler(up, "up", textField);
        setLevelButtonsHandler(down, "down", textField);
        setGraphic(up, "up");
        setGraphic(down, "down");
        up.setStyle("-fx-border-color: #000000;");
        down.setStyle("-fx-border-color: #000000;");
        root.getChildren().addAll(up, down, textField);
        root.setMargin(up,new Insets(100,100,190,350));
        root.setMargin(down,new Insets(100,100,120,350));
    }

    public void setGraphic(Button button, String label) throws FileNotFoundException {
        switch (label) {
            case "up" -> {
                try {
                    File file = new File("src/main/resources/images/jpg2png/up.png");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    button.setGraphic(new ImageView(new Image(fileInputStream)));
                }catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            case "down" -> {
                try {
                    File file = new File("src/main/resources/images/jpg2png/down.png");
                    FileInputStream fileInputStream = new FileInputStream(file);
                    button.setGraphic(new ImageView(new Image(fileInputStream)));
                }catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                }catch(Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            default -> {

            }
        }
    }

    public void setTextField(TextField textField) {
        textField.setPromptText("0");
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        textField.setMaxWidth(50);
        textField.setMaxHeight(50);
        root.setMargin(textField,new Insets(100,100,150,200));
        textField.setStyle("-fx-font: 20px \"Verdana\";\n" +
                "-fx-text-inner-color: black;");
    }

    public void setLevelButtonsHandler(Button button, String label, TextField textField) {
        switch (label) {
            case "up" -> button.setOnAction(event -> {
                if(level + 1 <= MAX_VIRUS_LEVEL) {
                    level++;
                }
                textField.setText(Integer.toString(level));
            });
            case "down" -> button.setOnAction(event -> {
                if(level - 1 >= 0) {
                    level--;
                }
                textField.setText(Integer.toString(level));
            });
            default -> {

            }
        }
    }

    public void setSpeedOptions(GridPane root) {
        RadioButton low = new RadioButton("low");
        RadioButton medium = new RadioButton("medium");
        RadioButton high = new RadioButton("high");
        low.setToggleGroup(toggleGroup1);
        medium.setToggleGroup(toggleGroup1);
        high.setToggleGroup(toggleGroup1);
        root.getChildren().addAll(low, medium, high);
        root.setMargin(low,new Insets(100,100,-70,200));
        root.setMargin(medium,new Insets(100,100,-70,320));
        root.setMargin(high,new Insets(100,100,-70,460));
    }

    public void setMusicOptions(GridPane root) {
        RadioButton fever = new RadioButton("fever");
        RadioButton chill = new RadioButton("chill");
        RadioButton off = new RadioButton("off");
        fever.setToggleGroup(toggleGroup2);
        chill.setToggleGroup(toggleGroup2);
        off.setToggleGroup(toggleGroup2);
        root.getChildren().addAll(fever, chill, off);
        root.setMargin(fever,new Insets(350,100,-50,200));
        root.setMargin(chill,new Insets(350,100,-50,320));
        root.setMargin(off,new Insets(350,100,-50,460));
    }

    public void setNextButton(GridPane root) {
        Button next = new Button("next >>");
        next.setStyle("-fx-border-color: #000000;\n" +
                "    -fx-padding: 5px 15px;\n" +
                "    -fx-background-color: #000000");
        next.setOnAction(event -> {
            try {
                setSpeed();
                stage.setScene(new PlayersMenu(stage).getPlayersScene());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(next);
        root.setMargin(next,new Insets(500,0,0,620));
    }

    public void setSpeed() {
        if(toggleGroup1.getSelectedToggle() == null) {
            speed = Speed.LOW;
        }else {
            switch (((RadioButton) toggleGroup1.getSelectedToggle()).getText()) {
                case "low" -> speed = Speed.LOW;
                case "medium" -> speed = Speed.MED;
                case "high" -> speed = Speed.HI;
                default -> {
                }
            }
        }
    }
}
