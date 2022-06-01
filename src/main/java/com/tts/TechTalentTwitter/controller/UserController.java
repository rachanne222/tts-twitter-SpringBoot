package com.tts.TechTalentTwitter.controller;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.model.TweetDisplay;
import com.tts.TechTalentTwitter.model.UserProfile;
import com.tts.TechTalentTwitter.service.TweetService;
import com.tts.TechTalentTwitter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TweetService tweetService;

    @GetMapping(value = "/users/{username}")
    public String getUser(@PathVariable(value="username") String username, Model model) {
        //all tweets created by a user
        UserProfile loggedInUser= userService.getLoggedInUser();
        UserProfile user = userService.findByUsername(username);
        List<TweetDisplay> tweets = tweetService.findAllByUser(user);

        List<UserProfile> following= loggedInUser.getFollowing();
        boolean isFollowing=false;
        for (UserProfile followedUser : following) {
            if (followedUser.getUsername().equals(username)) {
                isFollowing = true;
            }
        }
        boolean isSelfPage = loggedInUser.getUsername().equals(username);
        model.addAttribute("isSelfPage", isSelfPage);
        model.addAttribute("following", isFollowing);
        model.addAttribute("tweetList", tweets);
        model.addAttribute("user", user);
        return "user";
    }


    @GetMapping(value = "/users")
    public String getUsers(Model model) {
        UserProfile loggedInUser = userService.getLoggedInUser();
        List<UserProfile> usersFollowing = loggedInUser.getFollowing();
        List<UserProfile> users = userService.findAll();
        SetFollowingStatus(users, usersFollowing, model);
        model.addAttribute("users", users);
        SetTweetCounts(users, model);
        return "users";
    }


    private void SetTweetCounts(List<UserProfile> users, Model model) {
        HashMap<String,Integer> tweetCounts = new HashMap<>();

        for (UserProfile user : users) {
            List<TweetDisplay> tweets = tweetService.findAllByUser(user);
            tweetCounts.put(user.getUsername(), tweets.size());
        }
        model.addAttribute("tweetCounts", tweetCounts);

    }

    private void SetFollowingStatus(List<UserProfile> users, List<UserProfile> usersFollowing, Model model) {
        HashMap<String, Boolean> followingStatus = new HashMap<>();
        String username = userService.getLoggedInUser().getUsername();
    }












}