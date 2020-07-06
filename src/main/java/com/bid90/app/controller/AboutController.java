package com.bid90.app.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bid90.app.DTO.ContactDTO;
import com.bid90.app.DTO.EducationDTO;
import com.bid90.app.DTO.ImageDTO;
import com.bid90.app.DTO.AboutDTO;
import com.bid90.app.DTO.SkillDTO;
import com.bid90.app.DTO.WorkDTO;
import com.bid90.app.exception.CustomException;
import com.bid90.app.model.About;
import com.bid90.app.model.Contact;
import com.bid90.app.model.Education;
import com.bid90.app.model.FileInfo;
import com.bid90.app.model.Image;
import com.bid90.app.model.Skill;
import com.bid90.app.model.Work;
import com.bid90.app.service.AboutService;
import com.bid90.app.service.ContactService;
import com.bid90.app.service.EducationService;
import com.bid90.app.service.FileStorageService;
import com.bid90.app.service.ImageService;
import com.bid90.app.service.SkillService;
import com.bid90.app.service.WorkService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("api/personal-info")
public class AboutController {

	@Autowired
	AboutService aboutService;

	@Autowired
	FileStorageService fileStorageService;

	@Autowired
	ImageService imageService;

	@Autowired
	ContactService contactService;

	@Autowired
	EducationService educationService;

	@Autowired
	SkillService skillService;

	@Autowired
	WorkService workService;

	@Autowired
	ModelMapper modelMapper;



	@ApiOperation(value = "add or update profile", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/add/profile", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public AboutDTO setImage(
			@RequestParam(name = "file" , required = false) MultipartFile file,
			@RequestParam(name = "name" , required = false) String name,
			@RequestParam(name = "dateOfBirth" , required = false) String dateOfBirth,
			@RequestParam(name = "ocupation" , required = false) String ocupation,
			HttpServletRequest request) {
		String link = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/file/get/image?name=");

		About about = aboutService.reade(1L);
		if (about == null) {
			about = new About();
	
		}
		
		if(name != null) {
			about.setName(name);
		}
		if(dateOfBirth != null) {
			about.setDateOfBirth(LocalDate.parse(dateOfBirth));
		}
		if(ocupation != null) {
			about.setOcupation(ocupation);
		}
		
		if(file != null && about.getImage() != null) {
			
				fileStorageService.removeFile(about.getImage().getPath());
				Image rmImage = about.getImage();
				imageService.delete(rmImage);
				about.setImage(null);
				System.out.println("asfasdfsdfsdf");
		}
		if(file != null && about.getImage() == null) {
			String fileType = fileStorageService.fileType(file);
			Image newImage = new Image();
			
			if (fileType.contains("image/png") || fileType.contains("image/jpeg")) {
				FileInfo fileInfo = fileStorageService.storeFile(file, true,"profile");
				newImage.setPath(fileInfo.getPath());
				newImage.setName(fileInfo.getName());
				newImage.setSize(fileInfo.getSize());
				about.setImage(newImage);
			} else {
				throw new CustomException("The file is not supported", HttpStatus.BAD_REQUEST);
			}
			
		}
		
		
		About aboutSaved = aboutService.create(about);
		AboutDTO updatedabout = modelMapper.map(aboutSaved, AboutDTO.class);
		updatedabout.getImage().setLink(link);
		return updatedabout;
	}

