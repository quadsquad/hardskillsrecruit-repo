package com.hardskills.demo.services;

import org.hibernate.exception.ConstraintViolationException;

import com.hardskills.demo.entities.HardSkills;

public interface HardSkillsService {

	public void createHardSkills(HardSkills hard) throws ConstraintViolationException;

}
