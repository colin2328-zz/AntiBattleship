package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Clears board of current game
public class ClearBoardListener implements ActionListener {
	SetUp s;
	
	public ClearBoardListener(SetUp s) {
		this.s = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		s.clearBoard();
	}
}
