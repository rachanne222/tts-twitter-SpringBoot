package com.tts.TechTalentTwitter.service;

import com.tts.TechTalentTwitter.model.Role;
import com.tts.TechTalentTwitter.model.UserProfile;
import com.tts.TechTalentTwitter.respository.RoleRepository;
import com.tts.TechTalentTwitter.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public UserProfile findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserProfile> findAll(){
        return (List<UserProfile>) userRepository.findAll();
    }

    public void save(UserProfile userProfile) {
        userRepository.save(userProfile);
    }
    public UserProfile saveNewUser(UserProfile userProfile) {
        userProfile.setPassword(bCryptPasswordEncoder.encode(userProfile.getPassword()));
        userProfile.setActive(1);
        Role userRole = roleRepository.findByRole("USER");
        userProfile.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(userProfile);
    }
    public UserProfile getLoggedInUser() {
        String loggedInUsername = SecurityContextHolder.
                getContext().getAuthentication().getName();

        return findByUsername(loggedInUsername);
    }
}