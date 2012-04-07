package boardEngine;

/*@author Kevin and Zach*/

import java.util.ArrayList;

import datatypes.Coordinate;

public class OpponentBoardModel extends BoardModel {
	int squaresTargeted;
	int nSquares;

	/**
	 * OpponentBoardModel representation
	 * 
	 * @effects constructs OpponentBoardModel object
	 * @param width
	 *            int specifying width
	 * @param height
	 *            int specifying height
	 * @param shipList
	 *            Arraylist<Integer> shipList with ships on the board
	 */
	public OpponentBoardModel(int width, int height, ArrayList<Integer> shipList) {
		super(width, height, shipList);
		squaresTargeted = 0;
		nSquares = width * height;
	}

	/**
	 * Updates model according to square's state
	 * 
	 * @param row
	 *            int for coordinate reference
	 * @param col
	 *            int for coordinate reference
	 * @param response
	 *            boolean that refers to hit or not
	 */
	public void updateModel(int row, int col, boolean response) {
		if (response) {
			getBoard()[row][col] = HIT;
		}
		if (!response) {
			getBoard()[row][col] = MISS;
		}
		squaresTargeted++;
	}

	/**
	 * Creates string message of BoardModel to Array
	 * 
	 * @returns String with boardHashString
	 */
	public String boardRepFromArray() {
		String dummyString = new String("");
		for (int i = 0; i < super.getHeight(); ++i) {
			for (int j = 0; j < super.getWidth(); ++j) {
//				System.out.println("(" + i + "," + j + ")");
				String boardHashString = dummyString + (getBoard()[i][j] % 2);
				dummyString = boardHashString;
			}
		}
		return dummyString;
	}

	/**
	 * Sets last targeted cell as last targeted
	 * 
	 * @param targetMessageCoordinate
	 *            Coordinate to mark last coordinate message
	 */
	public void setLastTargeted(Coordinate targetMessageCoordinate) {
		super.lastTargeted = targetMessageCoordinate;
	}

	public void setLastTargetedSalvo(
			ArrayList<Coordinate> salvoTargetMessageCoordinate) {
		super.lastTargetedList = salvoTargetMessageCoordinate;
	}

	/**
	 * Checks if square is vacant
	 * 
	 * @param i
	 *            int for coordinate reference
	 * @param j
	 *            int for coordinate reference
	 * @return boolean if cell is vacant or not
	 */
	public boolean isVacant(int i, int j) {

		if ((i < this.getWidth() && i > -1) && (j < this.getHeight() && j > -1))
			return this.getBoard()[i][j] == BoardModel.VAC;
		else
			return true;
	}

	/**
	 * Checks if there are no neighbors
	 * 
	 * @param i
	 *            int for coordinate reference
	 * @param j
	 *            int for coordinate reference
	 * @return boolean if cell has any neighbors or not
	 */
	public boolean noNeighbors(int i, int j) {
		boolean up = super.isOccupied(i + 1, j);
		boolean down = super.isOccupied(i - 1, j);
		boolean left = super.isOccupied(i, j - 1);
		boolean right = super.isOccupied(i, j + 1);
		return (!((up || down) || (left || right)));
	}

	/**
	 * Checks if there are no neighbors in pairs
	 * 
	 * @param i
	 *            int for coordinate reference
	 * @param j
	 *            int for coordinate reference
	 * @return boolean if cell has any neighbors in pairs or not
	 */
	public boolean noNeighborPairs(int i, int j) {
		boolean up = super.isOccupied(i+1,j);
		boolean down = super.isOccupied(i-1, j);
		boolean left = super.isOccupied(i, j-1);
		boolean right = super.isOccupied(i, j+1);
		return (!((up && down) || (left && right)));
	}

	/**
	 * Determines rightAngle
	 * 
	 * @param i
	 *            int for coordinate reference
	 * @param j
	 *            int for coordinate reference
	 * @return boolean of rightAngle
	 */
	public boolean rightAngle(int i, int j) {
		boolean ur = (super.isOccupied(i + 1, j + 1) && super.isOccupied(i,
				j + 1));
		boolean ru = (super.isOccupied(i + 1, j + 1) && super.isOccupied(i + 1,
				j));
		boolean dr = (super.isOccupied(i, j + 1) && super.isOccupied(i - 1,
				j + 1));
		boolean dl = (super.isOccupied(i, j - 1) && super.isOccupied(i - 1,
				j - 1));
		boolean rd = (super.isOccupied(i - 1, j + 1) && super.isOccupied(i - 1,
				j));
		boolean ld = (super.isOccupied(i - 1, j - 1) && super.isOccupied(i - 1,
				j));

		if (ur || ru || dr || dl || rd || ld) {
			return true;
		}
		return false;
	}

	public int getSquaresTargeted() {
		return squaresTargeted;
	}

	public int nSquares() {
		return nSquares;
	}

}
