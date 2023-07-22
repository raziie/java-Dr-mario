package ir.ac.kntu.map;

import ir.ac.kntu.GameLoop;
import ir.ac.kntu.constants.GlobalConstants;
import ir.ac.kntu.gameObjects.Color;
import ir.ac.kntu.gameObjects.Direction;
import ir.ac.kntu.gameObjects.Pill;
import ir.ac.kntu.menu.Menu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Map {

    public static String[][] map;

    public Map() {
        map = new String[8][16];
        for (String[] strings : map) {
            Arrays.fill(strings, "n");
        }
        generateMap();
    }

    public void generateMap() {
        int totalElement = map.length * map[0].length;
        ArrayList<Integer> selected = new ArrayList<>();
        for(int index = 0; index< (Menu.level * 4); index++){
            int indexToSelect = (int)(Math.random() * totalElement);
            while(selected.contains(indexToSelect) || isUnreachable(indexToSelect)){
                indexToSelect = (int)(Math.random() * totalElement);
            }
            selected.add(indexToSelect);
            int yIndex = indexToSelect / map.length;
            int xIndex = indexToSelect % map.length;
            Color randomColor = generateRandomColor();
            setVirusPlaces(xIndex, yIndex, randomColor);
        }
    }

    public boolean isUnreachable(int index) {
        if(index / map.length == 0) {
            return true;
        }else if(index / map.length == 1) {
            return true;
        }else {
            return (index / map.length == 2);
        }
    }

    public Color generateRandomColor() {
        Color color = Color.NONE;
        switch (new Random().nextInt(3)) {
            case 0 -> color = Color.RED;
            case 1 -> color = Color.BLUE;
            case 2 -> color = Color.YELLOW;
            default -> {}
        }
        return color;
    }

    public void setVirusPlaces(int x, int y, Color color) {
        switch (color) {
            case BLUE -> map[x][y] = "b";
            case RED -> map[x][y] = "r";
            case YELLOW -> map[x][y] = "y";
            default -> {}
        }
    }

    public static boolean isCollided(Pill pill, Direction direction) {
        int newX1, newY1, newX2, newY2;
        newX1 = getNewXAndY1(pill, direction)[0];
        newY1 = getNewXAndY1(pill, direction)[1];
        newX2 = getNewXAndY2(pill, newX1, newY1)[0];
        newY2 = getNewXAndY2(pill, newX1, newY1)[1];
        return (!Map.map[newX1][newY1].equals("n") || !Map.map[newX2][newY2].equals("n"));
    }

    public static int[] getNewXAndY1(Pill pill, Direction direction) {
        int[] result = new int[2];
        switch (direction) {
            case DOWN -> {
                result[0] = (pill.getX()) / 28;
                result[1] = ((pill.getY() + (GlobalConstants.PILL_STEP)) / 25);
            }
            case RIGHT -> {
                result[0] = ((pill.getX() + (GlobalConstants.PILL_STEP))) / 28;
                result[1] = (pill.getY()) / 25;
            }
            case LEFT -> {
                result[0] = ((pill.getX() - (GlobalConstants.PILL_STEP))) / 28;
                result[1] = (pill.getY()) / 25;
            }
            default -> {}
        }
        return result;
    }

    public static int[] getNewXAndY2(Pill pill, int x, int y) {
        int[] result = new int[2];
        if(pill.isVertical()) {
            result[0] = x;
            result[1] = y + 1;
        }else {
            result[0] = x + 1;
            result[1] = y;
        }
        return result;
    }

    public static void addToMap(Pill pill) {
        if(firstIsBlue(pill.getType())) {
            Map.map[(pill.getX()) / 28][(pill.getY()) / 25] = "b";
        }else if(firstIsRed(pill.getType())) {
            Map.map[(pill.getX()) / 28][(pill.getY()) / 25] = "r";
        }else {
            Map.map[(pill.getX()) / 28][(pill.getY()) / 25] = "y";
        }
        if(secondIsBlue(pill.getType())) {
            if(pill.isVertical()) {
                Map.map[(pill.getX()) / 28][((pill.getY()) / 25) + 1] = "b";
            }else {
                Map.map[((pill.getX()) / 28) + 1][(pill.getY()) / 25] = "b";
            }
        }else if(secondIsRed(pill.getType())) {
            if(pill.isVertical()) {
                Map.map[(pill.getX()) / 28][((pill.getY()) / 25) + 1] = "r";
            }else {
                Map.map[((pill.getX()) / 28) + 1][(pill.getY()) / 25] = "r";
            }
        }else {
            if(pill.isVertical()) {
                Map.map[(pill.getX()) / 28][((pill.getY()) / 25) + 1] = "y";
            }else {
                Map.map[((pill.getX()) / 28) + 1][(pill.getY()) / 25] = "y";
            }
        }
    }

    public static boolean firstIsBlue(Pill.Type type) {
        return (type == Pill.Type.BLUE || type == Pill.Type.BLUE_RED || type == Pill.Type.BLUE_YELLOW);
    }

    public static boolean firstIsRed(Pill.Type type) {
        return (type == Pill.Type.RED || type == Pill.Type.RED_BLUE || type == Pill.Type.RED_YELLOW);
    }

    public static boolean secondIsBlue(Pill.Type type) {
        return (type == Pill.Type.BLUE || type == Pill.Type.RED_BLUE || type == Pill.Type.YELLOW_BLUE);
    }

    public static boolean secondIsRed(Pill.Type type) {
        return (type == Pill.Type.RED || type == Pill.Type.BLUE_RED || type == Pill.Type.YELLOW_RED);
    }

    public static void destroyIfNeeded(GameLoop gameLoop) {
        ArrayList<int[]> verticalToRemove = findVerticalToRemove();
        ArrayList<int[]> horizontalToRemove = findHorizontalToRemove();
        ArrayList<int[]> toRemove = attach(verticalToRemove, horizontalToRemove);
        gameLoop.removeImages(toRemove);
        for(int[] coordinate : toRemove) {
            map[coordinate[0]][coordinate[1]] = "n";
        }
    }

    public static ArrayList<int[]> attach(ArrayList<int[]> first, ArrayList<int[]> second) {
        ArrayList<int[]> result = new ArrayList<>();
        result.addAll(first);
        result.addAll(second);
        return result;
    }

    public static ArrayList<int[]> findHorizontalToRemove() {
        ArrayList<int[]> result = new ArrayList<>();
        for(int j=0; j< map[0].length; j++) {
            String cell = map[0][j];
            int similarCount = 1;
            for(int i=1; i< map.length; i++) {
                if(Objects.equals(map[i][j], cell) && !cell.equals("n")) {
                    if(i + 1 == map.length - 1 && map[i + 1][j] == cell) {
                        similarCount++;
                    }
                    similarCount++;
                }else {
                    if(similarCount >= 4) {
                        for(int k=1; k <= similarCount; k++) {
                            result.add(new int[] {i - k, j});
                        }
                    }
                    cell = map[i][j];
                    similarCount = 1;
                }
            }
        }
        return result;
    }

    public static ArrayList<int[]> findVerticalToRemove() {
        ArrayList<int[]> result = new ArrayList<>();
        for(int i=0; i< map.length; i++) {
            String cell = map[i][0];
            int similarCount = 1;
            for(int j=1; j< map[i].length; j++) {
                if(Objects.equals(map[i][j], cell) && !cell.equals("n")) {
                    if(j + 1 == map[i].length - 1 && map[i][j + 1] == cell) {
                        similarCount++;
                    }
                    similarCount++;
                }else {
                    if(similarCount >= 4) {
                        for(int k=1; k <= similarCount; k++) {
                            result.add(new int[] {i, j - k});
                        }
                    }
                    cell = map[i][j];
                    similarCount = 1;
                }
            }
        }
        return result;
    }

    public static void printMap() {
        for(int i=0; i < Map.map.length; i++) {
            for(String element : Map.map[i]) {
                System.out.print(element + " ");
            }
            System.out.println("");
        }
    }
}
