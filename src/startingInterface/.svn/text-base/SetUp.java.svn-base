package startingInterface;

/*@author Kevin and Zach*/

import info.clearthought.layout.TableLayout;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import boardEngine.*;

import communication.ClientManager;

import datatypes.Coordinate;
import datatypes.Orientation;

// Setup panel to place ships
public class SetUp extends JPanel {
	private static final long serialVersionUID = 1L;

	// Marks initial coordinate target
	private Coordinate firstCell;

	// Swing Components
	final JPanel middle = new JPanel();
	final JPanel right = new JPanel();
	final JPanel left = new JPanel();
	final JPanel leftMiddle = new JPanel();
	final JPanel listShips = new JPanel();
	final JPanel imagePanel = new JPanel();

	final JButton play = new JButton("Play!");
	final JButton clearBoard = new JButton("Clear Board");
	final JLabel selectShipLabel = new JLabel(
			"1. Select a ship from the Ship List on the left and click on the board.");
	final JLabel selectShipLabel1 = new JLabel(
			"2. A valid placement of the ship is marked by green squares.");
	final JLabel selectShipLabel2 = new JLabel(
			"3. Click on an orientation to place the ship in that position.");
	final JLabel selectShipLabel3 = new JLabel(
			"*If there is only a white square, then there are no valid placements.");
	final JLabel selectShipLabel4 = new JLabel(
			"**Battleships may NOT be placed within a one block radius of each other.");
	final JLabel shipListLabel = new JLabel("Ship List: ", JLabel.CENTER);
	private HostBoardModel hostBoardModel;
	private HostBoardGUI hostBoardGUI;
	private ClientManager cm;
	private DefaultListModel currentShipList;
	private int currentLength;
	final Color boardBackground = Color.blue;
	final Color validColor = Color.green;
	final Color occupiedColor = Color.DARK_GRAY;
	private File f = new File("tbardy00.wav");

	// Graphics used
	final ImageIcon shipGraphic = createImageIcon("ship.jpg");
	final JLabel ship = new JLabel(shipGraphic, JLabel.CENTER);
	final ImageIcon setUpImage = createImageIcon("shipPlacement.jpg");
	final JLabel shipPlacement = new JLabel(setUpImage, JLabel.CENTER);

	// Stores current square
	Coordinate currentStartingSquare;
	private ArrayList<Integer> allOrientations;

