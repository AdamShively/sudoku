/*
* The Solver program implements an application that
* is to be used in conjunction with the SudokuGUI class.
* @author  Adam Shively
* @version 1.0
* @since   02-17-2022
*/

public class Solver {
	
	/**
 	* Represents a position on the board.
 	*/
	private class Position {
		private int row;	//Holds row for empty spot.
		private int col;	//Holds column for empty spot.

		Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

	/**
	* This method is used to solve a 2D array representation of a 
	* sudoku board using backtracking.
	* @param board An unsolved board representation.
	* @return boolean The board is solvable based on current entries or it is not.
	*/
	public boolean solve(int[][] board) {
		
		Position pos = getEmpty(board);  //Set coordinates of empty spot
		int r = pos.row;
		int c = pos.col;
		
		if(r == -1) {		   //If no empty spots, board is solved
			return true;
		}
			
		for(int posNum = 1; posNum <= 9; posNum++) {
			if(isValid(board, posNum, pos.row, pos.col)) {
				board[r][c] = posNum;	   //Fill with valid number
					
				if(solve(board)) {		 
					return true;
				}
				board[r][c] = 0;		   //Fill with zero
			}
		}
		return false;						//If we get here, board can't be solved
	}
	
	/**
	* This method is used to obtain the position of the next empty spot.
	* @param board A board representation.
	* @return int[] The row and column of empty spot.
	*/
	private Position getEmpty(int[][] board) {
		
		for(int r = 0; r < 9; r++) {
			for(int c = 0; c < 9; c++) {
				if(board[r][c] == 0) {
					return new Position(r, c);
				}
			}
		}
		return new Position(-1, -1);
	}
	
	/**
	* This method is used check if a potential entry is valid.
	* @param board A board representation.
	* @param possibleNum A digit that could potentially be a valid entry.
	* @param row Row on board.
	* @param col Column on board.
	* @return boolean Entry is valid or not.
	*/
	private static boolean isValid(int[][] board, int possibleNum, int row, int col) {
		
		//Check row and column for potential entry.
		for(int i = 0; i < 9; i++) {
			if(board[row][i] == possibleNum || board[i][col] == possibleNum) {
				return false;
			}
		}

		int rowStart = row - row%3;	//Row to start check in.
		int colStart = col - col%3;	//Column to start check in.
		int rowEnd = rowStart+3;	//Row to end check in.
		int colEnd = colStart+3;	//Column to end check in.
		
		//Check 3 x 3 square for potential entry.
		for(int r = rowStart; r < rowEnd; r++) {
			for(int c = colStart; c < colEnd; c++) {
				if(board[r][c] == possibleNum) {
					return false;
				}
			}
		}
		return true;
	}
}
