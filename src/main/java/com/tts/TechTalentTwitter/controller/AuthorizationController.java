package com.tts.TechTalentTwitter.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tts.TechTalentTwitter.model.UserProfile;
import com.tts.TechTalentTwitter.service.UserService;

@Controller
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping(value="/signup")
    public String registration(Model model){
        //sends new user with default values for filling out form
        UserProfile userProfile = new UserProfile();
        model.addAttribute("user", userProfile);
        //return strings point to templates in Thymeleaf
        return "registration";
    }

    //TO get user value back from form after it is submitted
    @PostMapping(value = "/signup")
    public String createNewUser(@Valid UserProfile userProfile, BindingResult bindingResult, Model model) {
        //calls the database service- need to inject UserService to get access by @Autowired
        UserProfile userProfileExists = userService.findByUsername(userProfile.getUsername());
        //BindingResult used when you validate a user- it allows us to review the result of the validation
        //reject value takes in 3 parameter (field, arbitrary error, message)
        if (userProfileExists != null) {
            bindingResult.rejectValue("username", "error.user", "Username is already taken");
        }
        if (!bindingResult.hasErrors()) {
            //return user
            userService.saveNewUser(userProfile);
            //add attributes to the model
            model.addAttribute("success", "Sign up successful!");
            model.addAttribute("user", new UserProfile());

        }
        return "registration";
    }

}