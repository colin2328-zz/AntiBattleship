package boardEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import communication.ClientManager;
import datatypes.Coordinate;
import datatypes.Orientation;

public class TextShipSetup extends JFrame
implements ActionListener {

	JLabel text, clicked;
	JButton button;
	JPanel panel;
	JTextField textField;
	ClientManager cm;
	HostBoardModel hbm;

	public TextShipSetup(ClientManager cm){ //Begin Constructor
		text = new JLabel("{up,right};(x,y);length");
		textField = new JTextField(20);
		button = new JButton("Place Ship");
		button.addActionListener(this);
		this.cm = cm;
		this.hbm = cm.getHostBoardModel();
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		getContentPane().add(panel);
		panel.add("North", text);
		panel.add("Center", textField);
		panel.add("South", button);
	} //End Constructor

	public void actionPerformed(ActionEvent event){
		Object source = event.getSource();

		if(source == button){
			//Send data over socket
			String text = textField.getText();
			String shipLocationArgs[] = text.split(";");
			String orientation = shipLocationArgs[0];
			Integer corrOrientation = null;
			if (orientation.equals("up")) {
				corrOrientation = Orientation.UP;
			}
			if (orientation.equals("down")) {
				corrOrientation = Orientation.DOWN;
			}
			if (orientation.equals("left")) {
				corrOrientation = Orientation.LEFT;
			}
			if (orientation.equals("right")) {
				corrOrientation = Orientation.RIGHT;
			}
			
		
			String coords = shipLocationArgs[1];
			String coords1 = coords.replace(")","");
			String coords2 = coords1.replace("(", "");
			System.out.println(coords2);
			String xy[] = coords2.split(",");
			int x = Integer.parseInt(xy[0]);
			int y = Integer.parseInt(xy[1]);
			String lengthString = shipLocationArgs[2];
			int length = Integer.parseInt(lengthString);
			hbm.setShipLocation(corrOrientation,new Coordinate(x,y),length);
			//out.println(text);
			textField.setText(new String(""));
			System.out.printf("Ships remaining: %d \n",hbm.shipsRemaining());
			if (hbm.isValidShipSetup()) {
				cm.prepareHash();
				//System.exit(0);
			}
			
			//Receive text from server
		}
	}
/*
	public static void main(String[] args){
		TextShipSetup frame = new TextShipSetup();
		frame.setTitle("Ship Setup");
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};

		frame.addWindowListener(l);
		frame.pack();
		frame.setVisible(true);
	}*/
}

