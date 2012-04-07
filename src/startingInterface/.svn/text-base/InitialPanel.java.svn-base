package startingInterface;

import info.clearthought.layout.TableLayout;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import communication.ClientManager;

// InitialPanel for setting up board size and number/size of ships
public class InitialPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ClientManager cm;
	protected ArrayList<Integer> shipSize = new ArrayList<Integer>();

	final JPanel middle = new JPanel();
	final JPanel middleBottom = new JPanel();
	final JPanel left = new JPanel();
	final JPanel xP = new JPanel();
	final JPanel yP = new JPanel();
	final JPanel right = new JPanel();
	final JPanel rightTop = new JPanel();

	final JLabel boardsizeLabel = new JLabel("Board Size:");
	final JLabel xLabel = new JLabel("X: ");
	final JLabel yLabel = new JLabel("Y: ");
	final JLabel shipSizeLabel = new JLabel("Ship Size: ");
	protected JLabel createdShipsLabel = new JLabel("", JLabel.CENTER);

	private JList allCurrentShips;
	private DefaultListModel listModelCurrentShips;

	private JTextField createdShipsList = new JTextField("");
	private JTextField instructions = new JTextField(
			"Please enter board size and click on 'Set Board'.");
	private JTextField currentAction = new JTextField("");
	private JTextField sizeShip = new JTextField();
	protected JTextField xField = new JTextField("10");
	protected JTextField yField = new JTextField("10");

	private JCheckBox salvo = new JCheckBox("Salvo Mode");

	private JButton addShipButton = new JButton("Add Ship");
	private JButton clear = new JButton("Clear All");
	private JButton sendGameParameters = new JButton("Send Game Parameters");
	private JButton setBoardButton = new JButton("Set Board");

	private String output = "";

	/**
	 * Constructor for InitialPanel
	 * 
	 * @param cm
	 *            ClientManager that 'runs' game
	 */
	public InitialPanel(ClientManager cm) {
		this.cm = cm;

		// Adds listeners
		sizeShip.addActionListener(new AddShipButtonListener(this));
		getAddShipButton().addActionListener(new AddShipButtonListener(this));
		getClear().addActionListener(new ClearButtonListener(this));
		getSendGameParameters().addActionListener(
				new SendParametersListener(this, this.cm));
		getSetBoardButton().addActionListener(new SetBoardListener(this));

		// Dimensions of layouts
		double sizeThis[][] = {
				{ 10, TableLayout.PREFERRED, 10, },
				{ 10, TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10,
						TableLayout.PREFERRED, 10 } };
		double sizeMiddle[][] = {
				{ TableLayout.FILL },
				{ TableLayout.PREFERRED, TableLayout.PREFERRED, 10,
						TableLayout.FILL } };
		double sizeMiddleBottom[][] = { { 175, 30, 175 },
				{ TableLayout.PREFERRED } };
		double sizeLeft[][] = {
				{ TableLayout.FILL },
				{ TableLayout.PREFERRED, TableLayout.PREFERRED,
						TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10,
						TableLayout.PREFERRED } };
		double sizeLeftXY[][] = { { 30, TableLayout.FILL },
				{ TableLayout.PREFERRED } };
		double sizeRight[][] = {
				{ TableLayout.FILL },
				{ TableLayout.PREFERRED, 10, TableLayout.PREFERRED, 10,
						TableLayout.PREFERRED, TableLayout.PREFERRED } };
		double sizeRightTop[][] = { { 75, TableLayout.FILL },
				{ TableLayout.PREFERRED } };

		// Sets layouts
		this.setLayout(new TableLayout(sizeThis));
		middle.setLayout(new TableLayout(sizeMiddle));
		middleBottom.setLayout(new TableLayout(sizeMiddleBottom));
		right.setLayout(new TableLayout(sizeRight));
		rightTop.setLayout(new TableLayout(sizeRightTop));
		left.setLayout(new TableLayout(sizeLeft));
		xP.setLayout(new TableLayout(sizeLeftXY));
		yP.setLayout(new TableLayout(sizeLeftXY));

		// Adds components
		this.add(middle, "1,1");
		this.add(clear, "1,3");
		this.add(sendGameParameters, "1,5");

		middle.add(getInstructions(), "0,0");
		middle.add(getCurrentAction(), "0,1");
		middle.add(middleBottom, "0,3");

		middleBottom.add(left, "0,0");
		middleBottom.add(right, "2,0");

		rightTop.add(shipSizeLabel, "0,0");
		rightTop.add(sizeShip, "1,0");
		right.add(rightTop, "0,0");
		right.add(getAddShipButton(), "0,2");
		right.add(createdShipsLabel, "0,4");
		right.add(createdShipsList, "0,5");

		left.add(boardsizeLabel, "0,0");
		left.add(xP, "0,1");
		left.add(yP, "0,2");
		xP.add(xLabel, "0,0");
		xP.add(xField, "1,0");
		yP.add(yLabel, "0,0");
		yP.add(yField, "1,0");
		left.add(getSetBoardButton(), "0,4");
		left.add(salvo, "0,6");

		// Sets list parameters
		listModelCurrentShips = new DefaultListModel();
		final JList thisJList = new JList(listModelCurrentShips);
		JScrollPane scrollPane = new JScrollPane(thisJList);

		// Disables buttons
		getSendGameParameters().setEnabled(false);
		sizeShip.setEnabled(false);
		getAddShipButton().setEnabled(false);
		createdShipsList.setBackground(Color.white);
		getCreatedShipsList().setEditable(false);
		getInstructions().setEditable(false);
		getCurrentAction().setEditable(false);
	}

	/**
	 * Get ship ArrayList
	 * 
	 * @return ArrayList<Integer> for ship reference
	 */
	public ArrayList<Integer> getShips() {
		String shipSizes[] = this.getCreatedShipsList().getText().split(",");
		ArrayList<Integer> returnList = new ArrayList<Integer>();
		for (String s : shipSizes) {
			returnList.add(Integer.parseInt(s.trim()));
		}
		return returnList;
	}

	// Getters/Setters
	public int getGameWidth() {
		return Integer.parseInt(xField.getText().trim());
	}

	public int getGameHeight() {
		return Integer.parseInt(yField.getText().trim());
	}

	public void setCreatedShipsList(JTextField createdShipsList) {
		this.createdShipsList = createdShipsList;
	}

	public JTextField getCreatedShipsList() {
		return createdShipsList;
	}

	public void setClear(JButton clear) {
		this.clear = clear;
	}

	public JButton getClear() {
		return clear;
	}

	public void setSizeShip(JTextField sizeShip) {
		this.sizeShip = sizeShip;
	}

	public JTextField getSizeShip() {
		return sizeShip;
	}

	public void setSendGameParameters(JButton sendGameParameters) {
		this.sendGameParameters = sendGameParameters;
	}

	public JButton getSendGameParameters() {
		return sendGameParameters;
	}

	public void setInstructions(JTextField instructions) {
		this.instructions = instructions;
	}

	public JTextField getInstructions() {
		return instructions;
	}

	public void setAddShipButton(JButton addShipButton) {
		this.addShipButton = addShipButton;
	}

	public JButton getAddShipButton() {
		return addShipButton;
	}

	public void setSetBoardButton(JButton setBoardButton) {
		this.setBoardButton = setBoardButton;
	}

	public JButton getSetBoardButton() {
		return setBoardButton;
	}

	public void setCurrentAction(JTextField currentAction) {
		this.currentAction = currentAction;
	}

	public JTextField getCurrentAction() {
		return currentAction;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getOutput() {
		return output;
	}

	public boolean getSalvo() {
		// TODO Auto-generated method stub
		return salvo.isSelected();
	}

}