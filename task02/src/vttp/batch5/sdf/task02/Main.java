package vttp.batch5.sdf.task02;

import java.io.*;
//import java.util.*;
//import java.lang.*;

// add code to ignore board 4
// basically if number  of occurrence of X > O, ignore board

public class Main {

	private static char[][] board = new char[3][3];
	private static char currentPlayer;

	public static void loadGame(String filename) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			int x = 0;
			while ((line = br.readLine()) != null && x < 3) {
				String[] cell = line.split("");
				//System.out.println(Arrays.toString(cell));

				for (int y = 0; y < 3; y++) {
					board[x][y] = cell[y].charAt(0);
				}
				x++;
			}
		} catch (IOException e) {
			System.err.println("Error occurred :  " + e.getMessage());
		}
	}

	// Print the current state of the board
	private static void printBoard() {
		
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				System.out.print(board[x][y] + " ");
			}
			System.out.println();
		}
	}

	
	public static boolean validateBoard(char[][] board) {

		int countX = 0;
		int countO = 0;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				if (board [x][y] == 'X') {
					countX++;
				} else if (board[x][y] == 'O') {
					countO++;
				}

			}
		}
		if (countX > countO) {
			System.out.println("the next player should be playing O");
			return false;
		}
		return true; 
		
	}

	public static void main(String[] args) throws Exception {

		System.out.printf("hello, world\n");

		String filename = args[0];
		if (args.length == 0) {
			System.err.println("No configuration files mentioned. please try again");
			return;
		}

		loadGame(filename);
		//i am X? --> yes i am
		currentPlayer = 'X'; 

		System.out.println("processing: " + filename + "\n\n");

		System.out.println("BOARD:");
		printBoard();

		if (!validateBoard(board)) {
			System.out.println("this board is invalid");
			return;
		}
		System.out.println("---------------------------------------");
		
		
		// add a code for print out the utility results

		evaluateMoves();
	}

	private static void evaluateMoves() {
		for (int x = 0; x < 3; x++) {
			for (int y= 0; y < 3; y++) {
				if (board[x][y] == '.') {
					board[x][y] = currentPlayer;
					int utility = evaluateUtility();
					System.out.printf("x = %d, y = %d, utility = %d%n", x, y, utility);
					board[x][y] = '.';
				}
			}
		}

	}

	private static int evaluateUtility() {
		
		if (checkWin(currentPlayer))
			return 1;
		
		if (checkWin('O'))
			return -1;
		
		if (isaDraw())
			return 0;
		
		return 0;
	}

	private static boolean checkWin(char player) {
        // i need to check rows, columns, and diagonals
        for (int x = 0; x < 3; x++) {
            if (board[x][0] == player && board[x][1] == player && board[x][2] == player) return true;
            if (board[0][x] == player && board[1][x] == player && board[2][x] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    
    private static boolean isaDraw() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (board[x][y] == '.') return false;
            }
        }
        return true;
    }

	
	
	
	// private static boolean checkWinner() {
	
	// for (int i = 0; i < 3; i++) {
	// if ((board[i][0] == currentPlayer && board[i][1] == currentPlayer &&
	// board[i][2] == currentPlayer) ||
	// (board[0][i] == currentPlayer && board[1][i] == currentPlayer && board[2][i]
	// == currentPlayer)) {
	// return true;
	// }
	// }
	
	// if ((board[0][0] == currentPlayer && board[1][1] == currentPlayer &&
	// board[2][2] == currentPlayer) ||
	// (board[0][2] == currentPlayer && board[1][1] == currentPlayer && board[2][0]
	// == currentPlayer)) {
	// return true;
	// }
	// return false;

	// }
}
