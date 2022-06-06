package com.tts.TechTalentTwitter.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.tts.TechTalentTwitter.model.Tweet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//setters and getters auto in lombok
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long id;

    //phrase for the hashtag
    private String phrase;

    //map by tags to tweets with these tags
    ///mappedBy  indicates an inverse relationship
    @ManyToMany(mappedBy = "tags")
    private List<Tweet> tweets;
}