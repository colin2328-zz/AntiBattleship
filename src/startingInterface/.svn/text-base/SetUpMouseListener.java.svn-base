package startingInterface;

/*@author Kevin and Zach*/


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import boardEngine.*;
import datatypes.Coordinate;

// SetUpMouseListener to store clicked coordinates
public class SetUpMouseListener implements MouseListener {
	private SetUp s;
	private int currentLength;
	private HostBoardGUI hostBoardGUI;
	
	

	public SetUpMouseListener(SetUp s, HostBoardModel hostBoardModel) {
		this.s = s;
	}

	public void setCurrentLength(int i) {
		currentLength = i;
	}

	public void mouseClicked(MouseEvent e) {
		s.setFirstCell(new Coordinate(e.getY() / s.getBoardGUI().getVal(), e.getX()
				/ s.getBoardGUI().getVal()));
		s.placeShip();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
