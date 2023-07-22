package ir.ac.kntu;

import ir.ac.kntu.menu.Menu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * @author Sina Rostami
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Dr.mario");
        GridPane root = new GridPane();
        adjustRoot(root);
        Scene scene = new Scene(root, 1090, 600, Color.ORANGE);
        Menu menu = new Menu(root, scene, primaryStage);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void adjustRoot(GridPane root) {
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
}