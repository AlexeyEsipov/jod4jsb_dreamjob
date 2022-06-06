package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import ru.job4j.dreamjob.service.UserService;
import ru.job4j.dreamjob.model.User;

@ThreadSafe
@Controller
public class UserControl {

    private static final String FAIL = "Пользователь с такой почтой уже существует";
    private static final String SUCCESS =
            "Регистрация прошла успешно, для продолжения работы авторизуйтесь!";
    private final UserService userService;

    public UserControl(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/formRegistration")
    public String formRegistration(Model model,
                                   @RequestParam(name = "fail", required = false) Boolean fail,
                                   HttpSession session) {
        setUser(model, session);
        model.addAttribute("fail", fail != null);
        return "registration";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session) {
        setUser(model, session);
        model.addAttribute("message", SUCCESS);
        return "success";
    }

    @GetMapping("/fail")
    public String fail(Model model) {
        model.addAttribute("message", FAIL);
        return "fail";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findUserByEmailAndPwd(
                user.getEmail(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @PostMapping("/successRedirect")
    public String successRedirect(Model model, HttpSession session) {
        setUser(model, session);
        return "redirect:/loginPage";
    }

    private void setUser(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}