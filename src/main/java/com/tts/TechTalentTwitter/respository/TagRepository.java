package com.tts.TechTalentTwitter.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TechTalentTwitter.model.Tag;


@Repository
public interface TagRepository extends CrudRepository<Tag, Long>  {

    Tag findByPhrase(String phrase);

}

