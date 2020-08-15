package model;


import java.io.*;
import static model.Consts.DIFFICULTY_NAMES;

/**
 * SudokuBase uses FileManipulator class to open and read from file.
 *  It gets a SudokuBoard object in {@link #makeSudokuBoard(int)} and fills fixed and non-fixed values into its [9][9] fields array.
 *  @author Michał Belniak
 *  @see SudokuFileReader
 */
public class SudokuBase {

    /**
     * Initializes {@link SudokuFileReader} object with a file name specified by difficulty argument and uses
     * {@link SudokuFileReader#fillTheFields(SudokuBoard)} method to put the values from file to the board.
     * @param difficulty Integer that specifies the file name which is stored in String array of SudokuBoard object.
     * @throws IOException When a file is unreachable, contains incorrectly formatted text or the board was not found.
     * @see SudokuFileReader#fillTheFields(SudokuBoard)
     */
    SudokuBoard makeSudokuBoard(int difficulty) throws IOException
    {   //open "difficulty" file, read it and fill the values in board
        SudokuFileReader sudokuFileReader = new SudokuFileReader("files/" + DIFFICULTY_NAMES[difficulty] + ".txt");
        SudokuBoard board = new SudokuBoard();
        sudokuFileReader.fillTheFields(board);
        return board;
    }

}

