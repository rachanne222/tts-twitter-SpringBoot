package com.tts.TechTalentTwitter.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserProfile {
    //    primary key
    @Id
    //autogenerate primarykey
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Name column for privary key
    @Column(name = "user_id")
    private Long id;

    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide an email")
    private String email;

    @NotEmpty(message = "Please provide a username")
    @Length(min = 3, message = "Your username must have at least 3 characters")
    @Length(max = 15, message = "Your username cannot have more than 15 characters")
    //^Does not allow whitespace
    @Pattern(regexp = "[^\\s]+", message = "Your username cannot contain spaces")
    private String username;

    @Length(min = 5, message = "Your password must have at least 5 characters")
    @NotEmpty(message = "Please provide a password")
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @NotEmpty(message = "Please provide your first name")
    private String firstName;

    @NotEmpty(message = "Please provide your last name")
    private String lastName;

    private int active;

    @CreationTimestamp
    private Date createdAt;
    //creates many to many relationship between user and roles (table)
    //Cascade- if user is deleted- roles associated with user are deleted
    @ManyToMany(cascade = CascadeType.ALL)

    //Name the table user_role
    //column names = user_id and role_id
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    //We are going to create a manay to many mapping of user profile to user profiles
    //A user has many followers and a user has many people they are following
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_follower", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<UserProfile> followers;

    @ManyToMany(mappedBy = "followers")
    private List<UserProfile> following;






}



