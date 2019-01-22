package service;

import model.Model;
import model.SudokuField;
import org.springframework.stereotype.Service;

@Service
public class SudokuService {

    private Model model = new Model();

    public void makeBoard(int diff_level)
    {
        model.makeGeneratedBoard(diff_level);
    }

    public SudokuField[][] getFields()
    {
        if(model.getBoard()==null)
            makeBoard(0);
        return model.getBoard().getFields();
    }

}
