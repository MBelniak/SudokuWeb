package service;

import model.Model;
import model.SudokuBoard;
import model.SudokuField;
import org.springframework.stereotype.Service;

@Service
public class SudokuService {

    private Model model = new Model();

    private SudokuBoard currentBoard;

    public void makeBoard(int diff_level) throws Model.IncorrectDifficultyException {
        currentBoard = model.createGeneratedBoard(diff_level);
    }

    public SudokuField[][] getFields() throws Model.IncorrectDifficultyException {
        if(currentBoard == null) {
            makeBoard(0);
        }
        return currentBoard.getFields();
    }

}
