package boardEngine;

/*@author Kevin*/

import info.clearthought.layout.TableLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.*;
import javax.swing.*;

import datatypes.Coordinate;

public class BoardGUI extends JPanel {
	static final long serialVersionUID = 1L;

	// HashMap to store square objects for playingBoard
	private HashMap<Coordinate, JPanel> cells = new HashMap<Coordinate, JPanel>();
	// Scaling variable
	private final int val = 30;

	// playingBoard utilized in hostBoardGUI and opponentBoardGUI
	private JPanel playingBoard;

	// Panel layouts for BoardGUI
	private JPanel allBoardPanel = new JPanel();
	private JPanel playingallBoardPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	
	/**
	 * Parent class to HostBoardGUI and OppoenentBoardGUI,
	 * GUI representation of game based on initial BoardModel
	 * 
	 * @requires BoardModel b to construct
	 * @effects creates a BoardGUI based on the parameter BoardModel
	 * @param b
	 *            BoardModel that will constructed
	 */
	public BoardGUI(BoardModel b) {

		// Creates playingBoard passed on to hostBoardGUI and opponentBoardGUI
//		System.out.println("In BoardGUI constructor");
//		System.out.printf("width: %d, height: %d \n", b.getWidth(),
//				b.getHeight());
		Dimension boardSize = new Dimension(b.getWidth() * getVal(),
				b.getHeight() * getVal());
		setPlayingBoard(new JPanel());
		getPlayingBoard()
				.setLayout(new GridLayout(b.getHeight(), b.getWidth()));
		getPlayingBoard().setPreferredSize(boardSize);
		getPlayingBoard().setBounds(0, 0, boardSize.height, boardSize.width);
		getPlayingBoard().setBorder(
				BorderFactory.createLineBorder(Color.lightGray, 5));

		// Sizes for layouts in playingBoard
		double sizeAll[][] = {
				{ TableLayout.PREFERRED },
				{ TableLayout.PREFERRED, TableLayout.PREFERRED,
						TableLayout.FILL } };
		double sizeBottom[][] = {
				{ TableLayout.FILL },
				{ 10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED,
						TableLayout.PREFERRED } };
		double sizeTop[][] = {
				{ TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL },
				{ TableLayout.PREFERRED } };

		// Set layout and background for allBoardPanel and bottomPanel
		getallBoardPanel().setLayout(new TableLayout(sizeAll));
		getallBoardPanel().setBackground(Color.black);
		getBottomPanel().setLayout(new TableLayout(sizeBottom));
		getBottomPanel().setBackground(Color.black);
		playingallBoardPanel.setLayout(new TableLayout(sizeTop));
		playingallBoardPanel.setBackground(Color.black);
		playingBoard.setBackground(Color.lightGray);

		// Separate playingallBoardPanel to protect size
		playingallBoardPanel.add(getPlayingBoard(), "1,0");

		// Adds everything to allBoardPanel
		getallBoardPanel().add(playingallBoardPanel, "0,1");
		getallBoardPanel().add(getBottomPanel(), "0,2");
	}

	// Getters/Setters
	public int getVal() {
		return val;
	}

	public void setCells(HashMap<Coordinate, JPanel> cells) {
		this.cells = cells;
	}

	public HashMap<Coordinate, JPanel> getCells() {
		return cells;
	}

	public void setPlayingBoard(JPanel playingBoard) {
		this.playingBoard = playingBoard;
	}

	public JPanel getPlayingBoard() {
		return playingBoard;
	}

	public void setBottomPanel(JPanel bottomPanel) {
		this.bottomPanel = bottomPanel;
	}

	public JPanel getBottomPanel() {
		return bottomPanel;
	}

	public void setallBoardPanel(JPanel allBoardPanel) {
		this.setAllBoardPanel(allBoardPanel);
	}

	public JPanel getallBoardPanel() {
		return getAllBoardPanel();
	}

	public void setAllBoardPanel(JPanel allBoardPanel) {
		this.allBoardPanel = allBoardPanel;
	}

	public JPanel getAllBoardPanel() {
		return allBoardPanel;
	}
}