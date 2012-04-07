package boardEngine;

/*@author Kevin and Zach*/

import info.clearthought.layout.TableLayout;

import java.awt.*;
import javax.swing.*;

import communication.ClientManager;

import datatypes.Coordinate;

import java.awt.Color;
import java.util.ArrayList;

public class OpponentBoardGUI extends BoardGUI {
	private static final long serialVersionUID = 1L;
	private Coordinate currCoor;
	private Coordinate currentCell;
	private Coordinate targetedCell;
	final ImageIcon eWater = createImageIcon("enemyWaters.jpg");
	final JLabel enemyWaters = new JLabel(eWater, JLabel.CENTER);

	// Fire button specific to opponentBoardGUI
	final JButton fireButton = new JButton("Fire!");

	// Mute panel capability
	final JPanel mutePanel = new JPanel();
	final JCheckBox mute = new JCheckBox("Mute Sounds");

	private ClientManager cm;
	private OpponentBoardModel obm;
	
	ArrayList<Coordinate> listOfTargets = new ArrayList<Coordinate>();

	/**
	 * OpponentBoardGUI to be used in the game
	 * 
	 * @effects Constructs BoardGUI representation
	 * @param cm
	 *            ClientManager object that is "running" the boardGUI in a game
	 */
	public OpponentBoardGUI(ClientManager cm) {
		super(cm.getOpponentBoardModel());

		double sizeMutePanel[][] = {
				{ TableLayout.FILL, TableLayout.PREFERRED },
				{ TableLayout.PREFERRED } };

		// Stores client manager and opponenetBoardModel
		this.setCm(cm);
		this.obm = cm.getOpponentBoardModel();

		// Fill in squares with appropriate colors
		for (int i = 0; i < obm.getHeight(); i++) {
			for (int j = 0; j < obm.getWidth(); j++) {
				JPanel square = new JPanel(new BorderLayout());
				square.setBackground(Color.blue);
				// Set borderline
				square.setBorder(BorderFactory.createLineBorder(
						Color.lightGray, 2));
				// Stores square into hashMap
				getCells().put(new Coordinate(i, j), square);

				// square.addMouseListener(new SquareListener(square));
				// Adds square to playingBoard
				getPlayingBoard().add(square);
			}
		}

		// Creates mute panels
		mutePanel.setLayout(new TableLayout(sizeMutePanel));
		mutePanel.setBackground(Color.black);
		mutePanel.add(getMute(), "1,0");

		// Sets graphic of enemyWaters
		enemyWaters.setBackground(Color.black);

		// Adds listeners
		getPlayingBoard().addMouseListener(new BoardMouseListener(this));
		getFireButton()
				.addActionListener(new FireButtonListener(this.getCm(), this));

		// Adds components to panels
		getBottomPanel().add(getFireButton(), "0,1");
		getallBoardPanel().add(enemyWaters, "0,0");
		getBottomPanel().add(mutePanel, "0,4");
		getFireButton().setEnabled(false);
	}

	/**
	 * @effects Depending on verify, will store targetedCell equal to
	 *          currentCell and enable the fireButton; updates target square
	 *          location, called by BoardMouseListener
	 */
	public void target() {
		this.setTargetedCell(new Coordinate(getCurrentCell().getRow(),
				getCurrentCell().getColumn()));
		getFireButton().setEnabled(true);

		for (JPanel j : getCells().values()) {
			if (j.getBackground() == Color.white)
				j.setBackground(Color.blue);
		}
		getCells().get(getTargetedCell()).setBackground(Color.white);
	}

	/**
	 * @effects Verifies that currentCell is blue, a valid location to aim and
	 *          fire at, called by BoardMouseListener
	 * @returns boolean if square is a valid location to aim at
	 */
	public boolean verify() {
		return getCells().get(getCurrentCell()).getBackground() == Color.blue;
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
	public void updateGUI(int row, int col, boolean response) {
		// If response is true, then updates HIT
		if (response) {
			getCells().get(new Coordinate(row, col)).setBackground(Color.red);
		}
		// If false, then updates to MISS
		else {
			getCells().get(new Coordinate(row, col)).setBackground(Color.gray);
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

	// Getters/Setters
	public void setCurrentCell(Coordinate currentCell) {
		this.currentCell = currentCell;
	}

	public Coordinate getCurrentCell() {
		return currentCell;
	}

	public void setTargetedCell(Coordinate targetedCell) {
		this.targetedCell = targetedCell;
	}

	public Coordinate getTargetedCell() {
		return targetedCell;
	}

	public JButton getFireButton() {
		return fireButton;
	}

	public JCheckBox getMute() {
		return mute;
	}

	public void setCm(ClientManager cm) {
		this.cm = cm;
	}

	public ClientManager getCm() {
		return cm;
	}
}
