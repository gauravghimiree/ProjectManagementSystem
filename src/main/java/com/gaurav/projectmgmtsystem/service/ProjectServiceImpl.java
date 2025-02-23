package com.gaurav.projectmgmtsystem.service;

import com.gaurav.projectmgmtsystem.model.Chat;
import com.gaurav.projectmgmtsystem.model.Project;
import com.gaurav.projectmgmtsystem.model.User;
import com.gaurav.projectmgmtsystem.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject = new Project();
        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategory(project.getCategory());
        createdProject.setDescription(project.getDescription());

        // Set start date automatically to today's date if not provided
        createdProject.setStartDate(project.getStartDate() != null ? project.getStartDate() : LocalDate.now());
        createdProject.setEndDate(project.getEndDate()); // Optional, may be null
        createdProject.setStatus(project.getStatus() != null ? project.getStatus() : "pending");

        // Ensure the owner is added to the project team
        createdProject.getTeam().add(user);

        // Save the project
        Project savedProject = projectRepository.save(createdProject);

        // Create a chat and associate it with the saved project
        Chat chat = new Chat();
        chat.setProject(savedProject);
        Chat projectChat = chatService.createChat(chat);

        // Update the project with the chat reference
        savedProject.setChat(projectChat);
        return projectRepository.save(savedProject);
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tags) throws Exception {
        List<Project> projects = projectRepository.findByTeamContainingOrOwner(user, user);

        if (category != null) {
            projects = projects.stream()
                    .filter(project -> category.equals(project.getCategory()))
                    .collect(Collectors.toList());
        }

        if (tags != null) {
            projects = projects.stream()
                    .filter(project -> project.getTags().contains(tags))
                    .collect(Collectors.toList());
        }
        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("Project not found"));
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {
        getProjectById(projectId); // Ensures project exists
        projectRepository.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project updatedProject, Long id) throws Exception {
        Project project = getProjectById(id);

        if (updatedProject.getName() != null) {
            project.setName(updatedProject.getName());
        }

        if (updatedProject.getDescription() != null) {
            project.setDescription(updatedProject.getDescription());
        }

        if (updatedProject.getTags() != null) {
            project.setTags(updatedProject.getTags());
        }

        if (updatedProject.getCategory() != null) {
            project.setCategory(updatedProject.getCategory());
        }

        if (updatedProject.getStartDate() != null) {
            project.setStartDate(updatedProject.getStartDate());
        }

        if (updatedProject.getEndDate() != null) {
            project.setEndDate(updatedProject.getEndDate());
        }

        if (updatedProject.getStatus() != null) {
            project.setStatus(updatedProject.getStatus());
        }

        return projectRepository.save(project);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (!project.getTeam().contains(user)) {
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }

        projectRepository.save(project);
    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project = getProjectById(projectId);
        User user = userService.findUserById(userId);

        if (project.getTeam().contains(user)) {
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }

        projectRepository.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {
        Project project = getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {
        return projectRepository.findByTeamContainingAndTeamContains(keyword, user);
    }
}