	@ApiOperation(value = "add or update contact", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/add/contact")
	public Contact setContact(@RequestBody ContactDTO contactDTO) {
		Contact contact = contactService.findContactById(contactDTO.getId());
		if (contact == null) {
			contact = new Contact();
		}
		contact.setType(contactDTO.getType());
		contact.setValue(contactDTO.getValue());
		return contactService.create(contact);
	}

	@ApiOperation(value = "add or update skill", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/add/skill")
	public Skill setSkill(@RequestBody SkillDTO skillDTO) {
		Skill skill = skillService.findSkillById(skillDTO.getId());
		if (skill == null) {
			skill = new Skill();
		}
		skill.setName(skillDTO.getName());
		skill.setValue(skillDTO.getValue());

		return skillService.create(skill);
	}

	@ApiOperation(value = "add or update education", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/add/education")
	public Education setEducation(@RequestBody EducationDTO educationDTO) {
		Education education = educationService.findEducationById(educationDTO.getId());
		if (education == null) {
			education = new Education();
		}
		education.setCity(educationDTO.getCity());
		education.setDate(educationDTO.getDate());
		education.setCountry(educationDTO.getCountry());
		education.setOrganisation(educationDTO.getOrganisation());
		education.setQualification(educationDTO.getQualification());
	
		return educationService.create(education);
	}

	@ApiOperation(value = "add or update work", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/add/work")
	public Work setWork(@RequestBody WorkDTO workDTO) {
		Work work = workService.findWorkById(workDTO.getId());
		if (work == null) {
			work = new Work();
		}
		work.setCity(workDTO.getCity());
		work.setDate(workDTO.getDate());
		work.setCountry(workDTO.getCountry());
		work.setEmployer(workDTO.getEmployer());
		work.setQualification(workDTO.getQualification());
		return workService.create(work);
	}

	

	@ApiOperation(value = "Delete image profileinfo", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping(value = "/delete/image")
	public HashMap<String, Object> removeImage() {
		HashMap<String, Object> message = new HashMap<String, Object>();

		Image imageToRemove = imageService.findByName("myPhoto");


		if (imageToRemove == null) {
			throw new CustomException("No image to remove", HttpStatus.BAD_REQUEST);
		}
		
		if(fileStorageService.removeFile(imageToRemove.getPath())) {
			imageToRemove.setName(null);
			imageToRemove.setSize(null);
			imageService.create(imageToRemove);
		};
		
		
		message.put("message", "Image removed");
		return message;
	}

	@ApiOperation(value = "Delete contact", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping(value = "/delete/contact/{id}")
	public HashMap<String, Object> removeContact(@PathVariable(name = "id") Long id) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		Contact contact = contactService.findContactById(id);
		if (contact == null ) {
			throw new CustomException("No contact to remove", HttpStatus.BAD_REQUEST);
		}
		contactService.delete(contact);
		message.put("message", "Contact deleted");
		return message;
	}

	@ApiOperation(value = "Delete education", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping(value = "/delete/education/{id}")
	public HashMap<String, Object> removeEducation(@PathVariable(name = "id") Long id) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		Education education = educationService.findEducationById(id);
		if (education == null ) {
			throw new CustomException("No education to remove", HttpStatus.BAD_REQUEST);
		}
		educationService.delete(education);
		message.put("message", "Education deleted");
		return message;
	}

	@ApiOperation(value = "Delete skill", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping(value = "/delete/skill/{id}")
	public HashMap<String, Object> removeSkill(@PathVariable(name = "id") Long id) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		Skill skill = skillService.findSkillById(id);
		if (skill == null ) {
			throw new CustomException("No skill to remove", HttpStatus.BAD_REQUEST);
		}
		skillService.delete(skill);
		message.put("message", "Skill deleted");
		return message;
	}

	@ApiOperation(value = "Delete work", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping(value = "/delete/work/{id}")
	public HashMap<String, Object> removeWork(@PathVariable(name = "id") Long id) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		Work work = workService.findWorkById(id);
		if (work == null ) {
			throw new CustomException("No work to remove", HttpStatus.BAD_REQUEST);
		}
		workService.delete(work);
		message.put("message", "Work deleted");
		return message;
	}

	@ApiOperation(value = "get personal info", authorizations = { @Authorization(value = "Bearer") })
	@GetMapping(value = "/get")
	public HashMap<String, Object> getInfo(HttpServletRequest request) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		String link = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/file/get/image?name=");
		About about = aboutService.reade(1L);
		if (about == null) {
			about = new About();
		}

		AboutDTO info = modelMapper.map(about, AboutDTO.class);
		if (info.getImage() == null) {
			info.setImage(new ImageDTO());

		}
		info.getImage().setLink(link);
		List<Education> educations = educationService.findAll();
		List<Skill> skills = skillService.findAll();
		List<Work> works = workService.findAll();
		List<Contact> contacts = contactService.findAll();
		message.put("name", info.getName());
		message.put("image", info.getImage());
		message.put("dateOfBirth", info.getdateOfBirth());
		message.put("ocupation", info.getOcupation());
		
		message.put("educations", educations);
		message.put("skills", skills);
		message.put("works", works);
		message.put("contacts", contacts);
		return message;
	}
	
	@ApiOperation(value = "get skill info", authorizations = { @Authorization(value = "Bearer") })
	@GetMapping(value = "/get/skill")
	public Skill getSkillInfo(@RequestParam(name = "id") Long id) {

		Skill skill = skillService.findSkillById(id);
		if (skill == null) {
			throw new CustomException("Id not exist", HttpStatus.BAD_REQUEST);
		}
		return skill;
	}
	
	@ApiOperation(value = "get education info", authorizations = { @Authorization(value = "Bearer") })
	@GetMapping(value = "/get/education")
	public Education getEducationInfo(@RequestParam(name = "id") Long id) {

		Education education = educationService.findEducationById(id);
		if (education == null) {
			throw new CustomException("Id not exist", HttpStatus.BAD_REQUEST);
		}
		return education;
	}
	
	@ApiOperation(value = "get contact info", authorizations = { @Authorization(value = "Bearer") })
	@GetMapping(value = "/get/contact")
	public Contact getContactInfo(@RequestParam(name = "id") Long id) {

		Contact contact = contactService.findContactById(id);
		if (contact == null) {
			throw new CustomException("Id not exist", HttpStatus.BAD_REQUEST);
		}
		return contact;
	}
	
	@ApiOperation(value = "get work info", authorizations = { @Authorization(value = "Bearer") })
	@GetMapping(value = "/get/work")
	public Work getWorkInfo(@RequestParam(name = "id") Long id) {

		Work work = workService.findWorkById(id);
		if (work == null) {
			throw new CustomException("Id not exist", HttpStatus.BAD_REQUEST);
		}
		return work;
	}
}
