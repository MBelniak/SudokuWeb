package model;
import java.util.HashSet;

/**
 * This class is like {@link SudokuField} but additionally, keeps all the neighbouring cells in a set.
 * Neighbour is a cell, that is in the same row, column or 3x3 square.
 * It is essential for generating a Sudoku board.
 * @author Michał Belniak
 * @see SudokuGenerator
 * @see SudokuField
 */
public class SudokuCell extends SudokuField {
    /**
     * Keeps all the neighbours of this cell.
     */
    public HashSet<SudokuCell> neighbors;

    /**
     * Initializes a cell with its (x,y) coordinates.
     * @param x x-coo
     * @param y y-coo
     */
    SudokuCell(int x, int y)
    {
        super(x,y);
        neighbors = new HashSet<>();

    }

    /**
     * Adds all naighbours from a passed {@link SudokuGenerator} object's SudokuCell list to {@link #neighbors}.
     * @param board SudokuGenerator object from which to take neighbours.
     */
    void generateAllNeighbors(SudokuGenerator board)
    {
        for (int n = 0; n < 9; ++n) {
            neighbors.add(board.getCellByCoords(new int[]{n, getY()}));
            neighbors.add(board.getCellByCoords(new int[]{getX(), n}));
        }

        int iFloor = (getX() / 3) * 3;
        int jFloor = (getY() / 3) * 3;


        for (int n = iFloor; n < iFloor + 3; n++) {
            for (int m = jFloor; m < jFloor + 3; m++) {
                neighbors.add(board.getCellByCoords(new int[]{n, m}));
            }
        }
    }

}

