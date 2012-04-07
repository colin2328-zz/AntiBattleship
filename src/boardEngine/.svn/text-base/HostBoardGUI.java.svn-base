package boardEngine;

/*@author Kevin and Zach*/

import gameInterface.DeclareVictoryListener;

import java.awt.*;
import javax.swing.*;

import datatypes.Coordinate;
import datatypes.Message;
import datatypes.Orientation;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ArrayBlockingQueue;

public class HostBoardGUI extends BoardGUI {
	private static final long serialVersionUID = 1L;
	private ArrayBlockingQueue<Message> cmQueue;
	final ImageIcon fWater = createImageIcon("friendlyWaters.jpg");
	final JLabel friendlyWaters = new JLabel(fWater, JLabel.CENTER);

	/**
	 * HostBoardGUI that represents the players board, used in inGame and SetUp,
	 * inherits from BoardGUI
	 * 
	 * @effects Constructs the HostBoardGUI
	 * @param b
	 *            HostBoardModel that is represented
	 * @param queue
	 *            ArrayBlockingQueue<Message> to facilitate game
	 */
	public HostBoardGUI(HostBoardModel b, ArrayBlockingQueue<Message> queue) {
		super(b);

		// Fill in squares with appropriate colors
		for (int i = 0; i < b.getHeight(); i++) {
			for (int j = 0; j < b.getWidth(); j++) {
				JPanel square = new JPanel(new BorderLayout());
				// If square is vacant
				if (b.getBoard()[i][j] == b.VAC)
					square.setBackground(Color.blue);
				// If square is occupied
				if (b.getBoard()[i][j] == b.OCC)
					square.setBackground(Color.darkGray);
				// Set borderline
				square.setBorder(BorderFactory.createLineBorder(
						Color.lightGray, 2));
				// Stores square into hashMap
				getCells().put(new Coordinate(i, j), square);
				// Adds square to playingBoard
				getPlayingBoard().add(square);
			}
		}
		getallBoardPanel().add(friendlyWaters, "0,0");
	}

	/**
	 * Updates game based on previous actions
	 * 
	 * @requires Valid int x and y and a result string
	 * @effects Updates the coordinate cell with the appropriate response/color
	 * @param row
	 *            int for coordinate reference
	 * @param column
	 *            int for coordinate reference
	 * @param result
	 *            String which contains result of last move
	 */
	public void updateGUI(int row, int column, String result) {
		// Checks status and either changes color or responds with game error
//		System.out.println(result);
		if (result.equals("water")) {
//			System.out.println("in the update water");
			JPanel thisCell = getCells().get(new Coordinate(row, column));
			thisCell.setBackground(Color.gray);
			thisCell.repaint();
		} else if (result.equals("fire") || result.equals("sunk")) {
//			System.out.println("in the update fire");
			JPanel thisCell = getCells().get(new Coordinate(row, column));
			thisCell.setBackground(Color.red);
			thisCell.repaint();
		}
	}

	/**
	 * Colors squares from start to end
	 * 
	 * @effects alters colors on HostBoardGUI
	 * @param currentStartingSquare
	 *            Coordinate that marks starting square
	 * @param coordinate
	 *            Coordinate that markes where ship is
	 * @param color
	 *            Color to change square to
	 */
	public void colorStartingEnding(Coordinate currentStartingSquare,
			Coordinate coordinate, Color color) {
		if (currentStartingSquare.getColumn() == coordinate.getColumn()) {
			int col = currentStartingSquare.getColumn();
			int minRow = currentStartingSquare.getRow();
			int maxRow = coordinate.getRow();
			if (currentStartingSquare.getRow() > coordinate.getRow()) {
				minRow = coordinate.getRow();
				maxRow = currentStartingSquare.getRow();
			}
			for (int row = minRow; row < maxRow + 1; row++) {
				Coordinate ab = new Coordinate(row, col);
				ab.display();
				getCells().get(ab).setBackground(color);
			}
		}
		if (currentStartingSquare.getRow() == coordinate.getRow()) {
			int row = currentStartingSquare.getRow();
			int minCol = currentStartingSquare.getColumn();
			int maxCol = coordinate.getColumn();
			if (currentStartingSquare.getColumn() > coordinate.getColumn()) {
				minCol = coordinate.getColumn();
				maxCol = currentStartingSquare.getColumn();
			}
			for (int col = minCol; col < maxCol + 1; col++) {
				Coordinate bc = new Coordinate(row, col);
				bc.display();
				getCells().get(bc).setBackground(color);
			}
		}
//		System.out.println("end");
	}

