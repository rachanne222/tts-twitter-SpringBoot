package com.tts.TechTalentTwitter.controller;


import com.tts.TechTalentTwitter.model.UserProfile;
import com.tts.TechTalentTwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class FollowController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/follow/{username}")
    public String follow(@PathVariable(value="username") String username,
                         HttpServletRequest request) {
        UserProfile loggedInUser = userService.getLoggedInUser();
        UserProfile userToFollow = userService.findByUsername(username);
        List<UserProfile> followers = userToFollow.getFollowers();
        followers.add(loggedInUser);
        userService.save(userToFollow);
        //refers back to the page we came from
        return "redirect :" +request.getHeader("Referer");
    }
    @PostMapping(value = "/unfollow/{username}")
    public String unfollow(@PathVariable(value="username") String username,
                         HttpServletRequest request) {
        UserProfile loggedInUser = userService.getLoggedInUser();
        UserProfile userToUnFollow = userService.findByUsername(username);
        List<UserProfile> followers = userToUnFollow.getFollowers();
        followers.remove(loggedInUser);
        userService.save(userToUnFollow);
        //refers back to the page we came from
        return "redirect :" +request.getHeader("Referer");
    }
}
