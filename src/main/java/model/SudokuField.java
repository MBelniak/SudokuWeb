package model;


/**
 * Represents a single Sudoku field. Keeps its coordinates, value to be displayed and fixed value that is a part of a solution.
 * @author Michał Belniak
 *
 */
public class SudokuField{
    /**
     * x coordinate of a field.
     */
    private final int x;
    /**
     * y coordinate of a field.
     */
    private final int y;
    /**
     * Initial value given that can be revealed at the start of a game.
     */
    private int currentValue;
    /**
     * Value that corresponds to board solution.
     */
    private int fixedValue;

    public SudokuField(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int value) {
        this.currentValue = value;
    }

    public int getFixedValue() {
        return fixedValue;
    }

    public void setFixedValue(int fixedValue) {
        this.fixedValue = fixedValue;
    }
}
