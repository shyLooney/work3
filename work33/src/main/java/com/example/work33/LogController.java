package com.example.work33;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LogController {
    private String login = "sema";
    private String password = "qwerty";
    private long start = 0;

    @GetMapping("/")
    public String log() {
        return "design";
    }

    @GetMapping("/log")
    public String login(@RequestParam(value = "login", required = false, defaultValue = "defaultLogin") String login,
                        @RequestParam(value = "password", required = false, defaultValue = "defaultPassword") String password,
                        Model model) {
        if (login.equals("defaultLogin"))
            return "log";

        if (start > System.currentTimeMillis()) {
            model.addAttribute("message",
                    "Подождите " + ((start - System.currentTimeMillis()) / (1000 * 60))
                            + " минут.\nПрежде чем пробовать снова");
            return "log";
        }


        boolean condition = false;
        if (login.equals(this.login) && password.equals(this.password)) {
            condition = true;
        }

        if (condition) {
            model.addAttribute("message", "С возвращением " + login + "!");
        } else {
            start = System.currentTimeMillis() + 1000 * 60 * 15;
            model.addAttribute("message", "Логин или пароль введены неверно!");
        }

        return "log";
    }
}
