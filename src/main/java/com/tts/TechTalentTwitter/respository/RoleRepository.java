package com.tts.TechTalentTwitter.respository;

//Details about how Springboot will create our repository and how it will be implemented

import com.tts.TechTalentTwitter.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Inherit from repository subclasses.
// Never inherit from repository directly.
//CrudRepository takes two Generic Types,
//    1.  The object stored in the repository
//    2. The type of object that is the primary key

@Repository //Marks as a repository- not a critical as inheriting from repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    //Inheriting from CRUD repositorty gives us lots o methods
    //  .save, .findbyId, ... etc

    //create method - name is parsed by SpringBoot and must conform to a pattern.
    //Springboot figures out how to turn it into a query.

    Role findByRole(String role);


}
