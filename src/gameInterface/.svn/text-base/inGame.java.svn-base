package gameInterface;

/*@author Kevin*/

import info.clearthought.layout.TableLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import boardEngine.*;

import datatypes.Message;

// Main GUI
public class inGame extends JPanel {
	static final long serialVersionUID = 1L;

	// Swing components
	final JPanel top = new JPanel();
	final JPanel bottom = new JPanel();
	final JPanel boardsPanel = new JPanel();
	final JPanel imagePanel = new JPanel();
	final JPanel midPanel = new JPanel();
	final ImageIcon titleBanner = createImageIcon("TitleBanner.jpg");
	final ImageIcon shipGraphic = createImageIcon("ussiowa.jpg");
	final JLabel title = new JLabel(titleBanner, JLabel.CENTER);
	final JLabel ship = new JLabel(shipGraphic, JLabel.CENTER);

	/**
	 * Constructor for inGame
	 * 
	 * @param mine
	 *            BoardGUI that is hosts
	 * @param your
	 *            BoardGUI that is opponents
	 * @param gup
	 *            GameUpdatePanel to place in middle
	 */
	public inGame(BoardGUI mine, BoardGUI your, GameUpdatePanel gup) {
		
		// Layouts for GUI
		double sizeTop[][] = { { TableLayout.PREFERRED },
				{ TableLayout.PREFERRED, TableLayout.PREFERRED } };

		double sizeBottom[][] = { { 100, TableLayout.FILL, 100 },
				{ 20, TableLayout.PREFERRED, 20 } };

		double sizeBoardsPanel[][] = {
				{ 50, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10,
						TableLayout.PREFERRED, 50 }, { TableLayout.PREFERRED } };

		double imageP[][] = { { 15, TableLayout.FILL, 15 },
				{ TableLayout.PREFERRED } };

		double sizeMid[][] = { { TableLayout.PREFERRED },
				{ TableLayout.PREFERRED, 10, TableLayout.PREFERRED } };
		
		// Sets layouts and backgrounds
		this.setLayout(new TableLayout(sizeTop));
		this.setBackground(Color.black);

		bottom.setLayout(new TableLayout(sizeBottom));
		bottom.setBackground(Color.black);

		imagePanel.setLayout(new TableLayout(imageP));
		imagePanel.add(ship, "1,0");
		imagePanel.setBackground(Color.black);

		midPanel.setLayout(new TableLayout(sizeMid));
		midPanel.setBackground(Color.black);
		
		boardsPanel.setLayout(new TableLayout(sizeBoardsPanel));
		boardsPanel.setBackground(Color.black);
		
		
		// Adds components
		midPanel.add(imagePanel, "0,0");
		midPanel.add(gup, "0,2");
		midPanel.setBackground(Color.black);
		this.add(title, "0,0");
		boardsPanel.add(midPanel, "3,0");
		boardsPanel.add(mine.getallBoardPanel(), "1,0");
		boardsPanel.add(your.getallBoardPanel(), "5,0");
		bottom.add(boardsPanel, "1,1");
		this.add(bottom, "0,1");
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