	/**
	 * Generates orientation length of ship
	 * 
	 * @effects Places ship and changes color of BoardGUI
	 * @param color
	 *            Color to color squares
	 * @param startingSquare
	 *            Coordinate to mark starting location
	 * @param orientation
	 *            Integer with correct direction
	 * @param length
	 *            int that marks length of ship
	 */
	public void colorStartOrientationLength(Color color,
			Coordinate startingSquare, Integer orientation, int length) {

//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.RIGHT)) {
			Coordinate rightSquare = new Coordinate(startingSquare.getRow(),
					startingSquare.getColumn() + 1);
			colorStartingEnding(
					rightSquare,
					new Coordinate(startingSquare.getRow(), startingSquare
							.getColumn() + length - 1), color);
		}
//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.LEFT)) {
			Coordinate leftSquare = new Coordinate(startingSquare.getRow(),
					startingSquare.getColumn() - 1);
			colorStartingEnding(
					leftSquare,
					new Coordinate(startingSquare.getRow(), startingSquare
							.getColumn() - length + 1), color);
		}
//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.UP)) {
			Coordinate upSquare = new Coordinate(startingSquare.getRow() + 1,
					startingSquare.getColumn());
			colorStartingEnding(upSquare,
					new Coordinate(startingSquare.getRow() + length - 1,
							startingSquare.getColumn()), color);
		}
//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.DOWN)) {
			Coordinate downSquare = new Coordinate(startingSquare.getRow() - 1,
					startingSquare.getColumn());
			colorStartingEnding(downSquare,
					new Coordinate(startingSquare.getRow() - length + 1,
							startingSquare.getColumn()), color);
		}
	}
	/**
	 * Generates orientation length of ship of all
	 * 
	 * @effects Places ship and changes color of BoardGUI
	 * @param color
	 *            Color to color squares
	 * @param startingSquare
	 *            Coordinate to mark starting location
	 * @param orientation
	 *            Integer with correct direction
	 * @param length
	 *            int that marks length of ship
	 */
	public void colorStartOrientationLengthAll(Color color,
			Coordinate startingSquare, Integer orientation, int length) {
//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.RIGHT)) {
			colorStartingEnding(
					startingSquare,
					new Coordinate(startingSquare.getRow(), startingSquare
							.getColumn() + length - 1), color);
		}
//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.LEFT)) {

			colorStartingEnding(
					startingSquare,
					new Coordinate(startingSquare.getRow(), startingSquare
							.getColumn() - length + 1), color);
		}
//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.UP)) {

			colorStartingEnding(startingSquare,
					new Coordinate(startingSquare.getRow() + length - 1,
							startingSquare.getColumn()), color);
		}

//		System.out.printf("My orientation is %d \n", orientation);
		if (orientation.equals(Orientation.DOWN)) {

			colorStartingEnding(startingSquare,
					new Coordinate(startingSquare.getRow() - length + 1,
							startingSquare.getColumn()), color);
		}
	}

	/**
	 * Graphic generator method
	 * 
	 * @param path
	 *            String that specifies file location
	 * @return returns ImageIcon with specified path for graphical use
	 */
	protected ImageIcon createImageIcon(String path) {
		return new ImageIcon(path);
	}
}
