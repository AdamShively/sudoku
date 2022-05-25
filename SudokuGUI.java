/*
* The SudokuGUI program implements an application that
* lets its user play a game of sudoku.
* @author  Adam Shively
* @version 1.0
* @since   02-17-2022
*/

import java.util.ArrayList;
import java.util.function.UnaryOperator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SudokuGUI extends Application {

	private static int[][] board;		  //Board representation.
	private static TextField[][][] textFields;  //1st dimension is row, 2nd is column, 3rd is guesses and answers TextFields.
	private static ComboBox<String> comboBox;   //ComboBox for changing puzzles.
	final static int ROW_COL_LENGTH = 9;  //Row and column length.

	public SudokuGUI() {
		board = getBoard(6);
		textFields = new TextField[9][9][2];
		comboBox = new ComboBox<String>();
	}
	
	/**
	* This method is used to get board selected by user.
	* @param boardNum The index of selected board.
	* @return int[][] The board selected.
	*/
	private static int[][] getBoard(int boardNum) {
		
		ArrayList<int[][]> boardList = new ArrayList<int[][]>();
		
		int[][] board1 = {{5,3,0,0,7,0,0,0,0}, 
				  {6,0,0,1,9,5,0,0,0},
				  {0,9,8,0,0,0,0,6,0},
				  {8,0,0,0,6,0,0,0,3},
				  {4,0,0,8,0,3,0,0,1},
				  {7,0,0,0,2,0,0,0,6},
				  {0,6,0,0,0,0,2,8,0},
				  {0,0,0,4,1,9,0,0,5},
				  {0,0,0,0,8,0,0,7,9}}, 
		
			board2 = {{0,0,0,0,0,0,2,0,0},
				  {0,8,0,0,0,7,0,9,0},
				  {6,0,2,0,0,0,5,0,0},
				  {0,7,0,0,6,0,0,0,0},
				  {0,0,0,9,0,1,0,0,0},
				  {0,0,0,0,2,0,0,4,0},
				  {0,0,5,0,0,0,6,0,3},
				  {0,9,0,4,0,0,0,7,0},
				  {0,0,6,0,0,0,0,0,0}},

			board3 = {{0,0,0,8,0,1,0,0,0},
				  {0,0,0,0,0,0,0,4,3},
				  {5,0,0,0,0,0,0,0,0},
				  {0,0,0,0,7,0,8,0,0},
				  {0,0,0,0,0,0,1,0,0},
				  {0,2,0,0,3,0,0,0,0},
				  {6,0,0,0,0,0,0,7,5},
				  {0,0,3,4,0,0,0,0,0},
				  {0,0,0,2,0,0,6,0,0}},

			board4 = {{0,0,0,0,0,1,2,3,0},
				  {1,2,3,0,0,8,0,4,0},
				  {8,0,4,0,0,7,6,5,0},
				  {7,6,5,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,1,2,3},
				  {0,1,2,3,0,0,8,0,4},
				  {0,8,0,4,0,0,7,6,5},
				  {0,7,6,5,0,0,0,0,0}},

			board5 = {{0,7,5,0,9,0,0,0,6},
				  {0,2,3,0,8,0,0,4,0},
				  {8,0,0,0,0,3,0,0,1},
				  {5,0,0,7,0,2,0,0,0},
				  {0,4,0,8,0,6,0,2,0},
				  {0,0,0,9,0,1,0,0,3},
				  {9,0,0,4,0,0,0,0,7},
				  {0,6,0,0,7,0,5,8,0},
				  {7,0,0,0,1,0,3,9,0}},

			board6 = {{0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0},
				  {0,0,0,0,0,0,0,0,0}};
		
		boardList.add(board1); boardList.add(board2);
		boardList.add(board3); boardList.add(board4);
		boardList.add(board5); boardList.add(board6);

		return boardList.get(boardNum-1);
	}
	
	/**
	* This method is updates the board field based on what the player has entered.
	*/
	private static void updateBoardField() {

		for(int row = 0; row < ROW_COL_LENGTH; row++) {
			   for(int col = 0; col < ROW_COL_LENGTH; col++) {
				   TextField ansField = textFields[row][col][1];   //Get answer TextField
				   String cell = ansField.getText();                   //Get answer TextField text
				   board[row][col] = (cell == "") ? 0 : Integer.valueOf(cell);
			   }
		}
	}
	
	/**
	* This method is used to create a copy of the board field.
	* @param b What is currently in the board field.
	* @return int[][] A copy of the board.
	*/
	private static int [][] copyBoard(int[][] b) {
		
		int [][] boardCopy = new int[ROW_COL_LENGTH][ROW_COL_LENGTH];
		for(int i = 0; i < ROW_COL_LENGTH; i++) {
			boardCopy[i] = b[i].clone();	
		}
		return boardCopy;
	}
	
	private class BoardStatus {
		private boolean isCorrect; //Determine if user entries correct so far.
		private boolean isSolved;  //Determine if user entries match board solution.

		BoardStatus(boolean isCorrect, boolean isSolved) {
			this.isCorrect = isCorrect;
			this.isSolved = isSolved;
		}
	}
	
	/**
	* This method is used to determine if user entries are correct so far
	* or if the user has solved the current puzzle.
	* @return BoardStatus Instance of BoardStatus class.
	*/
	private BoardStatus checkCurrentBoard() {
		
		updateBoardField();
		int [][] boardCopy = copyBoard(board);
		boolean isCorrect = new Solver().solve(board);
		boolean isSolved = java.util.Arrays.deepEquals(boardCopy, board);
		board = copyBoard(boardCopy);
		BoardStatus bs = new BoardStatus(isCorrect, isSolved);
		
		return bs;
	}
	
	/**
	* This method is used to change the puzzle and updates GUI board.
	* @param puzzleNumber Index of the puzzle selected.
	*/
	private static void changePuzzle(int puzzleNumber) {
		
		board = getBoard(puzzleNumber);
		for(int row = 0; row < ROW_COL_LENGTH; row++) {
			   for(int col = 0; col < ROW_COL_LENGTH; col++) {
				   
				   int boardPos = board[row][col];
			   	   String textToSet = (boardPos > 0) ? Integer.toString(boardPos) : "";
			   	   
			   	   TextField guessesField = textFields[row][col][0];
			   	   TextField ansField = textFields[row][col][1];
			   	   
			   	   guessesField.setText("");
			   	   ansField.setText(textToSet);
			   	   
			   	   if(boardPos > 0) {	//User can't change pre determined numbers.
			   		   ansField.setEditable(false);
			   		   guessesField.setEditable(false);
			   	   }
			   	   else {
			   		   ansField.setEditable(true);
			   		   guessesField.setEditable(true);
			   	   }
			   }
		}
	}
	
	/**
	* This method is used to limit user entered text. 
	* Guesses TextFields are limited to 1-9 separated by commas.
	* Answer TextFields are limited to 1-9 and only a single digit can be entered.
	* @param boolean TextField passed to method is either answer TextField or not.
	* @return TextFormatter Changes made to TextField based on limitations.
	*/
	private static TextFormatter<String> limitInput(boolean isAnswerTextField) {
		UnaryOperator<Change> filter = change -> {
			String fieldText = change.getText();
		        if(!fieldText.matches("[1-9]")){
		    	    change.setText(""); //Replace the input text
		        }
		    
		        int textLength = change.getControlNewText().length();
			if (isAnswerTextField && textLength > 1) {
			    String s = change.getControlNewText().substring(0, 1);  //Replace the input text
			    change.setText(s);
			    change.setRange(0, 1);  //Replace the range
			}
	        
			else if (!isAnswerTextField) {
				String newText = change.getControlNewText();
				StringBuilder sb = new StringBuilder();
				int itLength = change.getText().length() + change.getRangeStart();

				for(int i = 0; i < itLength; i++) {
					char num = newText.charAt(i);
					char comma = (i < itLength-1) ? ',' : '\0'; //Only add comma if not last character
					if (Character.isDigit(num)) {
					    sb.append(num);
					    sb.append(comma);
					}
				}

				int sbLength = sb.length();

			        //Replace with modified text
			        change.setRange(0, change.getRangeEnd());
			        change.setText(sb.toString());
			        change.setCaretPosition(sbLength);
			        change.setAnchor(sbLength);
			}
	        
		    	return change;
		};
		return new TextFormatter<>(filter);
	}
	/**
	Create an event handler for changing puzzle.
	*/
	private class ChooseButtonHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			String value = comboBox.getValue();
			//Last character in puzzle name is puzzle 
			//number (i.e. "Puzzle 1") except "Add My Own Values"
			char lastChar = value.charAt(value.length()-1); 
			int puzzleNum = (Character.isDigit(lastChar)) ? Character.getNumericValue(lastChar) : 6;
			changePuzzle(puzzleNum);
		}
	 }
	
	/**
	Create an event handler to check for board correctness and if puzzle is solved.
	*/
	private class CheckButtonHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event)
		{
			BoardStatus bs = checkCurrentBoard();
			String displayMessage = "";

			boolean correctSoFar = bs.isCorrect;
			boolean gameWon = bs.isSolved;
			
			if(correctSoFar) {
				if(gameWon) {
					displayMessage = "YOU WIN!! Congrats!";
				}
				else {
					displayMessage = "Correct so far.";
				}
			}
			else {
				displayMessage = "At least one cell is incorrect.";
			}
			JOptionPane.showMessageDialog(new JFrame(), displayMessage,"Your Progress", JOptionPane.WARNING_MESSAGE);    
		}
	}
	
	/**
	Create an event handler for solving current puzzle.
	*/   
	private class SolveButtonHandler implements EventHandler<ActionEvent>
	{
		@Override
		public void handle(ActionEvent event) {
			updateBoardField();
			boolean isCorrect = new Solver().solve(board);
			if(isCorrect) {
				for(int row = 0; row < ROW_COL_LENGTH; row++) {
					for(int col = 0; col < ROW_COL_LENGTH; col++) {
						TextField guessField = textFields[row][col][0];
						guessField.setEditable(false); //Set cell's guesses TextField to uneditable
						guessField.setText("");
						   
						int cell = board[row][col];
						TextField ansField = textFields[row][col][1];
						ansField.setEditable(false); //Set cell's answer TextField to uneditable
						ansField.setText(String.valueOf(cell));
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(new JFrame(), "At least one cell is incorrect.","Invalid Answer", JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	/**
	* This method creates the main game scene that is added to the stage 
	* and all components that will appear in it.
	* @param stage GUI window
	*/   
	@Override
	public void start(Stage stage)
	{
		comboBox.getItems().addAll("Add My Own Values", "Puzzle 1", "Puzzle 2",
									   "Puzzle 3", "Puzzle 4", "Puzzle 5");
		//Create a GridPane
		GridPane mainGridPane = new GridPane();
		mainGridPane.getStyleClass().add("main-grid-pane");

		for(int row = 0; row < ROW_COL_LENGTH; row++) {
			for(int col = 0; col < ROW_COL_LENGTH; col++) {
				TextField guesses = new TextField();
				guesses.getStyleClass().add("guesses-text-field");
				guesses.setTextFormatter(limitInput(false));

				TextField answers = new TextField();
				answers.getStyleClass().add("answers-text-field");
				answers.setTextFormatter(limitInput(true));

				textFields[row][col][0] = guesses;
				textFields[row][col][1] = answers;
				GridPane cellPane = new GridPane();
				cellPane.addRow(0, guesses);  
				cellPane.addRow(1, answers);

				String top = (row == 3 || row == 6) ? "10" : "1";
				String right = (col == 2 || col == 5) ? "10" : "1";
				String values = String.format("%s %s 1 1 ;",top, right);   //top, right, bottom, left
				//https://stackoverflow.com/questions/50552728/dynamically-change-javafx-css-property
				cellPane.setStyle(
						"-fx-border-insets: "+ values		//Make 9 cell "blocks" visibly distinct with bigger insets separating them
					+ "-fx-border-width: 3;"			//each containing 3 rows and 3 columns
					+ "-fx-border-color: blue;");
				mainGridPane.add(cellPane, col, row);
			}
		}
		
	        mainGridPane.setMaxWidth(600);
	      
	        Label puzzleLabel = new Label("Choose a Puzzle:   ");
	        puzzleLabel.getStyleClass().add("puzzle-label");
	      
	        Button solveButton = new Button("Solve!");
	        solveButton.setOnAction(new SolveButtonHandler());
	      
	        Button okButton = new Button("Ok");
	        okButton.setOnAction(new ChooseButtonHandler());
	      
	        Button checkButton = new Button("Check My Progress");
	        checkButton.setOnAction(new CheckButtonHandler());
	      
	        HBox hbox = new HBox(puzzleLabel, comboBox, okButton);
	        hbox.getStyleClass().add("hbox");

	        VBox vbox = new VBox(mainGridPane, hbox, checkButton, solveButton);
	        vbox.getStyleClass().add("vbox");

	        Scene scene = new Scene(vbox, 900, 800);
	        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	        // Add the Scene to the Stage.
	        stage.setScene(scene);
	        stage.setMaximized(true);
	        // Show the window.
	        stage.show();
	}
	
	public static void main(String[] args)
	{
	        launch(args);	//Launch the application.
	}
}
