package com.hardskills.demo.services;

import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hardskills.demo.entities.HardSkills;
import com.hardskills.demo.repositories.HardSkillsRepository;

@Service
public class HardSkillsServiceImpl implements HardSkillsService {

	@Autowired
	private HardSkillsRepository hardrepo;
	
	public void createHardSkills(HardSkills hard) throws ConstraintViolationException {		
		Optional<HardSkills> hardOptional = hardrepo.findById(hard.getHardskills_name());
		//String connected =  SecurityContextHolder.getContext().getAuthentication().getName();
		if (hardOptional.isPresent()){
			System.err.println("IS PRESENT");			
		}else{
			//hard.setUserauth(connected);
			hardrepo.save(hard);
		}
	}

}
