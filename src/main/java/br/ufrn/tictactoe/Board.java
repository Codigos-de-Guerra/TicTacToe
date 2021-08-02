package br.ufrn.tictactoe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Board {
	enum Marker { BLANK, X, O };
	
	public Marker[][] board = new Marker[3][3];
	
	private static final Logger log = LoggerFactory.getLogger(Board.class);

	/**
	 * Clears the TicTacTow board of all of the markers.
	 */
	public void clear() {
		for(int r = 0;  r < 3;  ++r ) {
			for(int c = 0;  c < 3;  ++c) {
				board[r][c] = Marker.BLANK;
			}
		}
	}
	
	/* Helper used at front-end
	** It returns what is filled at certain position in board
	 */
	public String markAt(int row, int col)
	{
		Marker marker = board[row][col];
		if(marker.equals(Marker.X)) {
			return "X";
		}
		else if(marker.equals(Marker.O)) {
			return "O";
		}
		else if(marker.equals(Marker.BLANK)) {
			return " ";
		}
		return "#";
	}
	
	/* Fill a position in board based on player move*/
	public void move(int row, int col, Marker marker) throws Exception {
		if( board[row][col] != Marker.BLANK) {
			throw new Exception( "Square @ (" + row + ", " + col + ") is not empty");
		}
		if(marker == Marker.BLANK) {
			throw new IllegalArgumentException("Playing a BLANK marker is not valid");
		}
		board[row][col] = marker;
	}

	/* Checks if determined marker is winner */
	public boolean isWinner_DEPRE(Marker marker) {
		// Check for three in a row down
		return (this.hasColumn(marker) || this.hasRow(marker) || this.hasDiagonal(marker));
	}

	// Above is bugged
	public boolean isWinner(Marker marker) {
		// Check for three in a row down
		for(int r = 0; r < 3;  ++r) {
			boolean isWinner = true;
			for(int c = 0; isWinner && (c < 3); ++c) {
				if(board[r][c] != marker) {
					isWinner = false;
				}
			}
			if(isWinner) {
				return true;
			}
		}
		
		// Check for three in a row across
		for(int c = 0; c < 3;  ++c) {
			boolean isWinner = true;
			for(int r = 0; isWinner && (r < 3); ++r) {
				if(board[r][c] != marker) {
					isWinner = false;
				}
			}
			if(isWinner) {
				return true;
			}
		}
		
		// Check the diagonals
		if((board[0][0] == marker) && (board[1][1] == marker) && (board[2][2] == marker)) {
			return true;
		}
		if((board[2][0] == marker) && (board[1][1] == marker) && (board[0][2] == marker)) {
			return true;
		}
		
		return false;
	}

	/* Checks if it has the win condition "fill row"*/
	private boolean hasRow(Marker marker) {
		boolean aux_hasRow = true;
		for(int r = 0; r < 3; r++) {
			aux_hasRow = true;
			for(int c = 0; c < 3; c++) {
				if(board[r][c] != marker) {
					aux_hasRow = false;
				}
			}
		}

		return aux_hasRow;
	}

	/* Checks if it has the win condition "fill column"*/
	private boolean hasColumn(Marker marker) {
		boolean aux_hasColumn = true;
		for(int c = 0; c < 3;  c++) {
			aux_hasColumn = true;
			for(int r = 0; r < 3; r++) {
				if(board[r][c] != marker) {
					aux_hasColumn = false;
					break;
				}
			}
		}
		return aux_hasColumn;
	}
	
	/* Checks if it has the win condition "fill diagonal"*/
	private boolean hasDiagonal(Marker marker) {
		if(board[1][1] == marker) {
			return ((board[0][0] == marker) && (board[2][2] == marker)) || ((board[2][0] == marker) && (board[0][2] == marker));
		}

		return false;
	}

	/* Checks if ended in draw */
	public boolean isDraw() {
		// If all squares are filled, and a winner not declared, it's a draw pure and simple
		for(int r = 0 ;  r < 3;  ++r) {
			for(int c = 0 ;  c < 3;  ++c) {
				if(board[r][c].equals(Marker.BLANK)) {
					return false;
				}
			}
		}
		return true;
	}
}
