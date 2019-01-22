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
    private int current_value;
    /**
     * Value that corresponds to board solution.
     */
    private int fixed_value;

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

    public int getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(int value) {
        this.current_value = value;
    }

    public int getFixed_value() {
        return fixed_value;
    }

    public void setFixed_value(int fixed_value) {
        this.fixed_value = fixed_value;
    }
}
