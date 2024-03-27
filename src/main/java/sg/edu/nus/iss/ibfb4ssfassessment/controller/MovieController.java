package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Login;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.service.DatabaseService;

@Controller
public class MovieController {

    @Autowired
    DatabaseService dbService;

    // Task 8
    @GetMapping(path = "/movies")
    public ModelAndView displayMovies(HttpSession sess) {
        ModelAndView mav = new ModelAndView("view2");
        if (sess == null || null == sess.getAttribute("login")) {
            mav.setViewName("redirect:/");
            return mav;
        }
        List<Movie> movieList = dbService.getAllMovies();
        mav.addObject("movieList", movieList);
        return mav;
    }

    // Task 9
    @PostMapping(path = "/book")
    public ModelAndView bookMovie(HttpSession sess, @RequestParam Integer movieId)  {
        ModelAndView mav = new ModelAndView();
        Login user = (Login)sess.getAttribute("login");
        Date birthDate = user.getBirthDate();
        int age = getAge(birthDate);
        System.out.println(">>> age: " + age);

        Movie movie = dbService.getMovieById(movieId);
        int ageRequired = 0;
        switch (movie.getRated()) {
            case "PG-13":
                ageRequired = 13;
                break;
            case "R":
                ageRequired = 18;
                break;
            default:
                break;
        }

        // underage
        if (age < ageRequired) {
            mav.setViewName("BookError");
            return mav;
        }

        int count = movie.getCount() + 1;
        movie.setCount(count);
        dbService.saveRecord(movie);
        mav.setViewName("BookSuccess");
        mav.addObject("movieTitle", movie.getTitle());
        return mav;
    }

    private int getAge(Date birthday) {
        LocalDate birthLocalDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(birthLocalDate, LocalDate.now()).getYears();
    }

}
