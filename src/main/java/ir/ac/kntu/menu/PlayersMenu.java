package ir.ac.kntu.menu;

import ir.ac.kntu.File.FileMethods;
import ir.ac.kntu.GameLoop;
import ir.ac.kntu.Main;
import ir.ac.kntu.gameObjects.Player;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PlayersMenu {

    private Stage stage;

    private GameLoop gameLoop;

    private ListView<Player> listView = new ListView<>();

    public PlayersMenu(Stage stage) throws FileNotFoundException {
        this.stage = stage;
        gameLoop = new GameLoop(stage);
    }

    public Scene getPlayersScene() throws IOException, ClassNotFoundException {
        GridPane root= new GridPane();
        new Main().adjustRoot(root);
        root.setBackground(Menu.getBackground(new File("src/main/resources/images/jpg2png/background2.png")));
        root.getStyleClass().add("root");
        setText(root);
        setList(root);
        TextField textField = new TextField();
        setTextField(root, textField);
        setAddButton(root, textField);
        setStartButton(root);
        return new Scene(root, 870, 695);
    }

    public void setText(GridPane root) {
        Text text = new Text("players");
        text.setStyle("    -fx-font: 40px \"Papyrus\";\n" +
                "    -fx-fill: white;");
        root.getChildren().add(text);
        root.setMargin(text,new Insets(100,100,550,370));
    }

    public void setList(GridPane root) throws IOException, ClassNotFoundException {
        FileMethods.read();
        listView.getItems().addAll(GameLoop.players);
        listView.setMaxSize(500,300);
        listView.getStyleClass().add("listView");
        root.getChildren().add(listView);
        root.setMargin(listView,new Insets(100,50,100,200));
        listView.setOnMouseClicked(event -> {
            GameLoop.currentPlayer = listView.getSelectionModel().getSelectedItem();;
        });
    }

    public void setTextField(GridPane root, TextField textField) {
        textField.setPromptText("name");
        textField.setFocusTraversable(false);
        textField.setMaxWidth(200);
        textField.setMaxHeight(50);
        root.setMargin(textField,new Insets(400,100,0,200));
        textField.setStyle("-fx-font: 20px \"Verdana\";\n" +
                "-fx-text-inner-color: black;");
        root.getChildren().add(textField);
    }

    public void setAddButton(GridPane root, TextField textField) {
        Button add = new Button("add");
        add.setStyle("-fx-border-color: #000000;\n" +
                "    -fx-padding: 5px 15px;\n" +
                "    -fx-background-color: #000000");
        add.setOnAction(event -> {
            GameLoop.players.add(new Player(textField.getText()));
            listView.getItems().add(new Player(textField.getText()));
        });
        root.getChildren().add(add);
        root.setMargin(add,new Insets(400,100,0,400));
    }

    public void setStartButton(GridPane root) {
        Button start = new Button("start >>");
        start.setStyle("-fx-border-color: #000000;\n" +
                "    -fx-padding: 5px 5px;\n" +
                "    -fx-background-color: #000000");
        start.setOnAction(event -> {
            try {
                stage.setScene(gameLoop.getGameScene());
                FileMethods.write();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(start);
        root.setMargin(start,new Insets(400,0,0,600));
    }
}
