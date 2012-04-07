package datatypes;

import java.util.ArrayList;

public class InitMessage {
	ArrayList<Integer> shipSizes;
	int width;
	int height;
	boolean salvoMode;

	/**
	 * Constructor for initilization message
	 * 
	 * @param w
	 *            int for coordinate reference
	 * @param h
	 *            int for coordinate reference
	 * @param shipSizes
	 *            ArrayList<Integer> for ships on board
	 * @param salvoMode
	 *            boolean if in salvo mode
	 */
	public InitMessage(int w, int h, ArrayList<Integer> shipSizes,
			boolean salvoMode) {
		this.width = w;
		this.height = h;
		this.shipSizes = shipSizes;
		this.salvoMode = salvoMode;
	}

	public ArrayList<Integer> getShipSizes() {
		return this.shipSizes;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public int getLongestShipSize() {
		int maxSize = 0;
		for (int ship : shipSizes) {
			if (ship > maxSize) {
				maxSize = ship;
			}
		}
		return maxSize;
	}

	public boolean getSalvoMode() {
		return salvoMode;
	}
}
