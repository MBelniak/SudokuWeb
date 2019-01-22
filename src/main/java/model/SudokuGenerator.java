package model;

import java.util.*;

/**
 * This class is able to generate a random but valid Sudoku board.
 * It uses {@link #doFillCells(int)} method to generate full Sudoku board
 * and {@link #doFillCellsUnique(int)} to try to fill given board twice, which would mean
 * that the solution wasn't unique.
 * @author Michał Belniak
 * @see SudokuCell
 */
public class SudokuGenerator {
    /**
     * Keeps numbers from 1 to 9. It is a base, which used with {@link SudokuCell#neighbors} let
     * generate possible values in a specific field.
     * @see SudokuCell
     */
    static final HashSet<Integer> VALID = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));

    /**
     * A list with all 81 Sudoku cells. The first element is a cell with (0,0) coordinates, (8,8) is the last element.
     */
    private LinkedList<SudokuCell> sudokuList;
    /**
     * Used by a {@link #doFillCellsUnique(int)} method. Indicates whether the last empty cell
     * was already visited.
     */
    private boolean lastCellSecondTime;

    /**
     * Initializes all 81 cells.
     */
    SudokuGenerator(){
        sudokuList = new LinkedList<>();
        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                sudokuList.add(new SudokuCell(i,j));
            }
        }
    }

    /**
     * Resets recently generated board and ask sqb to create a new one.
     */
    void generateBoard()
    {
        reset();
        doFillCells(0);
    }


    /**
     * Sets all cells' fixed values to 0.
     */
    void reset () {
        for(SudokuCell cell : sudokuList) {
            cell.setFixed_value(0);
        }
    }

    LinkedList<SudokuCell> getSudokuList()
    {
        return sudokuList;
    }
    // fill the board with valid solution

    /**
     * Checks, if the current board has only one solution.
     * Values in particular fields can be earlier set using {@link #getCellByCoords(int[])} method.
     * This algorithm uses backtracking algorithm, so it's based on recursion.
     * It fills all cells with proper values until it gets to the last one.
     * Then, it checks if it's the first time it got there and if so,
     * it backtracks to check if its possible to get there once again with other values filled earlier.
     * If it gets to the last cell once again, it means the current board has more than ona solution.
     * @param index Number of currently considered cell (it also shows the level of recursion).
     * @return true if there is more than one solution, false if the solution is unique.
     */
    boolean doFillCellsUnique(int index) {
        // get first cell and tail
        SudokuCell cell = sudokuList.get(index);
        cell.generateAllNeighbors(this);

        HashSet<Integer> neighborValues = new HashSet<>();

        for(SudokuCell neighbor : cell.neighbors) { //let's see what values are unavailable
            int value = neighbor.getFixed_value();
            neighborValues.add(value);
        }

        ArrayList<Integer> options = new ArrayList<>(SudokuGenerator.VALID);
        options.removeAll(neighborValues);  //It will give us only those values that are avaiable
        Collections.shuffle(options);   //random shuffle
        for(int option : options) {

            cell.setFixed_value(option);

            for(int i = index+1; i<sudokuList.size(); i++) //algorithm has to avoid the values, that were given at the beginning
            {
                if(sudokuList.get(i).getFixed_value()==0)   //so if the cell's value wasn't given, let's fill it
                    if(doFillCellsUnique(i))            //Solution find, let's go back and quit the algorithm.
                        return true;
                if(i==sudokuList.size() - 1 )         //every following cell was set at the beginning so it's the last cell of the algorithm
                {
                    if(!lastCellSecondTime)         //First time at the last cell and it's not (8,8) cell, so let's set the value to 0
                    {                               //and try to get there once again to see if it was the only one solution.
                        cell.setFixed_value(0);
                        lastCellSecondTime=true;    //Mark last cell as visited
                    }
                    else
                        return true;            //We were here, at the last cell, before - so the solution wasn't unique. Let's quit the algorithm.
                }
            }
            if(index==sudokuList.size()-1)  //We are at the (8,8) cell
                if(!lastCellSecondTime) {   //We haven't been here before, so let's mark it
                    cell.setFixed_value(0);
                    lastCellSecondTime=true;
                }
                else return true;   //We were here before, let's quit the algorithm - solution wasn't unique.

        }

        // out of options, backtrack
        cell.setFixed_value(0);
        return false;   //If the (0,0) cell returns false it means, that the given board had one, unique solution.
    }

    /**
     * Fills the entire board with values that creates a valid Sudoku solution.
     * Uses backtracking algorithm.
     * @param index Number of currently considered cell (it also shows the level of recursion).
     * @return
     */
    boolean doFillCells(int index) {
        // get first cell and tail
        SudokuCell cell = sudokuList.get(index);
        cell.generateAllNeighbors(this);

        HashSet<Integer> neighborValues = new HashSet<>();

        for(SudokuCell neighbor : cell.neighbors) {
            int value = neighbor.getFixed_value();
            neighborValues.add(value);
        }

        ArrayList<Integer> options = new ArrayList<>(SudokuGenerator.VALID);
        options.removeAll(neighborValues);
        Collections.shuffle(options);

        for(int option : options) {

            cell.setFixed_value(option);


            if (index == sudokuList.size() - 1 || doFillCells(index + 1)) {
                return true;
            }
        }

        // out of options backtrack
        cell.setFixed_value(0);
        return false;
    }

    /**
     *
     * @param coords (x,y) of a cell to be returned.
     * @return cell with (coords[0], coords[1]) coordinates.
     */
    SudokuCell getCellByCoords(int[] coords) {
        return sudokuList.get(coords[0] * 9 + coords[1]);
    }


}
