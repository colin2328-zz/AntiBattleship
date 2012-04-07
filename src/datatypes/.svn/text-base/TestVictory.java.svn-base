package datatypes;

import java.util.ArrayList;
import java.util.Collections;

import boardEngine.BoardModel;
import boardEngine.OpponentBoardModel;

public class TestVictory {
	
	/**
	 * @effects checks to ensure that the opponent obeyed the rules about ship placement
	 * @modifies none
	 * @param finalBoardState; opponent's stated ship locations
	 * @return a boolean indicating whether the opponent's final board state was valid
	 */
	public static boolean validBoardPlacement(String finalBoardState, OpponentBoardModel opponentBoardModel) {
		//check to ensure opponent has placed ships in a valid fashion
		//check if the # of digits == m x n
		int height = opponentBoardModel.getHeight();
		int width = opponentBoardModel.getWidth();
		String[] theirVersionSplit = finalBoardState.split("");
		int size = theirVersionSplit.length - 1;
		String[] theirVersion = new String[size];
		System.arraycopy(theirVersionSplit, 1, theirVersion, 0, size);
		if (size != (height * width)) {
//			System.out.println("board size invalid");
			return false;
		}
		
		//check to see if there are enough 1's, if the one's form enough ships
		ArrayList<Integer> boardState = new ArrayList<Integer>();
		int one_count = 0;
		for (int i = 0; i < size; i++){
			boardState.add(Integer.valueOf(theirVersion[i]));
			if (boardState.get(i) == 1) {
				one_count += 1;
			}
		}
		int shipsCount = 0;
		ArrayList<Integer> shipList = opponentBoardModel.getShipList();
		for (Integer ship: shipList){
			shipsCount += ship;
		}
		if (shipsCount != one_count){
//			System.out.println("not enough ones!");
			return false;
		}
		
		//find the ships- see if they match ship list
		int[][] boardArray = new int[height][width];
		for (int i = 0; i < height; i++){
			for (int j = 0; j < width; j++){
				boardArray[i][j] = boardState.get(i* width + j);
			}
		}
		//iterate down array, find a one, dec one count, find the 
		int i = 0;
		int j = 0;
		int ship_size;
		ArrayList<Integer> ships = new ArrayList<Integer>();

		while (one_count > 0){
//			System.out.println("i is " + i);
//			System.out.println("j is " + j);

			
			if (boardArray[i][j] == 1){
//				System.out.println("found a ship");
				boardArray[i][j] = 2;
				ship_size = 1;
				one_count -= 1;
				int dummy_j = j;
				if ( (dummy_j  + 1 < width) &&(boardArray[i][dummy_j +1] == 1)) {// extends to right
//					System.out.println("extends to the right");
					dummy_j +=1;
					while ( (dummy_j < width) && (boardArray[i][dummy_j] == 1)){
						//check to make sure there is no ship above and below
						if ((i+1 < height) && (boardArray[i+1][dummy_j] == 1)){
//							System.out.println("tried to place a ship next to another ship!");
							return false;
						}
						boardArray[i][dummy_j] = 2;
						ship_size += 1;
						one_count -= 1;
						dummy_j += 1;
					}
//					System.out.println("adding ship of size  " + ship_size);
					ships.add(ship_size);

				} else if ( (i + 1 < height) &&(boardArray[i +1][j] == 1)){//extends down
//					System.out.println("extends downs");

					int dummy_i = i;
					dummy_i +=1;
					while ((dummy_i < height) && (boardArray[dummy_i][j] ==1)){
						if ((j + 1 < width) && (boardArray[dummy_i] [j + 1] == 1)){
//							System.out.println("tried to place a ship next to another ship!");
							return false;
						}
						boardArray[dummy_i][j] = 2;
						ship_size +=1;
						one_count -= 1;
						dummy_i +=1;
					}
//					System.out.println("adding ship of size  " + ship_size);
					ships.add(ship_size);
					
				} else {
//					System.out.println("didn't get here!!!!");
//					System.out.println("adding ship of size  " + ship_size);
					ships.add(ship_size);
				}
			}
			if (j + 1 < width){
				j++;
			} else {
				j = 0;
				i++;
			}
			
//			System.out.println("one count is " + one_count);

			
		}
		
		//make sure they have the right number/types of ships
		Collections.sort(ships);
		Collections.sort(shipList);
//		System.out.println("The expected ship list size: " + shipList.size());
//		System.out.println("THe actual ship list size: " + ships.size());
		if (ships.size() != shipList.size()) {
//			System.out.println("don't have the right number of ships");
			return false;
		}
		for (int i1 = 0; i1 < ships.size(); i1 ++){	
//			System.out.println("supposed ship size :" + ships.get(i1));
//			System.out.println("actual ship size : " + shipList.get(i1));
			int supposed = ships.get(i1);
			int actual = shipList.get(i1);
			if ( supposed != actual){
			
//				System.out.println("ships aren't of the correct sizes");
				return false;
			}
		}

		return true;
	}
	
	
	/**
	 * @effects anti-cheating: checks to ensure that the opponent's stated ship locations are consistent
	 * with what was told to the host in gameplay
	 * @param finalBoardState
	 * @return a boolean; true for no cheating, false for cheating
	 */
	public static boolean congruentWithCommunication(String finalBoardState, OpponentBoardModel opponentBoardModel) {

		String ourVersion = opponentBoardModel.printBoardModel();		

		String[] ourVersionSplit = ourVersion.split("");
		String[] theirVersionSplit = finalBoardState.split("");
		if (ourVersionSplit.length != theirVersionSplit.length) return false;
		
		for (int i = 1; i < ourVersionSplit.length; i++){
//			System.out.println("i :" + i);
//			System.out.println("ours: " + ourVersionSplit[i]);
//			System.out.println("theirs: " + theirVersionSplit[i]);
			int ours = Integer.parseInt(ourVersionSplit[i]);
			int theirs = Integer.parseInt(theirVersionSplit[i]);
			//if ours is a hit, theirs better be a 1
			//if ours is a miss, theirs better be a 0
			// if ours is Vac, doesn't matter
			if (ours == BoardModel.VAC){
			} else if (ours == BoardModel.HIT){
				if (theirs != 1) return false;
			} else if (ours == BoardModel.MISS){
				if (theirs != 0) return false;
			} else {
//				System.out.println("Our board model is inconsistent! We detected " + ours + " as an int");
			}
		}
//		System.out.println("Valid Victory!");
		return true;
		
	}

}
