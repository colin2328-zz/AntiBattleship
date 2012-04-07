package boardEngine;

/*@author Kevin and Zach*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import datatypes.Coordinate;
import datatypes.Orientation;
import datatypes.SHAHash;
import datatypes.Ship;

public class HostBoardModel extends BoardModel {

	private ArrayList<Ship> shipList;
	private ArrayList<Integer> leftToPlace;
	private Map<Coordinate, Ship> coordinateShipMap;
	private ArrayList<Ship> leftToSink;
	int salt;

	/**
	 * HostBoardModel representation that inherits from BoardModel
	 * 
	 * @effects Constructs the HostBoardModel representation
	 * @param width
	 *            int that specifies width
	 * @param height
	 *            int that specifies height
	 * @param shipSizeList
	 *            ArrayList<Integer> that specifies ships that are on the board
	 */
	public HostBoardModel(int width, int height, ArrayList<Integer> shipSizeList) {
		super(width, height, shipSizeList);
		salt = 10;
//		System.out.printf("width: %d, height: %d \n", width, height);
		shipList = new ArrayList<Ship>();
		coordinateShipMap = new HashMap<Coordinate, Ship>();
		leftToPlace = new ArrayList<Integer>();
		leftToSink = new ArrayList<Ship>();
		for (Integer i : shipSizeList) {
			leftToPlace.add(i);
		}
	}

	/**
	 * Checks if all ships have been placed
	 * 
	 * @return boolean if all ships have been placed
	 */
	public boolean allPlaced() {
		if (leftToPlace.size() == 0)
			return true;
		else
			return false;
	}

	/**
	 * Gives size of ships left
	 * 
	 * @return int of how many ships are still remaining
	 */
	public int numShipsLeftToSink() {
		return this.leftToSink.size();
	}

	/**
	 * Updates model according to square's state
	 * 
	 * @param row
	 *            int for coordinate reference
	 * @param col
	 *            int for coordinate reference
	 * @return String message to be passed with result
	 */
	public String updateModel(int row, int col) {

		if (getBoard()[row][col] == VAC) {
			getBoard()[row][col] = MISS;
			return "water";
		} else if (getBoard()[row][col] == MISS) {
//			System.out.println("ALREADY MISSED!");
			return "game-error";
		} else if (getBoard()[row][col] == HIT) {
			return "game-error";
		}
		if (getBoard()[row][col] == OCC) {
			getBoard()[row][col] = HIT;
			Ship hitShip = coordinateShipMap.get(new Coordinate(row, col));
			hitShip.addHit(row, col);
			if (hitShip.isSunk()) {
				leftToSink.remove(hitShip);
				return "sunk";
			}
			return "fire";
		} else {
			return new String("game-error");
		}
	}

	/**
	 * Checks ship location on board model
	 * 
	 * @requires A startingSquare coordinate, a specified orientation, and a
	 *           specified ship length board should be initialized already
	 * @effects verifies whether a ship location (orientation, startingSquare,
	 *          length) is valid
	 * @param orientation
	 *            int that represents right, up, left, down
	 * @param startingSquare
	 *            Coordinate that tells where ship starts
	 * @param length
	 *            int that represents ship length
	 * @return boolean verifying orientation
	 */
	public boolean checkShipLocation(int orientation,
			Coordinate startingSquare, int length) {
		boolean up = (orientation == 0);
		boolean right = (orientation == 1);
		boolean down = (orientation == 2);
		boolean left = (orientation == 3);

		if (!(startingSquare.getColumn() < super.getWidth() && startingSquare
				.getColumn() > -1)) {
//			System.out.printf(
//					"did not satisfy width constraint: %d vs. width of %d \n",
//					startingSquare.getColumn(), super.getWidth());
			return false;
		}
		if (!(startingSquare.getRow() < super.getHeight() && startingSquare
				.getRow() > -1)) {
//			System.out.println("did not satisfy height constraint");
			return false;
		}

		if ((right && startingSquare.getColumn() + length > super.getWidth())
				|| (up && startingSquare.getRow() + length > super.getHeight())) {
			return false;
		}
		if ((left && startingSquare.getColumn() - length < -1)
				|| (down && startingSquare.getRow() - length < -1)) {
			return false;
		}
		if (!leftToPlace.contains(new Integer(length)))
			return false;

		// right
		if (right) {
			for (int col = startingSquare.getColumn() - 1; col < startingSquare
					.getColumn() + length + 1; ++col) {
				for (int row = startingSquare.getRow() - 1; row < startingSquare
						.getRow() + 2; ++row) {
//					System.out.printf("checked: (%d,%d) \n", row, col);
					if (super.isOccupied(row, col))
						return false;
				}
			}
		}
		// up
		else if (up) {
			for (int row = startingSquare.getRow() - 1; row < startingSquare
					.getRow() + length + 1; ++row) {
				for (int col = startingSquare.getColumn() - 1; col < startingSquare
						.getColumn() + 2; ++col) {
//					System.out.printf("checked: (%d,%d) \n", row, col);
					if (super.isOccupied(row, col))
						return false;
				}
			}
		}
		// down
		if (down) {
//			System.out.println("In down check");
			for (int row = startingSquare.getRow() + 1; row > startingSquare
					.getRow() - length - 1; row--) {
				for (int col = startingSquare.getColumn() - 1; col < startingSquare
						.getColumn() + 2; col++) {

//					System.out.printf("checked: (%d,%d) \n", row, col);
					if (super.isOccupied(row, col))
						return false;
				}
			}
		}
		// left
		else if (left) {
//			System.out.println("In left check");
			for (int column = startingSquare.getColumn() + 1; column > startingSquare
					.getColumn() - length - 1; --column) {
				for (int row = startingSquare.getRow() - 1; row < startingSquare
						.getRow() + 2; ++row) {
//					System.out.printf("checked: (%d,%d) \n", row, column);
					if (super.isOccupied(row, column))
						return false;
				}
			}
		}
		return true;
	}

	/**
	 * Creates an ArrayList<Coordinate> of the ship
	 * 
	 * @param orientation
	 *            Integer that represents the orientation
	 * @param startingSquare
	 *            Coordinate starting point of the ship
	 * @param length
	 *            int size of ship
	 * @return ArrayList<Coordinate> that represents a single ship
	 */
	public ArrayList<Coordinate> setShipLocation(Integer orientation,
			Coordinate startingSquare, int length) {
		Ship newShip = createShip(orientation, startingSquare, length);
		return addShip(newShip);
	}

	/**
	 * Method that creates a Ship object
	 * 
	 * @param orientation
	 *            Integer with specified direction
	 * @param startingSquare
	 *            Coordinate starting point
	 * @param length
	 *            int size of ship
	 * @return Ship with specified parameters
	 */
	public Ship createShip(Integer orientation, Coordinate startingSquare,
			int length) {
		if (orientation == null) {
//			System.out.println("orientation is null");
		}
		if (startingSquare == null) {
//			System.out.println("startingSquare is null");
		}

		boolean validLocation = checkShipLocation(orientation, startingSquare,
				length);
		if (!validLocation) {
//			System.out
//					.println("Not valid location. Ship will not be placed. Returning null.");
			return null;
		}

		ArrayList<Coordinate> coordinateList = new ArrayList<Coordinate>();
		for (int i = 0; i < length; ++i) {
			int row = startingSquare.getRow();
			int col = startingSquare.getColumn();
			int newRow = 0;
			int newColumn = 0;
			if (orientation.equals(Orientation.UP)) {
				newRow = row + i;
				newColumn = col;
				getBoard()[newRow][newColumn] = OCC;
			} else if (orientation.equals(Orientation.RIGHT)) {
				newRow = row;
				newColumn = col + i;
				getBoard()[newRow][newColumn] = OCC;
			} else if (orientation.equals(Orientation.DOWN)) {
				newRow = row - i;
				newColumn = col;
				getBoard()[newRow][newColumn] = OCC;
			} else if (orientation.equals(Orientation.LEFT)) {
				newRow = row;
				newColumn = col - i;
				getBoard()[newRow][newColumn] = OCC;
			}

			coordinateList.add(new Coordinate(newRow, newColumn));
		}
		Ship thisShip = new Ship(length, coordinateList);
		return thisShip;
	}

	/**
	 * Add ship method to boardModel
	 * 
	 * @param thisShip
	 *            Ship that is being added
	 * @return ArrayList<Coordinate> of ships on board
	 */
	public ArrayList<Coordinate> addShip(Ship thisShip) {
		ArrayList<Coordinate> coordinateList = thisShip.getCoordinates();
		for (Coordinate c : coordinateList) {
			coordinateShipMap.put(c, thisShip);
		}
		this.shipList.add(thisShip);
		leftToPlace.remove(new Integer(coordinateList.size()));
		this.leftToSink.add(thisShip);
		ArrayList<Coordinate> coordinateListToReturn = new ArrayList<Coordinate>();
		for (Coordinate c : coordinateList) {
			coordinateListToReturn
					.add(new Coordinate(c.getRow(), c.getColumn()));
		}
		return coordinateListToReturn;
	}

	/**
	 * Checks if boardModel is valid
	 * 
	 * @return boolean associated with whether all ships have been placed on the
	 *         board
	 * @effects used for determine if ready to send a board hash
	 */
	public boolean isValidShipSetup() {
		if (leftToPlace.size() == 0) {
			return true;
		} else {
			// System.out.println("hello, still not enough ships");
			return false;
		}
	}

	/**
	 * Creates board representation from given Array
	 * 
	 * @requires A board with all ships placed; game play should not be started
	 *           when this is called.
	 * @return Board representation of ArrayList in binary string
	 * @effects Gets the board representation from the current board state
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
	 * @return Number of ships left to be placed
	 */
	public int shipsRemaining() {
		return leftToPlace.size();
	}

	public int getSalt() {
		return this.salt;
	}

	public void setLeftToPlace(ArrayList<Integer> newLeftToPlace) {
		leftToPlace = newLeftToPlace;
	}

	/**
	 * @effects Clears boardModel
	 */
	public void resetBoard() {
		for (int i = 0; i < this.getHeight(); ++i) {
			for (int j = 0; j < this.getWidth(); ++j) {
				super.getBoard()[i][j] = 0;
			}
		}
	}
}
