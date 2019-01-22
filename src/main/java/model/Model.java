package model;

import java.io.IOException;
import java.util.LinkedList;

/**
 * This class creates a kind of an API for all the logic of the application.
 * It manages classes which create boards from file or generate them with an algorithm,
 * keeps a reference to a logical Sudoku board, checks the correctness of a board
 * and can return an array of fields that are filled wrongly.
 * <p>
 *     The most interesting methods are {@link #makeGeneratedBoard(int)} and {@link #makeBoardFromFile(int)}.
 *     The first one uses {@link SudokuGenerator} and {@link SudokuFiller} to generate a random, but valid Sudoku board.
 *     Depending on the difficulty chosen by a user, this method can reject some boards until it finds the one, that
 *     suits the selected difficulty. Difficulty is determined by a number of fields initially inserted into the board.
 *     The second method uses {@link SudokuBase} to generate board from file. SudokuBase connects to a file
 *     via FileManipulator class implemented in the same SudokuBase.java file. There is a control
 *     of validity and correctness of that file, then {@link SudokuBoard} object passed to this method is filled with
 *     values and fixed values. Value is the actual digit in a particular field, fixed value is a digit that suits the solution
 *     of a particular board.
 * </p>
 * @author Michał Belniak
 * @version 1.0
 * @see SudokuGenerator
 * @see SudokuFiller
 * @see FileHandler
 * @see SudokuBase
 * @see UniqueChecker
 * @see SudokuField
 */

public class Model {

    /**
     * Keeps all fields (with values and fixed values) in [9][9] array
     */
    private SudokuBoard board;
    /**
     * Responsible for connecting to a file and generate a board based on the info in this file.
     */
    private SudokuBase base;
    /**
     * Able to generate new, random, valid and fully filled Sudoku board.
     */
    private SudokuGenerator generator;
    /**
     * After getting a fully filled board, it can generate a board with only some fields filled but still with a unique solution.
     */
    private SudokuFiller filler;        //to fill enough values for the board to have unique solution

    private String error_message;

