package com.tts.TechTalentTwitter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



//
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//Stored in database
@Entity
public class Tag {
    //primary key
    @Id
    //autogenerated primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    //name of id column used in queries
    @Column(name = "tag_id")
    private Long id;
    private String tweet_tag;}