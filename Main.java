//TRAVERSING A MAZE


import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Main {

    private static char[][] maze;
    private static int row;
    private static int column;

    public static void main(String[] args) {

        File file = new File("maze.dat");
        try (Scanner scan = new Scanner(file)) {

            //location of maze file
            row = scan.nextInt();
            column = scan.nextInt();
            scan.nextLine();

            //read the maze
            maze = new char[row][column];

            for (int i = 0; i < row; i++) {
                String line = scan.nextLine();
                for (int j = 0; j < column; j++) {
                    maze[i][j] = line.charAt(j);
                    if (maze[i][j] == 'S') {  //S-Start '+'
                        int nrow = i;
                        int ncol = j;
                    }
                }
            }

            //find the start of the maze and solve
            int[] start = findStart();
            if (start[0] == -1) {
                System.out.println("No start position'+' found in the maze.");
            } else {
                if (solveMaze(start[0], start[1])) {
                    System.out.println("Maze solved!");
                } else {
                    System.out.println("No solution found.");
                }
            }

            //print maze after solving
            printMaze();

        } //catch an exception in case the file is not found 
            catch (FileNotFoundException e) {
            System.out.println("Maze File not found: " + file);
        }

    }

    //find the start of the maze
    public static int[] findStart(){
        for (int i = 0; i < maze.length; i++){ //i<row
            for (int j = 0; j < maze[i].length; j++){ //j<column
                if (maze[i][j] == '+'){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1}; //if no start found
    }

    // check if the move is correct
    public static boolean validMove(char[][] maze, int x, int y) {
        return x >= 0 && x < row && y >= 0 && y < column && (maze[x][y] == ' ' || maze[x][y] == '-');
    }

    // solve the maze
    public static boolean solveMaze(int row, int column){

        if (maze[row][column] == '-') {
            //System.out.println("Maze solved!");
            return true;
        }
        
        //
        maze[row][column] = '+';

        int[] rowDirections = {-1, 1, 0, 0}; // up down
        int[] colDirections = {0, 0, -1, 1}; // left right

        for (int i = 0; i < 4; i++) {
            int newRow = row + rowDirections[i]; // Move in new row direction
            int newCol = column + colDirections[i]; // Move in new column direction 


            if (validMove(maze, newRow, newCol)) {
                if (solveMaze(newRow, newCol)) {
                    return true; // if works
                }
            }
        }

        maze[row][column] = '.';
        return false;
    }

    // print the maze
    public static void printMaze(){

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
    
}
