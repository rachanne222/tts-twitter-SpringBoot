package com.tts.TechTalentTwitter.respository;

import java.util.List;

import com.tts.TechTalentTwitter.model.UserProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TechTalentTwitter.model.Tweet;

@Repository
//defines Twet object for repository and type of primary key
//Query methods defined
public interface TweetRepository extends CrudRepository<Tweet, Long> {
    //All tweets by date of creation
    List<Tweet> findAllByOrderByCreatedAtDesc();
    //All tweets created by a user
    List<Tweet> findAllByUserProfileOrderByCreatedAtDesc(UserProfile userProfile);
    //**Word In -   All tweets created any user in list
    List<Tweet> findAllByUserProfileInOrderByCreatedAtDesc(List<UserProfile> userProfiles);
}