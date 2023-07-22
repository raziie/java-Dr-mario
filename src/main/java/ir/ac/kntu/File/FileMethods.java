package ir.ac.kntu.File;

import ir.ac.kntu.GameLoop;
import ir.ac.kntu.gameObjects.Player;

import java.io.*;
import java.util.ArrayList;

public class FileMethods {

    public static <T extends Number & Comparable<T>> void write() throws IOException {
        File file = new File("data.txt");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(GameLoop.players);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("saved");
    }

    @SuppressWarnings("unckecked")
    public static void read() throws IOException, ClassNotFoundException {
        File file = new File("data.txt");
        try (FileInputStream fileInputStream = new FileInputStream(file);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            GameLoop.players = (ArrayList<Player>) objectInputStream.readObject();
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
