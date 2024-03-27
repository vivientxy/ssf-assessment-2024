package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    // Task 6
    @GetMapping(path = "/")
    public ModelAndView login(HttpSession sess) {
        ModelAndView mav = new ModelAndView("view0");
        mav.addObject("login", new Login());
        return mav;
    }

    // Task 7
    @PostMapping(path = "/")
    public ModelAndView processlogin(HttpSession sess, @ModelAttribute @Valid Login login, BindingResult bindings) {
        ModelAndView mav = new ModelAndView("view0");
        mav.addObject("login", login);

        // check for today only, since Spring validation doesn't capture this
        if (login.getBirthDate() != null) { // if null, skip to regular object validation below
            Date birthday = login.getBirthDate();
            if (isToday(birthday)) {
                ObjectError error = new ObjectError("globalError", "Birthday cannot be a current or future date");
                bindings.addError(error);
            }
        }

        if (bindings.hasErrors()) {
            return mav;
        }

        // successful login
        sess.setAttribute("login", login);
        mav.setViewName("view1");
        return mav;
    }

    // For the logout button shown on View 2
    // On logout, session should be cleared
    @GetMapping(path = "/logout")
    public String logout(HttpSession sess) {
        sess.invalidate();
        return "redirect:/";
    }

    private Boolean isToday(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        int day1 = Integer.parseInt(sdf.format(date));
        sdf.applyPattern("MM");
        int month1 = Integer.parseInt(sdf.format(date));
        sdf.applyPattern("yyyy");
        int year1 = Integer.parseInt(sdf.format(date));
        LocalDate today = LocalDate.now();
        int day2 = today.getDayOfMonth();
        int month2 = today.getMonthValue();
        int year2 = today.getYear();
        return (day1 == day2)
                && (month1 == month2)
                && (year1 == year2);
    }

}
