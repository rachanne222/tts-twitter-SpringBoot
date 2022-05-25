package com.tts.TechTalentTwitter.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //add instructions omnn how injectable(autowired) objects
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Bean  //an object whose lifecycle creation and desctructions will not be
    // done by the user calling the constructors but will be managed by SpringBoot.

    //The @Bean annotation tells Springboot how to create objects when they are  needed
    //Allowing SB to manage your objects via beans rather than instatiating them yourself
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
