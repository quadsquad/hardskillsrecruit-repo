package com.hardskills.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hardskills.demo.entities.HardSkills;
import com.hardskills.demo.repositories.HardSkillsRepository;
import com.hardskills.demo.services.HardSkillsService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/hardskills")
public class HardSkillsController {
	
	@Autowired
	private HardSkillsRepository hardrepo;
	
	@Autowired
	private HardSkillsService hardservice;
	
	@Autowired
	private RestTemplate rest;
	
	 @Autowired
	 @LoadBalanced
	 private RestTemplate loadBalanced;
	
	
	@GetMapping("/findall")
	public ResponseEntity<?> getAllHardSkills(){
		List<HardSkills> hardallskills =hardrepo.findAll();
		if(!hardallskills.isEmpty()){
			return new ResponseEntity<>(hardallskills, HttpStatus.OK);
			
			}
		else 
		{
				return new ResponseEntity<String>("No hard skills Available",HttpStatus.NOT_FOUND);
		}
	}
	
	
	public static String getBearerTokenHeader() {
	    return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
	  }
	
	@GetMapping(value = "/getuserconnect")
	public ResponseEntity<?> getConnectedUser() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", getBearerTokenHeader()); 
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        ResponseEntity<String> response = rest.exchange("http://authrecruit/content", HttpMethod.GET, entity, String.class);


		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createHard(@RequestBody HardSkills hard){
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", getBearerTokenHeader()); 
	        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
	        ResponseEntity<String> response = rest.exchange("http://authrecruit/content", HttpMethod.GET, entity, String.class);

			hard.setUserauth(response.getBody());
			hardservice.createHardSkills(hard);
			return new ResponseEntity<HardSkills>(hard, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);

		}
	}
	
	@PutMapping("/put/{id}")
	public ResponseEntity<?> updateByID(@PathVariable("id") String id, @RequestBody HardSkills hard){
		Optional<HardSkills> hardskillsOptional = hardrepo.findById(id);
		if (hardskillsOptional.isPresent())
		{
			HardSkills hardToSave = hardskillsOptional.get();
			hardToSave.setHardskills_name(hard.getHardskills_name() != null? hard.getHardskills_name(): hardToSave.getHardskills_name());
			hardToSave.setHardskills_note(hard.getHardskills_note() != 0 ? hard.getHardskills_note(): hardToSave.getHardskills_note());
			hardToSave.setHardskills_niveau(hard.getHardskills_niveau() != null? hard.getHardskills_niveau(): hardToSave.getHardskills_niveau());
			hardrepo.save(hardToSave);
			return new ResponseEntity<HardSkills>(hardskillsOptional.get(), HttpStatus.OK);

		}else{
			return new ResponseEntity<String>("Soft Skills not found", HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteHardSkills(@PathVariable("id") String id){
		try{
			hardrepo.deleteById(id);
			return new ResponseEntity<String>("Successfully deleted", HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
