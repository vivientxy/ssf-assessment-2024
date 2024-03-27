package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class MovieController {



    // TODO: Task 8
    @GetMapping(path = "/movies")
    public ModelAndView displayMovies() {
        ModelAndView mav = new ModelAndView();

        return mav;
    }

    // TODO: Task 9
    public String bookMovie()  {

    }

    // TODO: Task 9
    // ... ...

}
