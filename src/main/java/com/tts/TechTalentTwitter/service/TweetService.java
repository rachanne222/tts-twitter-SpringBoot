package com.tts.TechTalentTwitter.service;

import java.util.List;

import com.tts.TechTalentTwitter.model.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.respository.TweetRepository;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    public List<Tweet> findAll() {
        List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
        return tweets;
    }

    public List<Tweet> findAllByUser(UserProfile userProfile) {
        List<Tweet> tweets = tweetRepository.findAllByUserProfileOrderByCreatedAtDesc(userProfile);
        return tweets;
    }

    public List<Tweet> findAllByUsers(List<UserProfile> userProfiles){
        List<Tweet> tweets = tweetRepository.findAllByUserProfileInOrderByCreatedAtDesc(userProfiles);
        return tweets;
    }

    public void save(Tweet tweet) {
        tweetRepository.save(tweet);
    }
}
