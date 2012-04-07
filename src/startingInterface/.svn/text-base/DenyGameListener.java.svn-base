package startingInterface;

import java.awt.event.ActionEvent;
import communication.ClientManager;
import communication.ConstructMessage;

import java.awt.event.ActionListener;

//ActionListener that denies games in AcceptDenyPanel
public class DenyGameListener implements ActionListener {
	private ClientManager cm;
	
	public DenyGameListener(ClientManager cm) {
		this.cm = cm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cm.addToQueue(ConstructMessage.makeGameDenyMessage());
	}

}
