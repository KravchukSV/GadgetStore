package com.example.gadgetsore.controller;

import com.example.gadgetsore.repository.SmartphoneRepository;
import com.example.gadgetsore.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Objects;

@Controller
@RequestMapping("/smartphones")
public class MainController {
    private final UserRepository userRepository;
    private final SmartphoneRepository smartphoneRepository;

    public MainController(SmartphoneRepository smartphoneRepository, UserRepository userRepository) {
        this.smartphoneRepository = smartphoneRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public String mainPage(Model model, @AuthenticationPrincipal OAuth2User user){
        HashMap<Object, Object> data = new HashMap<>();

        if(Objects.nonNull(user)){
            data.put("profile", userRepository.findById(user.getAttribute("sub")).get());

        }
        else{
            data.put("profile", null);
        }
        data.put("smartphones", smartphoneRepository.findAll());
        model.addAttribute("frontendData", data);
        return "index";
    }
}
