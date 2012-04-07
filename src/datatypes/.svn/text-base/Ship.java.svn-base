package datatypes;

/*@author Kevin*/

import java.util.ArrayList;


// Ship object, stores size, coordinates, and hitCoordinates
public class Ship {
	private int size;
	private ArrayList<Coordinate> coordinates;
	private ArrayList<Coordinate> occCoordinates;
	
	public Ship(int size, ArrayList<Coordinate> coordinates) {
		this.size = size;
		this.coordinates = coordinates;
		this.occCoordinates = new ArrayList<Coordinate>();
		for (Coordinate c : this.coordinates) {
			occCoordinates.add(c);
		}
		
	}
	// Adds a verified hit to hitCoordinates
	public void addHit(int x, int y) {
		// TODO Auto-generated method stub
		occCoordinates.remove(new Coordinate(x,y));
	}
	
	// Reports if a ship is sunk
	public boolean isSunk() {
		if (occCoordinates.size() == 0) {
			return true;
		}
		else return false;
	}
	
	public ArrayList<Coordinate> getCoordinates() {
		return coordinates;
	}
}
