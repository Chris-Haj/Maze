import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.AbstractMap.SimpleEntry;

public class Maze {
    /**
     * Class that represents a Coordiantes of x and y
     */
    static class Coordinates {
        private int x;
        private int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * List with Entries that represent the coordinates and the
     * direction led to those coordiantes
     */
    private List<AbstractMap.SimpleEntry<String, Coordinates>> path;

    //Width and height of the matrix
    private int width;
    private int height;

    /**
     * Start X-Coordiante (S)
     */
    private int startX;
    /**
     * Start Y-Coordinate (S)
     */
    private int startY;

    /**
     * Finish X-Coordinate (F)
     */
    private int endX;
    /**
     * Finish Y-Coordinate (F)
     */
    private int endY;

    /**
     * Maze-Matrix
     * <p>
     * {@code true} for obstacle.
     * <p>
     * {@code false} for clear road.
     * </p>
     */
    private boolean[][] maze;
    /**
     * Matrix to keep track of visited roads
     * <p>
     * {@code true} for visited indices.
     * <p>
     * {@code false} for unvisited indices.
     * </p>
     */
    private boolean[][] wasHere;

    /**
     * Standard-Constructor to initialize Matrices.
     *
     * @param fileName File name of the input-file.
     */
    public Maze(String fileName) {
        parseFile(fileName);
        path = new ArrayList<AbstractMap.SimpleEntry<String, Coordinates>>();
    }

    /**
     * Method to set the distance that should be covered in a specific direction.
     *
     * @param x         X-Coordinate.
     * @param y         Y-Coordinate.
     * @param direction Directions: {@code right}, {@code left}, {@code up}, {@code down}.
     * @return Returns the distance that should be covered or 0, if the move was illegal.
     */
    public int distanceInDirection(int x, int y, String direction) {
        if (direction.equals("down")) {
            //if y on the bottom limit
            if (y == height - 1) return 0;

            boolean hitObstacle = false;
            int distance = 0;
            while (y < height - 1 && !hitObstacle) {
                if (maze[y + 1][x] == false) {
                    y++;
                    distance++;
                }
                else
                    hitObstacle = true;
            }
            //if the distantion is already visited then return 0
            if (wasHere[y][x]) return 0;
            return distance;
        } else if (direction.equals("up")) {
            if (y == 0)
                return 0;
            boolean hitObstacle = false;
            int distance = 0;
            while (y > 0 && !hitObstacle) {
                if (maze[y - 1][x] == false) {
                    y--;
                    distance++;
                } else hitObstacle = true;
            }
            if (wasHere[y][x]) return 0;
            return distance;
        } else if (direction.equals("right")) {
            if (x == width - 1) return 0;

            boolean hitObstacle = false;
            int distance = 0;
            while (x < width - 1 && !hitObstacle) {
                if (maze[y][x + 1] == false) {
                    x++;
                    distance++;
                }
                else
                    hitObstacle = true;
            }
            if (wasHere[y][x])
                return 0;
            return distance;
        }
        else if (direction.equals("left")) {
            if (x == 0) return 0;

            boolean hitObstacle = false;
            int distance = 0;
            while (x > 0 && !hitObstacle) {
                if (maze[y][x - 1] == false) {
                    x--;
                    distance++;
                }
                else hitObstacle = true;
            }
            return distance;
        }
        return -5;
    }

    /**
     * Method that reads an input file and parse input and
     * initialize variables.
     *
     * @param filename Name of the file to be read.
     */
    public void parseFile(String filename) {
        try {
            Scanner scan = new Scanner(new FileInputStream(filename));
            int rows = 0;
            while (scan.hasNext()) {
                rows++;
                char[] arr = scan.nextLine().toCharArray();
                width = arr.length;
            }
            height = rows;
            maze = new boolean[height][width];
            wasHere = new boolean[height][width];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Scanner scan = new Scanner(new FileInputStream(filename));
            int row = 0;
            while (scan.hasNext()) {
                char[] arr = scan.nextLine().toCharArray();
                for (int i = 0; i < arr.length; i++) {
                    if (arr[i] == 'S') {
                        startX = i;
                        startY = row;
                        maze[row][i] = false;
                    } else if (arr[i] == 'F') {
                        endX = i;
                        endY = row;
                        maze[row][i] = false;
                    } else if (arr[i] == '0')
                        maze[row][i] = true;
                    else
                        maze[row][i] = false;

                    wasHere[row][i] = false;
                }
                row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recursive-Backtracking-Algorithm to find the shortest path.
     *
     * @param x X-Coordinate
     * @param y Y-Coordinate
     * @return Return true if finish point was found, otherwise false.
     */
    public boolean backtracking(int x, int y) {
        if (x == endX && y == endY) return true;

        if (wasHere[y][x]) return false;

        wasHere[y][x] = true;
        if (x != 0) {
            int distance = distanceInDirection(x, y, "left");
            if (backtracking(x - distance, y)) {
                path.add(new SimpleEntry<String, Coordinates>("left", new Coordinates(x - distance, y)));
                return true;
            }
        }
        if (x != width - 1) {
            int distance = distanceInDirection(x, y, "right");
            if (backtracking(x + distance, y)) {
                path.add(new SimpleEntry<String, Coordinates>("right", new Coordinates(x + distance, y)));
                return true;
            }
        }
        if (y != 0) {
            int distance = distanceInDirection(x, y, "up");
            if (backtracking(x, y - distance)) {
                path.add(new SimpleEntry<String, Coordinates>("up", new Coordinates(x, y - distance)));
                return true;
            }
        }
        if (y != height - 1) {
            int distance = distanceInDirection(x, y, "down");
            if (backtracking(x, y + distance)) {
                path.add(new SimpleEntry<String, Coordinates>("down", new Coordinates(x, y + distance)));
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
/*        Maze maze = new Maze("in.txt");
        maze.backtracking(maze.startX, maze.startY);

        var count = 0;
        System.out.println(count++ + ". Start at (" + (maze.startX + 1) + ", " + (maze.startY + 1) + ")");
        for (int i = maze.path.size() - 1; i >= 0; i--) {
            String direction = maze.path.get(i).getKey();
            Coordinates c = maze.path.get(i).getValue();
            c.x++;
            c.y++;
            switch (direction) {
                case "up":
                    System.out.println(count++ + ". Move up to (" + c.x + ", " + c.y + ")");
                    break;
                case "down":
                    System.out.println(count++ + ". Move down to (" + c.x + ", " + c.y + ")");
                    break;
                case "left":
                    System.out.println(count++ + ". Move left to (" + c.x + ", " + c.y + ")");
                    break;
                case "right":
                    System.out.println(count++ + ". Move right to (" + c.x + ", " + c.y + ")");
            }
        }
        System.out.println(count++ + ". Done!");*/


    }
}
