package com.tts.TechTalentTwitter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tweet_id")
    private Long id;

    //denotes a foreign key
    //FetchType.LAZY- mean it loads late - not that important
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    //Maintains referential integrity to cascade to all related to tables
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserProfile user;



    @NotEmpty(message = "Tweet cannot be empty")
    @Length(max = 280, message = "Tweet cannot have more than 280 characters")
    private String message;

    @CreationTimestamp
    private Date createdAt;

    //add list of tags for each tweet
    //If a tweet is deleted from the hashtag it is deleted from the list of tweets for the hashtag
    //"fetch = FetchType.LAZY" is telling JPA when to fill out the "List<Tag> tags in the Tweet.
    //It controls what happens when data is deleted from say Tweets or Tags
    //does our JoinTable get updated auotomatically? or does it keep the information...
    @ManyToMany(fetch = FetchType.LAZY, cascade =
            { CascadeType.PERSIST, CascadeType.MERGE })

    //"tweet_tag" table joins tweet table on tweet_id
    //@JoinTable is just giving names to the table and the columns so they aren't random
    @JoinTable(name = "tweet_tag",
            joinColumns = @JoinColumn(name = "tweet_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    //Tells SpringBoot we are storing tags
    //and it would have that table store the primary key of Tag (the item that is being stored in the collection)
    private List<Tag> tags;


}
