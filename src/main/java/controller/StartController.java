package controller;

import model.SudokuField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.ModelAndView;
import service.SudokuService;



@Controller
public class StartController {

    @Autowired
    private SudokuService sudokuService;

    @RequestMapping("/get_fields")
    @ResponseBody
    public SudokuField[][] getFields()
    {
        return sudokuService.getFields();
    }

    @RequestMapping("/showSudoku")
    public ModelAndView showSudoku(@RequestParam("difficulty") Integer diff)
    {
        ModelAndView mv = new ModelAndView();
        sudokuService.makeBoard(diff);
        mv.setViewName("Sudoku_game");
        return mv;
    }



}