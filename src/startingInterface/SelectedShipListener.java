package startingInterface;


import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import boardEngine.HostBoardGUI;

public class SelectedShipListener implements ListSelectionListener {
	SetUp setup;
	public SelectedShipListener(SetUp s) {
		this.setup = s ;
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
//		System.out.println("inValueChanged");
		JList list = (JList) e.getSource();
		Integer item = (Integer) list.getSelectedValue();
		if (item != null) {
//			System.out.println("hello");
//			System.out.printf("Ship size is: %d \n",item);
			setup.setCurrentShipLength(item);
			
		}
	}

}
