package com.tts.TechTalentTwitter.respository;
import com.tts.TechTalentTwitter.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
//Details about how Springboot will create our repository and how it will be implemented

//Inherit from repository subclasses.
// Never inherit from repository directly.
//CrudRepository takes two Generic Types,
//    1.  The object stored in the repository
//    2. The type of object that is the primary key

@Repository  //Marks as a repository- not a critical as inheriting from repository
public interface UserRepository extends CrudRepository<User, Long> {

    //Inheriting from CRUD repositorty gives us lots o methods
    //  .save, .findbyId, ... etc

    //create method - name is parsed by SpringBoot and must conform to a pattern.
    //Springboot figures out how to turn it into a query.

    User findByUsername(String username);

    @Override
    List<User> findAll();
}


