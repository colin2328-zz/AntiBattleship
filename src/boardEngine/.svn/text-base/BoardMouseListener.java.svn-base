package boardEngine;

/*@author Kevin*/

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import datatypes.Coordinate;

// MouseListener that listens to playingBoard and updates GUI based on previous colors
public class BoardMouseListener implements MouseListener {
	private OpponentBoardGUI g;

	public BoardMouseListener(OpponentBoardGUI g) {
		this.g = g;
	}

	public void mouseClicked(MouseEvent e) {
		g.setCurrentCell(new Coordinate(e.getY() / g.getVal(), e.getX()
				/ g.getVal()));
		if (g.getCm().isSalvoMode()) {
			
			int numMoves = g.getCm().getHostBoardModel().numShipsLeftToSink();

			if (g.listOfTargets.size() < numMoves ) {
				if (g.getCells().get(g.getCurrentCell()).getBackground() == Color.blue) {
	
					g.listOfTargets.add(g.getCurrentCell());
					g.getCells().get(g.getCurrentCell())
							.setBackground(Color.white);
		
					if (g.listOfTargets.size() == numMoves) {
						g.getPlayingBoard().setEnabled(false);
						g.fireButton.setEnabled(true);
					}
				}
			}

		} else {
			// Stores component coordinates
			g.setCurrentCell(new Coordinate(e.getY() / g.getVal(), e.getX()
					/ g.getVal()));
			// Relays commands to OpponentBoardGUI
			if (g.verify())
				g.target();
//			System.out.println("target cell coordinates: ");
			g.getTargetedCell().display();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}
}
