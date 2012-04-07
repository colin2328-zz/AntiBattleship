package communication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import boardEngine.HostBoardModel;
import boardEngine.OpponentBoardModel;

import datatypes.Coordinate;
import datatypes.Orientation;
import datatypes.Ship;


public class AIPlayer {
	
	private HostBoardModel hostBoardModel;
	private OpponentBoardModel opponentBoardModel;
	private int width;
	private int height;
	private ArrayList<Coordinate> border;
	private HashSet<Coordinate> alreadyTargeted;
	
	public AIPlayer(HostBoardModel hostBoardModel,OpponentBoardModel opponentBoardModel) {
		this.hostBoardModel = hostBoardModel;
		this.height = this.hostBoardModel.getHeight();
		this.width = this.hostBoardModel.getWidth();
		this.opponentBoardModel = opponentBoardModel;
		this.border = new ArrayList<Coordinate>();
		this.alreadyTargeted = new HashSet<Coordinate>();
	}
	
	/**
	 * @effects adds a ship
	 * @modifies hostBoardModel
	 */
	public void placeShips(int algo) {
		if (algo == 0){
		int row = 0;
		int col = 0;
		for (Integer i : hostBoardModel.getShipSizeList()) {
			//if thisShip == null do again
			Ship thisShip = this.hostBoardModel.createShip(Orientation.UP, new Coordinate(row,col), i);
			
			this.hostBoardModel.addShip(thisShip);
			int newColumn = col + 2;
			col = newColumn;
		}
		} else {
			Random rand = new Random();
			
			for (Integer ship : hostBoardModel.getShipSizeList()){
				Ship thisShip = null;

				while (thisShip == null){
					int row = rand.nextInt(height);
					int col = rand.nextInt(width);
					Coordinate coord = new Coordinate(row, col);
					int pick = rand.nextInt(4);
					if (pick == 0) {
						 thisShip = this.hostBoardModel.createShip(Orientation.UP, coord, ship);
					} else if (pick == 1) {
						 thisShip = this.hostBoardModel.createShip(Orientation.DOWN, coord, ship);
					}
					else if (pick == 2) {
						 thisShip = this.hostBoardModel.createShip(Orientation.RIGHT, coord, ship);
					}
					else {
						 thisShip = this.hostBoardModel.createShip(Orientation.LEFT, coord, ship);
					}
				}
				this.hostBoardModel.addShip(thisShip);
			}		
				
		}
	}

	/**
	 * @requires an up-to-date version of opponentBoardModel
	 * @modifies none
	 * @effects determines the next move that the AI player should make
	 * @return Coordinate associated with the coordinates of the next move.
	 */
	public Coordinate calculateMove() {
		if (border.size() > 0) {
			Coordinate c = new Coordinate(border.get(0).getRow(),border.get(0).getColumn());
			border.remove(0);
			return c;
		}
		
		for ( int i = 0; i < this.height; ++i) {
			for (int j = 0; j < this.width; ++j) {
				
				if (opponentBoardModel.getBoard()[i][j] == 0) {
				
//					System.out.println(i+","+j+": "+opponentBoardModel.getBoard()[i][j]);
				if ((i + j) % 2 == 0) {
					return new Coordinate(i,j);
				}
				}
			}
		}
		
		for (int k = 0; k < this.height; ++k) {
			for (int j = 0; j< this.width; ++j) {
				if (opponentBoardModel.getBoard()[k][j] == 0) {
				if ((k+j) %2 == 1 && opponentBoardModel.noNeighbors(k,j)) {
					return new Coordinate(k,j);
				}
				}
			}
		}
	
		for (int m = 0; m < this.height; ++m) {
			for (int j = 0; j < this.width; ++j) {
				if (opponentBoardModel.getBoard()[m][j] == 0) {
				if ((m+j) % 2 == 1 && opponentBoardModel.noNeighborPairs(m,j)) {
					return new Coordinate(m,j);
				}
				}
			}
		}
		for (int n = 0; n <this.height; ++n) {
			for (int j = 0; j < this.width; ++j) {
				if (opponentBoardModel.getBoard()[n][j] == 0) {
				if ((n+j) %2 == 1 && opponentBoardModel.rightAngle(n,j)) {
					return new Coordinate(n,j);
				}
				}
			}
		}
		
		//System.out.println("past rightangle");
		for (int p = 0; p < this.height; ++p) {
			for (int j =0; j < this.width; ++j) {
				if (opponentBoardModel.getBoard()[p][j] == 0) {
				return new Coordinate(p,j);
				}
			}
		}
		
		for (int q = 0; q < this.height; ++q) {
			for (int j =0; j < this.width; ++j) {
				//if (opponentBoardModel.isVacant(q,j)) {
				return new Coordinate(q,j);
			}
		}
	
		return new Coordinate(0,0);
	}

