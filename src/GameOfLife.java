import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Nolan Hoffmann
 * Game of life #11
 * This class replicates the Conway's 'Game Of Life' via terminal window.
 */
public class GameOfLife {
	static int[][] grid;
	static boolean[][] temp;
	static File file;

	/*
	 * Constructs a GameOfLife object which will generate a blank board as well
	 * as grab a file to read from that was part of the assignment.
	 */
	public GameOfLife() throws FileNotFoundException {
		grid = new int[20][20];
		temp = new boolean[20][20];
		file = new File("C:\\Users\\13hoffmannn\\Downloads\\life100.txt");
	}

	public static void main(String[] args) throws FileNotFoundException,
			InterruptedException {
		GameOfLife game = new GameOfLife();
		renew();
		String s = "";
		Scanner board = new Scanner(file);
		Scanner number = new Scanner(System.in);
		board.nextLine();
		//Read the file and add the contents to a single String
		while (board.hasNextLine()) {
			s += board.nextLine();
		}
		String[] g = s.trim().split("   ");
		System.out.println(Arrays.toString(g));
		//Apply the locations from the file read to the blank board
		for (int i = 0; i < g.length; i += 2) {
			int x = Integer.parseInt(g[i].trim());
			int y = Integer.parseInt(g[i + 1].trim());
			temp[x - 1][y - 1] = true;
			grid[x - 1][y - 1] = 1;
		}
		//Cycle 'x' many generations
		System.out.print("Enter the <number> of generations to cycle: ");
		int gens = number.nextInt();
		for (int i = 0; i < gens; i++) {
			for (int j = 0; j < grid.length; j++) {
				for (int q = 0; q < grid.length; q++) {
					cycleLife(new Point(j, q));
				}
			}
			copyTemp();
			printGrid();
			System.out.println("-----------------");
			Thread.sleep(1000);
		}
	}

	/**
	 * Copies the boolean array so all life cycles happen simultaneously
	 */
	public static void copyTemp() {
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				if (temp[i][j]) {
					grid[i][j] = 1;
				} else {
					grid[i][j] = 0;
				}
			}
		}
	}

	/**
	 * Kills all cells on the board
	 */
	public static void renew() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[i][j] = 0;
			}
		}
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				temp[i][j] = false;
			}
		}
	}

	/**
	 * Gives birth to a cell at the Point passed in
	 * 
	 * @param p Point to give birth at
	 * @return True if the Point passed in did not have a cell in it already,
	 * false otherwise
	 */
	public static boolean birth(Point p) {
		if (grid[p.x][p.y] == 0) {
			grid[p.x][p.y] = 1;
			return true;
		}
		return false;
	}

	/**
	 * Cycles a generation
	 * 
	 * @param p The point to run a cycle of life on
	 */
	public static void cycleLife(Point p) {
		int n = getNeighbors(p);
		if (n == 3 && grid[p.x][p.y] == 0) {
			temp[p.x][p.y] = true;
		}
		if ((n <= 1 && grid[p.x][p.y] == 1) || (n >= 4 && grid[p.x][p.y] == 1)) {
			temp[p.x][p.y] = false;
		}
		if ((n == 3 || n == 2) && grid[p.x][p.y] == 1) {

		}
	}

	/**
	 * Gets the number of neighbors next to the given Point
	 * 
	 * @param p The Point to get the neighbors of
	 * @return The number of neighbors
	 */
	public static int getNeighbors(Point p) {
		int xS, xE;
		int yS, yE;
		int count = 0;
		if (p.x - 1 != -1) {
			xS = p.x - 1;
		} else {
			xS = 0;
		}
		if (p.x + 1 != grid.length) {
			xE = p.x + 1;
		} else {
			xE = grid.length - 1;
		}
		if (p.y - 1 != -1) {
			yS = p.y - 1;
		} else {
			yS = 0;
		}
		if (p.y + 1 != grid.length) {
			yE = p.y + 1;
		} else {
			yE = grid.length - 1;
		}
		for (int i = xS; i <= xE; i++) {
			for (int j = yS; j <= yE; j++) {
				if (grid[i][j] == 1 && (!new Point(i, j).equals(p))) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * Prints the grid
	 */
	public static void printGrid() {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				System.out.print(grid[i][j] + "   ");
			}
			System.out.println();
		}
	}
}
/*
 * OUTPUT: 
 * 0 0 0 0 0 0 1 1 0 0 0 0 0 0 0 0 0 0 0 0
 * 
 * 0 0 0 0 0 1 0 1 0 0 0 0 0 0 0 0 0 0 0 0
 * 
 * 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 1 1 0 0 0
 * 
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 0 1 0 0
 * 
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 1 1 0 1 1 0 0
 * 
 * 0 0 0 0 0 0 0 0 0 0 0 0 1 0 1 0 1 1 0 0
 * 
 * 0 0 0 0 0 0 0 0 0 0 1 0 1 1 0 0 0 0 0 0
 * 
 * 0 0 0 0 0 0 0 0 0 0 1 0 0 0 0 0 0 0 0 0
 * 
 * 0 0 0 0 0 0 0 1 1 1 0 0 1 1 0 0 0 0 0 0
 * 
 * 0 0 0 0 0 1 1 0 1 1 0 1 1 0 1 1 0 0 0 0
 * 
 * 0 0 0 0 0 1 1 1 1 1 1 0 0 0 0 0 0 0 0 0
 * 
 * 0 0 0 0 0 0 1 1 0 1 1 1 1 0 1 1 0 0 0 0
 * 
 * 0 0 0 0 0 0 0 1 0 0 1 1 0 0 1 1 1 0 0 0
 * 
 * 0 0 0 0 0 0 1 0 0 1 0 1 1 1 0 1 1 0 0 0
 * 
 * 0 0 0 0 0 0 0 1 1 0 0 0 0 0 1 1 0 1 0 0
 * 
 * 0 0 0 0 0 0 1 1 0 0 0 0 0 0 1 1 0 0 1 0
 * 
 * 0 0 0 0 0 1 0 1 0 0 0 0 1 1 0 0 0 0 1 0
 * 
 * 0 0 0 0 0 1 0 1 0 0 0 1 1 1 0 0 0 0 1 0
 * 
 * 0 0 0 0 0 1 0 0 0 0 1 0 0 1 0 1 1 0 0 0
 * 
 * 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
 */