package com.tts.TechTalentTwitter.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;



@SuppressWarnings("depreciation")
@Configuration
@EnableWebSecurity  //
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private DataSource dataSource;

    @Value("${spring.queries.users-query}") //get the value from the configuration file
    private String usersQuery;

    @Value("${spring.queries.roles-query}")
    private String rolesQuery;

    @Override  //Overrides a configuration routine defualt config
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        //using a pattern.  Each time if configures it returns a copy of itself.

        auth.
                jdbcAuthentication()  //We want tto authenticate using a Java Databasee Connection backend
                                        //ie authenticaton is by database
                .usersByUsernameQuery(usersQuery) //
                .authoritiesByUsernameQuery(rolesQuery) //gives the roles
                .dataSource(dataSource)   //database we are using- works if you autowire DataSource to
                                        //tells Spring security what database to query
                .passwordEncoder(bCryptPasswordEncoder); //  knows how to check password- select one by Autowire
    }

    @Override  //Webpage security- what parts of the website can be accesses and which parts cannot
    protected void configure(HttpSecurity http) throws Exception {

        http.
                authorizeRequests()
                .antMatchers("/console/**").permitAll()  //Pattern Matchers -allows and
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/custom.js").permitAll()
                .antMatchers("/custom.css").permitAll()
                .antMatchers().hasAuthority("USER").anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable()
                .formLogin()
                .loginPage("/login").failureUrl("/login?error=true")
                .defaultSuccessUrl("/tweets")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling();

        http.headers().frameOptions().disable();
    }

    //What files can be accessed
    //** means anything in the directory
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
}