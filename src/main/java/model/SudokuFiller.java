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
    private SudokuGenerator boardWithSolution;
    /**
     * Represents a board that will be filled in with some values.
     */
    private SudokuGenerator boardToFill;

    public void setBoardWithSolution(SudokuGenerator boardWithSolution) {
        this.boardWithSolution = boardWithSolution;
    }

    /**
     * Fills in the values into {@link #boardToFill} to make a playable, one solution board.
     * Adds random field from boardWithSolution to boardToFill and checks the uniqueness of solution.
     * Continues until the board has only one solution.
     */
    void fillValues()
    {
        boardToFill = new SudokuGenerator();
        SudokuCell cellptr;
        LinkedList<SudokuCell> toShuffle = new LinkedList<>();
        toShuffle.addAll(boardWithSolution.getSudokuList());
        Collections.shuffle(toShuffle);
        for(SudokuCell cell : toShuffle)
        {
            cellptr = boardWithSolution.getCellByCoords(new int[]{cell.getX(), cell.getY()});
            boardToFill.getCellByCoords(new int[]{cell.getX(), cell.getY()}).setFixed_value(cellptr.getFixed_value());
            if(checkIfSolutionUnique(boardToFill))
                break;
        }
    }

    SudokuGenerator getBoardToFill()
    {
        return boardToFill;
    }

    /**
     * Uses {@link UniqueChecker} class to check if current configuration of fields makes the board have only one solution.
     * @param sg {@link SudokuGenerator} object that contains board to check and conduct the algorithm.
     * @return true if solution unique, false otherwise.
     */
    private boolean checkIfSolutionUnique(SudokuGenerator sg)
    {
        UniqueChecker checker = new UniqueChecker();
        return checker.checkIfSolutionUnique(sg);
    }
}
