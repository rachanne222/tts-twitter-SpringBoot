package com.tts.TechTalentTwitter.service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tts.TechTalentTwitter.model.Tag;
import com.tts.TechTalentTwitter.model.TweetDisplay;
import com.tts.TechTalentTwitter.model.UserProfile;
import com.tts.TechTalentTwitter.respository.TagRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tts.TechTalentTwitter.model.Tweet;
import com.tts.TechTalentTwitter.respository.TweetRepository;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<TweetDisplay> findAll() {
        List<Tweet> tweets = tweetRepository.findAllByOrderByCreatedAtDesc();
        return formatTweets(tweets);
    }

    public List<TweetDisplay> findAllByUser(UserProfile userProfile) {
        List<Tweet> tweets = tweetRepository.findAllByUserOrderByCreatedAtDesc(userProfile);
        return formatTweets(tweets);
    }

    public List<TweetDisplay> findAllByUsers(List<UserProfile> userProfiles) {
        List<Tweet> tweets = tweetRepository.findAllByUserInOrderByCreatedAtDesc(userProfiles);
        return formatTweets(tweets);
    }

    public void save(Tweet tweet) {
        tweetRepository.save(tweet);
    }


    //scan tweet for tags before we save it and add them
    private void handleTags(Tweet tweet) {
        List<Tag> tags = new ArrayList<Tag>();
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(tweet.getMessage());
        while (matcher.find()) {
            String phrase = matcher.group().substring(1).toLowerCase();
            Tag tag = tagRepository.findByPhrase(phrase);
            if (tag == null) {
                tag = new Tag();
                tag.setPhrase(phrase);
                tagRepository.save(tag);
            }
            tags.add(tag);
        }
        tweet.setTags(tags);
    }


    private List<TweetDisplay> formatTweets(List<Tweet> tweets) {
        addTagLinks(tweets);
        shortenLinks(tweets);
        //it now returns a list of TweetDisplay objects instead of Tweet objects.
        //Then, we need to add a call to a new method, which takes in a list of Tweets and returns back a list of TweetDisplay.
        List<TweetDisplay> displayTweets = formatTimestamps(tweets);
        return displayTweets;
    }

    // User pattern to scan tweets, loop through tweets, grab message from tweet, match all places where hash tags are used (in a set for not repeating)
    //Adds matches to the found match.

    private void addTagLinks(List<Tweet> tweets) {
        Pattern pattern = Pattern.compile("#\\w+");
        for (Tweet tweet : tweets) {
            String message = tweet.getMessage();
            Matcher matcher = pattern.matcher(message);
            Set<String> tags = new HashSet<String>();
            while (matcher.find()) {
                tags.add(matcher.group());
            }
            //replace the message with a link to the tweets with the hashtag
            for (String tag : tags) {
                message = message.replaceAll(tag,
                        "<a class=\"tag\" href=\"/tweets/" + tag.substring(1).toLowerCase() + "\">" + tag + "</a>");
            }
            tweet.setMessage(message);
        }
    }

//Inside the loop we created, we extract the message from the tweet object, apply the regular expression to find all links, and then
// iterate through the found links one by one.
    private void shortenLinks(List<Tweet> tweets) {
        Pattern pattern = Pattern.compile("https?[^ ]+");
        for (Tweet tweet : tweets) {
            String message = tweet.getMessage();
            Matcher matcher = pattern.matcher(message);
            while (matcher.find()) {
                String link = matcher.group();
                String shortenedLink = link;
                //For each link, we begin by shortening it if necessary.
                if (link.length() > 23) {
                    shortenedLink = link.substring(0, 20) + "...";
                    //message = message.replace(link, "<a class=\"tag\" href=\"" + link + "\"
                    //target=\"_blank\">" + shortenedLink + "</a>");
                    message = message.replace(link,
                            "<a class=\"tag\" href=\"" + link + "\" target=\"_blank\">" + shortenedLink + "</a>");
                }
                //Finally, we set the tweet message to the modified message, which now includes links:
                tweet.setMessage(message);
            }

        }

    }
    public List<TweetDisplay>findAllWithTag(String tag){
        List<Tweet> tweets = tweetRepository.findByTags_PhraseOrderByCreatedAtDesc(tag);
        return formatTweets(tweets);
    }

    private List<TweetDisplay> formatTimestamps(List<Tweet> tweets) {
        List<TweetDisplay> response = new ArrayList<>();
        PrettyTime prettyTime = new PrettyTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("M/d/yy");
        Date now = new Date();
        for (Tweet tweet : tweets) {
            TweetDisplay tweetDisplay = new TweetDisplay();
            tweetDisplay.setUser(tweet.getUser());
            tweetDisplay.setMessage(tweet.getMessage());
            tweetDisplay.setTags(tweet.getTags());
            long diffInMillies = Math.abs(now.getTime() - tweet.getCreatedAt().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff > 3) {
                tweetDisplay.setDate(simpleDate.format(tweet.getCreatedAt()));
            } else {
                tweetDisplay.setDate(prettyTime.format(tweet.getCreatedAt()));
            }
            response.add(tweetDisplay);
        }
        return response;
    }
}