	public Coordinate calculateMoveSalvo(int numMoves) {
		ArrayList<Coordinate> moves = new ArrayList<Coordinate>();


//		if (border.size() > 0) {
//			Coordinate c = new Coordinate(border.get(0).getRow(),border.get(0).getColumn());
//			if (!alreadyTargeted.contains(c)){
//				border.remove(0);
//				alreadyTargeted.add(c);
//				return c;
//			}
//		}

		//checkerboard
		for ( int i = 0; i < this.height; ++i) {
			for (int j = 0; j < this.width; ++j) {
				
				if (opponentBoardModel.getBoard()[i][j] == 0) {
				
//					System.out.println(i+","+j+": "+opponentBoardModel.getBoard()[i][j]);
					if ((i + j) % 2 == 0) {
						Coordinate move =  new Coordinate(i,j);
						if (!alreadyTargeted.contains(move)){
							alreadyTargeted.add(move);
//							System.out.println("adding 1st loop");
							return move;
							
	
						}
					}
				}
			}
		}
		//off checkboard
		for (int k = 0; k < this.height; ++k) {
			for (int j = 0; j< this.width; ++j) {
				if (opponentBoardModel.getBoard()[k][j] == 0) {
				if ((k+j) %2 == 1 && opponentBoardModel.noNeighbors(k,j)) {
					Coordinate coord = new Coordinate(k,j);
					if (!alreadyTargeted.contains(coord)){
						alreadyTargeted.add(coord);
//						System.out.println("adding 2nd loop");
						return coord;

					}
				}
				}
			}
		}
	// allows neighbor, not across neighbors
		for (int m = 0; m < this.height; ++m) {
			for (int j = 0; j < this.width; ++j) {
				if (opponentBoardModel.getBoard()[m][j] == 0) {
				if ((m+j) % 2 == 1 && opponentBoardModel.noNeighborPairs(m,j)) {
					Coordinate coord =  new Coordinate(m,j);
					if (!alreadyTargeted.contains(coord)){
						alreadyTargeted.add(coord);
						return coord;

					}
				}
				}
			}
		}
		//allows right angle
		for (int n = 0; n <this.height; ++n) {
			for (int j = 0; j < this.width; ++j) {
				if (opponentBoardModel.getBoard()[n][j] == 0) {
				if ((n+j) %2 == 1 && opponentBoardModel.rightAngle(n,j)) {
					Coordinate coord = new Coordinate(n,j);
					if (!alreadyTargeted.contains(coord)){
						alreadyTargeted.add(coord);
						return coord;

					}
				}
				}
			}
		}
		
		//System.out.println("past rightangle");
		for (int p = 0; p < this.height; ++p) {
			for (int j =0; j < this.width; ++j) {
				if (opponentBoardModel.getBoard()[p][j] == 0) {
				Coordinate coord =  new Coordinate(p,j);
				if (!alreadyTargeted.contains(coord)){
					alreadyTargeted.add(coord);
					return coord;

				}
				}
			}
		}
		
		for (int q = 0; q < this.height; ++q) {
			for (int j =0; j < this.width; ++j) {
				//if (opponentBoardModel.isVacant(q,j)) {
				return new Coordinate(q,j);
			}
		}
	
		return new Coordinate(0,0);
		
		

	}
}
