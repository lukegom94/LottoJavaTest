package com.lottoland.assignment.controller;

import com.lottoland.assignment.service.RPSGameService;
import com.lottoland.assignment.utils.dto.GameStats;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RPSGameController {

    final RPSGameService rpsGameService;

    public RPSGameController(RPSGameService rpsGameService) {
        this.rpsGameService = rpsGameService;
    }

    
    @GetMapping("/game")
    public String game(Model model, HttpSession session) {

        this.updateSessionModel(model, session);
        return "game";
    }

    
    @GetMapping("/gameStats")
    public String gameStats(Model model, HttpSession session) {
        var gameStats = rpsGameService.getGlobalGameStats();
        this.updateGlobalStats(model, session, gameStats);
        return "gamestats";
    }

    
    @PostMapping("/playRound")
    public String playRound(Model model, HttpSession session) {

        rpsGameService.playRound(session.getId());
        this.updateSessionModel(model, session);
        return "game";
    }

    
    @PostMapping("/restartGame")
    public String restartGame(Model model, HttpSession session) {

        rpsGameService.clearSessionRounds(session.getId());
        this.updateSessionModel(model, session);
        return "game";
    }

    // @Loggable(LogLevel.DEBUG)
    private void updateSessionModel (Model model, HttpSession session) {
        model.addAttribute("roundCount", rpsGameService.getSessionRoundCount(session.getId()));
        model.addAttribute("rounds", rpsGameService.getSessionRounds(session.getId()));
    }

    private void updateGlobalStats (Model model, HttpSession session, GameStats gameStats) {
        model.addAttribute("totalGamesPlayed", gameStats.totalGames());
        model.addAttribute("gamesWonByPlayer1", gameStats.p1Victories());
        model.addAttribute("gamesWonByPlayer2", gameStats.p2Victories());
        model.addAttribute("draws", gameStats.draws());
    }

}