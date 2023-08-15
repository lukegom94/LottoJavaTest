package com.lottoland.assignment.controller;

import com.lottoland.assignment.aop.Loggable;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RPSGameController {

    @Loggable(LogLevel.INFO)
    @GetMapping("/game")
    public String game(Model model) {

        return "gameview";
    }

    @Loggable(LogLevel.INFO)
    @GetMapping("/gameStats")
    public String gameStats(Model model) {

        return "gamestatsview";
    }

}