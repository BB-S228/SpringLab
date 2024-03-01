package com.example.sprlabs.Controllers;

import com.example.sprlabs.Classes.Developers;
import com.example.sprlabs.Repository.Developers.DevelopersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("developers")
@SessionAttributes("list")
@Controller
public class DevelopersController {
    private final DevelopersRepository developersRepository;

    @Autowired
    public DevelopersController(DevelopersRepository developersRepository) {this.developersRepository = developersRepository;}

    @GetMapping
    public String show(Model model) {
        Developers dev = new Developers();
        model.addAttribute("dev",dev);
        Iterable<Developers> developers = developersRepository.findAll();
        model.addAttribute("list", developers);
        return "Developer";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("dev") Developers dev, BindingResult result) {
        if(result.hasErrors()){
            return "Developer";
        }
        else{
            developersRepository.addDev(dev);return "redirect:/developers";}
    }

    @PostMapping(value = "/remove")
    public String remove(@ModelAttribute(value = "var") Developers dev) {
        developersRepository.deleteDev(dev.getId());
        return "redirect:/developers";
    }

    @PostMapping(value = "/refactor_first")
    public String update_start(@ModelAttribute(value = "var") Developers dev, Model model) {
        model.addAttribute("dev",dev);
        Iterable<Developers> developers = developersRepository.findAll();
        model.addAttribute("list", developers);
        return "Dev_Ref";
    }

    @PostMapping(value = "/refactor_second")
    public String update_end(@ModelAttribute(value = "dev") Developers dev) {
        developersRepository.updateDev(dev);
        return "redirect:/developers";
    }
}
