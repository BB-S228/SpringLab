package com.example.sprlabs.Controllers;

import com.example.sprlabs.Classes.Developers;
import com.example.sprlabs.Classes.Games;
import com.example.sprlabs.Repository.Developers.DevelopersRepository;
import com.example.sprlabs.Repository.Games.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/games")
@Controller
@SessionAttributes("list")
public class GamesController {
    private final GamesRepository gamesRepository;
    private final DevelopersRepository developersRepository;
    @Autowired
    public GamesController(GamesRepository gamesRepository, DevelopersRepository developersRepository){
        this.gamesRepository = gamesRepository;
        this.developersRepository = developersRepository;
    }
    @GetMapping
    public String read(Model model){
        Games game = new Games();
        model.addAttribute("game", game);
        Iterable<Games> games = gamesRepository.findAll();
        Iterable<Developers> developers = developersRepository.findAll();
        model.addAttribute("developers", developers);
        model.addAttribute("list", games);
        return "Game";
    }
    @PostMapping(value = "/save")
    public String save(@Valid @ModelAttribute(value = "game") Games game, BindingResult result, Model model) {
        game.setDev(developersRepository.findById(game.getDev_id()).get());
        if (result.hasErrors()){
            model.addAttribute("developers", developersRepository.findAll());
            return "Game";}
        else{
            gamesRepository.addGames(game);
            return "redirect:/games";}
    }
    @PostMapping(value = "/remove")
    public String remove(@ModelAttribute(value = "var") Games game) {
        gamesRepository.deleteGames(game.getId());
        return "redirect:/games";
    }
    @PostMapping(value = "/refactor_first")
    public String refactor_first(@ModelAttribute(value = "var") Games game, Model model) {
        System.out.println(game);
        game.setDev(developersRepository.findById(game.getDev_id()).get());
        System.out.println(game);
        model.addAttribute("game", game);
        Iterable<Games> games = gamesRepository.findAll();
        model.addAttribute("list", games);
        Iterable<Developers> developers = developersRepository.findAll();
        model.addAttribute("developers", developers);
        return "Game_Ref";
    }
    @PostMapping(value = "/refactor_second")
    public String refactor_second(@ModelAttribute(value = "game") Games game) {
        game.setDev(developersRepository.findById(game.getDev_id()).get());
        gamesRepository.updateGames(game);
        return "redirect:/games";
    }
}