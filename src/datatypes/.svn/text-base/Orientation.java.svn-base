package datatypes;

// Orientation class
public class Orientation {
	public static final Integer UP = 0;
	public static final Integer RIGHT = 1;
	public static final Integer DOWN = 2;
	public static final Integer LEFT = 3;

	
	public static final Integer getOrientation(Coordinate startingSquare,Coordinate otherSquare,int length) {
		if (startingSquare.getRow() == otherSquare.getRow()) {
			if (startingSquare.getColumn() < otherSquare.getColumn() && startingSquare.getColumn() + length > otherSquare.getColumn()) {
				return RIGHT;
			}
		}
		
		if (startingSquare.getRow() == otherSquare.getRow()) {
			if (startingSquare.getColumn() > otherSquare.getColumn() && startingSquare.getColumn() - length < otherSquare.getColumn()) {
				return LEFT;
			}
		}
		
		if (startingSquare.getColumn() == otherSquare.getColumn()) {
			if (startingSquare.getRow() < otherSquare.getRow() && startingSquare.getRow() + length > otherSquare.getRow()) {
				return UP;
			}
		}
		
		if (startingSquare.getColumn() == otherSquare.getColumn()) {
			if (startingSquare.getRow() > otherSquare.getRow() && startingSquare.getRow() - length < otherSquare.getRow()) {
				return DOWN;
			}
		}
		
		return -1;
	
	}
	
}
