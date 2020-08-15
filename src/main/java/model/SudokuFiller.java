package model;
import java.util.Collections;
import java.util.LinkedList;

/**
 * This class can take an entirely filled board and create a board with only few fields filled with values.
 * Created board will have 17-77 fields filled and only one solution similar to the one in {@link #boardWithSolution}.
 */
public class SudokuFiller {
    /**
     * Keeps a fully filled board that represents the actual solution of considered Sudoku board.
     */
    private LinkedList<SudokuCell> boardWithSolution;
    /**
     * Represents a board that will be filled in with some values.
     */
    private LinkedList<SudokuCell>
            boardToFill;

    public void setBoardWithSolution(LinkedList<SudokuCell> boardWithSolution) {
        this.boardWithSolution = boardWithSolution;
    }

    /**
     * Fills in the values into {@link #boardToFill} to make a playable, one solution board.
     * Adds random field from boardWithSolution to boardToFill and checks the uniqueness of solution.
     * Continues until the board has only one solution.
     */
    void fillValues()
    {
        boardToFill = new SudokuGenerator().getSudokuList();
        SudokuCell cellptr;
        LinkedList<SudokuCell> toShuffle = new LinkedList<>();
        toShuffle.addAll(boardWithSolution);
        Collections.shuffle(toShuffle);
        for(SudokuCell cell : toShuffle)
        {
            cellptr = SudokuCell.getCellByCoords(boardWithSolution, new int[]{cell.getX(), cell.getY()});
            SudokuCell.getCellByCoords(boardToFill, new int[]{cell.getX(), cell.getY()}).setFixedValue(cellptr.getFixedValue());
            if(checkIfSolutionUnique(boardToFill))
                break;
        }
    }

    LinkedList<SudokuCell> getBoardToFill()
    {
        return boardToFill;
    }

    /**
     * Uses {@link UniqueChecker} class to check if current configuration of fields makes the board have only one solution.
     * @param sudokuCells LinkedList of {@link SudokuCell} that contains board to check and conduct the algorithm.
     * @return true if solution unique, false otherwise.
     */
    private boolean checkIfSolutionUnique(LinkedList<SudokuCell> sudokuCells)
    {
        UniqueChecker checker = new UniqueChecker();
        return checker.checkIfSolutionUnique(sudokuCells);
    }
}