	/**
	 * Constructor for SetUp
	 * 
	 * @param cm
	 *            ClientManager that 'runs' the game
	 */
	public SetUp(ClientManager cm) {
		// Declaration
		this.cm = cm;
		this.hostBoardModel = cm.getHostBoardModel();
		this.setBoardGUI(cm.getHostBoardGUI());
		this.currentStartingSquare = null;

		this.clearBoard.addActionListener(new ClearBoardListener(this));
		setCurrentShipList(new DefaultListModel());
		JList SelectShipList = new JList(getCurrentShipList());
		SelectShipList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane jsp = new JScrollPane(SelectShipList);
		ListSelectionModel lsm = SelectShipList.getSelectionModel();

		SelectShipList.addListSelectionListener(new SelectedShipListener(this));
		play.addActionListener(new SendHashListener(this.cm));
		play.setEnabled(false);
		getBoardGUI().getPlayingBoard().addMouseListener(
				new SetUpMouseListener(this, hostBoardModel));
		currentLength = 0;
		allOrientations = new ArrayList<Integer>();
		allOrientations.add(Orientation.UP);
		allOrientations.add(Orientation.RIGHT);
		allOrientations.add(Orientation.DOWN);
		allOrientations.add(Orientation.LEFT);

		selectShipLabel.setForeground(Color.lightGray);
		selectShipLabel1.setForeground(Color.lightGray);
		selectShipLabel2.setForeground(Color.lightGray);
		selectShipLabel3.setForeground(Color.lightGray);
		selectShipLabel4.setForeground(Color.lightGray);
		shipListLabel.setForeground(Color.lightGray);

		// Layout sizes for SetUp
		double sizeAll[][] = { { 25, TableLayout.PREFERRED, 25 },
				{ 25, TableLayout.PREFERRED, 25 } };
		double sizeMiddle[][] = {
				{ TableLayout.PREFERRED, 10, TableLayout.PREFERRED },
				{ TableLayout.PREFERRED } };
		double sizeRight[][] = {
				{ 10, TableLayout.PREFERRED, 10 },
				{ 10, TableLayout.FILL, 10, TableLayout.PREFERRED, 10,
						TableLayout.PREFERRED } };
		double sizeLeft[][] = {
				{ TableLayout.PREFERRED },
				{ TableLayout.PREFERRED, TableLayout.PREFERRED,
						TableLayout.PREFERRED, TableLayout.PREFERRED,
						TableLayout.PREFERRED, TableLayout.PREFERRED,
						TableLayout.FILL, TableLayout.PREFERRED, 10 } };
		double sizeLeftMiddle[][] = {
				{ TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL },
				{ TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL } };
		double sizeListShips[][] = {
				{ TableLayout.PREFERRED, 30, TableLayout.PREFERRED,
						TableLayout.FILL },
				{ TableLayout.PREFERRED, TableLayout.FILL } };

		// Sets layouts for panels
		right.setLayout(new TableLayout(sizeRight));
		listShips.setLayout(new TableLayout(sizeListShips));
		left.setLayout(new TableLayout(sizeLeft));
		leftMiddle.setLayout(new TableLayout(sizeLeftMiddle));
		middle.setLayout(new TableLayout(sizeMiddle));
		this.setLayout(new TableLayout(sizeAll));

		// Sets background colors
		this.setBackground(Color.black);
		listShips.setBackground(Color.black);
		middle.setBackground(Color.black);
		right.setBackground(Color.black);
		left.setBackground(Color.black);
		leftMiddle.setBackground(Color.black);
		jsp.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));

		// Adds components
		imagePanel.add(ship, "0,1");
		imagePanel.setBackground(Color.black);
		listShips.add(shipListLabel, "0,0");
		listShips.add(imagePanel, "2,1");
		listShips.add(jsp, "0,1");
		leftMiddle.add(getBoardGUI().getPlayingBoard(), "1,1");
		left.add(leftMiddle, "0,6");
		left.add(shipPlacement, "0,0");
		left.add(selectShipLabel, "0,1");
		left.add(selectShipLabel1, "0,2");
		left.add(selectShipLabel2, "0,3");
		left.add(selectShipLabel3, "0,4");
		left.add(selectShipLabel4, "0,5");
		right.add(listShips, "1,1");
		right.add(clearBoard, "1,3");
		right.add(play, "1,5");
		middle.add(left, "0,0");
		middle.add(right, "2,0");
		this.add(middle, "1,1");

		for (Integer i : hostBoardModel.getShipSizeList()) {
			getCurrentShipList().addElement(i);
		}
	}

	/**
	 * @effects Clear possible ships
	 */
	public void clearPossibles() {
//		System.out.println("in clear possibles");
		if (currentStartingSquare != null) {
//			System.out.println("currentStartinSquare is null");
			getBoardGUI().getCells().get(currentStartingSquare)
					.setBackground(boardBackground);
			for (Integer i : allOrientations) {
				if (hostBoardModel.checkShipLocation(i, currentStartingSquare,
						currentLength)) {
					getBoardGUI().colorStartOrientationLengthAll(
							boardBackground, currentStartingSquare, i,
							currentLength);
				}
			}
		}
	}

	/**
	 * @effects Places ships on board with valid conditions
	 */
	public void placeShip() {
		Integer correspondingOrientation = null;
		if (currentStartingSquare != null) {
			correspondingOrientation = Orientation.getOrientation(
					currentStartingSquare, getFirstCell(), currentLength);
		}

		ArrayList<Integer> allOrientations = new ArrayList<Integer>();
		allOrientations.add(Orientation.UP);
		allOrientations.add(Orientation.RIGHT);
		allOrientations.add(Orientation.DOWN);
		allOrientations.add(Orientation.LEFT);

		if (hostBoardModel.allPlaced()) {
			return;
		}
		
		if (hostBoardModel.isOccupied(getFirstCell().getRow(),getFirstCell().getColumn())) {
			return;
		}
		
		if (this.currentLength == 1) {
			if (hostBoardModel.checkShipLocation(0, getFirstCell(), 1)) {
				try {
					FileInputStream fs = new FileInputStream(f);
					AudioStream st = new AudioStream(fs);
					AudioPlayer.player.start(st);
				} catch (IOException er) {
					er.printStackTrace();
				}
				getCurrentShipList().removeElement(new Integer(currentLength));
				hostBoardModel.setShipLocation(new Integer(0), getFirstCell(),
						currentLength);
				getBoardGUI().colorStartingEnding(getFirstCell(),
						getFirstCell(), occupiedColor);
				currentStartingSquare = null;
				if (hostBoardModel.allPlaced()) {
					play.setEnabled(true);
				}
				return;
			}
		}

		if ((this.currentStartingSquare != null && correspondingOrientation != -1)
				&& hostBoardModel.checkShipLocation(correspondingOrientation,
						currentStartingSquare, currentLength)) {
			getCurrentShipList().removeElement(new Integer(currentLength));
			for (Integer i : allOrientations) {
				if (!i.equals(correspondingOrientation)) {
//					System.out.println("I'm in here");
					if (hostBoardModel.checkShipLocation(i,
							currentStartingSquare, currentLength)) {
						getBoardGUI().colorStartOrientationLength(
								boardBackground, currentStartingSquare, i,
								currentLength);
					}
				}
			}
			try {
				FileInputStream fs = new FileInputStream(f);
				AudioStream st = new AudioStream(fs);
				AudioPlayer.player.start(st);
			} catch (IOException er) {
				er.printStackTrace();
			}

			hostBoardModel.setShipLocation(correspondingOrientation,
					currentStartingSquare, currentLength);
			getBoardGUI().colorStartOrientationLengthAll(occupiedColor,
					currentStartingSquare, correspondingOrientation,
					currentLength);

			currentStartingSquare = null;
		}

		else {
			if (currentStartingSquare != null) {
				getBoardGUI().getCells().get(currentStartingSquare)
						.setBackground(boardBackground);
				for (Integer i : allOrientations) {
					if (hostBoardModel.checkShipLocation(i,
							currentStartingSquare, currentLength)) {
						getBoardGUI().colorStartOrientationLengthAll(
								boardBackground, currentStartingSquare, i,
								currentLength);
					}
				}
			}

			currentStartingSquare = getFirstCell();
			getBoardGUI().getCells().get(currentStartingSquare)
					.setBackground(Color.white);
//			System.out.println("in the else of SetupMouseListener");
			for (Integer i : allOrientations) {
//				System.out.println("assessing orientations");
//				System.out.printf("currentLength: %d", currentLength);
				if (hostBoardModel.checkShipLocation(i, currentStartingSquare,
						currentLength)) {
					// System.out.println("found valid orientation");
					// s.currentStartingSquare
					getBoardGUI().colorStartOrientationLength(validColor,
							currentStartingSquare, i, currentLength);
					// System.out.println("done with this orientation");
				}
			}
		}
		if (hostBoardModel.allPlaced()) {
			play.setEnabled(true);
		}
	}

	/**
	 * Clears board
	 */
	public void clearBoard() {
		for (Coordinate c : getBoardGUI().getCells().keySet()) {
			getBoardGUI().getCells().get(c).setBackground(boardBackground);
		}

		ArrayList<Integer> newLeftToPlace = new ArrayList<Integer>();
		for (Integer s : this.hostBoardModel.getShipSizeList()) {
			newLeftToPlace.add(s);
		}

		this.hostBoardModel.setLeftToPlace(newLeftToPlace);
		getCurrentShipList().clear();
		for (Integer i : hostBoardModel.getShipSizeList()) {
			getCurrentShipList().addElement(i);
		}

		this.hostBoardModel.resetBoard();
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

	// Getters/Setters
	public void setCurrentShipLength(int i) {
		clearPossibles();
		currentLength = i;
	}

	public int getCurrentLength() {
		return this.currentLength;
	}

	public void setFirstCell(Coordinate firstCell) {
		this.firstCell = firstCell;
	}

	public Coordinate getFirstCell() {
		return firstCell;
	}

	public void setBoardGUI(HostBoardGUI boardGUI) {
		this.hostBoardGUI = boardGUI;
	}

	public HostBoardGUI getBoardGUI() {
		return hostBoardGUI;
	}

	public void setCurrentShipList(DefaultListModel currentShipList) {
		this.currentShipList = currentShipList;
	}

	public DefaultListModel getCurrentShipList() {
		return currentShipList;
	}

}
