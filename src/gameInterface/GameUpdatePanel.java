package gameInterface;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.ArrayBlockingQueue;

import info.clearthought.layout.TableLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import startingInterface.AddShipButtonListener;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import datatypes.Message;

/**
 * 
 * @author Zach
 * 
 */

// GUI for chat and history
public class GameUpdatePanel extends JPanel {
	JTextArea updates = new JTextArea(
			"INSTRUCTIONS: START by clicking on a square on the ENEMY board and clicking on FIRE! If a player"
					+ " hits an enemy ship, then that player goes again. First player to hit all enemy ships loses!"
					+ "\n"
					+ "*SALVO MODE: Target as many squares as the number of your unsunk ships. "
					+ "Players do NOT take extra turns no matter the results.");
	JTextField chatBox;
	JTextArea chatMessages;
	JScrollPane jspChat;
	JScrollPane jspUpdate;
	ArrayBlockingQueue<Message> queue;
	private ImageIcon chatGraphic = createImageIcon("chat.jpg");
	private ImageIcon historyGraphic = createImageIcon("history.jpg");
	private ImageIcon musicGraphic = createImageIcon("backgroundmusic.jpg");
	JLabel chat = new JLabel(chatGraphic, JLabel.CENTER);
	JLabel history = new JLabel(historyGraphic, JLabel.CENTER);

	/**
	 * Constructor for GameUpdatePanel
	 * 
	 * @param queue
	 *            ArrayBlockQueue<Message> for messages to be passed
	 */
	public GameUpdatePanel(ArrayBlockingQueue<Message> queue) {
		// Sets boundaries of text fields
		this.queue = queue;
		updates.setEditable(false);
		updates.setColumns(15);
		updates.setRows(5);
		updates.setLineWrap(true);
		updates.setWrapStyleWord(true);

		chatBox = new JTextField();
		chatBox.setEditable(true);

		chatMessages = new JTextArea();
		chatMessages.setEditable(false);
		chatMessages.setColumns(25);
		chatMessages.setRows(5);
		chatMessages.setLineWrap(true);
		chatMessages.setWrapStyleWord(true);

		// Size layout
		double sizeCP[][] = {
				{ 5, TableLayout.FILL, 5 },
				{ 3, TableLayout.PREFERRED, 3, TableLayout.PREFERRED, 6,
						TableLayout.PREFERRED, 3, TableLayout.PREFERRED, 3,
						TableLayout.PREFERRED, 10, TableLayout.PREFERRED } };
		double sizeMP[][] = { { TableLayout.FILL, TableLayout.PREFERRED, TableLayout.FILL },
				{ TableLayout.PREFERRED, TableLayout.PREFERRED } };

		// Sets layouts and borders
		this.setLayout(new TableLayout(sizeCP));
		this.setBackground(Color.black);
		
		jspChat = new JScrollPane(chatMessages);
		jspUpdate = new JScrollPane(updates);
		jspUpdate.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));
		jspChat.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));
		chatBox.setBorder(BorderFactory.createLineBorder(Color.lightGray, 4));

		// Adds components
		this.add(history, "1,1");
		this.add(jspUpdate, "1,3");
		this.add(jspChat, "1,7");
		this.add(chat, "1,5");
		this.add(chatBox, "1,9");

		chatBox.addActionListener(new SendChatListener(queue));
	}

	public void addUpdate(String text) {
		updates.append(text + "\n");
		updates.setCaretPosition(updates.getDocument().getLength());
	}

	public void addChatMessage(String text) {
		chatMessages.append(text + "\n");
		chatMessages.setCaretPosition(chatMessages.getDocument().getLength());
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
