package boardEngine;

/*@author Kevin*/

import java.util.ArrayList;

import datatypes.Coordinate;

public class BoardModel {
	public final static int VAC = 0; // Vacant
	public final static int OCC = 1; // Occupied - ship is there but not hit
	public final static int MISS = 2; // Shot at, but hit water
	public final static int HIT = 3; // Shot at, hit ship

	// Stores dimensions of the model
	private int width;
	private int height;

	// Actual data representation
	private int[][] board;

	// ArrayList of ship sizes
	private ArrayList<Integer> shipSizeList;

	// Stores lastTargeted cell
	public Coordinate lastTargeted;
	public ArrayList<Coordinate> lastTargetedList;
	

	/**
	 * Parent class to HostBoardModel and OpponentBoardModel, Matrix representation
	 * of game with states of squares
	 * 
	 * @requires int width & height, ArrayList shipList to construct
	 * @effects creates a BoardModel data representation
	 * @param width
	 *            int that determines width
	 * @param height
	 *            int that determines height
	 * @param shipList
	 *            ArrayList<Integer> that contains ships on board
	 */
	public BoardModel(int width, int height, ArrayList<Integer> shipList) {
		this.width = width;
		this.height = height;
		board = new int[height][width];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				board[i][j] = VAC;
			}
		}
		setShipSizeList(shipList);
	}

	/**
	 * Print method to display current BoardModel
	 * 
	 * @effects Prints into console BoardModel representation
	 */
	public String printBoardModel() {
		String rtn = "";
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				// System.out.print(this.board[i][j] + " ");
				rtn = rtn + this.board[i][j];
			}
		}
		return rtn;
	}

	/**
	 * Occupies specified coordinate by changing state to OCC
	 * 
	 * @effects Changes state at specified coordinates
	 * @param x
	 *            int for coordinate reference
	 * @param y
	 *            int for coordinate reference
	 */
	public void occupySquare(int x, int y) {
		board[x][y] = OCC;
	}

	/**
	 * Checks if square is occupied or not
	 * 
	 * @param x
	 *            int for coordinate reference
	 * @param y
	 *            int for coordinate reference
	 * @return boolean whether a square is occupied or not
	 */
	public boolean isOccupied(int x, int y) {
		if (!(x < height && x > -1) || !(y > -1 && y < width)) {
			return false;
		}
		if (board[x][y] == OCC || board[x][y] == HIT) {
			return true;
		} else {
			return false;
		}
	}

	// Getters/Setters
	public Coordinate getLastTargeted() {
		return lastTargeted;
	}
	
	public ArrayList<Coordinate> getLastTargetedSalvo() {
		return lastTargetedList;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public ArrayList<Integer> getShipList() {
		return this.getShipSizeList();
	}

	public int[][] getBoard() {
		return board;
	}

	public void setShipSizeList(ArrayList<Integer> shipSizeList) {
		this.shipSizeList = shipSizeList;
	}

	public ArrayList<Integer> getShipSizeList() {
		return shipSizeList;
	}
}