    public SudokuBoard getBoard() {
        return board;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    private static final int SIZE = 81;
    private static final int[] DIFFICULTYLEVEL = {50, 40, 30, 17};

    /**
     * Initializes SudokuBoard, SudokuBase, ArrayList{@literal <}SudokuField{@literal >}, SudokuGenerator and SudokuFiller objects.
     */
    public Model()
    {
        board = null;
        base = new SudokuBase();
        generator = new SudokuGenerator();
        filler = new SudokuFiller();
    }

    /**
     * Generates random, valid Sudoku board with number of initially filled values depending on chosen difficulty.
     * Uses {@link SudokuGenerator} and {@link SudokuFiller} objects to do it.
     * Also, calls {@link #convertToBoard(LinkedList, LinkedList)} method to convert values
     * from generator and filler into proper values in the list in {@link SudokuBoard}
     * @param difficulty Integer indicating the chosen difficulty:
     *                   <ul>
     *                   <li>0 - easy</li>
     *                   <li>1 - medium</li>
     *                   <li>2 - hard</li>
     *                   </ul>
     * @throws NullPointerException When difficulty does not belong to {0,1,2}.
     */
    public void makeGeneratedBoard(int difficulty) //makes randomly generated, valid Sudoku board
    {
        if(difficulty>2||difficulty<0)
            return;
        int counter;
        do {
            generator.generateBoard();

            filler.setBoardWithSolution(generator);
            filler.fillValues();
            counter=0;
            for (SudokuCell cell : filler.getBoardToFill().getSudokuList()) {   //counting the filled cells to define the difficulty
                if (cell.getFixed_value()!=0)
                    counter++;
            }
        }
        while(counter>=DIFFICULTYLEVEL[difficulty]
                ||counter<DIFFICULTYLEVEL[difficulty+1]);     //if the board has to many fields filled, find another one

        convertToBoard(generator.getSudokuList(), filler.getBoardToFill().getSudokuList());
        board.setDifficulty(difficulty);
    }

    /**
     * Calls SudokuBase method - {@link SudokuBase#make_Sudoku_Board(int, SudokuBoard)} - which opens proper file depending on difficulty passed
     * and creates a board randomly selected from this file. Then, checks if loaded board is valid - someone could manipulate the file.
     * @param diff specifies which file to open. 0 - Latwy.txt, 1 - Sredni.txt, 2 - Trudny.txt.
     * @return 0 if board loaded fine, 1 if board contains errors
     * @throws IOException when specified file doesn't exist or the text in the file is incorrectly formatted
     */
    int makeBoardFromFile(int diff) throws IOException   //make a board from "diff" file
    {
            base.make_Sudoku_Board(diff, board);
            if(!CheckIfBoardCorrect()) {    //check if the board inserted into the file is valid
                error_message = "Niepoprawne wartosci w pliku - plansza bedzie zawierala niepoprawne rozwiązanie.";
                return 1;
            }
            return 0;
    }

    /**
     * Takes two SudokuCell lists and assigns proper values from the cells contained in the lists to the values in SudokuBoard object
     * referenced in the Model.
     * @param fixed list containing cells which fixed values will be board's fixed values
     * @param filled list containing cells which fixed values will be board's values
     * @see SudokuCell
     */
    private void convertToBoard(LinkedList<SudokuCell> fixed, LinkedList<SudokuCell> filled)    //get a SudokuBoard object from two SudokuCell lists
    {                                                                                           //that generator and filler generated
        if(board==null)
            board = new SudokuBoard();
        for(int i = 0; i<SIZE; i++)
        {
            board.getFields()[i%9][i/9].setFixed_value(fixed.get(i).getFixed_value());
            board.getFields()[i%9][i/9].setCurrent_value(filled.get(i).getFixed_value());
        }
    }

    /**
     * Checks if {@link #board} is valid in terms of values uniqueness in columns, rows ans 3x3 squares.
     * @return True if board is correct, false otherwise.
     */
    private boolean CheckIfBoardCorrect()       //called after getting a board from a file
    {
        return  CheckRowsAndColumnsForFixedValues()&&CheckSquaresForFixedValues();
    }
    /**
     * Checks the uniqueness of values in columns and rows of Sudoku board contained in {@link #board}.
     * @return true if all values are correct, false otherwise.
     */
    private boolean CheckRowsAndColumnsForFixedValues()
    {
        for(int i=0; i<9;i++)
        {
            int[] bitsRow = {0,0,0,0,0,0,0,0,0};    //each element represents if a value has appeared in a row (or column)
            int[] bitsCol = {0,0,0,0,0,0,0,0,0};
            for(int k=0; k<9; k++) {
                if (bitsRow[board.getFields()[k][i].getFixed_value() - 1] == 0) {
                    bitsRow[board.getFields()[k][i].getFixed_value() - 1] = 1;
                }
                else
                    return false;

                if (bitsCol[board.getFields()[i][k].getFixed_value() - 1] == 0) {
                    bitsCol[board.getFields()[i][k].getFixed_value() - 1] = 1;
                }
                else
                    return false;
            }
        }
        return true;
    }

    /**
     * Checks the uniqueness of values in 3x3 squares of Sudoku board contained in {@link #board}.
     * @return true if all values are correct, false otherwise.
     */
    private boolean CheckSquaresForFixedValues()   //checking Sudoku for squares
    {

        for (int i=0; i<3; i++)
            for(int k=0; k<3; k++)
                for(int j=3*i; j<3*i+3; j++)
                    for(int g=3*k; g<3*k+3; g++) {
                        int[] bits = {0, 0, 0, 0, 0, 0, 0, 0, 0};
                            if (bits[board.getFields()[g][j].getFixed_value() - 1] == 0) {
                                bits[board.getFields()[g][j].getFixed_value() - 1] = 1;
                            }
                            else
                                return false;
                    }
        return true;
    }

}