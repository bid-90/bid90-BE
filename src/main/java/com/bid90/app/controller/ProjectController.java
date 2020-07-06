package com.bid90.app.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

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

import com.bid90.app.DTO.AddLinkDTO;
import com.bid90.app.DTO.LinksDTO;
import com.bid90.app.DTO.ProjectDTO;
import com.bid90.app.DTO.UpdateProjectDTO;
import com.bid90.app.exception.CustomException;
import com.bid90.app.model.FileInfo;
import com.bid90.app.model.Image;
import com.bid90.app.model.Link;
import com.bid90.app.model.Project;
import com.bid90.app.service.FileStorageService;
import com.bid90.app.service.ImageService;
import com.bid90.app.service.LinkService;
import com.bid90.app.service.ProjectService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("api/porject")
public class ProjectController {

	@Autowired
	ProjectService projectService;

	@Autowired
	ImageService imageService;

	@Autowired
	FileStorageService fileStorageService;

	@Autowired
	LinkService linkService;

	@Autowired
	ModelMapper modelMapper;

	@ApiOperation(value = "Add or Update project", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/add", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ProjectDTO add(@RequestParam(name = "id", required = false) Long id,
			@RequestParam(name = "title", required = false) String title,
			@RequestParam(name = "description", required = false) String description,
			@RequestParam(name = "image", required = false) MultipartFile image, HttpServletRequest request) {

		String link = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/file/get/image?name=");
		Image newImage = new Image();
		Project addProject = projectService.reade(id);
		if (addProject == null) {
			addProject = new Project();
		}
		if (title != null && !title.isBlank() && !title.isEmpty()) {
			addProject.setTitle(title);
		}
		if (description != null && !description.isBlank() && !description.isEmpty()) {
			addProject.setDescription(description);
		}

		if (image != null && addProject.getImage() != null) {
			if (addProject.getImage() != null) {
				fileStorageService.removeFile(addProject.getImage().getPath());
				addProject.setImage(null);
			}
		}

		if (image != null && addProject.getImage() == null) {

			String fileType = fileStorageService.fileType(image);
			if (fileType.contains("image/png") || fileType.contains("image/jpeg")) {
				FileInfo fileInfo = fileStorageService.storeFile(image, true, "project");
				newImage.setPath(fileInfo.getPath());
				newImage.setName(fileInfo.getName());
				newImage.setSize(fileInfo.getSize());
				imageService.update(newImage);
			} else {
				throw new CustomException("The file is not supported", HttpStatus.BAD_REQUEST);
			}
			addProject.setImage(newImage);
		}
		Project saved = projectService.create(addProject);

		ProjectDTO addedProjectDTO = modelMapper.map(saved, ProjectDTO.class);
		addedProjectDTO.getImage().setLink(link);
		return addedProjectDTO;
	}

