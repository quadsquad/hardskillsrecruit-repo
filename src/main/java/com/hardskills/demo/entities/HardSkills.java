package com.hardskills.demo.entities;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="hardskills")
public class HardSkills {
	
	@Id
	private String id;
	@Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)

	private String hardskills_name;
	private int hardskills_note;
	private String hardskills_niveau;
	
	private String userauth;
	
	
	

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHardskills_name() {
		return hardskills_name;
	}

	public void setHardskills_name(String hardskills_name) {
		this.hardskills_name = hardskills_name;
	}

	public int getHardskills_note() {
		return hardskills_note;
	}

	public void setHardskills_note(int hardskills_note) {
		this.hardskills_note = hardskills_note;
	}
	public String getHardskills_niveau() {
		return hardskills_niveau;
	}

	public void setHardskills_niveau(String hardskills_niveau) {
		this.hardskills_niveau = hardskills_niveau;
	}
	public String getUserauth() {
		return userauth;
	}

	public void setUserauth(String userauth) {
		this.userauth = userauth;
	}

	public HardSkills() {
	}
	

}
