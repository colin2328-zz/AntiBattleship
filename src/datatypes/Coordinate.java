package datatypes;

// Coordinate class to store x y information
public class Coordinate {
	int row, column;

	/**
	 * Constructor for coordinate class
	 * 
	 * @param row
	 *            int for coordinate reference
	 * @param column
	 *            int for coordinate reference
	 */
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	// Returns row
	public int getRow() {
		return this.row;
	}

	// Returns column
	public int getColumn() {
		return this.column;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinate))
			return false;
		Coordinate c = (Coordinate) o;
		return c.row == row && c.column == column;
	}

	@Override
	public int hashCode() {
		return (this.row + this.column * 2);
	}

	public void display() {
		// System.out.println("(" + this.row + "," + this.column + ")");
	}
}
