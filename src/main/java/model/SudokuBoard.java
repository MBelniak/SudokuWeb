package model;

/**
 * This class is a logical representation of a Sudoku board.
 * It stores Sudoku fields in 9x9 array.
 * By setters and getters it is possible conduct some operations, for example fill the fields with values.
 * @author Michał Belniak
 */
public class SudokuBoard
{
    /**
     * Represents 81 fields of Sudoku.
     */
    private SudokuField[][] fields;     //all Sudoku fields

    /**
     * Initializes each Sudoku field from {@link #fields}.
     */
    SudokuBoard()
    {
        fields = new SudokuField[9][9];     //[0][0] - left up, [8][8] - right down, [8][0] - right up
        for (int i = 0; i < 9; i++)         //fields initializing
            for (int k = 0; k < 9; k++)
            {
                fields[k][i] = new SudokuField(k,i);
            }
    }

    public SudokuField[][] getFields()
    {
        return fields;
    }

    /**
     * Sets field[x][y] value to values[0] and fixed value to values[1]
     * @param x value
     * @param y fixed value
     * @param values coordinates of a field
     * @see SudokuField
     */
    public void setSingleField(int x, int y, int values[])
    {
        fields[x][y].setCurrentValue(values[0]);
        fields[x][y].setFixedValue(values[1]);
    }
}