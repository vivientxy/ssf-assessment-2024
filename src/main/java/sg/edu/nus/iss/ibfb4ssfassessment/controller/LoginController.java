package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

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
        ModelAndView mav = new ModelAndView();
        if (bindings.hasErrors()) {
            mav.setViewName("view0");
            mav.addObject("login", login);
            return mav;
        }
        // successful login
        sess.setAttribute("login", login);
        mav.setViewName("view1");
        System.out.println(">>>login attribute saved: " + login);
        return mav;
    }

    // For the logout button shown on View 2
    // On logout, session should be cleared
    @GetMapping(path = "/logout")
    public String logout(HttpSession sess) {
        sess.invalidate();
        return "redirect:/";
    }
    
}
