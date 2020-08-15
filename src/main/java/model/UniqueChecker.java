package model;

import java.util.LinkedList;

/**
 * This class is a small API for checking if given Sudoku board has only one solution.
 * @author Michał Belniak
 * @see SudokuCell
 */
public class UniqueChecker {

    /**
     * It can store a copy of Sudoku cells' list to check, if their configuration gives unique solution.
     */
    private SudokuGenerator sudokuAlg;

    UniqueChecker(){
        sudokuAlg = new SudokuGenerator();
    }

    /**
     * Copies values from a list given in sudokuQ and checks if they give a unique solution.
     * @param boardToCheck {@link SudokuGenerator} object from which to take values.
     * @return true if solution is unique, fallse otherwise.
     */
    boolean checkIfSolutionUnique(LinkedList<SudokuCell> boardToCheck) {
       for (SudokuCell cell : boardToCheck)
            if (cell.getFixedValue() != 0) {
                SudokuCell.getCellByCoords(boardToCheck, new int[]{cell.getX(), cell.getY()}).setFixedValue(cell.getFixedValue());
            }

            return !sudokuAlg.doFillCellsUnique(0);

    }



}
