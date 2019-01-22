package model;


import java.io.*;

/**
 * SudokuBase uses FileManipulator class to open and read from file.
 *  It gets a SudokuBoard object in {@link #make_Sudoku_Board(int, SudokuBoard)} and fills fixed and non-fixed values into its [9][9] fields array.
 *  @author Michał Belniak
 *  @see FileHandler
 */
public class SudokuBase {

    /**
     * Initializes {@link FileHandler} object with a file name specified by difficulty argument and uses
     * {@link FileHandler#fill_the_fields(SudokuBoard)} method to put the values from file to the board.
     * @param difficulty Integer that specifies the file name which is stored in String array of SudokuBoard object.
     * @param board Logical instance of Sudoku board, usually passed by a Model.
     * @throws IOException When a file is unreachable or contains incorrectly formatted text.
     * @see FileHandler#fill_the_fields(SudokuBoard)
     */
    void make_Sudoku_Board(int difficulty, SudokuBoard board) throws IOException
    {   //open "difficulty" file, read it and fill the values in board
        board.setDifficulty(difficulty);
        FileHandler file_manipulator = new FileHandler("C:\\Users\\model\\IdeaProjects\\SpringMVC1\\src\\main\\webapp\\resources\\"+board.getDifficultyName()+".txt");
        file_manipulator.fill_the_fields(board);
    }

}

