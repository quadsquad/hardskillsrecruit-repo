package com.hardskills.demo.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hardskills.demo.entities.HardSkills;

@Repository
public interface HardSkillsRepository extends MongoRepository<HardSkills, String> {

}