	@ApiOperation(value = "Add link project", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/add/link")
	public ProjectDTO addLink(

			@RequestBody AddLinkDTO addLinkDTO, HttpServletRequest request) {
		String link = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/file/get/image?name=");
		if (addLinkDTO == null || addLinkDTO.getLinks() == null) {
			throw new CustomException("Link not added", HttpStatus.BAD_REQUEST);
		}
		Project project = projectService.reade(addLinkDTO.getProjectId());
		if (project == null) {
			throw new CustomException("Bad project id", HttpStatus.BAD_REQUEST);
		}
		for (LinksDTO l : addLinkDTO.getLinks()) {
			Link newLink = new Link();
			newLink.setId(0L);
			newLink.setLink(l.getLink());
			newLink.setType(l.getType());
			project.addLinks(newLink);

		}
		Project saved = projectService.create(project);
		ProjectDTO addedProjectDTO = modelMapper.map(saved, ProjectDTO.class);
		addedProjectDTO.getImage().setLink(link);
		return addedProjectDTO;
	}

	@ApiOperation(value = "Update link project", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/update/link")
	public Map<String, Object> updateLink(

			@RequestParam(name = "id", required = true) Long id,
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "link", required = false) String link) {
		Map<String, Object> response = new HashMap<String, Object>();
		Link linkToUpdate = linkService.reade(id);
	
		if (linkToUpdate == null) {
			throw new CustomException("Bad id", HttpStatus.BAD_REQUEST);
		}
		if (type != null && !type.isBlank() && !type.isEmpty()) {
			linkToUpdate.setType(type);
		}
		if (link != null && !link.isBlank() && !link.isEmpty()) {
			linkToUpdate.setLink(link);
		}
		linkService.update(linkToUpdate);
		response.put("message", "Link updated");
		return response;
	}

	@ApiOperation(value = "Add image to project", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping(value = "/update/image", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ProjectDTO addImage(@RequestParam("projectId") Long projectId, @RequestParam("image") MultipartFile image,
			HttpServletRequest request) {

		String link = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/file/get/image?name=");
		String fileType = fileStorageService.fileType(image);
		Image newImage = new Image();
		Project addProject = projectService.reade(projectId);
		if (addProject == null) {
			throw new CustomException("Bad id", HttpStatus.BAD_REQUEST);
		}

		if (addProject.getImage() != null) {
			fileStorageService.removeFile(addProject.getImage().getPath());
			newImage = addProject.getImage();
		}

		if (fileType.contains("image/png") || fileType.contains("image/jpeg")) {
			FileInfo fileInfo = fileStorageService.storeFile(image, true, "project");
			newImage.setPath(fileInfo.getPath());
			newImage.setName(fileInfo.getName());
			newImage.setSize(fileInfo.getSize());

		} else {
			throw new CustomException("The file is not supported", HttpStatus.BAD_REQUEST);
		}

		addProject.setImage(newImage);
		Project saved = projectService.update(addProject);

		ProjectDTO addedProjectDTO = modelMapper.map(saved, ProjectDTO.class);
		addedProjectDTO.getImage().setLink(link);
		return addedProjectDTO;
	}

	@ApiOperation(value = "Delete project", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping("/delete")
	public HashMap<String, Object> delete(@RequestParam(required = true, name = "id") Long id) {
		HashMap<String, Object> message = new HashMap<String, Object>();
		Project deletedPost = projectService.reade(id);
		if (deletedPost == null) {
			throw new CustomException("Bad id", HttpStatus.BAD_REQUEST);
		}

		if (deletedPost.getImage() != null) {
			fileStorageService.removeFile(deletedPost.getImage().getPath());

		}

		projectService.delete(deletedPost);
		message.put("message", "Post with id=" + id + " deleted");
		return message;
	}

	@ApiOperation(value = "Update project", authorizations = { @Authorization(value = "Bearer") })
	@PostMapping("/update")
	public ProjectDTO update(@RequestBody UpdateProjectDTO updateProjectDTO, HttpServletRequest request) {

		String link = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/file/get/image?name=");
		Project project = projectService.reade(updateProjectDTO.getId());
		if (project == null) {
			throw new CustomException("Bad id", HttpStatus.BAD_REQUEST);
		}
		project.setDescription(updateProjectDTO.getDescription());
		project.setTitle(updateProjectDTO.getTitle());
		Project updatedProject = projectService.update(project);

		ProjectDTO updatedProjectDTO = modelMapper.map(updatedProject, ProjectDTO.class);
		updatedProjectDTO.getImage().setLink(link);
		return updatedProjectDTO;
	}

	@ApiOperation(value = "Delete image from project", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping(value = "/delete/image")
	public Map<String, Object> updateRemoveImage(@RequestParam("projectId") Long projectId,
			@RequestParam(name = "name") String name, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<String, Object>();

		Image imageToRemove = imageService.findByName(name);
		Project project = projectService.reade(projectId);

		if (imageToRemove == null || project == null || !imageToRemove.getName().equals(project.getImage().getName())) {
			throw new CustomException("Bad project id or image name", HttpStatus.BAD_REQUEST);
		}
		fileStorageService.removeFile(imageToRemove.getPath());
		project.removeImage(imageToRemove);
		projectService.update(project);
		response.put("message", "Image deleted");
		return response;
	}

	@ApiOperation(value = "Remove link project", authorizations = { @Authorization(value = "Bearer") })
	@DeleteMapping(value = "/link/{id}")
	public HashMap<String, Object> removeLink(@PathVariable(name = "id") Long id) {

		HashMap<String, Object> message = new HashMap<String, Object>();
		Link link = linkService.reade(id);
		if (link == null) {
			throw new CustomException("No link to remove", HttpStatus.BAD_REQUEST);
		}
		linkService.delete(link);
		message.put("message", "Link deleted");
		return message;
	}

	@ApiOperation(value = "Find project")
	@GetMapping("/find")
	public List<ProjectDTO> find(@RequestParam(required = false, name = "id") Long id, HttpServletRequest request) {
		List<Project> listProject = null;
		String link = request.getRequestURL().toString().replace(request.getRequestURI(), "/api/file/get/image?name=");

		if (id == null) {
			listProject = projectService.findAll();
		} else {
			Project project = projectService.reade(id);
			if (project == null) {
				throw new CustomException("Bad id", HttpStatus.BAD_REQUEST);
			}
			listProject = Arrays.asList(project);
		}

		List<ProjectDTO> listPostDTO = modelMapper.map(listProject, new TypeToken<List<ProjectDTO>>() {
		}.getType());
		listPostDTO.forEach((p) -> {
			if (p.getImage() != null)
				p.getImage().setLink(link);

		});
		return listPostDTO;
	}

}
