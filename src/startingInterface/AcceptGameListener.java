package startingInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import communication.ClientManager;
import communication.ConstructMessage;

// ActionListener that accepts games in AcceptDenyPanel
public class AcceptGameListener implements ActionListener {
	private ClientManager cm;
	
	public AcceptGameListener(ClientManager cm) {
		this.cm = cm;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		cm.addToQueue(ConstructMessage.makeGameAcceptMessage());
	}
	

}
