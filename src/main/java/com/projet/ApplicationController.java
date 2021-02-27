package com.projet;

import com.projet.Tournament.Tournament;
import com.projet.Tournament.TournamentRepository;
import com.projet.Tournament.TournamentService;
import com.projet.Users.User;
import com.projet.Users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class ApplicationController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TournamentRepository trepo;

    @Autowired
    private TournamentService tournamentService;

    @GetMapping("")
    public String viewHomePage(Model model, @RequestParam(defaultValue = "1") int p) {

        Page<Tournament> page = tournamentService.getTournaments(p);
        List<Tournament> listTournoi = page.getContent();
        model.addAttribute("currentPage", p);
        model.addAttribute("previousPage", p-1);
        model.addAttribute("nextPage", p +1);
        model.addAttribute("totalPage", page.getTotalPages());
        model.addAttribute("listTournoi", listTournoi);
        return "index";

    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/account")
    public String viewAccount(Model model) {
        return "account";
    }

    @GetMapping("/account/editAccount")
    public String editAccount(Model model){ return "editAccount"; }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login_form";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }
    @GetMapping("/search")
    public String showSearch(Model model, @RequestParam(defaultValue = "") String search){
        if(search != ""){
            List<Tournament> tournamentsSearch = tournamentService.findbyWord(search);
            model.addAttribute("tournaments", tournamentsSearch);
            model.addAttribute("recherche", search);
        }
        return "Search";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepo.save(user);

        return "register_success";
    }


}
