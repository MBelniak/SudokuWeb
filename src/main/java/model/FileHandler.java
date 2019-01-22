package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Opens desired file (throws exception if need) and reads its lines.
 * It gets randomly selected board from those which are available in the file.
 * When a random number of board is selected it uses {@link #set_reading_at(int, BufferedReader)} method,
 * which sets the cursor in the file at the beginning of selected board.
 */
public class FileHandler {
    //opens desired file (throws exception if need) and reads its lines.
    //It gets randomly selected board from those which are available.
    private String line;
    private String file_name;
    /**
     * Stores board number, which is randomly selected from those that are available.
     */
    private int board_to_read;

    /**
     * Initializes BuffereReader object and opens the file_name file. Read the first line,
     * which should contain number of boards in this file. Then chooses a random number
     * from 1-n range and stores it in {@link #board_to_read}.
     *
     * @param file_name File to be opened.
     * @throws IOException When file unreachable or text in the file is incorrect.
     */
    FileHandler(String file_name) throws IOException {
        this.file_name = file_name;
        System.out.println(file_name);
        BufferedReader br = new BufferedReader(new FileReader(file_name)); //open a file
        line = br.readLine();

        if (line == null || (!line.substring(0, 8).equals("<boards>")))    //there should be number of boards info at the first line of a file
            throw new FileHandler.InvalidTextFormatException("Błąd wczytywania planszy - niepoprawny tekst pliku.");

        //read number of boards and get a random number from 1 to number of boards
        board_to_read = ThreadLocalRandom.current().nextInt(1, Integer.parseInt(line.substring(8, line.length() - 9)) + 1);
        br.close(); //close buffer to end reading

    }

    /**
     * Opens a file specified by {@link #file_name}, calls {@link #set_reading_at(int, BufferedReader)} method.
     * Then, fills sb with values found in the file.
     *
     * @param sb SudokuBoard that has to be filled with values.
     * @throws IOException When text in the file is incorrect.
     */
    void fill_the_fields(SudokuBoard sb) throws IOException //reads line after line and sets fields' values
    {
        int[] values = new int[2];
        BufferedReader br = new BufferedReader(new FileReader(file_name));

        if (set_reading_at(board_to_read, br) == 1) //sets reader at the first coords of a board that whas randomly selected
            throw new FileHandler.InvalidTextFormatException("Błąd wczytywania planszy - niepoprawny tekst pliku.");

        for (int i = 0; i < 9; i++)   //taking values line after line
        {
            for (int k = 0; k < 9; k++) {
                getNextValues(br, values);

                if ((values[0] != values[1] && values[0] != 0) || values[1] == 0)  //the first value has to be the same as the second(fixed) or 0 and fixed value can't be 0
                    throw new FileHandler.InvalidTextFormatException("Niepoprawne wartości pól.");

                sb.set_single_field(k, i, values);  //setting the value for [k][i] field
            }
        }

        br.close();
    }

    /**
     * Sets reader at the beginning of selected board presented in the opened file.
     *
     * @param board_number Selected board to be read from file
     * @param file_reader  BufferedReader which has to be set to reading at the beginning of selected board.
     * @return 1 if beginning of the board wasn't found, 0 if everything OK.
     * @throws IOException
     */
    private int set_reading_at(int board_number, BufferedReader file_reader) throws IOException //setting reader at the first coords of a board
    {
        String searched = "<board" + board_number + ">";
        while ((line = file_reader.readLine()) != null && !line.contains(searched)) ;
        if (line == null)
            return 1;
        return 0;
    }

    /**
     * Gets next value and fixed value from file.
     *
     * @param br    BufferedReader going through a file.
     * @param array int[] that will be filled with read value and fixed value.
     * @throws IOException
     */
    private void getNextValues(java.io.BufferedReader br, int array[]) throws IOException {    //takes a line and analyzes it, inserts ready-for-fill values into int[2] array
        String line;

        if ((line = br.readLine()) != null) {
            if (line.length() != 8)
                throw new FileHandler.InvalidTextFormatException("Niepoprawny teskt pliku");

            array[0] = line.charAt(5) - '0';
            array[1] = line.charAt(7) - '0';
        } else {
            throw new FileHandler.InvalidTextFormatException("Niepoprawny tekst pliku");
        }
    }

    /**
     * Exception created to indicate incorrectly formatted text in a file.
     */
    class InvalidTextFormatException extends IOException {
        private String message;

        InvalidTextFormatException(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
